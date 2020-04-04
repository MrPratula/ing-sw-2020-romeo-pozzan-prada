package it.polimi.ingsw.observer;

import java.util.ArrayList;
import java.util.List;

public class Observable<T> {

    private final List<Observer<T>> observers = new ArrayList<>();

    public void addObserver(Observer<T> observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void removeObserver(Observer<T> observer){
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    protected void notifyMove(T message){
        synchronized (observers) {
            for(Observer<T> observer : observers){
                observer.updateMove(message);
            }
        }
    }

    protected void notifyCheckWin(T message){
        synchronized (observers) {
            for(Observer<T> observer : observers){
                observer.updateCheckWin(message);
            }
        }
    }

    protected void notifyBuild(T message){
        synchronized (observers) {
            for(Observer<T> observer : observers){
                observer.updateBuild(message);
            }
        }
    }

}
