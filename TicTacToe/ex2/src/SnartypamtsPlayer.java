public class SnartypamtsPlayer implements Player {
    private static final int[][] ARRAY_DIRECTIONS = {{0, 1}, {0, -1},
            {1, 0}, {1, 1}, {1, -1},
            {-1, 0}, {-1, 1}, {-1, -1}};

    /***
     * play turn of snartypamts player
     * @param board- the board to play on
     * @param mark - the player mark
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        Mark enemyMark;
        WhateverPlayer whateverPlayer = new WhateverPlayer();
        // define the enemy mark
        if (mark == Mark.X) {
            enemyMark = Mark.O;
        } else {
            enemyMark = Mark.X;
        }
        //check if the player can win with adding one mark, and add it if so
        if (completeSequence(board, Board.WIN_STREAK-1, mark, mark)) {
            return;
        }
        // check if the enemy can win with adding one mark, and block him if so
        if (completeSequence(board, Board.WIN_STREAK-1, enemyMark, mark)) {
            return;
        }
        // adding one mark for the longest sequence
        for (int index = Board.WIN_STREAK - 1; 0 < index; index--) {
            if (completeSequence(board, index, mark, mark)) {
                return;
            }
        }
        // if could not add any mark, play like whatever
        whateverPlayer.playTurn(board, mark);


    }

    /***
     *  add mark to the board if there is sequence of markToCheck
     * @param board - the board to check
     * @param sequenceLen - len of sequence to check
     * @param markToCheck - mark to check
     * @param mark - mark to add to board
     * @return true if added mark to board, false otherwise
     */
    private boolean completeSequence(Board board, int sequenceLen, Mark markToCheck, Mark mark) {
        int[] coorToMark;
        // for every cell in board
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                // get the coordinate that will complete the sequence
                coorToMark = checkDirections(board, sequenceLen, markToCheck, row, col);
                if (coorToMark != null && board.putMark(mark, coorToMark[0], coorToMark[1])) {
                    // if added mark to board
                    return true;
                }
            }
        }
        // if couldn't complete
        return false;

    }

    /***
     * check for every direction if there is streak of marks
     * @param board - board to check
     * @param sequenceLen - len of the sequence to check
     * @param mark - mark to check
     * @param row - row of the coordinate to check
     * @param col - col of the coordinate to check
     * @return the coordinate that complete the sequence, else null
     */
    private int[] checkDirections(Board board, int sequenceLen, Mark mark, int row, int col) {
        int[] coorToMark;
        // for every direction
        for (int[] direction : ARRAY_DIRECTIONS) {
            if (isStreak(board, mark, row, col, direction[0], direction[1], sequenceLen)) {
                coorToMark = new int[]{row + (direction[0] * (sequenceLen)),
                                        col + (direction[1] * (sequenceLen))};
                //check if can complete to sequenceLen+1 streak
                if (!(coorToMark[0] < 0 || coorToMark[1] < 0 || Board.SIZE - 1 < coorToMark[0]
                        || Board.SIZE - 1 < coorToMark[1])) {
                    if (board.getMark(coorToMark[0], coorToMark[1]) == Mark.BLANK) {
                        return coorToMark;
                    }
                }
            }

        }
        return null;
    }

    /***
     * check if there is streak of marks in that direction from that coordinate
     * @param board - board to check
     * @param mark - mark to check
     * @param row - row of the coordinate to check from
     * @param col - col of the coordinate to check from
     * @param directionRow - direction row to check
     * @param directionCol - direction col to check
     * @param sequenceLen - len to check
     * @return true if there is streak, false otherwise
     */
    private boolean isStreak(Board board, Mark mark, int row, int col, int directionRow, int directionCol,
                             int sequenceLen) {
        for (int i = 0; i < sequenceLen; i++) {
            int[] currentSquare = {row + (directionRow * i), col + (directionCol * i)};
            // get mark of the new coordinate
            Mark currentMark = board.getMark(currentSquare[0], currentSquare[1]);
            if (currentMark != mark) {
                // not streak
                return false;
            }
        }
        return true;
    }
}