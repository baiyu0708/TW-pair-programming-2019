package board;

import java.awt.*;
import java.awt.image.BufferedImage;

import static board.Board.DEAD;
import static board.Board.LIVING;

public class BoardIO {

    public static Board readState(BufferedImage image) {
        int[][] state = new int[image.getHeight()][image.getWidth()];
        for (int row = 0; row < image.getHeight(); row++) {
            for (int column = 0; column < image.getWidth(); column++) {
                boolean isAlive = image.getRGB(column, row) != Color.white.getRGB();
                state[row][column] = isAlive ? LIVING : DEAD;
            }
        }

        return new Board(state);
    }
}
