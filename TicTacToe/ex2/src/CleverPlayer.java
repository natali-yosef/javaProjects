public class CleverPlayer implements Player {

    private static final int[][] ARRAY_DIRECTIONS = {{0, 1}, {0, -1},
            {1, 0}, {1, 1}, {1, -1},
            {-1, 0}, {-1, 1}, {-1, -1}};

    /***
     * checks if there is coordinate near that could be marked, and mark it
     * @param board - the board to mark the coordinate
     * @param row - the row of the coordinate
     * @param col - the col of the coordinate
     * @param mark - the mark
     * @return - true if marked, false else
     */
    private boolean markNearCoor(Board board, int row, int col, Mark mark) {
        int newRow, newCol;

        // ensure that the coordinate is marked in the correct mark
        if (board.getMark(row, col) != mark) {
            return false;
        }
        //check for every direction if the coordinate near could be marked
        for (int[] direction : ARRAY_DIRECTIONS) {
            newRow = row + direction[0];
            newCol = col + direction[1];
            if (board.putMark(mark, newRow, newCol)) {
                return true;
            }
        }
        return false;
    }


    /***
     * play one turn of clever player
     * @param board - the board to play on
     * @param mark - the mark of the current player
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        WhateverPlayer whateverPlayer = new WhateverPlayer();

        // for every cell in the board
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                // check if there is cell near to mark
                if (markNearCoor(board, row, col, mark)) {
                    return;
                }
            }
        }
        // if there is no sell to mark, play like whatever player
        whateverPlayer.playTurn(board, mark);
    }
}


