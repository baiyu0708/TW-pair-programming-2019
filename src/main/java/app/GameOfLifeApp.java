package app;

import board.Board;
import board.BoardImage;
import ui.board.BoardUI;
import ui.load.BoardDesc;
import ui.load.LoadUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameOfLifeApp {

    private final JFrame loadWindow;
    private final LoadUI loadUI;
    private final JFrame boardWindow;
    private final BoardUI boardUI;

    private final File boardsDir;

    public GameOfLifeApp(File boardsDir) throws IOException {
        this.boardsDir = boardsDir;

        loadUI = new LoadUI(boardsDir, this::jumpToBoardUI);
        loadWindow = createWindow(loadUI, 1024, 768);
        boardUI = new BoardUI(this::save, this::jumpToLoadUI);
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

        loadWindow.setVisible(false);

        boardWindow.setTitle(board.name);
        boardUI.setBoard(board.board);
        boardWindow.setVisible(true);
    }

    private void jumpToLoadUI() {
        boardWindow.setVisible(false);
        loadUI.reload();
        loadWindow.setVisible(true);
    }

    private void save(Board board) {
        JFileChooser fileChooser = new JFileChooser(boardsDir);
        fileChooser.setFileFilter(new PngFileFilter());
        fileChooser.showDialog(boardWindow, "保存");

        File file = fileChooser.getSelectedFile();
        if (file == null) {
            return;
        }

        file = asPng(file);
        save(board, file);
    }

    private void save(Board board, File path) {
        BufferedImage image = BoardImage.encode(board);
        try {
            ImageIO.write(image, "png", path);
        } catch (IOException ignored) {
        }
    }

    private File asPng(File file) {
        if (!isPng(file)) {
            file = new File(file.getAbsolutePath() + ".png");
        }
        return file;
    }

    private static boolean isPng(File f) {
        return f.getName().endsWith(".png");
    }

    private static class PngFileFilter extends FileFilter {

        @Override
        public boolean accept(File f) {
            return isPng(f);
        }

        @Override
        public String getDescription() {
            return "图像（.png）";
        }

    }
}
