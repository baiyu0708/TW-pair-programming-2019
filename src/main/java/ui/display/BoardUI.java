package ui.display;

import board.Board;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ui.BoardDisplayPanel;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;

class BoardUI extends JPanel {

    private Duration updateInterval = Duration.ofSeconds(1);

    private final BoardDisplayPanel boardDisplayPanel = new BoardDisplayPanel();
    @Nullable // 当用户还没有选择要显示的board时，该字段为null
    private Board board;

    BoardUI() {
        setLayout(new BorderLayout());
        add(boardDisplayPanel);

        new Thread(() -> {
            //noinspection InfiniteLoopStatement
            while (true) {
                try {
                    Thread.sleep(updateInterval.toMillis());
                } catch (InterruptedException ignored) {
                }

                if (board == null) {
                    continue;
                }

                board.evolve();
                boardDisplayPanel.display(board);
            }
        }).start();
    }

    void setBoard(@NotNull Board board) {
        this.board = board;
        boardDisplayPanel.display(board);
    }
}
