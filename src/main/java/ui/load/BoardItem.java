package ui.load;

import org.jetbrains.annotations.Nullable;

import java.awt.image.BufferedImage;

class BoardItem {

    @Nullable
    final BufferedImage image;
    final String name;

    BoardItem(@Nullable BufferedImage image, String name) {
        this.image = image;
        this.name = name;
    }
}
