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
    private EditModePanel editModePanel;

    private final Runnable onReturn;
    @Nullable // 当用户还没有选择要显示的board时，该字段为null
    private Board board;

    private final Repeater updateRepeater;
    private boolean isPlaying = false;

    private static final String TEXT_WHILE_NOT_PLAYING = "播放□/停止√";
    private static final String TEXT_WHILE_PLAYING = "播放√/停止□";
    private JButton playStopButton;

    public BoardUI(Consumer<Board> onSave, Runnable onReturn) {
        this.onSave = onSave;
        this.onReturn = onReturn;

        setLayout(new BorderLayout());
        add(boardDisplayPanel);
        add(createControlPanel(), BorderLayout.EAST);

        boardDisplayPanel.setCellClickListener(this::editCell);
        updateRepeater = new Repeater(this::update, Duration.ofMillis((long) (INITIAL_UPDATE_INTERVAL_SEC * 1000)));
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new VFlowLayout());

        panel.add(new JLabel("更新间隔（秒）："));
        panel.add(playRateSpinner());
        panel.add(playStopButton());
        panel.add(new JLabel("编辑模式"));
        editModePanel = new EditModePanel();
        panel.add(editModePanel);
        panel.add(new JLabel("文件"));
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
        playStopButton = new JButton(TEXT_WHILE_NOT_PLAYING);
        playStopButton.addActionListener(e -> {
            if (isPlaying) {
                stop();
            } else {
                play();
            }
        });
        return playStopButton;
    }

    private void play() {
        playStopButton.setText(TEXT_WHILE_PLAYING);
        updateRepeater.start(true);
        isPlaying = true;
    }

    public void stop() {
        playStopButton.setText(TEXT_WHILE_NOT_PLAYING);
        updateRepeater.stop();
        isPlaying = false;
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

    private void editCell(Point point) {
        if (board == null) {
            return;
        }

        switch (editModePanel.mode()) {
            case EditModePanel.FLIP:
                board.flip(point.y, point.x);
                break;
            case EditModePanel.PENCIL:
                board.setLiving(point.y, point.x);
                break;
            case EditModePanel.ERASER:
                board.setDead(point.y, point.x);
                break;
            default:
                throw new IllegalStateException();
        }

        boardDisplayPanel.display(board);
    }

    public void setBoard(@NotNull Board board) {
        this.board = board;
        boardDisplayPanel.display(board);
    }
}
