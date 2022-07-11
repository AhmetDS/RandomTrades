package com.ahmetdinc.randomtrades;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.ahmetdinc.randomtrades.Villager.Villager;
import com.ahmetdinc.randomtrades.Villager.VillagerManager;

public class RandomTrades extends JavaPlugin{
	public FileConfiguration config;
	public static RandomTrades INSTANCE;
	public VillagerManager villagerManager;

	@Override
	public void onEnable() {
		INSTANCE = this;
		YamlWrapper YAMLWRAPPER = new YamlWrapper(this, getDataFolder(), "config", true, true);    
		config = YAMLWRAPPER.getConfig();
		
		villagerManager = new VillagerManager(config);
		
		Scheduler scheduler = new Scheduler(this, villagerManager);
		
		if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) 
            new Placeholder(this, scheduler).register();
	}
	
	@Override
	public void onDisable() {
		for(Villager villager : villagerManager.getVillagers()) {
			villager.getVillager().remove();
		}
	}
	
}
