package app;

import ui.display.BoardUI;
import ui.load.BoardDesc;
import ui.load.LoadUI;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class GameOfLifeApp {

    private final JFrame loadWindow;
    private final JFrame boardWindow;
    private final BoardUI boardUI;

    public GameOfLifeApp(File boardsDir) throws IOException {
        loadWindow = createWindow(new LoadUI(boardsDir, this::jumpToBoardUI), 1024, 768);

        boardUI = new BoardUI();
        boardWindow = createWindow(boardUI, 1600, 900);

        loadWindow.setVisible(true);
    }

    private static JFrame createWindow(JPanel panel, int width, int height) {
        JFrame window = new JFrame();
        window.setSize(width, height);
        window.add(panel);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return window;
    }

    private void jumpToBoardUI(BoardDesc board) {
        assert board.board != null;

        boardWindow.setTitle(board.name);
        boardUI.setBoard(board.board);

        loadWindow.setVisible(false);
        boardWindow.setVisible(true);
    }
}
