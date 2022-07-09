package de.amin.bingo;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;

import de.amin.bingo.commands.RerollCommand;
import de.amin.bingo.game.BingoGame;
import de.amin.bingo.game.MainState;
import de.amin.bingo.game.board.map.BoardRenderer;
import de.amin.bingo.listeners.DropListener;
import de.amin.bingo.team.PlayerManager;
import de.amin.bingo.utils.Config;
import de.amin.bingo.utils.Scheduler;

public final class BingoPlugin extends JavaPlugin {

    public static BingoPlugin INSTANCE;

    @Override
    public void onEnable() {
    	INSTANCE = this;

        getLogger().info(ChatColor.GREEN + "Plugin has been initialized");

        saveDefaultConfig();

        PlayerManager  playerManager= new PlayerManager();

        BingoGame game = new BingoGame();
        BoardRenderer renderer = new BoardRenderer(this, game);
        
        MainState mainState = new MainState(this, game, playerManager);
        
        Scheduler scheduler = new Scheduler(this, playerManager);

        registerListeners(getServer().getPluginManager(), playerManager, game);
        registerCommands(game, renderer, playerManager);
        
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
            new Placeholder(this, scheduler).register();
    }

    private void registerListeners(PluginManager pluginManager, PlayerManager playerManager, BingoGame game) {
        pluginManager.registerEvents(new DropListener(playerManager), this);
    }

    private void registerCommands(BingoGame game, BoardRenderer renderer, PlayerManager playerManager) {
        getCommand("reroll").setExecutor(new RerollCommand(this, game, renderer, playerManager));
    }

}
