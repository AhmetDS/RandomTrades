package de.amin.bingo.team;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerManager {

    private final ArrayList<UUID> players;
    private final ArrayList<UUID> timed;

    public PlayerManager() {
    	players = new ArrayList<>();
    	timed = new ArrayList<>();
    }
    
    public void addPlayer(UUID UUID) {
        players.add(UUID);
        addTimed(UUID);
    }

    public void removePlayer(UUID UUID) {
    	players.remove(UUID);
    }

    public ArrayList<UUID> getPlayers() {
        return players;
    }
    
    public void addTimed(UUID UUID) {
    	timed.add(UUID);
    }

    public void removeTimed(UUID UUID) {
    	timed.remove(UUID);
    }

    public ArrayList<UUID> getTimed() {
        return timed;
    }
}
