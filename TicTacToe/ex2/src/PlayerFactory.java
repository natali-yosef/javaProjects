/**
 * factory of player
 */
public class PlayerFactory {

    /**
     *
     * @param strPlayer - the name of the player
     * @return - the player object
     */
    public Player buildPlayer(String strPlayer){
        switch (strPlayer) {
            case "human":
                return new HumanPlayer();
            case "whatever":
                return new WhateverPlayer();
            case "clever":
                return new CleverPlayer();
            case "snartypamts":
                return new SnartypamtsPlayer();
            default:
                return null;
        }

    }
}
