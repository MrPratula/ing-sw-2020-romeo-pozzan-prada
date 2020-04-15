package it.polimi.ingsw.view;

import it.polimi.ingsw.exception.CellOutOfBattlefieldException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.*;

public abstract class View extends Observable<PlayerAction> implements Observer<ServerResponse>  {

    private Player player;

    public View(Player player) {
        this.player = player;
    }

    protected Player getPlayer(){
        return player;
    }

    @Override
    public void update(ServerResponse serverResponse) {

        switch (serverResponse.getAction()) {

            case START_NEW_TURN: {

            }
        }

    }


    
    public void notifyRemoteController(PlayerAction playerAction) throws CellOutOfBattlefieldException {
        notify(playerAction);
    }

}
