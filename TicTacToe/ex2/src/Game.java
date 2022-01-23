
public class Game {

    private final Player playerO;
    private final Player playerX;
    private final Renderer renderer;

    /**
     * game constructor
     * @param playerO - player with mark O
     * @param playerX - player with mark X
     * @param renderer - the renderer for the game
     */
    Game(Player playerO, Player playerX, Renderer renderer){
        this.playerO = playerO;
        this.playerX = playerX;
        this.renderer = renderer;
    }

    /**
     * runs the game
     * @return the winner mark
     */
    public Mark run(){
        Board board = new Board();
        int sum = 0;
        Mark winner = Mark.BLANK;

        while (!board.gameEnded() && winner == Mark.BLANK){
            renderer.renderBoard(board);

            if(sum%2 == 0){
                playerO.playTurn(board,Mark.O);
            }
            else {
                playerX.playTurn(board,Mark.X);
            }

            sum++;
            winner = board.getWinner();
        }
        renderer.renderBoard(board);

        return winner;

    }

}
