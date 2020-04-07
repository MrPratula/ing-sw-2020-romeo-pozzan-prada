package it.polimi.ingsw.model;

import java.util.*;


public class Cell {

    private int posX;
    private int posY;
    private Build build;
    private boolean isFree;

    public Cell(int x, int y) {
        this.posX = x;
        this.posY = y;
        this.build = new Build();  //uguale a this.build=new Build(0, false);
        this.isFree = true;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Build getBuild() {
        return build;
    }

    public boolean isFree (){
        return this.isFree;
    }

    public void setOccupied (){
        this.isFree = false;
    }

}