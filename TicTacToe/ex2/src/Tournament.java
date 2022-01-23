import static java.lang.System.exit;

public class Tournament {
    private static final String SCORE_MESSEGE = "=== player 1: %d | player 2: %d |Draws: %d ===";

    private final int rounds;
    private final Renderer renderer;
    private final Player[] players;

    /**
     * Tournament constructor
     *
     * @param rounds   - num of rounds to play
     * @param renderer - the renderer
     * @param players  - the players
     */
    Tournament(int rounds, Renderer renderer, Player[] players) {
        this.rounds = rounds;
        this.renderer = renderer;
        this.players = players;
    }

    /**
     * run the game rounds time and print the score
     */
    public void playTournament() {
        int roundIndex = 0;
        int[] score = {0, 0, 0};
        Game game;
        while (roundIndex < this.rounds) {
            if (roundIndex % 2 == 0) {
                game = new Game(this.players[0], this.players[1], this.renderer);
            } else {
                game = new Game(this.players[1], this.players[0], this.renderer);
            }
            Mark winner = game.run();
            if (winner == Mark.BLANK) {
                score[2]++;
            }
            if (winner == Mark.O) {
                if (roundIndex % 2 == 0) {
                    score[0]++;
                } else {
                    score[1]++;
                }
            }
            if (winner == Mark.X) {
                if (roundIndex % 2 == 0) {
                    score[1]++;
                } else {
                    score[0]++;
                }
            }

            roundIndex++;

        }
        System.out.println(String.format(SCORE_MESSEGE, score[0], score[1], score[2]));

    }


    public static void main(String[] args) {
        final int ARGS_NUM = 4;
        final int EXIT_CODE = -1;
        final int MIN_ROUNDS = 0;
        final int ROUNDS_PLACE = 0;
        final int RENDERER_PLACE = 1;
        final int FIRST_PLAYER_PLACE = 2;
        final int SECOND_PLAYER_PLACE = 3;
        final String ERROR_ARGS = "please enter this format: java Tournament [round count]" +
                " [render target: console/none] [player: human/clever/whatever/snartypamts]" +
                " [player: human/clever/whatever/snartypamts]";
        final String ERROR_ROUNDS = "please enter positive number of rounds";

        if (args.length != ARGS_NUM) {
            System.err.println(ERROR_ARGS);
            exit(EXIT_CODE);
        }
        int rounds = Integer.parseInt(args[ROUNDS_PLACE]);
        if (rounds < MIN_ROUNDS) {
            System.err.println(ERROR_ROUNDS);
            exit(EXIT_CODE);
        }
        PlayerFactory playerFactory = new PlayerFactory();
        Player[] players = {playerFactory.buildPlayer(args[FIRST_PLAYER_PLACE]),
                playerFactory.buildPlayer(args[SECOND_PLAYER_PLACE])};

        RendererFactory rendererFactory = new RendererFactory();
        Renderer renderer = rendererFactory.buildRenderer(args[RENDERER_PLACE]);

        if (renderer == null || players[0] == null || players[1] == null) {
            System.err.println(ERROR_ARGS);
            exit(EXIT_CODE);
        }

        Tournament tournament = new Tournament(rounds, renderer, players);
        tournament.playTournament();

    }
}
