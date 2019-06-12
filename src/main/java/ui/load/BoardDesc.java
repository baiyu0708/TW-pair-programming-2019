package ui.load;

import board.Board;
import org.jetbrains.annotations.Nullable;

public class BoardDesc {

    @Nullable
    public final Board board;
    public final String name;

    BoardDesc(@Nullable Board board, String name) {
        this.board = board;
        this.name = name;
    }
}
