package ui.load;

import board.Board;
import board.BoardIO;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class LoadUI extends JPanel {


    LoadUI(File boardsDir, OnSelectBoard onSelectBoard) throws IOException {
        setLayout(new BorderLayout());
        add(new SelectPanel(loadImages(boardsDir), onSelectBoard));
    }

    private ArrayList<BoardDesc> loadImages(File boardsDir) throws IOException {
        File[] imageFiles = boardsDir.listFiles();
        if (imageFiles == null) {
            throw new IOException("Not a directory: " + boardsDir.getAbsolutePath());
        }

        ArrayList<BoardDesc> images = new ArrayList<>(imageFiles.length);
        images.add(new BoardDesc(emptyBoard(), "新建"));
        for (File file : imageFiles) {
            images.add(loadImageOrNull(file));
        }
        return images;
    }

    private Board emptyBoard() {
        return new Board(new int[5][5]);
    }

    private static BoardDesc loadImageOrNull(File file) {
        try {
            return new BoardDesc(BoardIO.readState(ImageIO.read(file)), file.getName());
        } catch (IOException e) {
            return new BoardDesc(null, file.getName());
        }
    }
}
