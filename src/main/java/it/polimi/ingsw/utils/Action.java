package it.polimi.ingsw.utils;

public enum Action {

    SELECT_TOKEN("Select the token you want to move (x,y): "),
    MOVE("Make your move (x,y):"),
    BUILD("Make your build (x,y):"),
    WAIT("Wait for the other player's move!"),
    chooseGodCardMessage ("Write the name of the god you want to play!"),
    winMessage ("You win!"),
    loseMessage ("You lose!"),
    drawMessage ("Draw!"),
    wrongTurnMessage ("It is not your turn!"),
    occupiedCellMessage ("The chosen cell is not empty!");

    private String info;

    Action(String info){ this.info = info;}

    public void print (Action message) {
        System.out.println(message);
    }

    public String getInfo(){ return info; }

    @Override
    public String toString(){
        return info;
    }


}