package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.RemoteView;

import java.io.IOException;
import java.util.*;

public class Observable<Message> {

    private final List<Observer<Message>> observers = new ArrayList<>();

    public void addObserver(Observer<Message> observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void removeObserver(Observer<Message> observer){
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    protected void notify(Message message, List<Player> receivers) throws CellOutOfBattlefieldException, ImpossibleTurnException, ReachHeightLimitException, CellHeightException, WrongNumberPlayerException, IOException {
        synchronized (observers) {
            for (Observer<Message> observer : observers) {
                if (receivers!=null){
                    for(Player p: receivers){
                        if ( ((RemoteView)observer).getPlayer().getTokenColor().equals(p.getTokenColor())){
                            observer.update(message);
                        }
                    }
                }else {
                    observer.update(message);
                }
            }
        }
    }

}




