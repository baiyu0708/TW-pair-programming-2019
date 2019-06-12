package board;

import org.junit.Test;
import util.file.CodeDir;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;

public class BoardIOTest {

    @Test
    public void load_board_state_from_file() throws IOException {
        CodeDir root = new CodeDir(CodeDir.MAVEN_TEST);
        File stateFile = root.child("state.png");

        int[][] state = BoardIO.read(stateFile);

        assertArrayEquals(new int[][]{
                {0, 0, 0, 0},
                {0, 1, 1, 1},
                {1, 1, 1, 0},
                {0, 0, 0, 0}
        }, state);
    }
}
