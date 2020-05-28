package it.polimi.ingsw.utils;

import it.polimi.ingsw.cli.Cell;
import it.polimi.ingsw.cli.Player;

import java.io.Serializable;

public class PlayerAction implements Serializable {

    private final Action action;
    private final Player player;
    private final Player oppo1;
    private final Player oppo2;
    private final int tokenMain;
    private final int tokenOther;
    private final boolean doWantUsePower;
    private final String args;

    private final Cell first_cell;
    private final Cell second_cell;

    public PlayerAction(Action action, Player player, Player oppo1, Player oppo2, int tokenMain, int tokenOther, Cell first_cell, Cell second_cell , boolean doWantUsePower, String args){
        this.action = action;
        this.player = player;
        this.oppo1 = oppo1;
        this.oppo2 = oppo2;
        this.tokenMain = tokenMain;
        this.tokenOther = tokenOther;
        this.first_cell = first_cell;
        this.second_cell = second_cell;
        this.doWantUsePower = doWantUsePower;
        this.args = args;
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

    public Cell getFirstCell() {
        return this.first_cell;
    }

    public Cell getSecondCell() {
        return this.second_cell;
    }

    public boolean getDoWantUsePower() {
        return doWantUsePower;
    }

    public String getArgs() {
        return this.args;
    }

}
