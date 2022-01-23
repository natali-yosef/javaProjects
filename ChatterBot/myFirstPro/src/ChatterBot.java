import java.util.*;

/**
 * Base file for the ChatterBot exercise.
 * The bot's replyTo method receives a statement.
 * If it starts with the constant REQUEST_PREFIX, the bot returns
 * whatever is after this prefix. Otherwise, it returns one of
 * a few possible replies as supplied to it via its constructor.
 * In this case, it may also include the statement after
 * the selected reply (coin toss).
 *
 * @author Dan Nirel
 */
class ChatterBot {
    static final String REQUEST_PREFIX = "say ";
    static final String REQUESTED_PHRASE_PLACEHOLDER = "<phrase>";
    static final String ILLEGAL_REQUEST_PLACEHOLDER = "<request>";


    Random rand = new Random();
    String[] repliesToLegalRequest;
    String[] repliesToIllegalRequest;
    String name;

    /**
     * constructor for bot object
     *
     * @param name                    - the bot name
     * @param repliesToLegalRequest   - options to answer for legal statements
     * @param repliesToIllegalRequest - options to answer for illegal statements
     */
    ChatterBot(String name, String[] repliesToLegalRequest, String[] repliesToIllegalRequest) {
        this.name = name;
        this.repliesToLegalRequest = new String[repliesToLegalRequest.length];
        for (int i = 0; i < repliesToLegalRequest.length; i++) {
            this.repliesToLegalRequest[i] = repliesToLegalRequest[i];
        }
        this.repliesToIllegalRequest = new String[repliesToIllegalRequest.length];
        for (int i = 0; i < repliesToIllegalRequest.length; i++) {
            this.repliesToIllegalRequest[i] = repliesToIllegalRequest[i];
        }
    }

    /**
     * geter function to name
     *
     * @return the name of the bot
     */
    String getName() {
        return this.name;
    }

    /**
     * @param statement - the statement to reply to
     * @return the answer
     */
    String replyTo(String statement) {

        if (statement.startsWith(REQUEST_PREFIX)) {
            //if there is prefix its legal statement
            return replacePlaceholderInARandomPattern(repliesToLegalRequest, REQUESTED_PHRASE_PLACEHOLDER, statement);
        }
        return replacePlaceholderInARandomPattern(repliesToIllegalRequest, ILLEGAL_REQUEST_PLACEHOLDER, statement);
    }

    /**
     * the function getting statement and returns the answer randomly
     *
     * @param optionsToReply - the statements array, legal or illegal statements
     * @param wordToReplace  - <phrase> or <request> depends on illegal or legal statement
     * @param statement      - the input
     * @return the answer
     */
    String replacePlaceholderInARandomPattern(String[] optionsToReply, String wordToReplace, String statement) {
        if (Objects.equals(wordToReplace, REQUESTED_PHRASE_PLACEHOLDER)) {
            statement = statement.replaceFirst(REQUEST_PREFIX, "");
        }
        int randomIndex = rand.nextInt(optionsToReply.length);
        String reply = optionsToReply[randomIndex];
        return reply.replaceAll(wordToReplace, statement);


    }
}
