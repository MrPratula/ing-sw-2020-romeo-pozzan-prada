package it.polimi.ingsw.utils;

import it.polimi.ingsw.cli.Cell;
import it.polimi.ingsw.cli.GodCard;
import it.polimi.ingsw.cli.ModelUtils;
import it.polimi.ingsw.cli.Player;

import java.io.Serializable;
import java.util.List;

public class Pack implements Serializable {

    private final Action action;

    private ModelUtils modelCopy;

    private List<Cell> validMoves;
    private List<Cell> validBuilds;

    private String messageInTurn;
    private String messageOpponents;

    private List<GodCard> godCards;

    private Player player;


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
}
