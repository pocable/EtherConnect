package ca.pocable.etherconnect;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;

public class EtherListener implements Listener{
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.getPlayer().setGameMode(GameMode.SPECTATOR);
	}
	
	@EventHandler
	public void onBlockDestory(BlockBreakEvent event) {
		if(event.getPlayer().isOp())
			return;
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if(event.getPlayer().isOp())
			return;
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onCreativeInv(InventoryCreativeEvent event) {
		if(event.getWhoClicked().isOp())
			return;
		event.setCancelled(true);
	}

}
