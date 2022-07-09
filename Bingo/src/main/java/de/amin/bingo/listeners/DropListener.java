package de.amin.bingo.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import de.amin.bingo.team.PlayerManager;

public class DropListener implements Listener {

	private final PlayerManager playerManager;

	public DropListener(PlayerManager playerManager) {
		this.playerManager = playerManager;
	}


	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		ItemStack itemStack = event.getItemDrop().getItemStack();
		if (itemStack != null && itemStack.getItemMeta().hasCustomModelData() && itemStack.getItemMeta().getCustomModelData() == 1001) {

			event.setCancelled(true);

		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = (Player) event.getEntity();
		for(ItemStack item : event.getDrops())  {
			if (item.getItemMeta().hasCustomModelData() &&  item.getItemMeta().getCustomModelData() == 1001) {
				event.getDrops().remove(item);
				playerManager.removePlayer(player.getUniqueId());
				break;
			}
		}	

	}

}
