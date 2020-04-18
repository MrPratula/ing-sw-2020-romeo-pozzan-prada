package it.polimi.ingsw.model;

import java.util.List;


/**
 *  A Player is an object that has an unique view.
 *  It is an observable for the controller and
 *  it is an observer of the controller.
 *  It interact with the network
 *  and notify the observer of the controller to let the game run.
 */
public class Player {

    private String username;
    private TokenColor tokenColor;
    private Token token1, token2;
    private GodCard myGodCard;
    private List<GodCard> opponentGodCards;

    private Battlefield battlefield; //penso che sia quella che vede il player sulla sua view

    /**
     * A new player is created when it connect to the server
     *
     * @param username    the name of the player. It should be unique
     * @param color       the color that identify the player and his tokens
     * @param battlefield the shared data to construct the view and notify the controller
     */

    public Player(String username, TokenColor color, Battlefield battlefield) {

        this.username = username;
        this.tokenColor = color;
        this.token1 = new Token(color);
        this.token2 = new Token(color);
        this.battlefield = battlefield;

    }

    /*  GETTER  */
    public Token getToken1() {
        return this.token1;
    }

    public Token getToken2() {
        return this.token2;
    }

    public String getUsername() {
        return this.username;
    }

    public GodCard getMyGodCard() {
        return this.myGodCard;
    }


    /*  SETTER  */

    public void setToken1(Token token1) {
        this.token1 = token1;
    }

    public void setToken2(Token token2) {
        this.token2 = token2;
    }




    /**
     * Used to return a player color to let
     * game know if it is the player's turn
     * @return the color of the player
     */
    public TokenColor getTokenColor() {
        return this.tokenColor;
    }
}