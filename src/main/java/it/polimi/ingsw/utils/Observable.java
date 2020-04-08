package it.polimi.ingsw.utils;

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

    protected void notify(Message message){
        synchronized (observers) {
            for (Observer<Message> observer : observers) {
                observer.update(message);
            }
        }
    }
}




