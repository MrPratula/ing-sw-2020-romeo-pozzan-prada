package it.polimi.ingsw.model;

/**
 * 
 */
public class Token {

    private TokenColor tokenColor;
    private Cell position;

    /**
     * Default constructor
     */
    public Token(TokenColor tokenColor, Cell position) {

        this.tokenColor = tokenColor;
        this.position = position;
    }

    public int getPositionX () {
        return position.getposX();
    }
    public int getPositionY () {
        return position.getPosY();
    }

    public int getHeight () {
        return position.getBuild().getHeight();
    }


}