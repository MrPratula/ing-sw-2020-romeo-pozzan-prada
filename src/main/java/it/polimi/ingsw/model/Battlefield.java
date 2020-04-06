package it.polimi.ingsw.model;
import java.util.*;


public class Battlefield {

    private static final int battlefieldSize = 5;
    private Cell[][] battlefield;
    private PieceSet pieceSet;
    private List<Player> players;
    private List<GodCard> allGodCardsInGame;

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
    /////////////////alert: metodi simili

    public Cell getCell (Cell cell) {
        int inputX = cell.getPosX();
        int inputY = cell.getPosY();
        return battlefield[inputX][inputY];
    }

    public void setCell(int posX, int posY, Cell marker) {
        if(posX < 0 || posY < 0 || posX > 2 || posY > 2){
            return;
        }
        battlefield[posX][posY] = marker;
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

    public List<GodCard> getAllGodCardsInGame() {
        return allGodCardsInGame;
    }

    public void setAllGodCardsInGame(List<GodCard> allGodCardsInGame) {
        this.allGodCardsInGame = allGodCardsInGame;
    }


    }








    /* public void print(){
        System.out.println("  0 1 2 3 4 ");
        for (int i = 0; i < battlefieldSize; i++) {
            System.out.print(i + "|");
            for (int j = 0; j < battlefieldSize; j++) {
                System.out.print(battlefield[i][j] + "|");
            }
            System.out.println();
        }
    }
    */

