package it.polimi.ingsw.view;

import it.polimi.ingsw.exception.CellOutOfBattlefieldException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.PlayerAction;

public abstract class View extends Observable<PlayerAction> implements Observer<Model> {

    private Player player;

    public View(Player player) {
        this.player = player;
    }

    protected Player getPlayer(){
        return player;
    }

    protected void notifyController(Player player, Action action, Token token, Cell cell) throws CellOutOfBattlefieldException {
        notify(new PlayerAction(player, action, token, cell));
    }

    protected abstract void showModel(Model model);

    @Override
    public void update(Model model) {
        showModel(model);
        }

}
