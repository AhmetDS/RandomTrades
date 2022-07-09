package de.amin.bingo.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import de.amin.bingo.BingoPlugin;
import de.amin.bingo.team.PlayerManager;

public class Scheduler {
	static FileConfiguration config = BingoPlugin.INSTANCE.getConfig();
	private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
	static Date date;
	private final PlayerManager playerManager;
	private final BingoPlugin plugin;
	
	public Date getDate() {
		return date;
	}

	private class Task extends TimerTask
	{
		@Override
		public void run()
		{
			// Looping it.
			new Scheduler(plugin, playerManager);
			
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

				@Override
				public void run() {
					// Removing players from cooldown
				 	playerManager.getTimed().forEach(timed -> playerManager.removeTimed(timed));
					
				}
				
			}, 40 * 60);
		}
	}

	public Scheduler(BingoPlugin plugin, PlayerManager playerManager) {
		this.playerManager = playerManager;
		this.plugin = plugin;
		
		String wanted = config.getString("Date"); // HH:mm
		int wantedHour = Integer.parseInt(wanted.split(":")[0]);
		if (wantedHour == 24) wantedHour = 0;  
		int wantedMinute = Integer.parseInt(wanted.split(":")[1]);

		// Getting the closest and past day.
		Date currentDate = new Date();

		int hour = currentDate.getHours();
		int minute = currentDate.getMinutes();

		if(hour > wantedHour)
			if(minute > wantedMinute)
				currentDate = new Date(currentDate.getTime() + MILLIS_IN_A_DAY);
		
		int day = currentDate.getDate();
		int month = currentDate.getMonth()+1;
		int year = currentDate.getYear()+1900;

		String dayS = day+"";
		String monthS = month+"";
		String wantedHourS = wantedHour+"";
		String wantedMinuteS = wantedMinute+"";
		if(day < 10) dayS = "0"+day;
		if(month < 10) monthS = "0"+month;
		if(wantedHour < 10) wantedHourS = "0"+wantedHour;
		if(wantedMinute < 10) wantedMinuteS = "0"+wantedMinute;

		//the Date and time at which you want to execute
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		try {
			date = dateFormatter .parse(year+"-"+monthS+"-"+dayS+" "+wantedHourS+":"+wantedMinuteS);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Now create the time and schedule it (( We dont use schedule(task, date, period) because of the placeholder. ?? Also we schedule it 2 minute before because of the wanted time calculation
		Timer timer = new Timer();
		timer.schedule(new Task(), new Date(date.getTime() - 2000 * 60));
		Scheduler.date = date;
	}
}
