package ui.load;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class SelectPanel extends JPanel {

    private static final int BOARD_COUNT_PER_PAGE = 10;
    private static final int BOARD_COUNT_PER_ROW = 2;

    private ArrayList<BoardDesc> items;
    private final BoardItemPanel[] boardItemPanels;

    private int startIndex = 0;

    SelectPanel(List<BoardDesc> items, Consumer<BoardDesc> onSelectBoard) {
        this.items = new ArrayList<>(items);

        setLayout(new GridLayout(BOARD_COUNT_PER_ROW, BOARD_COUNT_PER_PAGE / BOARD_COUNT_PER_ROW, 5, 5));
        boardItemPanels = new BoardItemPanel[BOARD_COUNT_PER_PAGE];
        for (int i = 0; i < BOARD_COUNT_PER_PAGE; i++) {
            boardItemPanels[i] = new BoardItemPanel(onSelectBoard);
            add(boardItemPanels[i]);
        }

        display();
    }

    void setItems(List<BoardDesc> items) {
        this.items = new ArrayList<>(items);
        display();
    }

    void nextPage() {
        startIndex += BOARD_COUNT_PER_PAGE;
        display();
    }

    boolean hasNextPage() {
        return startIndex + BOARD_COUNT_PER_PAGE < items.size();
    }

    void prevPage() {
        startIndex -= BOARD_COUNT_PER_PAGE;
        display();
    }

    boolean hasPrevPage() {
        return startIndex - BOARD_COUNT_PER_PAGE >= 0;
    }

    private void display() {
        // offset：当前页面所显示的第几张图像
        for (int offset = 0; offset < BOARD_COUNT_PER_PAGE; offset++) {
            int index = startIndex + offset;
            if (index < items.size()) {
                boardItemPanels[offset].set(items.get(index));
            } else {
                boardItemPanels[offset].set(null);
            }
        }
    }
}
