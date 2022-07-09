package de.amin.bingo.game;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import de.amin.bingo.BingoPlugin;
import de.amin.bingo.game.board.BingoItem;
import de.amin.bingo.team.PlayerManager;

public class MainState {

	private BukkitTask gameLoop;
	private final BingoPlugin plugin;
	private final BingoGame game;
	private final PlayerManager playerManager;


	public MainState(BingoPlugin plugin, BingoGame game, PlayerManager playerManager) {
		this.plugin = plugin;
		this.game = game;
		this.playerManager = playerManager;
		startTimer();
	}

	public void end() {
		gameLoop.cancel();
	}

	public void startTimer() {

		gameLoop = plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
			playerManager.getPlayers().forEach(UUID -> { Player player = Bukkit.getPlayer(UUID);
			if(player != null && player.isOnline()) {
				if(game.checkWin(player)) {
					for(ItemStack item : player.getInventory().all(Material.FILLED_MAP).values())  {
						if (item != null && item.getItemMeta().hasCustomModelData() &&  item.getItemMeta().getCustomModelData() == 1001) {
							player.getInventory().remove(item);
							player.getInventory().addItem(new ItemStack(Material.EMERALD, 16));
							player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 0.1f);
							playerManager.removePlayer(UUID);
							player.getInventory().all(Material.FILLED_MAP);
							break;
						}
					}
				}
				else {
					//check for all players if they have a new item from the board
					for (BingoItem item : game.getBoard(player).getItems()) {
						if (!item.isFound()) {
							for (ItemStack content : player.getInventory().getContents()) {
								if (content != null && content.getType().equals(item.getMaterial())) {
									item.setFound(true);
									player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1, 1);
								}
							}
						}
					}
				}
			}
			});
		}, 0, 5);

	}
}
