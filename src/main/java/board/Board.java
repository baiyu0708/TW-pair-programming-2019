package board;

class Board {

    /**
     * 行主序。元素[row][column]表示第row行、第column列的细胞，0-死，1-活
     * 如果不在board范围内（即越界），则必然为死
     */
    private int[][] state;

    Board(int[][] initialState) {
        state = initialState;
    }

    int[][] state() {
        return state;
    }

    void evolve() {
        int[][] newState = new int[rowCount()][columnCount()];

        for (int row = 0; row < rowCount(); row++) {
            for (int column = 0; column < columnCount(); column++) {
                newState[row][column] = nextState(row, column);
            }
        }

        state = newState;
    }

    private int nextState(int row, int column) {
        boolean isAlive = state(row, column) == 1;
        if (isAlive && neighbourCount(row, column) == 2) {
            return state(row, column);
        }
        return 0;
    }

    private int neighbourCount(int row, int column) {
        return state(row - 1, column - 1) + state(row - 1, column) + state(row - 1, column + 1)
                + state(row, column - 1) + state(row, column + 1)
                + state(row + 1, column - 1) + state(row + 1, column) + state(row + 1, column + 1);
    }

    private int state(int row, int column) {
        if (row < 0 || row >= rowCount()) {
            return 0;
        }
        if (column < 0 || column >= columnCount()) {
            return 0;
        }
        return state[row][column];
    }

    private int rowCount() {
        return state.length;
    }

    private int columnCount() {
        return state[0].length;
    }
}
