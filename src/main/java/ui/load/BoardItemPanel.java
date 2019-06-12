package ui.load;

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

    void set(@Nullable BoardDesc board) {
        if (board != null) {
            boardDisplayPanel.display(board.board);
            button.setText(board.name);
            button.setEnabled(true);
        } else {
            boardDisplayPanel.display(null);
            button.setText("（无）");
            button.setEnabled(false);
        }
    }
}
