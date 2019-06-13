package ui.load;

import board.Board;
import board.BoardImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

public class LoadUI extends JPanel {

    private final SelectPanel selectPanel;
    private final File boardsDir;

    public LoadUI(File boardsDir, Consumer<BoardDesc> onSelectBoard) throws IOException {
        setLayout(new BorderLayout());
        this.boardsDir = boardsDir;
        selectPanel = new SelectPanel(loadImages(this.boardsDir), onSelectBoard);
        add(selectPanel);
    }

    private ArrayList<BoardDesc> loadImages(File boardsDir) throws IOException {
        File[] imageFiles = boardsDir.listFiles();
        if (imageFiles == null) {
            throw new IOException("Not a directory: " + boardsDir.getAbsolutePath());
        }

        ArrayList<BoardDesc> images = new ArrayList<>(imageFiles.length);
        images.add(new BoardDesc(emptyBoard(), "新建"));
        for (File file : imageFiles) {
            images.add(loadOrNull(file));
        }
        return images;
    }

    private Board emptyBoard() {
        return new Board(new int[5][5]);
    }

    private static BoardDesc loadOrNull(File file) {
        try {
            return new BoardDesc(BoardImage.parse(ImageIO.read(file)), file.getName());
        } catch (IOException e) {
            return new BoardDesc(null, file.getName());
        }
    }

    public void reload(){
        try {
            selectPanel.setItems(loadImages(boardsDir));
        } catch (IOException ignored) {
        }
    }
}
