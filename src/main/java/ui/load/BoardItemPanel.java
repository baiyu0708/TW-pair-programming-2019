package ui.load;

import board.Board;
import board.BoardIO;
import org.jetbrains.annotations.Nullable;
import ui.BoardDisplayPanel;

import javax.swing.*;
import java.awt.*;

class BoardItemPanel extends JPanel {

    private final BoardDisplayPanel boardDisplayPanel = new BoardDisplayPanel();
    private final JButton button = new JButton();

    BoardItemPanel() {
        setLayout(new BorderLayout());
        add(boardDisplayPanel);
        add(button, BorderLayout.SOUTH);
    }

    void set(@Nullable BoardItem item) {
        if (item != null) {
            boardDisplayPanel.display(item.image != null ? new Board(BoardIO.readState(item.image)) : null);
            button.setText(item.name);
            button.setEnabled(true);
        } else {
            boardDisplayPanel.display(null);
            button.setText("（无）");
            button.setEnabled(false);
        }
    }
}
