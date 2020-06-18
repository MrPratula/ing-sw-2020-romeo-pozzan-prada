package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.TokenColor;

import java.io.Serializable;

/**
 * This is the message that is constructed by the model
 * and has to be sent to each view.
 * It has the purpose to communicate the change of the model and ask a player what he has to do
 * in order to let the game routine run properly.
 */
public class ServerResponse implements Serializable {

    private final TokenColor turn;
    private final Pack pack;

    public ServerResponse(TokenColor turn, Pack pack) {
        this.turn = turn;
        this.pack=pack;
    }

    public TokenColor getTurn(){
        return this.turn;
    }

    public Pack getPack(){
        return this.pack;
    }
}
