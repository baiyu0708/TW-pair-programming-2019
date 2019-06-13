package board;

import java.awt.*;
import java.awt.image.BufferedImage;

import static board.Board.DEAD;
import static board.Board.LIVING;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class BoardImage {

    private static final Color DEAD_CELL_COLOR = Color.white;
    private static final Color LIVING_CELL_COLOR = Color.black;

    public static Board parse(BufferedImage image) {
        int[][] state = new int[image.getHeight()][image.getWidth()];
        for (int row = 0; row < image.getHeight(); row++) {
            for (int column = 0; column < image.getWidth(); column++) {
                boolean isAlive = image.getRGB(column, row) != DEAD_CELL_COLOR.getRGB();
                state[row][column] = isAlive ? LIVING : DEAD;
            }
        }

        return new Board(state);
    }

    public static BufferedImage encode(Board board) {
        BufferedImage image = new BufferedImage(board.columnCount(), board.rowCount(), TYPE_INT_RGB);
        for (int row = 0; row < board.rowCount(); row++) {
            for (int column = 0; column < board.columnCount(); column++) {
                Color color = board.isLiving(row, column) ? LIVING_CELL_COLOR : DEAD_CELL_COLOR;
                image.setRGB(column, row, color.getRGB());
            }
        }
        return image;
    }
}
