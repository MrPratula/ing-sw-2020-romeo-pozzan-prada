package it.polimi.ingsw.model;

public enum Action {

    MOVE(1),
    BUILD(2),
    CHECKWIN(3);

    int value;

    private Action(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }


}
