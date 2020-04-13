package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.Action;

public class PlayerAction {

    private final Action action;

    private final Player player;

    private final Player oppo1;
    private final Player oppo2;

    private final Token token;

    private final Cell cell;

    public PlayerAction(Action action, Player player,  Player oppo1, Player oppo2, Token token, Cell cell){

        this.action = action;
        this.player = player;
        this.oppo1 = oppo1;
        this.oppo2 = oppo2;
        this.token = token;
        this.cell = cell;
    }

    public Action getAction(){
        return this.action;
    }

    public Player getPlayer(){
        return this.player;
    }

    public Player getOppo1(){
        return this.oppo1;
    }

    public Player getOppo2(){
        return this.oppo2;
    }

    public Token getToken() {
        return this.token;
    }

    public Cell getCell() {
        return this.cell;
    }

    public void processAction(){

    }

}
