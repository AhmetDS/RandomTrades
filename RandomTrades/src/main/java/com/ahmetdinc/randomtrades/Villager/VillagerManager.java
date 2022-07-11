package com.ahmetdinc.randomtrades.Villager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

public class VillagerManager {
	private final FileConfiguration config;
	public List<Villager> villagers = new ArrayList<>();
	
	public VillagerManager(FileConfiguration config) {
		this.config = config;
		createVillagers();
	}
	
	public List<Villager> getVillagers() {
		return villagers;
	}
	
	public void addVillager(Villager e) {
		villagers.add(e);
	}
	
	public void removeVillager(Villager e) {
		villagers.remove(e);
	}
	
	public List<MerchantRecipe> getDefaultRecipes(String type) {
		List<MerchantRecipe> recipes = new ArrayList<>();
		
		for(String forValue : config.getStringList("Villagers."+type+".Recipes")) { // type.recipes //  "<amount in pool>[<number> ingredient1, <number> ingredient2 > <number> reward]" 
			String[] split0 = forValue.split("\\[");
			split0[1] = split0[1].replace("]", "");
			String[] split1 = split0[1].split(" > ");
			String[] split = split1[1].split(" ");
			try {
				MerchantRecipe recipe = new MerchantRecipe(new ItemStack(Material.getMaterial(split[1]), Integer.parseInt(split[0])), Integer.MAX_VALUE);
			}
			catch(Exception e) {
				System.out.println("line:"+forValue);
				System.out.println(split[1]);
			}
			MerchantRecipe recipe = new MerchantRecipe(new ItemStack(Material.getMaterial(split[1]), Integer.parseInt(split[0])), Integer.MAX_VALUE);
			for(String split2 : split1[0].split(", ")) {
				String[] split3 = split2.split(" ");
				ItemStack ingredient = new ItemStack(Material.valueOf(split3[1]), Integer.parseInt(split3[0]));
				recipe.addIngredient(ingredient);
			}
			for(int i = 0; i < Integer.parseInt(split0[0]); i++) {
				recipes.add(recipe);
			}
		}
		return recipes;
	}
	
	public List<MerchantRecipe> getRandomTrades(List<MerchantRecipe> recipes) {
		List<MerchantRecipe> trades = new ArrayList<>();
		for (int i = 0; i < 7; i++) {
            MerchantRecipe recipe = recipes.get(new Random().nextInt(recipes.size()));
            while (trades.contains(recipe) || recipe == null) {
            	recipe = recipes.get(new Random().nextInt(recipes.size()));
            }
            trades.add(recipe);
        }
		return trades;
	}

	public void createVillagers() {
		for (String type : config.getConfigurationSection("Villagers").getKeys(false)) {
			String locationHindered = config.getString("Villagers."+type+".Location"); // world, x, y, z, yaw, pitch
			String[] split = locationHindered.split(", ");
			Location location = new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), Float.parseFloat(split[4]), Float.parseFloat(split[5]));
		
			List<MerchantRecipe> recipes = getRandomTrades(getDefaultRecipes(type));
			
			addVillager(new Villager(type, config.getString("Villagers."+type+".Name"), location, recipes));
		}
	}
}
