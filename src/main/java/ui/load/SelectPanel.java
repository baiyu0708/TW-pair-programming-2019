package ui.load;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class SelectPanel extends JPanel {

    private static final int BOARD_COUNT_PER_PAGE = 10;
    private static final int BOARD_COUNT_PER_ROW = 2;

    private final ArrayList<BoardItem> items;
    private final BoardItemPanel[] boardItemPanels;

    private int startIndex = 0;

    SelectPanel(List<BoardItem> items) {
        this.items = new ArrayList<>(items);

        setLayout(new GridLayout(BOARD_COUNT_PER_ROW, BOARD_COUNT_PER_PAGE / BOARD_COUNT_PER_ROW));
        boardItemPanels = new BoardItemPanel[BOARD_COUNT_PER_PAGE];
        for (int i = 0; i < BOARD_COUNT_PER_PAGE; i++) {
            boardItemPanels[i] = new BoardItemPanel();
            add(boardItemPanels[i]);
        }

        // offset：当前页面所显示的第几张图像
        for (int offset = 0; offset < BOARD_COUNT_PER_PAGE; offset++) {
            int index = startIndex + offset;
            if (index < this.items.size()) {
                boardItemPanels[offset].set(this.items.get(index));
            } else {
                boardItemPanels[offset].set(null);
            }
        }
    }
}
