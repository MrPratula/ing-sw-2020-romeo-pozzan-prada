package it.polimi.ingsw.model;

import java.io.Serializable;


/**
 * Each Player has 2 tokens
 * They are created with no position and when it is assigned they always have a position
 * Each token can move in any adjacent cell, with any exceptions
 * it can not move on a cell occupied by another token,
 * it can not move on a cell that is a dome,
 * it can not move on a cell that has a delta-height >= 2
 */
public class Token implements Serializable {

    /**
     * The ID of the token
     */
    private int id;

    /**
     * The color of the token
     */
    private final TokenColor tokenColor;

    /**
     * The position on the battlefield of the token
     */
    private Cell tokenPosition;

    /**
     * The previous height at this token was in the previous cell
     */
    private int oldHeight;


    /**
     * A token is created with a color, which is the same as it's owner
     * @param color color identifier
     */
    public Token(TokenColor color){
        this.tokenColor = color;
        this.tokenPosition = null;
    }


    /**
     * @return the position of this token
     * Be careful that this cell may not be the battlefield cell!
     */
    public Cell getTokenPosition() {
        return tokenPosition;
    }


    /**
     * @return the old height of this token
     */
    public int getOldHeight() {
        return oldHeight;
    }


    /**
     * @return the ID of this token
     */
    public int getId() {
        return this.id;
    }


    /**
     * @return the color of this token
     */
    public TokenColor getTokenColor() {
        return this.tokenColor;
    }


    /**
     * Set a token position
     * @param tokenPosition the cell to be set as token position
     */
    public void setTokenPosition(Cell tokenPosition) {
        this.tokenPosition = tokenPosition;
    }


    /**
     * Set old height of this token
     * @param oldHeight height of the cell where token was before make a move
     */
    public void setOldHeight(int oldHeight) {
        this.oldHeight = oldHeight;
    }


    /**
     * Set the ID of this token
     * @param id integer to set
     */
    public void setId(int id){
        this.id = id;
    }
}