package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.GodCard;
import it.polimi.ingsw.model.ModelUtils;
import it.polimi.ingsw.model.Player;

import java.io.Serializable;
import java.util.List;

/**
 * This is the message that is constructed by the model
 * and has to be sent to each view.
 * It has the purpose to communicate the change of the model and ask a player what he has to do
 * in order to let the game routine run properly.
 */
public class ServerResponse implements Serializable {

    private final Action action;
    private final ModelUtils modelCopy;

    private final List<Cell> validMoves;
    private final List<Cell> validBuilds;

    private final List<GodCard> godCards;
    private final String outMessage;

    private final Player player;

    public ServerResponse(Action action, ModelUtils modelCopy, List<Cell> validMoves, List<Cell> validBuilds, List<GodCard> godCards, String outMessage, Player player) {

        this.action = action;
        this.modelCopy = modelCopy;
        this.validMoves = validMoves;
        this.validBuilds = validBuilds;
        this.godCards = godCards;
        this.outMessage = outMessage;
        this.player = player;
    }

    public Action getAction() {
        return this.action;
    }

    public ModelUtils getModelCopy() {
        return this.modelCopy;
    }

    public List<Cell> getValidMoves() {
        return this.validMoves;
    }

    public List<Cell> getValidBuilds() {
        return this.validBuilds;
    }

    public List<GodCard> getGodCards() {
        return this.godCards;
    }

    public String getOutMessage() {
        return this.outMessage;
    }

    public Player getPlayer() {
        return this.player;
    }

}
