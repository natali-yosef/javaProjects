public class Board {

    public static final int SIZE = 4;
    public static final int WIN_STREAK = 4;
    private static final int[][] ARRAY_DIRECTIONS = {{0, 1}, {0, -1},
            {1, 0}, {1, 1}, {1, -1},
            {-1, 0}, {-1, 1}, {-1, -1}};

    private final Mark[][] boardArray;

    /**
     * bord constructor
     */
    public Board() {
        boardArray = new Mark[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                boardArray[row][col] = Mark.BLANK;
            }
        }
    }

    /**
     *
     * @param row - row of the coordinate to check
     * @param col - col of the coordinate to check
     * @return true if the coordinate in board, false otherwise
     */
    private boolean checkInBoard(int row, int col) {
        return !(row < 0 || col < 0 || SIZE - 1 < row || SIZE - 1 < col);
    }

    /**
     *  add mark to board
     * @param mark - mark to add to board
     * @param row - row of the coordinate to add to
     * @param col - col of the coordinate to add to
     * @return true if added mark to board, false otherwise
     */
    public boolean putMark(Mark mark, int row, int col) {
        if (!checkInBoard(row, col)) {
            return false;
        }
        // if cell is empty
        if (getMark(row, col) != Mark.BLANK) {
            return false;
        }
        this.boardArray[row][col] = mark;
        return true;
    }

    /**
     * getter function for mark in coordinate
     * @param row - row of the coordinate
     * @param col - col of the coordinate
     * @return the mark in the coordinate, BLANK if the coordinate is illegal
     */
    public Mark getMark(int row, int col) {
        if (!checkInBoard(row, col)) {
            return Mark.BLANK;
        }
        return this.boardArray[row][col];
    }

    /**
     * check if the game ended
     * @return true if the board is full, false otherwise
     */
    public boolean gameEnded() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (getMark(row, col) == Mark.BLANK) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * check if there is streak
     * @return return the winner mark, else return BLANK
     */
    public Mark getWinner() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Mark mark = getMark(row, col);
                if (mark != Mark.BLANK && checkAllDirections(mark, row, col)) {
                    return mark;
                }
            }
        }
        return Mark.BLANK;

    }

    /**
     *  check for every direction if there is streak of marks
     * @param mark - mark to check
     * @param row - row of the coordinate to check
     * @param col - col of the coordinate to check
     * @return true if there is streak, else false
     */
    private boolean checkAllDirections(Mark mark, int row, int col) {
        for (int[] direction : ARRAY_DIRECTIONS) {
            if (isStreak(mark, row, col, direction[0], direction[1]))
                return true;
        }
        return false;
    }

    /**
     * check if there is streak of marks in that direction from that coordinate
     * @param mark - mark to check
     * @param row - row of the coordinate to check from
     * @param col - col of the coordinate to check from
     * @param directionRow - direction row to check
     * @param directionCol - direction col to check
     * @return true is there is streak, else false
     */
    private boolean isStreak(Mark mark, int row, int col, int directionRow, int directionCol) {
        for (int i = 0; i < WIN_STREAK; i++) {
            int[] currentSquare = {row + (directionRow * i), col + (directionCol * i)};
            if (!checkInBoard(currentSquare[0], currentSquare[1])) {
                return false;
            }
            if (getMark(currentSquare[0], currentSquare[1]) != mark) {
                return false;
            }
        }
        return true;

    }

}
