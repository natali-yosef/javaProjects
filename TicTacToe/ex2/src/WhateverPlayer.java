import java.util.Random;

class WhateverPlayer implements Player {


    /**
     * play turn of whatever player
     * @param board - board to play on
     * @param mark  - the mark to add
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        int randomChoice;
        int[][] emptyCoordinates = coordinatesByMark(board, Mark.BLANK);
        // random from all the empty cells in the board
        randomChoice = new Random().nextInt(emptyCoordinates.length);
        board.putMark(mark, emptyCoordinates[randomChoice][0], emptyCoordinates[randomChoice][1]);
    }

    /**
     * @param board - the board
     * @param mark  - the mark
     * @return int[] type array of all the coordinates in the board with the mark 'mark'
     */
    private int[][] coordinatesByMark(Board board, Mark mark) {
        int[][] markCoordinates = new int[0][2];
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                // for every cell in board
                if (board.getMark(row, col) == mark) {
                    markCoordinates = addToArray(markCoordinates, new int[]{row, col});
                }
            }
        }
        return markCoordinates;
    }

    /**
     * adding int[] object to int[] type array
     * @param originalArr - the original array
     * @param item        - the item to add
     * @return a new array with the item in it
     */
    private int[][] addToArray(int[][] originalArr, int[] item) {
        int lenArray = originalArr.length;
        int[][] newarr = new int[lenArray + 1][2];
        for (int i = 0; i < lenArray; i++) {
            newarr[i][0] = originalArr[i][0];
            newarr[i][1] = originalArr[i][1];
        }
        newarr[lenArray][0] = item[0];
        newarr[lenArray][1] = item[1];
        return newarr;
    }
}


