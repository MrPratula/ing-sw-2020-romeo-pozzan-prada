package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.List;


/**
 * It extract from the model the battlefield, the turn and all player.
 * Then it is sent to the client.
 */
public class ModelUtils implements Serializable {


    /**
     * A battlefield
     */
    private Battlefield battlefield;

    /**
     * The player that has to play color
     */
    private TokenColor turn;

    /**
     * All the players in game
     */
    private List<Player> allPlayers;


    /**
     * Create a modelUtils with provided battlefield
     * @param battlefield his battlefield
     */
    public ModelUtils(Battlefield battlefield) {
        this.battlefield = battlefield;
    }


    /**
     * @return the battlefield
     */
    public Battlefield getBattlefield() {
        return battlefield;
    }


    /**
     * Set the battlefield as this battlefield
     * @param battlefield battlefield to set
     */
    public void setBattlefield(Battlefield battlefield) {
        this.battlefield = battlefield;
    }


    /**
     * @return the color of player that has to play
     */
    public TokenColor getTurn() {
        return turn;
    }


    /**
     * Set a color as this turn
     * @param turn color to set
     */
    public void setTurn(TokenColor turn) {
        this.turn = turn;
    }


    /**
     * @return all players in game
     */
    public List<Player> getAllPlayers() {
        return allPlayers;
    }


    /**
     * Set the players as this players in game
     * @param allPlayers the players to set
     */
    public void setAllPlayers(List<Player> allPlayers) {
        this.allPlayers = allPlayers;
    }
}
