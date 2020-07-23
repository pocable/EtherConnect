package ca.pocable.etherconnect;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLClassLoader;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import ca.pocable.etherconnect.utils.JarUtils;
import net.md_5.bungee.api.ChatColor;

public class EtherConnect extends JavaPlugin{
	
	public static String version;
	public static FileConfiguration config;
	public static File configFile;
	public static String prefix = ChatColor.WHITE + "[" + ChatColor.GRAY + "Ether" + ChatColor.DARK_GRAY + "Connect" + ChatColor.WHITE + "] " + ChatColor.RESET;
	public static int updateCheckTime = 1200; // In ticks. 20 ticks per second.
	int bukkitTimer = -1;
	
	protected static Plugin ec;
	
	@Override
	public void onEnable() {
		
		// Use the lib built into the jar.
        try {
            final File[] libs = new File[] {
                    new File(getDataFolder(), "console-4.1.1-all.jar")};
            for (final File lib : libs) {
                if (!lib.exists()) {
                    JarUtils.extractFromJar(lib.getName(),
                            lib.getAbsolutePath());
                }
            }
            for (final File lib : libs) {
                if (!lib.exists()) {
                    getLogger().warning(
                            "There was a critical error loading the plugin. Could not find lib: "
                                    + lib.getName());
                    Bukkit.getServer().getPluginManager().disablePlugin(this);
                    return;
                }
                addClassPath(JarUtils.getJarUrl(lib));
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        
        // Load config and initialize EthereumNetwork.
		saveDefaultConfig();
		PluginDescriptionFile pdf = this.getDescription();
		version = pdf.getVersion();
		
		// Set public plugin access and register listener.
		ec = this;
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new EtherListener(), this);
		
		// Read from the configuration file.
		if(this.getConfig().getBoolean("EnableAutoUpdateOnLaunch")) {
			bukkitTimer = EthereumNetwork.getInstance().setupAutoEtherUpdate();
		}
		updateCheckTime = getConfig().getInt("UpdateFrequency");
		
		// Check that the world seed matches the overworld.
		try {
			if(!EthereumNetwork.getInstance().getContract().worldSeed().send().equals(Bukkit.getWorld("world").getSeed() + "")) {
				getLogger().warning("Seed does not match the one provided in the contract!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			getLogger().warning("Alternative error occured. See stack trace. Disabling...");
			Bukkit.getPluginManager().disablePlugin(this);
		}
	}
	
	@Override
	public void onDisable() {
		getConfig().set("LastBlock", EthereumNetwork.getInstance().getLastUpdatedBlock());
		this.saveConfig();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getLabel().equalsIgnoreCase("ec")){
			if(args.length > 0){
				if(args[0].equals("info")) {
					String extraInfo = "";
					if(version.contains("d")) {
						extraInfo = ChatColor.DARK_RED + " -DEVELOPMENT BUILD- " + ChatColor.RESET;
					}
					sendPluginMessage(sender, ChatColor.GREEN + "EtherConnect Version " + version + extraInfo);					
				}else if(args[0].equals("checkowner")) {
					if(args.length == 4) {
						try {
							int x = Integer.parseInt(args[1]);
							int y = Integer.parseInt(args[2]);
							int z = Integer.parseInt(args[3]);
							MinecraftWorld mc = EthereumNetwork.getInstance().getContract();
							BigInteger res = mc.blockData(EthereumNetwork.createCoords(x, y, z)).send();
							if(res.longValue() == 0) {
								sendPluginMessage(sender, ChatColor.GREEN + "This block is not owned!");
							}else {
								sendPluginMessage(sender, ChatColor.GOLD + "The block is owned! Current block ID: " + res.longValue());
							}
						}catch(NumberFormatException e) {
							sendPluginMessage(sender, ChatColor.DARK_RED + "Invalid arguments! X Y and Z coordinates should be whole numbers!");
						} catch (Exception e) {
							sendPluginMessage(sender, ChatColor.DARK_RED + "The Ethereum network is having some troubles. Please try again later.");
							e.printStackTrace();
						}
					}else {
						sendPluginMessage(sender, ChatColor.DARK_RED + "Invalid command! Usage: /ec checkblock X Y Z");
					}
				}else if (args[0].equals("updateblock")){
					try {
						int x = Integer.parseInt(args[1]);
						int y = Integer.parseInt(args[2]);
						int z = Integer.parseInt(args[3]);
						EthereumNetwork.getInstance().updateBlock(x, y, z, Bukkit.getWorld("world"));
						sendPluginMessage(sender, ChatColor.GREEN + "The block has been updated!");
					}catch(NumberFormatException e) {
						sendPluginMessage(sender, ChatColor.DARK_RED + "Invalid arguments! X Y and Z coordinates should be whole numbers!");
					} catch (Exception e) {
						sendPluginMessage(sender, ChatColor.DARK_RED + "The Ethereum network is having some troubles. Please try again later.");
						e.printStackTrace();
					}
				} else if(args[0].equals("updateall")){
					if(sender.isOp())
						EthereumNetwork.getInstance().runAllEtherUpdate();
					
				}else if(args[0].equals("update")) {
					if(sender.isOp()) {
						EthereumNetwork.getInstance().updateToNewestBlock();
						sendPluginMessage(sender, ChatColor.GREEN + "The block update has started!");
					}
				} else if(args[0].equals("autoupdate")){
					if(sender.isOp()) {
						if(args.length == 2) {
							if(args[1].equals("stop")) {
								Bukkit.getScheduler().cancelTask(bukkitTimer);
								bukkitTimer = -1;
								sendPluginMessage(sender, ChatColor.GREEN + "Stopped scheduled task.");
							}else if(args[1].equals("start")) {
								if(bukkitTimer == -1) {
									bukkitTimer = EthereumNetwork.getInstance().setupAutoEtherUpdate();
									sendPluginMessage(sender, ChatColor.GREEN + "The block auto update has started!");
								}else {
									Bukkit.getScheduler().cancelTask(bukkitTimer);
									bukkitTimer = EthereumNetwork.getInstance().setupAutoEtherUpdate();
									sendPluginMessage(sender, ChatColor.GREEN + "Stopped original timer and updated to new one!");
								}
							}else {
								sendPluginMessage(sender, ChatColor.DARK_RED + "Invalid command! Usage: /ec autoupdate [start/stop].");
							}
						}else {
							sendPluginMessage(sender, ChatColor.DARK_RED + "Invalid command! Usage: /ec autoupdate [start/stop].");
						}
					}
					
				}else if(args[0].equals("block")) {
					sendPluginMessage(sender, "Updated to block: " + ChatColor.GOLD + EthereumNetwork.getInstance().getLastUpdatedBlock());
				}else if(args[0].equals("id")) {
					int x, y, z;
					try {
						x = Integer.parseInt(args[1]);
						y = Integer.parseInt(args[2]);
						z = Integer.parseInt(args[3]);
						sendPluginMessage(sender, "Block location ID of [" + x + ", " + y + ", " + z + "] is " + EthereumNetwork.createCoords(x, y, z).longValue());
					}catch(Exception e) {
						if(sender instanceof Player) {
							Player p = (Player) sender;
							Location l = p.getLocation();
							x = l.getBlockX();
							y = l.getBlockY();
							z = l.getBlockZ();
							sendPluginMessage(sender, "Block location ID of [" + x + ", " + y + ", " + z + "] is " + EthereumNetwork.createCoords(x, y, z));
						}else {
							sendPluginMessage(sender, ChatColor.DARK_RED + "You need to supply coordinates or be logged on to use the short version!");
						}
					}
				}else if(args[0].equals("spectator")) {
					if(sender instanceof Player) {
						Player pl = (Player) sender;
						if(pl.getGameMode().equals(GameMode.SPECTATOR)) {
							pl.setGameMode(GameMode.CREATIVE);
						}else {
							pl.setGameMode(GameMode.SPECTATOR);
						}
					}else {
						sendPluginMessage(sender, ChatColor.DARK_RED + "You must be a player to use this command!");
					}
				} else {  
					sendPluginMessage(sender, ChatColor.DARK_RED + "Unknown command!");
				}
			}else {
				sendPluginMessage(sender, ChatColor.GOLD + "/ec info - Version information.");
				sendPluginMessage(sender, ChatColor.GOLD + "/ec block - Get the current ether block.");
				sendPluginMessage(sender, ChatColor.GOLD + "/ec spectator - Toggle into spectator mode to move around the map.");
				sendPluginMessage(sender, ChatColor.GOLD + "/ec checkowner x y z - Check the block owner.");
				sendPluginMessage(sender, ChatColor.GOLD + "/ec id (x, y, z) - Get the block id of the x y z or current block your on.");
				//sendPluginMessage(sender, ChatColor.DARK_RED + "Unknown command!");
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Send a message to the sender in the proper format.
	 * @param sender The sender to send the message to.
	 * @param message The message.
	 */
	public static void sendPluginMessage(CommandSender sender, String message) {
		sender.sendMessage(prefix + message);
	}
	
	/**
	 * Add the class path.
	 * @param url The location of the file.
	 * @throws IOException If the URL is invalid.
	 * @author fletch_to_99 {@link https://bukkit.org/threads/tutorial-use-external-library-s-with-your-plugin.103781/}
	 */
    private void addClassPath(final URL url) throws IOException {
        final URLClassLoader sysloader = (URLClassLoader) ClassLoader
                .getSystemClassLoader();
        final Class<URLClassLoader> sysclass = URLClassLoader.class;
        try {
            final Method method = sysclass.getDeclaredMethod("addURL",
                    new Class[] { URL.class });
            method.setAccessible(true);
            method.invoke(sysloader, new Object[] { url });
        } catch (final Throwable t) {
            t.printStackTrace();
            throw new IOException("Error adding " + url
                    + " to system classloader");
        }
    }
}
