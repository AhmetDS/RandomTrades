package com.ahmetdinc.randomtrades.Villager;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.MerchantRecipe;

public class Villager {
	private final String type;
	private final String name;
    private Location location;
    private List<MerchantRecipe> rewards;
    private org.bukkit.entity.Villager villager;
	public Villager(String type, String name, Location location, List<MerchantRecipe> rewards) {
		this.type = type;
		this.location = location;
		this.rewards = rewards;
		this.name = name;
		
		villager = (org.bukkit.entity.Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
		villager.setCustomNameVisible(true);
        villager.setCustomName(name);
        villager.setSilent(true);
        villager.setPersistent(true);
        villager.setAI(false);

		villager.setRecipes(rewards);
	}
	
	public String getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public List<MerchantRecipe> getRewards() {
		return rewards;
	}
	
	public void setRewards(List<MerchantRecipe> rewards) {
		this.rewards = rewards;
		villager.setRecipes(rewards);
		
	}
	
	public org.bukkit.entity.Villager getVillager() {
		return villager;
	}
}
