package it.polimi.ingsw.model;
import it.polimi.ingsw.exception.CellOutOfBattlefieldException;

import java.io.Serializable;
import java.util.*;

/**
 * A Battlefield is a matrix of Cell.
 * It has a list with all the Players that are currently play on that Battlefield.
 */
public class Battlefield implements Serializable {

    private static final int battlefieldSize = 5;
    private Cell[][] battlefield;
    private List<Player> players;

    /**
     * The constructor create a matrix N*N of Cell where N is the battlefieldSize.
     */
    public Battlefield() {

        Cell[][] battlefield = new Cell[battlefieldSize][battlefieldSize];

        for(int x=0; x<battlefieldSize; x++) {
            for (int y=0; y<battlefieldSize; y++) {
                battlefield[x][y] = new Cell(x,y);
            }
        }
        this.battlefield = battlefield;
    }

    /**
     * It is used to get a Cell of the battlefield when the x-coords and y-coords are known.
     * @param posX column of the battlefield.
     * @param posY raw of the battlefield.
     * @return the Cell with specified coords.
     */
    public Cell getCell (int posX, int posY) throws CellOutOfBattlefieldException {

        if (0 >= posX && posX <= battlefieldSize) {
            if (0 >= posY && posY <= battlefieldSize) {
                return battlefield[posX][posY];
            }
        } else {
            throw new CellOutOfBattlefieldException(
                    String.format("the cell at position (%d,%d) is out the battlefield", posX, posY));
        }
        return null;            // non capisco perchè questo deve stare qui...
    }

    /**
     * It is used when I have a Cell (usually of the Token) and I want to locate
     * it on the Battlefield.
     * @param cell the cell i want to locate.
     * @return the Cell with the same coords that is part of the battlefield.
     */
    public Cell getCell (Cell cell) {
        int inputX = cell.getPosX();
        int inputY = cell.getPosY();
        return battlefield[inputX][inputY];
    }


    protected final Battlefield copy() {

        final Battlefield battlefieldClone = new Battlefield();

        for(int x=0; x<battlefieldSize; x++) {
            for (int y=0; y<battlefieldSize; y++){
                battlefieldClone.battlefield[x][y]= battlefield[x][y];
            }
        }

        battlefieldClone.players=this.players;
        return battlefieldClone;
    }


    /**
     * This method is called by the client in asyncReadFromSocket
     * and print in stdout the battlefield when a player modifies the battlefield.
     */
    public void printCLI() {

        int n=battlefieldSize-1;

        for(int y=4; y>-1; y--){
            // first of all, let's print the index of the battlefield
            System.out.print(y );

            for(int x=0; x<battlefieldSize; x++){
                // then we check if exists a token from any player in this general position

                if( !battlefield[x][y].ThereIsPlayer() ) {
                    System.out.print("\033[030m");          //black written
                    System.out.print("\033[047m");          //on white board
                    System.out.print(battlefield[x][y].getHeight());
                    System.out.print(" ");
                }
                else{

                    for (Player p : players) {
                        if(p.getToken1().getTokenPosition().equals(battlefield[x][y])) {  //forse meglio fare i controlli singolarmente su posX e posY
                            System.out.print("\033[039m");          //white written
                            TokenColor t = p.getTokenColor();
                            System.out.print(t.getEscape());        //on a board of the player color
                            System.out.print(battlefield[x][y].getHeight());
                            System.out.print("\033[047m");          //on a white board
                            System.out.print(" ");
                        }
                        else{
                            if(p.getToken2().getTokenPosition().equals(battlefield[x][y])) {
                                System.out.print("\033[039m");          //white written
                                TokenColor t = p.getTokenColor();
                                System.out.print(t.getEscape());        //on a board of the player color
                                System.out.print(battlefield[x][y].getHeight());
                                System.out.print("\033[047m");          //on a white board
                                System.out.print(" ");
                            }
                        }
                    }


                }
            }
            System.out.println("\033[039m");             //white written
            System.out.println("\033[049m\n");           //on a black board
            System.out.println("  0 1 2 3 4\n\n");
        }
    }
}

