package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerAction;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;

public abstract class View extends Observable<PlayerAction> implements Observer<Model> {

    private Player player;


    public View(Player player) {
        this.player = player;
    }


    protected Player getPlayer(){
        return player;
    }

    protected void processChoice(Action choice){
        notify(new PlayerAction(player, choice));
    }

    protected abstract void showModel(Model model);


    @Override
    public void update(Model model) {
        showModel(model);
        }
    }
}
