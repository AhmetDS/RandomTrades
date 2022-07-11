package com.ahmetdinc.randomtrades;

import java.util.Date;

import org.bukkit.OfflinePlayer;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;


public class Placeholder extends PlaceholderExpansion {
	private final RandomTrades plugin;
	private final Scheduler scheduler;
    
    public Placeholder(RandomTrades plugin, Scheduler scheduler) {
        this.plugin = plugin;
        this.scheduler = scheduler;
    }
    
    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }
	
	@Override
	public String onRequest(OfflinePlayer player, String params) {
		if(params.equalsIgnoreCase("timeToReset")) {
			if(player == null) return null;
			Date now = new Date();
			Date wanted = scheduler.getDate();
			return findDifference(now, wanted);
		}

		return null;
	}
	
	private String findDifference(Date date1, Date date2) {
		// Calucalte time difference
        // in milliseconds
        long difference_In_Time
            = date2.getTime() - date1.getTime();

        // Calucalte time difference in
        // seconds, minutes, hours, years,
        // and days
        long difference_In_Seconds
            = (difference_In_Time
               / 1000)
              % 60;

        long difference_In_Minutes
            = (difference_In_Time
               / (1000 * 60))
              % 60;

        long difference_In_Hours
            = (difference_In_Time
               / (1000 * 60 * 60))
              % 24;

        long difference_In_Days
            = (difference_In_Time
               / (1000 * 60 * 60 * 24))
              % 365;

        // date difference in
        // years, in days, in hours, in
        // minutes, and in seconds

            return + difference_In_Days
            + " gün, "
            + difference_In_Hours
            + " saat, "
            + difference_In_Minutes
            + " dakika, "
            + difference_In_Seconds
            + " saniye";
	}

    @Override
    public String getAuthor() {
        return "ahmetdinc";
    }
    
    @Override
    public String getIdentifier() {
        return "RandomTrades";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

}
