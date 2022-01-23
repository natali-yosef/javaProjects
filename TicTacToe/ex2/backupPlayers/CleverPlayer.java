import java.util.Random;

public class CleverPlayer implements Player {
    private static final int[][] ARRAY_DIRECTIONS = {{0, 1}, {0, -1},
            {1, 0}, {1, 1}, {1, -1},
            {-1, 0}, {-1, 1}, {-1, -1}};


    private int[][] addToArray(int lenArray, int[][] originalArr, int[] item) {
        int[][] newarr = new int[lenArray + 1][2];
        for (int i = 0; i < lenArray; i++) {
            newarr[i][0] = originalArr[i][0];
            newarr[i][1] = originalArr[i][1];
        }
        newarr[lenArray][0] = item[0];
        newarr[lenArray][1] = item[1];
        return newarr;
    }

    private int[] checkAllDirections(Board board, Mark mark, int row, int col) {
        for (int[] direction : ARRAY_DIRECTIONS) {
            if (isStreak(board, mark, row, col, direction[0], direction[1]))
                return new int[]{row + (direction[0] * (Board.WIN_STREAK - 1)), col + (direction[1] * (Board.WIN_STREAK - 1))};
        }
        return null;
    }

    private boolean isStreak(Board board, Mark mark, int row, int col, int directionRow, int directionCol) {
        for (int i = 0; i < Board.WIN_STREAK - 1; i++) {
            int[] currentSquare = {row + (directionRow * i), col + (directionCol * i)};
            Mark currentMark = board.getMark(currentSquare[0], currentSquare[1]);
            if (currentMark != mark) {
                return false;
            }
        }
        int[] currentSquare = {row + (directionRow * (Board.WIN_STREAK - 1)), col + (directionCol * (Board.WIN_STREAK - 1))};
        if (row < 0 || col < 0 || Board.SIZE - 1 < row || Board.SIZE - 1 < col) {
            return false;
        }
        Mark currentMark = board.getMark(currentSquare[0], currentSquare[1]);
        return currentMark == Mark.BLANK;

    }

    @Override
    public void playTurn(Board board, Mark mark) {
        int[] coorToFill;
        Mark enemyMark;
        int random;
        random = new Random().nextInt(100);
        WhateverPlayer whateverPlayer = new WhateverPlayer();
        if(random < 20) {
            if (mark == Mark.O) {
                enemyMark = Mark.X;
            } else {
                enemyMark = Mark.O;
            }
            for (int row = 0; row < Board.SIZE; row++) {
                for (int col = 0; col < Board.SIZE; col++) {
                    coorToFill = checkAllDirections(board, enemyMark, row, col);
                    if (coorToFill != null) {
                        if (board.putMark(mark, coorToFill[0], coorToFill[1])) {
                            return;
                        }
                    }
                }
            }
        }
        whateverPlayer.playTurn(board, mark);

    }

}
