package it.polimi.ingsw.model;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class Token {

    private TokenColor tokenColor;
    private Cell tokenPosition;
    private int oldHeight;

    public Token(TokenColor tokenColor) {
        this.tokenColor = tokenColor;
    }

    public int getOldHeight() {
        return oldHeight;
    }

    public void setOldHeight(int oldHeight) {
        this.oldHeight = oldHeight;
    }

    public Cell getTokenPosition() {
        return tokenPosition;
    }

    public void setTokenPosition(Cell tokenPosition) {
        this.tokenPosition = tokenPosition;
    }

    public List<Cell> validMoves (Battlefield battlefield) {

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

                if ( (provX>=0 && provX <5) && (provY>=0 && provY<5) &&                     // la cella provv è dentro le dimensioni del battlefield
                     (battlefield.getCell(provX,provY).getBuild().getHeight()-              // l'altezza della cella provv -
                     tokenPosition.getBuild().getHeight()<=1) &&                            // l'altezza del token <= 1
                     (!battlefield.getCell(provX,provY).getBuild().getIsDome()) ) {         // non deve essere una cupola

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


    public List<Cell> validBuilds (Battlefield battlefield) {

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
                    (!battlefield.getCell(provX,provY).getBuild().getIsDome()) ) {        // non deve essere una cupola

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