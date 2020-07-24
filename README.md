# Ether Connect
An Ethereum smart contract and Bukkit plugin designed to connect a Minecraft world to the Ethereum network. This is a proof of concept built in 2019 using bukkit 1.14, but still works with 1.16. 

The wallet file included should **never** be used. It's required for Etherscan and its just an empty wallet. Please do not deposit money into this wallet.

If you end up using this please open an issue or let me know :).

## How it works
The smart contract is in charge of handling block "purchases" and storing the locations and types of all the blocks. Due to me trying to lower fees, blocks are stored in bit format as XXXX XXXX XXXX YYYY YYYY ZZZZ ZZZZ ZZZZ. This means that 4096 * 4096 * 256 possible block spots are open and able to be claimed (starting at 0,0,0 and going to 4095, 255, 4095 in Minecraft coordinates). All possible block ID's are stored in the IDConverter class and the XMaterial class, while signs with text is handled in the solidity file via the placeSign function (since its storing text, it will cost more to execute).

Technically the entire world isn't stored on the ethereum network as that would be extremely expensive. Only the newly placed blocks are stored. Think of the seed as filling the world with the generation, and then everything stored in the contract as a block needing to be placed. If you want the "entire world" stored, make a completely empty world before running the plugin.


## Plugin Setup
This plugin disables building in the world unless you are an OP. This is because the plugin will take priority and overwrite any blocks placed in the world. It will also force all players into spectator mode on login. If anyone is interested in changing any of these settings, you can either build the plugin yourself or open an issue.
1. [Get Etherscan API key](https://etherscan.io/myapikey).
2. [Get Infura API key](https://infura.io/dashboard/ethereum).
3. Place the wallet file in your server root (by the server.jar) and the plugin in your plugin folder.
4. Run the Minecraft server with the plugin to generate the config.yml.
5. Setup the Ethereum Smart Contract to obtain the contract address.
6. Update config.yml with the API keys and the Smart Contract address. If you want to use the Kovan test network, update the EtherScanEndpoint to https://api-kovan.etherscan.io/api and InfuraEndpoint to https://kovan.infura.io/v3/.
7. Start the server.


## Ethereum Smart Contract Setup
If you are interesting in testing the contract, I recommend you deploy to the kovan network. 
1. Open MinecraftWorld / MinecraftWorld_Release your choice of solidity program ([remix is the easiest](https://remix.ethereum.org/)). Note that MinecraftWorld_Release is a contract **WITHOUT** a self destruct so the world is permanent. I am not responsible for anything put on the Ethereum Network or on your individual world.
2. Publish the contract on the Ethereum network. If you are using remix:
   1. Click on the compiler and compile the program.
   2. Click on deploy and run, select Injected Web3 (Requires Metamask) and connect your Metamask account.
   3. Set contract to be MinecraftWorld.
   4. Expand deploy and set selling to true/false (true enables "buying" blocks, false disables "buying" blocks), maxblock to be the max block ID (refer to XMaterial. To allow all blocks set this to 849), and the world seed (if you want this to be viewable from the contract so other servers may setup your world. This is optional.)
   5. Click on transact and confirm transaction.
3. Get the contract address for the contract you deployed.

Please read the sol file for contract functions. I find it easier to interact with the contract in remix.

## Hidden Commands
There are some hidden, OP only commands in the plugin. These are helpful for debugging and testing the connection.
* /ec update block - Update block at your current location.
* /ec updateblock x y z - Update the block at a given X,Y,Z.
* /ec updateall - Run through all block events and replace all blocks. Will be slower the more blocks are in the contract!
* /ec update - Force an update to start. Will check all updates from the lastblock in config until the current block.
* /ec autoupdate (stop/start) - Stop/Start the autoupdate timer.
