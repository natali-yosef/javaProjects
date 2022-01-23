import java.util.Random;

public class SnartypamtsPlayer implements Player {
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

    private int[][] coordinatesByMark(Board board, Mark mark) {
        int[][] emptyCoordinates = new int[0][2];
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                if (board.getMark(row, col) == mark) {
                    emptyCoordinates = addToArray(emptyCoordinates.length, emptyCoordinates, new int[]{row, col});
                }
            }
        }
        return emptyCoordinates;
    }

    @Override
    public void playTurn(Board board, Mark mark) {
        int randomChoice, row, col, rowDirection, colDirection;
        int[][] markCoordinates = coordinatesByMark(board, mark);
        if (markCoordinates.length == 0) {
            WhateverPlayer whateverPlayer = new WhateverPlayer();
            whateverPlayer.playTurn(board, mark);
            return;
        }
        for (int[] coordinate : markCoordinates) {
//            randomChoice = new Random().nextInt(markCoordinates.length);
            row = coordinate[0];
            col = coordinate[1];
            for (int[] arrayDirection : ARRAY_DIRECTIONS) {
                rowDirection = arrayDirection[0];
                colDirection = arrayDirection[1];
                if (board.putMark(mark, row + rowDirection, col + colDirection)) {
                    return;
                }
            }
        }
        CleverPlayer cleverPlayer = new CleverPlayer();
        cleverPlayer.playTurn(board, mark);
    }
}
