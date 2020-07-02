package it.polimi.ingsw.model;

import java.io.Serializable;


/**
 * Each Cell is a little square that form the Battlefield.
 * Each one of them is uniquely identified by two int.
 * A Cell always has an height is 0 if nobody has already build there
 * and always has a boolean that tell us if there is a player on it.
 */
public class Cell implements Serializable {

    /**
     * Position on X axis
     */
    private final int posX;

    /**
     * Position on Y axis
     */
    private final int posY;

    /**
     * Tells if there is a token on this cell
     */
    private boolean thereIsPlayer;

    /**
     * Number of builds on this cell
     */
    private int height;

    /**
     * When a cell become higher than 3 or Atlas uses it's power the cell becomes a dome
     */
    private boolean isDome;


    /**
     * Create a cell with given coordinates
     * @param x column of the Cell in the Battlefield
     * @param y raw of the Cell in the Battlefield
     */
    public Cell (int x, int y) {
        this.posX = x;
        this.posY = y;
        this.thereIsPlayer = false;
        this.height = 0;
        this.isDome = false;
    }

    /**
     * @return x position if this cell
     */
    public int getPosX() {
        return this.posX;
    }


    /**
     * @return y position if this cell
     */
    public int getPosY() {
        return this.posY;
    }


    /**
     * @return if there is a player on this cell
     */
    public boolean getThereIsPlayer() {
        return thereIsPlayer;
    }


    /**
     * @return the height of thic cell
     */
    public int getHeight() {
        return this.height;
    }


    /**
     * @return if the cell is a dome
     */
    public boolean getIsDome() {
        return this.isDome;
    }


    /**
     * Set the height of this cell
     * @param newHeight the new height
     */
    public void setHeight(int newHeight) {
        this.height=newHeight;
    }


    /**
     * Used when a player put a Token in this Cell
     */
    public void setOccupied() {
        this.thereIsPlayer = true;
    }


    /**
     * Used when a player move his token away from this cell
     */
    public void setFree() {
        this.thereIsPlayer = false;
    }


    /**
     * Make a Cell un-buildable.
     * This happen when incrementHeight method is called on a Cell's that is height 3
     */
    public void setIsDome() {
        this.isDome = true;
    }


    /**
     * Check if a cell is equal to a targetCell
     * Equal means that they have the same x and y coords
     * @param targetCell the cell to check
     * @return true if they have the same x and y coords
     */
    public boolean equals(Cell targetCell) {
        return this.getPosX()==targetCell.getPosX() && this.getPosY()==targetCell.getPosY();
    }


    /**
     * Increment the height of a Cell by one
     * The height start from zero and can be incremented up to 3
     * If a cell with an height of 3 is incremented it becomes a dome and become un-buildable
     */
    public void incrementHeight(){

        if (this.isDome)
            return;

        if (this.height == 3) {
            this.setIsDome();
            return;
        }
        if (0<=this.height && this.height<3) {
            this.height++;
        }
    }
}
