package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.utils.GameMessage;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;

public class View extends Observable implements Observer<GameMessage> {

    private Player player;


    public View(Player player) {
        this.player = player;
    }






    @Override
    public void update(GameMessage message) {

        if (message.waitMessage.equals(message)) {


        } else if (message.buildMessage.equals(message)) {


        } else if (message.chooseGodCardMessage.equals(message)) {


        }
    }
}
