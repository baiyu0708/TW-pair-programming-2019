package ui.display;

import board.Board;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ui.common.BoardDisplayPanel;
import util.timer.Repeater;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;

public class BoardUI extends JPanel {

    private final BoardDisplayPanel boardDisplayPanel = new BoardDisplayPanel();
    @Nullable // 当用户还没有选择要显示的board时，该字段为null
    private Board board;

    private final Repeater repeater;

    public BoardUI() {
        setLayout(new BorderLayout());
        add(boardDisplayPanel);

        repeater = new Repeater(this::update, Duration.ofMillis(500));
        repeater.start(false);
    }

    private void update() {
        if (board == null) {
            return;
        }
        board.evolve();
        boardDisplayPanel.display(board);
    }

    public void setBoard(@NotNull Board board) {
        this.board = board;
        boardDisplayPanel.display(board);
    }

    public void setUpdateInterval(Duration interval) {
        repeater.setInterval(interval);
    }
}
