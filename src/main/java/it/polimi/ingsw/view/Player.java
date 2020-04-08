package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.GodCard;
import it.polimi.ingsw.model.Token;
import it.polimi.ingsw.model.TokenColor;
import it.polimi.ingsw.utils.Observable;

import java.util.*;

/**
 *  A Player is an object that has an unique view. It interact with the network
 *  and notify the observer of the controller to let the game run.
 */

public class Player extends Observable {

    private String username;
    private TokenColor tokenColor;
    private Token token1, token2;
    private GodCard myGodCard;
    private List<GodCard> opponentGodCards;

    private Battlefield battlefield; //penso che sia quella che vede il player sulla sua view

}
