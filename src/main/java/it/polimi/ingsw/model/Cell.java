package it.polimi.ingsw.model;

import java.util.*;

/**
 * 
 */
public class Cell {

    private int posX;
    private int posY;
    private Build build;

    /**
     * Default constructor
     */
    public Cell(int x, int y) {

        this.posX = x;
        this.posY = y;

        this.build = new Build(0, false);
    }
}