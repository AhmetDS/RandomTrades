package de.amin.bingo.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import de.amin.bingo.BingoPlugin;
import de.amin.bingo.game.BingoGame;
import de.amin.bingo.game.board.map.BoardRenderer;
import de.amin.bingo.team.PlayerManager;

public class RerollCommand implements CommandExecutor {

    private BingoGame game;
    private PlayerManager playerManager;
    static FileConfiguration config = BingoPlugin.INSTANCE.getConfig();

    public RerollCommand(BingoPlugin plugin, BingoGame game, PlayerManager playerManager) {
        this.game = game;
        this.playerManager = playerManager;
        
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))return false;
        Player player = (Player) sender;
        if(playerManager.getPlayers().contains(player.getUniqueId()) && !player.hasPermission("group.yetkili")){
        	player.sendMessage(config.getString("Messages.1"));
        	return false;
        }
        
        if(playerManager.getTimed().contains(player.getUniqueId()) && !player.hasPermission("group.yetkili")){
        	player.sendMessage(config.getString("Messages.2"));
        	return false;
        }
        
        player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_CLUSTER_BREAK,1,1);

        game.createBoard(player);
        ItemStack boardMap = getRenderedMapItem(player);
        for(ItemStack notCool : player.getInventory().addItem(boardMap).values()) {
        	player.sendMessage(config.getString("Messages.3"));
        	return false;
        }
        playerManager.addPlayer(player.getUniqueId());
        return true;
    }
    
    private ItemStack getRenderedMapItem(Player player) {
        ItemStack itemStack = new ItemStack(Material.FILLED_MAP);
        MapView view = Bukkit.createMap(Bukkit.getWorlds().get(0));
        //clear renderers one by one
        for (MapRenderer renderer : view.getRenderers())
            view.removeRenderer(renderer);

        BoardRenderer renderer = game.getBoard(player).getRenderer();
        view.addRenderer(renderer);
        MapMeta mapMeta = (MapMeta) itemStack.getItemMeta();
        mapMeta.setMapView(view);
        mapMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("Item.name")));
        mapMeta.setLore(translateAlternateColorCodes(config.getStringList("Item.lore")));
        mapMeta.setCustomModelData(1001);
        itemStack.setItemMeta(mapMeta);
        return itemStack;
    }
    
    private List<String> translateAlternateColorCodes(List<String> list){
    	List<String> returnList = new ArrayList<String>();
    	for(String part : list) {
    		String newPart = ChatColor.translateAlternateColorCodes('&', part);
    		returnList.add(newPart);
    	}
    	return returnList;
    }
}
