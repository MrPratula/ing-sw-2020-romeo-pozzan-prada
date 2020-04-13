package it.polimi.ingsw.utils;

public enum Action {

    SELECT_TOKEN("Select the token you want to move (x,y): "),
    MOVE_TOKEN("Where do you want to move your token? (x,y):"),
    BUILD("Where do you want to build? (x,y):"),
    SELECT_CELL("Select the cell you want... (x,y):");

    private String info;

    Action(String info){ this.info = info;}

    public void print (Action message) {
        System.out.println(message);
    }

    public String getInfo(){
        return info;
    }

    @Override
    public String toString(){
        return info;
    }
}