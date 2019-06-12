package ui.load;

import org.jetbrains.annotations.Nullable;
import ui.BoardDisplayPanel;

import javax.swing.*;
import java.awt.*;

class BoardItemPanel extends JPanel {

    private final BoardDisplayPanel boardDisplayPanel = new BoardDisplayPanel();
    private final JButton button = new JButton();

    private BoardDesc board;

    BoardItemPanel(OnSelectBoard onSelectBoard) {

        setLayout(new BorderLayout());
        add(boardDisplayPanel);
        add(button, BorderLayout.SOUTH);

        button.addActionListener(event -> onSelectBoard.onSelect(board));
    }

    void set(@Nullable BoardDesc board) {
        if (board != null) {
            boardDisplayPanel.display(board.board);
            button.setText(board.name);
            button.setEnabled(true);
            this.board = board;
        } else {
            boardDisplayPanel.display(null);
            button.setText("（无）");
            button.setEnabled(false);
        }
    }
}
