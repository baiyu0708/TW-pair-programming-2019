package ui.load;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

class LoadUI extends JPanel {

    LoadUI(File boardsDir) throws IOException {
        setLayout(new BorderLayout());

        SelectPanel selectPanel = new SelectPanel(loadImages(boardsDir));
        add(selectPanel);
    }

    private ArrayList<BoardItem> loadImages(File boardsDir) throws IOException {
        File[] imageFiles = boardsDir.listFiles();
        if (imageFiles == null) {
            throw new IOException("Not a directory: " + boardsDir.getAbsolutePath());
        }

        ArrayList<BoardItem> images = new ArrayList<>(imageFiles.length);
        images.add(new BoardItem(emptyImage(), "新建"));
        for (File file : imageFiles) {
            images.add(loadImageOrNull(file));
        }
        return images;
    }

    private BufferedImage emptyImage() {
        int width = 5;
        int height = 5;
        BufferedImage image = new BufferedImage(width, height, TYPE_INT_RGB);
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                image.setRGB(row, column, Color.white.getRGB());
            }
        }
        return image;
    }

    private static BoardItem loadImageOrNull(File file) {
        try {
            return new BoardItem(ImageIO.read(file), file.getName());
        } catch (IOException e) {
            return new BoardItem(null, file.getName());
        }
    }
}
