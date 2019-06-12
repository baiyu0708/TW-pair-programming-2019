package board;

public class Board {

    static final int LIVING = 1;
    static final int DEAD = 0;

    /**
     * 行主序。元素[row][column]表示第row行、第column列的细胞，0-死，1-活
     * 如果不在board范围内（即越界），则必然为死
     */
    private int[][] state;

    public Board(int[][] initialState) {
        state = initialState;
    }

    int[][] state() {
        return state;
    }

    public void evolve() {
        int[][] newState = new int[rowCount()][columnCount()];

        for (int row = 0; row < rowCount(); row++) {
            for (int column = 0; column < columnCount(); column++) {
                newState[row][column] = nextState(row, column);
            }
        }

        state = newState;
    }

    private int nextState(int row, int column) {
        boolean isAlive = isLiving(row, column);

        if (isAlive && neighbourCount(row, column) == 2) {
            return state(row, column);
        }

        if (neighbourCount(row, column) == 3) {
            return LIVING;
        }

        return DEAD;
    }

    private int neighbourCount(int row, int column) {
        return state(row - 1, column - 1) + state(row - 1, column) + state(row - 1, column + 1)
                + state(row, column - 1) + state(row, column + 1)
                + state(row + 1, column - 1) + state(row + 1, column) + state(row + 1, column + 1);
    }

    private int state(int row, int column) {
        if (row < 0 || row >= rowCount()) {
            return DEAD;
        }
        if (column < 0 || column >= columnCount()) {
            return DEAD;
        }
        return state[row][column];
    }

    public boolean isLiving(int row, int column) {
        return state(row, column) == LIVING;
    }

    public int rowCount() {
        return state.length;
    }

    public int columnCount() {
        return state[0].length;
    }
}
