package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Token;

public class PlayerAction {

    private final Action action;
    private final Player player;
    private final Player oppo1;
    private final Player oppo2;
    private final int tokenMain;
    private final int tokenOther;

    private final Cell cell;

    public PlayerAction(Action action, Player player, Player oppo1, Player oppo2, int tokenMain, int tokenOther, Cell cell){
        this.action = action;
        this.player = player;
        this.oppo1 = oppo1;
        this.oppo2 = oppo2;
        this.tokenMain = tokenMain;
        this.tokenOther = tokenOther;
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

    public int getTokenMain() {
        return this.tokenMain;
    }

    public int getTokenOther() {
        return this.tokenOther;
    }

    public Cell getCell() {
        return this.cell;
    }

    public void processAction(){

    }

}
