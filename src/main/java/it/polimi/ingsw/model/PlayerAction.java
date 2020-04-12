package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.Action;

public class PlayerAction {

    private final Player player;
    private final Token token;
    private final Cell cell;
    private final Action action;

    public PlayerAction(Player player, Action action, Token token, Cell cell){
        this.player = player;
        this.token = token;
        this.cell = cell;
        this.action = action;
    }

    public Player getPlayer(){
        return this.player;
    }

    public Action getAction(){
        return this.action;
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
