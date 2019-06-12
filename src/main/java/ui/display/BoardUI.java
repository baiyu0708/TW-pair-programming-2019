package ui.display;

import board.Board;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ui.common.BoardDisplayPanel;
import util.timer.Repeater;
import util.ui.VFlowLayout;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;

public class BoardUI extends JPanel {

    private final BoardDisplayPanel boardDisplayPanel = new BoardDisplayPanel();
    @Nullable // 当用户还没有选择要显示的board时，该字段为null
    private Board board;

    private final Repeater updateRepeater;

    public BoardUI() {
        setLayout(new BorderLayout());
        add(boardDisplayPanel);
        add(createControlPanel(), BorderLayout.EAST);

        updateRepeater = new Repeater(this::update, Duration.ofMillis(500));
        updateRepeater.start(false);
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new VFlowLayout());

        panel.add(new JLabel("播放速率（秒）："));
        panel.add(playRateSpinner());
        panel.add(new JButton("播放/停止"));

        return panel;
    }

    private JSpinner playRateSpinner() {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(1f, 0.09f, 10f, 0.1f));
        spinner.addChangeListener(e -> {
            Duration interval = Duration.ofMillis((long) ((double) spinner.getValue() * 1000));
            updateRepeater.setInterval(interval);
        });
        return spinner;
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
}
