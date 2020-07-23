/**
Copyright 2019 Thomas Roskewich

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
**/

pragma solidity >=0.4.22 <0.6.0;

contract owned {
    address public owner;

    constructor() public {
        owner = msg.sender;
    }

    modifier onlyOwner {
        require(msg.sender == owner);
        _;
    }

    function transferOwnership(address newOwner) onlyOwner public {
        owner = newOwner;
    }
}

contract MinecraftWorld is owned{
    /**
     * Mapping of the blockOwners goes as follows:
     * XXXY YZZZ
     * OR in Bit Form:
     * XXXX XXXX XXXX YYYY YYYY ZZZZ ZZZZ ZZZZ
     * */

     // Block Owners keeps track of the owner for each block.
    mapping (uint32 => address) public blockOwners;

    // Block Data keeps track of what block is at what location.
    mapping (uint32 => uint16) public blockData;

    // Block Meta Data for signs.
    mapping (uint32 => string) public blockMetaData;

    bool public currentlySelling;
    uint32 public maxBlockId;
    string public worldSeed;

    constructor(bool selling, uint32 maxBlock, string memory worldSeedIn) public{
        currentlySelling = selling;
        maxBlockId = maxBlock;
        worldSeed = worldSeedIn;
    }


    event UpdateBlock(address user, uint32 blockLocation, uint16 newBlockID);
    event UpdateBlockOwner(address newOwner, uint32 blockInformation);
    event UpdateBlockMeta(uint32 blockLocation, string metadata);

    /**
     * Set Block Owning Address
     *
     * Set the owner of the given x y z block provided they do not already own a block.
     * Used for administration and is a public onlyOwner call.
     * @param x The relative X coordinate of the block.
     * @param y The relative Y coordinate of the block.
     * @param z The relative Z coordinate of the block.
     * @param newBlockOwner The address of the owner of the block.
     *
     * */
    function setBlockOwningAddress(uint32 x, uint32 y, uint32 z, address newBlockOwner) public onlyOwner{
        uint32 blockID = convertToBlockNumber(x, y, z);
        setBlockOwner(newBlockOwner, blockID);
    }

    /**
     * Set Max Block ID
     *
     * Sets the max block ID usable to place in the Minecraft world.
     * @param maxBlock The max block ID.
     * */
    function setMaxBlockId(uint32 maxBlock) public onlyOwner{
        maxBlockId = maxBlock;
    }

    /**
     * Convert To Block Number
     *
     * Converts a given X Y Z coordinate into its block number.
     * @param x The X coordinate
     * @param y The Y coordinate
     * @param z The Z coordinate
     * @return uint32 The block number
     * */
    function convertToBlockNumber(uint32 x, uint32 y, uint32 z) internal pure returns (uint32){
        require(x <= 4095 && y < 256 && z <= 4095);
        return ((x << 20) + (y << 12) + z);
    }

    /**
     * Set Block Owner
     *
     * Set the owner of the given x y z block provided.
     * @param newBlockOwner The address of the owner of the block.
     * @param loc The location of the block in the format used internally.
     *
     * */
    function setBlockOwner(address newBlockOwner, uint32 loc) internal{
        require(blockOwners[loc] == address(0x0));
        blockOwners[loc] = newBlockOwner;
        blockData[loc] = 4000;
        emit UpdateBlockOwner(newBlockOwner, loc);
        emit UpdateBlock(msg.sender, loc, 4000);
    }

    /**
     * Set Block Owner
     *
     * Set the owner of the given x y z block.
     * @param loc The location of the block in the format used internally.
     * @param newBlockOwner The address of the owner of the block.
     * @param blockID The block ID to be placed at the location.
     *
     * */
    function setBlockOwner(uint32 loc, address newBlockOwner, uint16 blockID) internal{
        require(blockOwners[loc] == address(0x0));
        require(blockID <= maxBlockId);
        blockOwners[loc] = newBlockOwner;
        blockData[loc] = blockID;
        emit UpdateBlockOwner(newBlockOwner, loc);
        emit UpdateBlock(msg.sender, loc, blockID);
    }


    /**
     * Set Block Owner Insecure
     *
     * Set the owner of the given x y z block provided they do not already own a block.
     * This version of the function does not check require functions.
     * @param loc The location of the block in the format used internally.
     * @param newBlockOwner The address of the owner of the block.
     * @param blockID The block ID to be placed at the location.
     *
     * */
    function setBlockOwnerInsecure(uint32 loc, address newBlockOwner, uint16 blockID) internal{
        blockOwners[loc] = newBlockOwner;
        blockData[loc] = blockID;
        emit UpdateBlockOwner(newBlockOwner, loc);
        emit UpdateBlock(msg.sender, loc, blockID);
    }


    /**
     * Set Block Data
     *
     * Sets the value of the block.
     * @param loc The location of the block.
     * @param newBlockID The Minecraft block id.
     * */
    function setBlockData(uint32 loc, uint16 newBlockID) public{

        require (blockOwners[loc] == msg.sender);
        require (newBlockID <= maxBlockId);
        require (newBlockID > 0);
        blockData[loc] = newBlockID;
        emit UpdateBlock(msg.sender, loc, newBlockID);
    }

    /**
     * Set Block Data
     *
     * Sets the value of the block.
     * @param x The X coordinate of the block.
     * @param y The Y coordinate of the block.
     * @param z The Z coordinate of the block.
     * @param newBlockID The Minecraft block id.
     * */
    function setBlockData(uint32 x, uint32 y, uint32 z, uint16 newBlockID) public{
        uint32 loc = convertToBlockNumber(x, y, z);
        setBlockData(loc, newBlockID);
    }

    /**
     * Place Sign
     *
     * Places a sign at the given location with the given string.
     * @param loc The location to place the sign.
     * @param text The text to put on the sign.
     * @return The location of the block in the correct format.
     * */
     function placeSign(uint32 loc, string memory text) public returns (uint32){
        require(currentlySelling);
        require(blockOwners[loc] == address(0x0));
        require(bytes(text).length <= 60);
        setBlockOwnerInsecure(loc, msg.sender, 4001);
        blockMetaData[loc] = text;
        emit UpdateBlockMeta(loc, text);
        return loc;
    }

    /**
     * Place Sign
     *
     * Places a sign at the given location with the given string.
     * @param x The X location.
     * @param y The Y location.
     * @param z The Z location.
     * @param text The text to put on the sign.
     * @return The location of the block in the correct format.
     * */
     function placeSign(uint32 x, uint32 y, uint32 z, string memory text) public returns (uint32){
        return placeSign(convertToBlockNumber(x, y, z), text);
    }

    /**
     * Purchase Specific Block
     *
     * Allows purchasing a specific block in the Minecraft World.
     * This is free, but made it payable if someone wants to send money.
     * @param x The X coordinate.
     * @param y The Y coordinate.
     * @param z The Z coordinate.
     * @return The location of the block in the correct format.
     * */
    function purchaseBlock(uint32 x, uint32 y, uint32 z) payable public returns (uint32){
        require(currentlySelling);
        uint32 num = convertToBlockNumber(x, y, z);
        setBlockOwner(msg.sender, num);
        return num;
    }

    /**
     * Purchase Specific Block
     *
     * Allows purchasing a specific block in the Minecraft World with a given block ID.
     * This is free, but made it payable if someone wants to send money.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param z The z coordinate.
     * @param blockID The block ID to be placed at the xyz coordinates.
     * @return The location of the block in the correct format.
     * */
    function purchaseBlock(uint32 x, uint32 y, uint32 z, uint16 blockID) payable public returns (uint32){
        require(currentlySelling);
        uint32 num = convertToBlockNumber(x, y, z);
        setBlockOwner(num, msg.sender, blockID);
        return num;
    }

    /**
     * Set Selling
     *
     * Allows for turning off or on selling of blocks to users.
     * @param isSelling Are we selling blocks?
     * */
    function setSelling(bool isSelling) public onlyOwner{
        currentlySelling = isSelling;
    }

    /**
     * Send Block
     *
     * Send a block to another address so they control it.
     * @param other The other address to transfer ownership to.
     * */
    function sendBlock(uint32 blockCoordID, address other) public{
        require(other != address(0x0));
        require(blockOwners[blockCoordID] == msg.sender);
        blockOwners[blockCoordID] = other;
    }


    /**
     * Send Block
     *
     * Send a block to another address so they control it.
     * @param other The other address to transfer ownership to.
     * */
    function sendBlock(uint32 x, uint32 y, uint32 z, address other) public{
        sendBlock(convertToBlockNumber(x, y, z), other);
    }


    /**
     * Withdraw Ethereum
     *
     * Remove Ethereum from the contract and send it to the address provided.
     * @param withdrawLocation The location to withdraw the Ethereum.
     * @param amount The amount of Ethereum to take out of the contract.
     * */
     function withdraw(address payable withdrawLocation, uint256 amount) public onlyOwner{
         require(address(this).balance >= amount);
         withdrawLocation.transfer(amount);
     }

    /**
     * Destroy the Contract. Used for development.
     * On release, delete this as the intended reason for this is to have
     * the data un able to be removed. It is stuck.
     * */
    function destroyContract(address payable ownerAddress) public onlyOwner{
        selfdestruct(ownerAddress);
    }


}
