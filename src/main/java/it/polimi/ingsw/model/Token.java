package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Each Player has 2 tokens.
 * They are created with no position and when it is assigned they always have a position.
 * Each token can move in any adiacent cell, with any exceptions.
 * it can not move on a cell occupied by another token,
 * it can not move on a cell that is a dome,
 * it can not move on a cell that has a delta-height >= 2.
 */
public class Token implements Serializable {

    private int id;
    private final TokenColor tokenColor;
    private Cell tokenPosition;
    private int oldHeight;

    /**
     * A token is created with a color, which is the same as it's owner.
     * @param color color identifier.
     */
    public Token(TokenColor color){
        this.tokenColor = color;
    }


    /*   GETTER   */

    public Cell getTokenPosition() {
        return tokenPosition;
    }

    public int getOldHeight() {
        return oldHeight;
    }

    public int getId() {
        return this.id;
    }

    public TokenColor getTokenColor() {
        return this.tokenColor;
    }


    /*   SETTER   */

    public void setTokenPosition(Cell tokenPosition) {
        this.tokenPosition = tokenPosition;
    }

    public void setOldHeight(int oldHeight) {
        this.oldHeight = oldHeight;
    }

    public void setId(int id){
        this.id = id;
    }
}



