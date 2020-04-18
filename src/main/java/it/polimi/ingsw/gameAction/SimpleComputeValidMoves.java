package it.polimi.ingsw.gameAction;

import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.GodCard;
import it.polimi.ingsw.model.Token;

import java.util.ArrayList;
import java.util.List;

public class SimpleComputeValidMoves implements MoveBehavior {

    @Override
    public List<Cell> computeValidMoves(Token selectedToken, Token otherToken, List<Token> enemyTokens, GodCard myGodCard, List<GodCard> enemyGodCards, Battlefield battlefield) throws CellOutOfBattlefieldException {

        List<Cell> allMoves = null;

        int provX, provY;

        for (int i=-1; i<2; i++) {                                                   // ciclo di +-1 intorno alla posizione del token
            provX = selectedToken.getTokenPosition().getPosX() + i;                            // per poter ottenere le 8 caselle in cui
            for (int j = -1; j < 2; j++) {                                               // posso muovere
                provY = selectedToken.getTokenPosition().getPosY() + j;
                if ((provX >= 0 && provX < 5) && (provY >= 0 && provY < 5) &&                     // la cella provv è dentro le dimensioni del battlefield
                        (battlefield.getCell(provX, provY).getHeight() -              // l'altezza della cella provv -
                                selectedToken.getTokenPosition().getHeight() <= 1) &&            // l'altezza del token <= 1
                        (!battlefield.getCell(provX, provY).getIsDome())) {         // non deve essere una cupola
                    allMoves.add(battlefield.getCell(provX, provY));
                }
            }
        }

        for (Cell validCell: allMoves) {

            allMoves.remove(battlefield.getCell(selectedToken.getTokenPosition()));
            allMoves.remove(battlefield.getCell(otherToken.getTokenPosition()));

            for (Token enemyToken : enemyTokens) {
                allMoves.remove(battlefield.getCell(enemyToken.getTokenPosition()));
            }
        }

        return allMoves;
    }
}