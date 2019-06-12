package board;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class BoardTest {

    @Test
    public void given_initial_state_then_state_can_be_queried() {
        int[][] initialState = {
                {0, 0, 1},
                {0, 1, 1},
                {1, 0, 1}
        };

        Board board = new Board(initialState);

        assertArrayEquals(initialState, board.state());
    }

    @Test
    public void living_cell_with_2_neighbours_will_live() {
        Board board = new Board(new int[][]{
                {1, 0, 1},
                {0, 1, 0},
                {0, 0, 0}
        });

        board.evolve();

        assertEquals(1, board.state()[1][1]);
    }

    @Test
    public void dead_cell_with_2_neighbours_will_die() {
        Board board = new Board(new int[][]{
                {1, 0, 1},
                {0, 0, 0},
                {0, 0, 0}
        });

        board.evolve();

        assertEquals(0, board.state()[1][1]);
    }

    @Test
    public void living_cell_with_3_neighbours_will_live() {
        Board board = new Board(new int[][]{
                {1, 0, 1},
                {0, 1, 0},
                {0, 0, 1}
        });

        board.evolve();

        assertEquals(1, board.state()[1][1]);
    }

    @Test
    public void dead_cell_with_3_neighbours_will_live() {
        Board board = new Board(new int[][]{
                {1, 0, 1},
                {0, 0, 0},
                {0, 0, 1}
        });

        board.evolve();

        assertEquals(1, board.state()[1][1]);
    }

    @Test
    public void living_cell_with_less_than_2_neighbours_will_die() {
        Board board = new Board(new int[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 0}
        });

        board.evolve();

        assertEquals(0, board.state()[1][1]);
    }

    @Test
    public void dead_cell_with_less_than_2_neighbours_will_die() {
        Board board = new Board(new int[][]{
                {1, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        });

        board.evolve();

        assertEquals(0, board.state()[1][1]);
    }

    @Test
    public void living_cell_with_more_than_3_neighbours_will_die() {
        Board board = new Board(new int[][]{
                {1, 0, 1},
                {0, 1, 1},
                {0, 0, 1}
        });

        board.evolve();

        assertEquals(0, board.state()[1][1]);
    }

    @Test
    public void dead_cell_with_more_than_3_neighbours_will_die() {
        Board board = new Board(new int[][]{
                {1, 0, 1},
                {0, 0, 1},
                {0, 0, 1}
        });

        board.evolve();

        assertEquals(0, board.state()[1][1]);
    }

    @Test
    public void boundary() {
        Board board = new Board(new int[][]{
                {1, 0, 1},
                {0, 0, 0},
                {0, 0, 1}
        });

        board.evolve();

        assertArrayEquals(new int[][]{
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0}
        }, board.state());
    }

    @Test
    public void flip() {
        Board board = new Board(new int[][]{
                {1, 0, 1},
                {0, 0, 0},
                {0, 0, 1}
        });

        board.flip(1, 2);

        assertArrayEquals(new int[][]{
                {1, 0, 1},
                {0, 0, 1},
                {0, 0, 1}
        }, board.state());
    }
}
