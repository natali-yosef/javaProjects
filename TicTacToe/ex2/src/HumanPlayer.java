
import java.util.Scanner;
public class HumanPlayer implements Player {

    private final Scanner scanner = new Scanner(System.in);
    private static final String INVALID_INPUT = "please enter only numbers";
    private static final String INVALID_COORDINATES = "please enter current coordinates";
    private static final String GET_COORDINATES_MESSEGE = "Player: %s type coordinates ";


    /**
     * play turn human player
     * @param board - board to play on
     * @param mark - mark to add
     */
    public void playTurn(Board board, Mark mark){

        boolean done = false;
        String playerChoice;
        int choiceInt;

        while(!done){
            System.out.print(String.format(GET_COORDINATES_MESSEGE,mark));
            playerChoice = scanner.nextLine();
            try{
                choiceInt = Integer.parseInt(playerChoice);
            }
            catch (NumberFormatException n) {
                System.out.println(INVALID_INPUT);
                continue;
            }

            if(99 < choiceInt || choiceInt < 0){
                System.out.println(INVALID_COORDINATES);
                continue;
            }

            int row = choiceInt/10;
            int col = choiceInt%10;

            if(!board.putMark(mark,row-1,col-1)){
                System.out.println(INVALID_COORDINATES);
                continue;
            }
            done = true;
        }


    }
}


