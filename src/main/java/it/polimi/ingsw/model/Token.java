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
        return position.getPosX();
    }
    public int getPositionY () {
        return position.getPosY();
    }

    public int getHeight () {
        return position.getBuild().getHeight();
    }

    public Cell[] validMoves () {
        Cell[] result;
        int provX;
        int provY;

        for (int i=-1, i<2, i++){
            provX = getPositionX()X()+i;
            for (int j=-1, j<2, j++){
                provY = getPositionY()+j;
                if (0<=provX<5 && 0<=provY<5) && (position.getBuild().getHeight()-)

            }
        }
    }


}