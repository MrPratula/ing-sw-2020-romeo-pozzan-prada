package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.CellOutOfBattlefieldException;

import java.util.ArrayList;
import java.util.List;

/**
 * Each Player has 2 tokens.
 * They are created with no position and when it is assigned they always have a position.
 * Each token can move in any adiacent cell, with any exceptions.
 * it can not move on a cell occupied by another token,
 * it can not move on a cell that is a dome,
 * it can not move on a cell that has a delta-height >= 2.
 */
public class Token {

    private TokenColor tokenColor;
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


    /*   SETTER   */

    public void setTokenPosition(Cell tokenPosition) {
        this.tokenPosition = tokenPosition;
    }

    public void setOldHeight(int oldHeight) {
        this.oldHeight = oldHeight;
    }







    public List<Cell> validMoves (Battlefield battlefield) throws CellOutOfBattlefieldException {

        List<Cell> result = new ArrayList<>();
        List<Player> players = battlefield.getPlayers();
        List<Token> allTokens = null;
        int provX, provY;

        for (Player player:players) {
            allTokens.add(player.getToken1());
            allTokens.add(player.getToken2());
        }
        for (int i=-1; i<2; i++){                                                   // ciclo di +-1 intorno alla posizione del token
            provX = this.getTokenPosition().getPosX()+i;                            // per poter ottenere le 8 caselle in cui
            for (int j=-1; j<2; j++){                                               // posso muovere
                provY = this.getTokenPosition().getPosY()+j;

                if ( (provX>=0 && provX <5) && (provY>=0 && provY<5) &&                     // la cella provv è dentro le dimensioni del battlefield
                        (battlefield.getCell(provX,provY).getHeight()-              // l'altezza della cella provv -
                                tokenPosition.getHeight()<=1) &&                            // l'altezza del token <= 1
                        (!battlefield.getCell(provX,provY).getIsDome())) {         // non deve essere una cupola

                    assert allTokens != null;   //suggerimento
                    for (Token token:allTokens) {
                        if (provX != token.getTokenPosition().getPosX() &&                 // il token non può andare dove c'è un altro token
                                provY != token.getTokenPosition().getPosX()) {             // compreso sè stesso, quindi non può stare fermo

                            result.add(battlefield.getCell(provX, provY));
                        }
                    }
                }
            }
        }
        return result;
    }





    public List<Cell> validBuilds (Battlefield battlefield) throws CellOutOfBattlefieldException {

        List<Cell> result = new ArrayList<>();
        List<Player> players = battlefield.getPlayers();
        List<Token> allOtherTokens = null;
        int provX, provY;

        for (Player player:players) {
            allOtherTokens.add(player.getToken1());
            allOtherTokens.add(player.getToken2());
        }
        for (int i=-1; i<2; i++){                                                   // ciclo di +-1 intorno alla posizione del token
            provX = this.getTokenPosition().getPosX()+i;                            // per poter ottenere le 8 caselle in cui
            for (int j=-1; j<2; j++){                                               // posso muovere
                provY = this.getTokenPosition().getPosY()+j;

                if ( (provX>=0 && provX <5) && (provY>=0 && provY<5) &&                       // la cella provv è dentro le dimensioni del battlefield
                        (!battlefield.getCell(provX,provY).getIsDome()) ) {        // non deve essere una cupola

                    for (Token token:allOtherTokens) {
                        if (provX != token.getTokenPosition().getPosX() &&                 // il token non può andare dove c'è un altro token
                                provY != token.getTokenPosition().getPosX()) {             // compreso sè stesso, quindi non può stare fermo

                            result.add(battlefield.getCell(provX, provY));
                        }
                    }
                }
            }
        }
        return result;
    }






}



