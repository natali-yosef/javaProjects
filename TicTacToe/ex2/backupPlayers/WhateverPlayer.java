import java.util.Arrays;
import java.util.Random;

class WhateverPlayer implements Player {

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

    @Override
    public void playTurn(Board board, Mark mark) {
        int randomChoice;
        int[][] emptyCoordinates = coordinatesByMark(board, Mark.BLANK);
        randomChoice = new Random().nextInt(emptyCoordinates.length);
        board.putMark(mark, emptyCoordinates[randomChoice][0], emptyCoordinates[randomChoice][1]);
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
}


