package de.amin.bingo.game.board;

import de.amin.bingo.BingoPlugin;
import de.amin.bingo.game.board.map.BoardRenderer;
import de.amin.bingo.utils.Config;

public class BingoBoard {

    private BingoItem[] bingoItems = new BingoItem[Config.BOARD_SIZE];
    private BoardRenderer renderer;

    public BingoBoard(BingoMaterial[] items, BoardRenderer renderer) {
        for (int i = 0; i < items.length; i++) {
            bingoItems[i] = new BingoItem(items[i]);
        }
        this.renderer = renderer;
    }

    public BingoItem[] getItems() {
        return bingoItems;
    }

    public int getFoundItems() {
        int count = 0;
        for (BingoItem bingoItem : bingoItems) {
            if(bingoItem.isFound()) count++;
        }
        return count;
    }
    
    public BoardRenderer getRenderer() {
    	return this.renderer;
    }
}
