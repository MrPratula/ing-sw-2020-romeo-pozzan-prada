package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.GodCard;
import it.polimi.ingsw.model.ModelUtils;
import it.polimi.ingsw.model.Player;

import java.io.Serializable;
import java.util.List;


/**
 * A pack is an object that pairs with a TokenColor and both make a server response.
 * It contains all the info needed to let a player know what it's happened to the model
 * and to let him know what he has to do.
 */
public class Pack implements Serializable {

    private final Action action;

    private ModelUtils modelCopy;

    private List<Cell> validMoves;
    private List<Cell> validBuilds;

    private String messageInTurn;
    private String messageOpponents;

    private List<GodCard> godCards;

    private Player player;

    private int numberOfPlayers;

    public Pack(Action action){
        this.action = action;
    }

    public Action getAction() {
        return this.action;
    }

    public ModelUtils getModelCopy() {
        return modelCopy;
    }

    public void setModelCopy(ModelUtils modelCopy) {
        this.modelCopy = modelCopy;
    }

    public List<Cell> getValidMoves() {
        return validMoves;
    }

    public void setValidMoves(List<Cell> validMoves) {
        this.validMoves = validMoves;
    }

    public List<Cell> getValidBuilds() {
        return validBuilds;
    }

    public void setValidBuilds(List<Cell> validBuilds) {
        this.validBuilds = validBuilds;
    }

    public List<GodCard> getGodCards() {
        return godCards;
    }

    public void setGodCards(List<GodCard> godCards) {
        this.godCards = godCards;
    }

    public String getMessageInTurn() {
        return messageInTurn;
    }

    public void setMessageInTurn(String messageInTurn) {
        this.messageInTurn = messageInTurn;
    }

    public String getMessageOpponents() {
        return messageOpponents;
    }

    public void setMessageOpponents(String messageOpponents) {
        this.messageOpponents = messageOpponents;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
}
