package ui.load;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
        images.add(loadImageOrNull(new File("res/load/新建.png")));
        for (File file : imageFiles) {
            images.add(loadImageOrNull(file));
        }
        return images;
    }

    private static BoardItem loadImageOrNull(File file) {
        try {
            return new BoardItem(ImageIO.read(file), file.getName());
        } catch (IOException e) {
            return new BoardItem(null, file.getName());
        }
    }
}
