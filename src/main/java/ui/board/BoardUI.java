package ui.board;

import board.Board;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ui.common.BoardDisplayPanel;
import util.timer.Repeater;
import util.ui.VFlowLayout;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.util.function.Consumer;

public class BoardUI extends JPanel {

    private static final float INITIAL_UPDATE_INTERVAL_SEC = 0.5f;

    private final BoardDisplayPanel boardDisplayPanel = new BoardDisplayPanel();
    private final Consumer<Board> onSave;
    private final Runnable onReturn;
    @Nullable // 当用户还没有选择要显示的board时，该字段为null
    private Board board;

    private final Repeater updateRepeater;
    private boolean isPlaying = false;

    public BoardUI(Consumer<Board> onSave, Runnable onReturn) {
        this.onSave = onSave;
        this.onReturn = onReturn;

        setLayout(new BorderLayout());
        add(boardDisplayPanel);
        add(createControlPanel(), BorderLayout.EAST);

        boardDisplayPanel.setCellClickListener(this::flipCell);
        updateRepeater = new Repeater(this::update, Duration.ofMillis((long) (INITIAL_UPDATE_INTERVAL_SEC * 1000)));
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new VFlowLayout());

        panel.add(new JLabel("更新间隔（秒）："));
        panel.add(playRateSpinner());
        panel.add(playStopButton());
        panel.add(saveButton());
        panel.add(returnButton());

        return panel;
    }

    private JSpinner playRateSpinner() {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(INITIAL_UPDATE_INTERVAL_SEC, 0.09f, 10f, 0.1f));
        spinner.addChangeListener(e -> {
            Duration interval = Duration.ofMillis((long) ((double) spinner.getValue() * 1000));
            updateRepeater.setInterval(interval);
        });
        return spinner;
    }

    private JButton playStopButton() {
        final String textWhileNotPlaying = "播放□/停止√";
        final String textWhilePlaying = "播放√/停止□";

        JButton button = new JButton(textWhileNotPlaying);
        button.addActionListener(e -> {
            if (isPlaying) {
                button.setText(textWhileNotPlaying);
                updateRepeater.stop();
            } else {
                button.setText(textWhilePlaying);
                updateRepeater.start(true);
            }
            isPlaying = !isPlaying;
        });
        return button;
    }

    private JButton saveButton() {
        JButton button = new JButton("保存");
        button.addActionListener(e -> onSave.accept(board));
        return button;
    }

    private JButton returnButton() {
        JButton button = new JButton("返回");
        button.addActionListener(e -> onReturn.run());
        return button;
    }

    private void update() {
        if (board == null) {
            return;
        }
        board.evolve();
        boardDisplayPanel.display(board);
    }

    private void flipCell(Point point) {
        if (board == null) {
            return;
        }

        board.flip(point.y, point.x);
        boardDisplayPanel.display(board);
    }

    public void setBoard(@NotNull Board board) {
        this.board = board;
        boardDisplayPanel.display(board);
    }
}
