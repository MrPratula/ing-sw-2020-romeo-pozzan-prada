package it.polimi.ingsw.model;

/**
 * 
 */
public class Battlefield {

    private static final int battlefieldSize = 5;
    Cell[][] battlefield;
    PieceSet pieceSet;

    /**
     * Default constructor
     */
    public Battlefield() {

        Cell[][] battlefield = new Cell[battlefieldSize][battlefieldSize];

        for(int x=0; x<5; x++) {
            for (int y=0; y<5; y++) {
                battlefield[x][y] = new Cell(x, y);
            }
        }
        this.battlefield = battlefield;
    }

    /**
     * 
     */
    private PieceSet[] pieceSets;






    /**
     * 
     */
    public void battlefield() {
        // TODO implement here
    }

}