package it.polimi.ingsw.view;

import it.polimi.ingsw.model.GodCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.utils.Message;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;

public class View extends Observable implements Observer<Message> {

    private Player player;


    public View(Player player) {
        this.player = player;
    }






    @Override
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
