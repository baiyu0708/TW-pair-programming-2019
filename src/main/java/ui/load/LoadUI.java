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
    private JButton prevButton;
    private JButton nextButton;

    private final File boardsDir;

    public LoadUI(File boardsDir, Consumer<BoardDesc> onSelectBoard) throws IOException {
        this.boardsDir = boardsDir;

        setLayout(new BorderLayout());
        selectPanel = new SelectPanel(loadImages(this.boardsDir), onSelectBoard);
        add(selectPanel);
        add(controlPanel(), BorderLayout.SOUTH);
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

    private JPanel controlPanel() {
        prevButton = new JButton("上一页");
        prevButton.addActionListener(e->{
            selectPanel.prevPage();
            updatePageButton();
        });

        nextButton = new JButton("下一页");
        nextButton.addActionListener(e -> {
            selectPanel.nextPage();
            updatePageButton();
        });

        JPanel panel = new JPanel();
        panel.add(prevButton);
        panel.add(nextButton);
        updatePageButton();
        return panel;
    }

    public void reload() {
        try {
            selectPanel.setItems(loadImages(boardsDir));
        } catch (IOException ignored) {
        }
        updatePageButton();
    }

    private void updatePageButton() {
        prevButton.setEnabled(selectPanel.hasPrevPage());
        nextButton.setEnabled(selectPanel.hasNextPage());
    }
}
