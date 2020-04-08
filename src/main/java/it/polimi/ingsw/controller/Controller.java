package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.utils.Message;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;


/**
 * The Controller is the game logic.
 * It Observe the Player till they notify with a message.
 * The Controller modify the Model via ad-hoc method selected by the message in the update.
 * Other than the update message, each update should give Controller objects he need to perform
 * what is specified in the message.
 */
public class Controller extends Observer {

    private Battlefield battlefield; //non so se è giusto, ma per ora lo metto per avere accesso ai dati


    /**
     * The update method uses the message parameter to choose the correct method to run.
     * @param message is used to specify the method to run via the switch.
     */
    public void update(Message message) {

        switch (message) {
            case MOVE: {

                break;
            }

            case BUILD: {

                break;
            }

            case SELECT: {

                break;
            }


        }
    }
}
