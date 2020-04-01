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
    private List<GodCard> allGodCards;

    public Battlefield() {

        Cell[][] battlefield = new Cell[battlefieldSize][battlefieldSize];

        for(int x=0; x<battlefieldSize; x++) {
            for (int y=0; y<battlefieldSize; y++) {
                battlefield[x][y] = new Cell(x,y);
                //pieceSet.addCell(battlefield[x][y]);
            }
        }
        this.battlefield = battlefield;
    }

    public Cell getCell (int posX, int posY) {
        if(posX >= 0 && posY >= 0 && posX < battlefieldSize && posY < battlefieldSize){
            return battlefield[posX][posY];
        } else {
            throw new RuntimeException();
        }
    }

    public PieceSet getPieceSet() {
        return pieceSet;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers (List<Player> players) {
        this.players = players;
    }



}