package de.amin.bingo.game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import de.amin.bingo.game.board.BingoBoard;
import de.amin.bingo.game.board.BingoMaterial;
import de.amin.bingo.utils.Config;

public class BingoGame {

    private HashMap<OfflinePlayer, BingoBoard> boards;
    BingoMaterial[] items = new BingoMaterial[Config.BOARD_SIZE];

    public BingoGame() {
        boards = new HashMap<>();
    }

    public void createBoard(Player player) {

        //generation of random items
        for (int i = 0; i < items.length; i++) {
            BingoMaterial bingoMaterial = getRandomMaterial();
            while (Arrays.asList(items).contains(bingoMaterial)) {
                bingoMaterial = getRandomMaterial();
            }
            items[i] = bingoMaterial;
        }
        boards.put(player, new BingoBoard(items));
    }

    public boolean checkWin(Player player) {
        int[][] winSituations = new int[][]{
                //horizonzal
                {0, 1, 2, 3, 4},
                {5, 6, 7, 8, 9},
                {10, 11, 12, 13, 14},
                {15, 16, 17, 18, 19},
                {20, 21, 22, 23, 24},
                //vertical
                {0, 5, 10, 15, 20},
                {1, 6, 11, 16, 21},
                {2, 7, 12, 17, 22},
                {3, 8, 13, 18, 23},
                {4, 9, 14, 19, 24},
                //diagonal
                {0, 6, 12, 18, 24},
                {4, 8, 12, 16, 20}
        };
        for (int[] winSituation : winSituations) {
            boolean win = true;
            for (int i : winSituation) {
                if (!getBoard(player).getItems()[i].isFound()) {
                    win = false;
                }
            }
            if (win) {
                return true;
            }
        }
        return false;
    }

    public BingoBoard getBoard(Player player) {
        return boards.get(player);
    }
    
    public HashMap<OfflinePlayer, BingoBoard> getBoards() {
        return boards;
    }

    public BingoMaterial[] getItems() {
        return items;
    }

    public BingoMaterial getRandomMaterial() {
        return BingoMaterial.values()[new Random().nextInt(BingoMaterial.values().length)];
    }
}
