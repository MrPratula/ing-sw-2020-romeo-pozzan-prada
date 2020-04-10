package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.CellHeightException;
import it.polimi.ingsw.exception.ReachHeightLimitException;

import java.io.Serializable;

/**
 * Each Cell is a little square that form the Battlefield.
 * Each one of them is uniquely identified by two int.
 * A Cell always has an height is 0 if nobody has already build there
 * and always has a boolean that tell us if there is a player on it.
 */

public class Cell implements Serializable {

    private int posX;
    private int posY;
    private boolean thereIsPlayer;

    private int height;
    private boolean isDome;

    /**
     * Constructor
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

    /*
     * GETTER
     */

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public boolean getThereIsPlayer() {
        return thereIsPlayer;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean IsDome() {
        return this.isDome;
    }

    /*
     * SETTER
     */

    public void setThereIsPlayer() {
        this.thereIsPlayer = true;
    }

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
     * This happen when incrementHeight method is called on a Cell's that is height 3.
     */
    public void setTerminated() {
        this.isDome = true;
    }

    /**
     * Increment the height of a Cell by one.
     * The height start from zero and can be incremented up to 3.
     * If a cell with an height of 3 is incremented it becomes a dome and become un-buildable
     *
     * @throws ReachHeightLimitException if try to increment a Cell that is a dome
     * @throws CellHeightException if the height of a cell is out of 0<=height<=3 parameters
     */
    public void incrementHeight() throws ReachHeightLimitException, CellHeightException{

        if (this.isDome) {
            throw new ReachHeightLimitException(
                    String.format("Cell at (%d,%d) is a dome and can't be built anymore", this.posX, this.posY));
        }
        if (this.height == 3) {
            this.setTerminated();
            return;
        }
        if (0<=this.height && this.height<3) {
            this.height++;
        }
        else throw new CellHeightException(
                String.format("Cell at (%d,%d) has height = %d out of range [0,3]", this.posX, this.posY, this.getHeight()));
    }
}
