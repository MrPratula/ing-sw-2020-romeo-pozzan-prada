package it.polimi.ingsw.observer;

public interface Observer<T> {

    void updateMove(T message);
    void updateCheckWin();
    void updateBuild();

}
