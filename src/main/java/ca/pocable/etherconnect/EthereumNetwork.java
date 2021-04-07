package ca.pocable.etherconnect;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.response.NoOpProcessor;

import ca.pocable.etherconnect.etherscan.BlockEvent;
import ca.pocable.etherconnect.etherscan.EtherEventResult;
import ca.pocable.etherconnect.etherscan.EtherResponse;
import ca.pocable.etherconnect.etherscan.EtherScan;
import ca.pocable.etherconnect.utils.IDConverter;
import net.md_5.bungee.api.ChatColor;

/**
 * Singleton of the EthereumNetwork.
 * Holds the credentials and communicates directly with MinecraftWorld.java
 * @see MinecraftWorld
 * @author Thomas
 * @version 1.0
 */
public class EthereumNetwork {
	
	private static EthereumNetwork instance;
	
	
	private MinecraftWorld contract;
	private Web3j web3j;
	private Web3jService service;
	private static String CONTRACT_ADDRESS;
	private static String END_POINT;
	private static String ESAPI_KEY;
	private static String BLOCK_UPDATE_TOPIC;
	private static String INFURA_API;
	private static String INFURA_LINK;
	private EtherScan es;
	private static int lastUpdatedBlock = 379224;
	
	@SuppressWarnings("deprecation")
	private EthereumNetwork() {
		try {
			loadConfig(EtherConnect.ec.getConfig());
			service = new HttpService(INFURA_LINK + INFURA_API);	
			web3j = Web3j.build(service);
			Credentials credentials = WalletUtils.loadCredentials("thisisasecurewalletpassword", "wallet");
			NoOpProcessor processor = new NoOpProcessor(web3j);
			TransactionManager txManager = new FastRawTransactionManager(web3j, credentials, processor);
			contract = MinecraftWorld.load(CONTRACT_ADDRESS, web3j, txManager, DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT);
			es = new EtherScan(END_POINT, ESAPI_KEY, CONTRACT_ADDRESS);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Load the API keys and such from the config.
	 * @param fc The config file.
	 */
	public static void loadConfig(FileConfiguration fc) {
		CONTRACT_ADDRESS = fc.getString("ContractAddress");
		END_POINT = fc.getString("EtherScanEndpoint");
		ESAPI_KEY = fc.getString("EtherScanAPIKey");
		INFURA_LINK = fc.getString("InfuraEndpoint");
		INFURA_API = fc.getString("InfuraAPIKey");
		BLOCK_UPDATE_TOPIC = fc.getString("BlockUpdateTopic");
		lastUpdatedBlock = fc.getInt("LastBlock");
	}
	
	/**
	 * Gets the static instance of the EthereumNetwork.
	 * @return The Ethereum Network.
	 */
	public static EthereumNetwork getInstance() {
		if(instance == null) {
			instance = new EthereumNetwork();
		}
		return instance;
	}
	
	/**
	 * Deallocate the service and shutdown web3j. Wipes the instance also.
	 */
	public void uninitializeInstance() {
		try {
			service.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		web3j.shutdown();
		instance = null;
	}
	
	/**
	 * Get the Ethereum Network smart contract.
	 * @return The MinecraftWorld contract.
	 */
	public MinecraftWorld getContract() {
		return contract;
	}
	
	/**
	 * Convert X Y Z coordinates into the format used in the contract.
	 * @param x The X coordinate.
	 * @param y The Y coordinate.
	 * @param z The Z coordinate.
	 * @return The BigInteger value of the format.
	 */
	public static BigInteger createCoords(int x, int y, int z) {
		long bitshift = ((x << 20) + (y << 12) + z);
		return BigInteger.valueOf(bitshift);
	}
	
	/**
	 * Convert a block location identifier into a Coordinate
	 * @param loc The location id
	 * @return Coordinates of the location.
	 */
	public static Location locToCoords(long loc) {
		BigInteger l = BigInteger.valueOf(loc);
		BigInteger xzAnd = BigInteger.valueOf(4095);
		BigInteger yAnd = BigInteger.valueOf(255);
		int z = l.and(xzAnd).intValue();
		l = l.shiftRight(12);
		int y = l.and(yAnd).intValue();
		l = l.shiftRight(8);
		int x = l.and(xzAnd).intValue();
		return new Location(Bukkit.getWorld("world"), x, y, z);
	}
	
	/**
	 * Updates the block at the position to whats on the Ethereum Network
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param z The z coordinate.
	 * @param w The world.
	 */
	public void updateBlock(int x, int y, int z, World w) {
		try {
			BigInteger val = contract.blockData(createCoords(x, y, z)).send();
			w.getBlockAt(x, y, z).setType(IDConverter.convertIDToBlock(val.intValue()));
		} catch (Exception e) {
			// Someone tried to place an item as a block. Could disable
			// Them in XMaterial but takes too long.
		}
	}
	
	/**
	 * Update the block at the given position
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param z The z coordinate.
	 * @param blockID The BlockID.
	 */
	public void updateBlock(int x, int y, int z, int blockID) {
		try {
			Bukkit.getWorld("world").getBlockAt(x, y, z).setType(IDConverter.convertIDToBlock(blockID));
		} catch (Exception e) {
			// Someone tried to place an item as a block. Could disable
			// Them in XMaterial but takes too long.
		}
	}
	
	
	/**
	 * Update the block at the given position
	 * @param loc The location of the block.
	 * @param blockID The BlockID.
	 */
	public void updateBlock(Location l, int blockID) {
		try {
			Bukkit.getWorld("world").getBlockAt(l).setType(IDConverter.convertIDToBlock(blockID));
		} catch (Exception e) {
			// Someone tried to place an item as a block. Could disable
			// Them in XMaterial but takes too long.
		}
	}
	
	/**
	 * Update the block at the given position
	 * @param l The location of the block.
	 * @param text The sign text.
	 */
	public void placeSign(Location l, String text) {
		Block b = Bukkit.getWorld("world").getBlockAt(l);
		b.setType(Material.OAK_SIGN);
		Sign sign = (Sign) b.getState();
		String[] lines = getSignLines(text);
		for(int i = 0; i < 4; i++) {
			sign.setLine(i, lines[i]);
		}
		sign.update();
		Bukkit.broadcastMessage(EtherConnect.prefix + ChatColor.AQUA + "Updated sign at position " + l.toString());
	}
	
	
	/**
	 * Converts a line of text to fit into a minecraft sign.
	 * @param text The string line of text.
	 * @return The array of 4 lines of the text.
	 */
	private String[] getSignLines(String text) {
		// String padding.
		text = text.replaceAll("\n", "");
		if(text.length() < 60) {
			text = text + "                                                            ";
		}
		String line1 = text.substring(0, 15);
		String line2 = text.substring(15, 30);
		String line3 = text.substring(30, 45);
		String line4 = text.substring(45, 60);
		String[] out = { line1.trim(), line2.trim(), line3.trim(), line4.trim() };
		return out;
	}
	
	/**
	 * Get all block updates with their data.
	 * @return ArrayList<BlockEvent> of each of the data sections.
	 */
	public ArrayList<BlockEvent> checkAllBlockUpdates() {
		return checkBlockUpdates("300000", "latest");
	}
	
	/**
	 * Check for Block Updates within the window (lastUpdatedblock to latest).
	 * @return An ArrayList of BlockEvents.
	 */
	public ArrayList<BlockEvent> checkRecentBlockUpdates(){
		ArrayList<BlockEvent> out = new ArrayList<BlockEvent>();
		try {
			String currentBlock = web3j.ethBlockNumber().send().getResult();
			if(!currentBlock.equals(lastUpdatedBlock + "")) {
				EtherResponse er = es.getEventResponse(lastUpdatedBlock + "", "latest", BLOCK_UPDATE_TOPIC);
				if(er.isValid()) {
					lastUpdatedBlock = getEtherBlock(currentBlock);
					for(EtherEventResult eer : er.getResult()) {
						out.add(convertDataToBlockEvent(eer.getData()));
					}
					return out;
				}
				return out;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}
	
	/**
	 * Get the current etherblock in a format which this class can use.
	 * @param block The block in string form (stored on here)
	 * @return The block int value.
	 */
	private int getEtherBlock(String block) {
		block = block.replace("0x", "");
		BigInteger bt = new BigInteger(block, 16);
		System.out.println(bt.intValue());
		return bt.intValue();
	}
	
	/**
	 * Check for block updates within a certain time.
	 * @param fromBlock The first block to begin the search.
	 * @param toBlock The newest block to search to.
	 * @return ArrayList<BlockEvent>
	 */
	private ArrayList<BlockEvent> checkBlockUpdates(String fromBlock, String toBlock){
		ArrayList<BlockEvent> out = new ArrayList<BlockEvent>();
		EtherResponse er = es.getEventResponse(fromBlock, toBlock, BLOCK_UPDATE_TOPIC);
		if(er.isValid()) {
			for(EtherEventResult eer : er.getResult()) {
				out.add(convertDataToBlockEvent(eer.getData()));
			}
			return out;
		}
		return out;
	}
	
	/**
	 * Convert a data chunk into a block event object.
	 * @param input The String representation of the data.
	 * @return The BlockEvent of its location and update.
	 */
	public BlockEvent convertDataToBlockEvent(String input) {
		String dataChunk = input.replace("0x", "");
		BigInteger bi = new BigInteger(dataChunk, 16);
		int blockID = bi.intValue();
		bi = bi.shiftRight(256);
		long blockNum = bi.longValue();
		bi = bi.shiftRight(256);
		String name = "0x" + bi.toString(16);
		return new BlockEvent(name, blockNum, blockID);
	}
	
	/**
	 * Force an update on the entire server for each block event placed.
	 * This is VERY COSTLY! It is not ran asynchronously and will cause lag.
	 * @deprecated Use runAllEtherUpdate if possible.
	 */
	public void forceRunAllEtherUpdate() {
		ArrayList<BlockEvent> bea = checkAllBlockUpdates();
		for(BlockEvent be : bea) {
			Location ce = locToCoords(be.getBlockLoc());
			updateBlock(ce, be.getBlockID());
		}
	}
	
	/**
	 * Update the entire map to the current ether block.
	 * Faster and safer than forceRunAllEtherUpdate as it runs the task Asynchronously.
	 */
	public void runAllEtherUpdate() {
		Bukkit.broadcastMessage(EtherConnect.prefix + "Beginning ether update...");
		Bukkit.getScheduler().runTaskAsynchronously(EtherConnect.ec, new Runnable() {
			@Override
			public void run() {
				Plugin ec = EtherConnect.ec;
				ArrayList<BlockEvent> bea = checkAllBlockUpdates();
				Bukkit.broadcastMessage(EtherConnect.prefix + bea.size() + " reported block updates.");
				for(BlockEvent be : bea) {
					Location ce = locToCoords(be.getBlockLoc());
					updateBlockTask(ec, be, ce);
				}
			}
		});
	}
	
	/**
	 * Sets up an schedule task which runs ether updates every minute.
	 * @return The id of the runnable.
	 */
	public int setupAutoEtherUpdate() {
		return setupAutoEtherUpdate(lastUpdatedBlock);
	}
	
	
	/**
	 * Sets up an schedule task which checks for block updates every configured update time.
	 * @return The id of the runnable.
	 */
	@SuppressWarnings("deprecation")
	public int setupAutoEtherUpdate(int block) {
		lastUpdatedBlock = block;
		return Bukkit.getScheduler().scheduleAsyncRepeatingTask(EtherConnect.ec, new Runnable() {
			@Override
			public void run() {
				updateToNewestBlock();
			}
			
		}, 0, EtherConnect.updateCheckTime);
	}
	
	/**
	 * Update the server to the newest block.
	 * @return The id of the runnable.
	 */
	public void updateToNewestBlock() {
		Bukkit.broadcastMessage(EtherConnect.prefix + "Beginning ether update...");
		Bukkit.getScheduler().runTaskAsynchronously(EtherConnect.ec, new Runnable() {
			@Override
			public void run() {
				Plugin ec = EtherConnect.ec;
				ArrayList<BlockEvent> bea = checkRecentBlockUpdates();
				Bukkit.broadcastMessage(EtherConnect.prefix + bea.size() + " reported block updates.");
				for(BlockEvent be : bea) {
					Location ce = locToCoords(be.getBlockLoc());
					updateBlockTask(ec, be, ce);
				}
			}
			
		});
	}
	
	/**
	 * Get the last updated block.
	 * @return The last updated block.
	 */
	public int getLastUpdatedBlock() {
		return lastUpdatedBlock;
	}

	/**
	 * Updates the block though a task. Needed since we will contact the Ethereum network to get sign information.
	 * @param ec The plugin to register the task to.
	 * @param be The block event from the ether network.
	 * @param ce The location of the block.
	 */
	private void updateBlockTask(Plugin ec, BlockEvent be, Location ce) {
		Bukkit.getScheduler().runTask(ec, new Runnable() {
			@Override
			public void run() {
				if(be.getBlockID() == 4001) {
					String text;
					try {
						text = contract.blockMetaData(BigInteger.valueOf(be.getBlockLoc())).send();
					} catch (Exception e) {
						text = "Failed to Connect";
					}
					placeSign(ce, text);
				}else {
					updateBlock(ce, be.getBlockID());
				}
				Bukkit.broadcastMessage(EtherConnect.prefix + ce.toString() + " location, " + be.getBlockID() + " block ID.");
			}
		});
	}
}
