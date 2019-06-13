package board;

import org.junit.Test;
import util.file.CodeDir;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class BoardImageTest {

    @Test
    public void parse_board_from_image() throws IOException {
        CodeDir root = new CodeDir(CodeDir.MAVEN_TEST);
        BufferedImage image = ImageIO.read(root.child("state.png"));

        Board board = BoardImage.parse(image);

        assertArrayEquals(new int[][]{
                {0, 0, 0, 0},
                {0, 1, 1, 1},
                {1, 1, 1, 0},
                {0, 0, 0, 0}
        }, board.state());
    }

    @Test
    public void encode_board_as_image() throws IOException {
        Board board = new Board(new int[][]{
                {0, 0, 0, 0},
                {0, 1, 1, 1},
                {1, 1, 1, 0},
                {0, 0, 0, 0}
        });

        BufferedImage image = BoardImage.encode(board);

        assertEquals(image.getRGB(0, 0), Color.white.getRGB());
        assertEquals(image.getRGB(1, 0), Color.white.getRGB());
        assertEquals(image.getRGB(2, 0), Color.white.getRGB());
        assertEquals(image.getRGB(3, 0), Color.white.getRGB());
        assertEquals(image.getRGB(0, 1), Color.white.getRGB());
        assertEquals(image.getRGB(1, 1), Color.black.getRGB());
        assertEquals(image.getRGB(2, 1), Color.black.getRGB());
        assertEquals(image.getRGB(3, 1), Color.black.getRGB());
        assertEquals(image.getRGB(0, 2), Color.black.getRGB());
        assertEquals(image.getRGB(1, 2), Color.black.getRGB());
        assertEquals(image.getRGB(2, 2), Color.black.getRGB());
        assertEquals(image.getRGB(3, 2), Color.white.getRGB());
        assertEquals(image.getRGB(0, 3), Color.white.getRGB());
        assertEquals(image.getRGB(1, 3), Color.white.getRGB());
        assertEquals(image.getRGB(2, 3), Color.white.getRGB());
        assertEquals(image.getRGB(3, 3), Color.white.getRGB());
    }
}
