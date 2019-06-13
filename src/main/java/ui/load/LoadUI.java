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
        images.add(new BoardDesc(emptyBoard(3, 3), "新建3*3"));
        images.add(new BoardDesc(emptyBoard(5, 5), "新建5*5"));
        images.add(new BoardDesc(emptyBoard(10, 10), "新建10*10"));
        images.add(new BoardDesc(emptyBoard(20, 20), "新建20*20"));
        images.add(new BoardDesc(emptyBoard(50, 50), "新建50*50"));
        for (File file : imageFiles) {
            images.add(loadOrNull(file));
        }
        return images;
    }

    private Board emptyBoard(int rowCount, int columnCount) {
        return new Board(new int[rowCount][columnCount]);
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
