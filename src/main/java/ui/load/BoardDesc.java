package ui.load;

import board.Board;
import org.jetbrains.annotations.Nullable;

import java.awt.image.BufferedImage;

class BoardDesc {

    @Nullable
    final Board board;
    final String name;

    BoardDesc(@Nullable Board board, String name) {
        this.board = board;
        this.name = name;
    }
}
