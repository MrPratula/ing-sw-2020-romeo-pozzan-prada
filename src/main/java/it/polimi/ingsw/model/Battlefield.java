package it.polimi.ingsw.model;

import java.util.List;

/**
 * 
 */
public class Battlefield {

    private static final int battlefieldSize = 5;
    private Cell[][] battlefield;
    private PieceSet pieceSet;
    private List<Player> players;

    public Battlefield() {

        Cell[][] battlefield = new Cell[battlefieldSize][battlefieldSize];

        for(int x=0; x<battlefieldSize; x++) {
            for (int y=0; y<battlefieldSize; y++) {
                battlefield[x][y] = new Cell(x,y);
                pieceSet.addCell(battlefield[x][y]);
            }
    }
        this.battlefield = battlefield;
    }

    public Cell getCell (int posX, int posY) {
        return battlefield[posX][posY];
    }

    public PieceSet getPieceSet() {
        return pieceSet;
    }

    public List<Player> getPlayers() { return players; }
}