package board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static board.Board.DEAD;
import static board.Board.LIVING;

public class BoardIO {

    public static int[][] read(File stateFile) throws IOException {
        BufferedImage image = ImageIO.read(stateFile);
        int[][] state = new int[image.getHeight()][image.getWidth()];
        for (int row = 0; row < image.getHeight(); row++) {
            for (int column = 0; column < image.getWidth(); column++) {
                boolean isAlive = image.getRGB(column, row) != Color.white.getRGB();
                state[row][column] = isAlive ? LIVING : DEAD;
            }
        }

        return state;
    }
}
