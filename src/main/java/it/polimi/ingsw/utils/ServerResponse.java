package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Model;

import java.util.List;

/**
 * This is the message that is constructed by the model
 * and has to be sent to each view.
 * It has the purpose to communicate the change of the model and ask a player what he has to do
 * in order to let the game routine run properly.
 */
public class ServerResponse {

    private final Action action;
    private final Model modelCopy;

    private final List<Cell> validMoves;
    private final List<Cell> validBuilds;

    private String outMessage;

    public ServerResponse(Action action, Model modelCopy, List<Cell> validMoves, List<Cell> validBuilds, String outMessage) {

        this.action = action;
        this.modelCopy = modelCopy;

        this.validMoves = validMoves;
        this.validBuilds = validBuilds;

        this.outMessage = outMessage;
    }

    public Action getAction() {
        return this.action;
    }

    public Model getModelCopy() {
        return this.modelCopy;
    }

    public List<Cell> getValidMoves() {
        return this.validMoves;
    }

    public List<Cell> getValidBuilds() {
        return this.validBuilds;
    }

    public String getOutMessage() {
        return this.outMessage;
    }

}
