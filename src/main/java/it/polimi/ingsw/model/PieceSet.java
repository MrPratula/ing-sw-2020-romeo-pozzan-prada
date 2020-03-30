package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * 
 */
public class PieceSet {

    private ArrayList<Cell> cells;
    public ArrayList<Token> tokens;   //perche public?

    /**
     * Default constructor
     */
     public PieceSet() {

    }

    public void addCell (Cell cell) {
         cells.add(cell);
    }

    public void addToken (Token token) {
         tokens.add (token);
    }


    public ArrayList<Token> getTokens() {
        return tokens;
    }
}