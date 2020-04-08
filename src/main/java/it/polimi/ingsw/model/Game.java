package it.polimi.ingsw.model;
import it.polimi.ingsw.utils.Observable;


/**
 * A Game is a Battlefield with an Observable for notify the player
 * when the model (me) change it's status
 */
public class Game extends Observable {

    private Battlefield battlefield;

}
