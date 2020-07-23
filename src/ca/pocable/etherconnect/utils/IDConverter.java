package ca.pocable.etherconnect.utils;

import org.bukkit.Material;

/**
 * IDConverter
 * 
 * Converts an Ethereum stored ID into a respective block object.
 * Needed for the newest Minecraft versions as they removed block ids.
 * This also allows for removing blocks and such.
 * 
 * @version 1.0
 * @author Thomas
 *
 */
public class IDConverter {
	
	/**
	 * Convert the ID received from the Ethereum Network and return a Block.
	 * @param id The ID from the network.
	 * @return The Block that the ID corresponds to.
	 */
	public static Material convertIDToBlock(int id) {
		switch(id) {
		case 4000: return Material.AIR;
		case 4001: return Material.OAK_SIGN;
		default: return XMaterial.values()[id].parseMaterial();
		}
	}

}
