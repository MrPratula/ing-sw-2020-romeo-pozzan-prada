package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.List;


/**
 * Class used to pass some utils from the Model, such as the battlefield,
 * turn, and a list of the Players in game
 */
public class ModelUtils implements Serializable {

    private Battlefield battlefield;
    private TokenColor turn;
    private List<Player> allPlayers;

    public ModelUtils(Battlefield battlefield) {
        this.battlefield= battlefield;
    }


    public Battlefield getBattlefield() {
        return battlefield;
    }

    public void setBattlefield(Battlefield battlefield) {
        this.battlefield = battlefield;
    }

    public TokenColor getTurn() {
        return turn;
    }

    public void setTurn(TokenColor turn) {
        this.turn = turn;
    }

    public List<Player> getAllPlayers() {
        return allPlayers;
    }

    public void setAllPlayers(List<Player> allPlayers) {
        this.allPlayers = allPlayers;
    }
}
