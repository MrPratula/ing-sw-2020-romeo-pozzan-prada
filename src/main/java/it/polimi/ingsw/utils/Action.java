package it.polimi.ingsw.utils;

public enum Action {

    selectToken ("Select the token you want to move (x,y): "),
    moveMessage ("Make your move (x,y):"),
    waitMessage ("Wait for the other player's move!"),
    chooseGodCardMessage ("Write the name of the god you want to play!"),
    buildMessage ("Make your build (x,y):"),
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