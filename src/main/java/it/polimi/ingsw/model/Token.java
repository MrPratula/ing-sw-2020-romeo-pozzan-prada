package it.polimi.ingsw.model;
import java.util.ArrayList;

/**
 * 
 */
public class Token {

    private TokenColor tokenColor;
    private Cell tokenPosition;

    public Token(TokenColor tokenColor) {
        this.tokenColor = tokenColor;
    }

    public Cell getTokenPosition(){ return tokenPosition; }
    public int getPositionX () {
        return tokenPosition.getPosX();
    }
    public int getPositionY () {
        return tokenPosition.getPosY();
    }
    public int getHeight () {
        return tokenPosition.getBuild().getHeight();
    }


    //setter??????????????


    public ArrayList<Cell> validMoves (Battlefield battlefield) {
        ArrayList<Cell> result = new ArrayList<Cell>();
        int provX, provY;

        for (int i=-1; i<2; i++){                                                   // ciclo di +-1 intorno alla posizione del token
            provX = getPositionX()+i;                                               // per poter ottenere le 8 caselle in cui
            for (int j=-1; j<2; j++){                                               // posso muovere
                provY = getPositionY()+j;

                if ( provX>=0 && provX <5 && provY>=0 && provY<5 &&                 // la cella provv è dentro le dimensioni del battlefield
                    battlefield.getCell(provX,provY).getBuild().getHeight()-       // l'altezza della cella provv -
                    tokenPosition.getBuild().getHeight()<=1 &&                     // l'altezza del token <= 1
                    // !(battlefield[provX,provY].equals(battlefield.getPlayers().getToken )                                     )
                    for(Token t : battlefield.getPieceSet().getTokens() ){
                        !(t.getTokenPosition().equals(battlefield[provX,provY]);
                    } )

                ////////////////////////////////////////////////////////////////// e se non c'è un altro player
                {


                        if (provX!=0 && provY!=0)                                   // il token non può stare fermo NB: oppure tokenPosition!=battlefield[provX,provY]
                            result.add(battlefield.getCell(provX,provY));
                }
            }
        }
        return result;
    }


}