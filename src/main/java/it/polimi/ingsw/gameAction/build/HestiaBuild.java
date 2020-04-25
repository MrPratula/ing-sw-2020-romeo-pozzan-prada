package it.polimi.ingsw.gameAction.build;

import it.polimi.ingsw.controller.CellHeightException;
import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.controller.ReachHeightLimitException;
import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;

public class HestiaBuild implements BuildBehavior {
    @Override
    public List<Cell> computeValidBuilds(Token selectedToken, Token otherToken, List<Token> enemyTokens, List<GodCard> enemyGodCards, Battlefield battlefield, List<Player> allPlayers) throws CellOutOfBattlefieldException {
        int provX, provY;
        List<Cell> buildableCells = new ArrayList<Cell>();

        enemyTokens.add(selectedToken);
        if (otherToken!=null)
            enemyTokens.add(otherToken);

        for (int i=-2; i<3; i++){                                                   // ciclo di +-1 intorno alla posizione del token
            provX = selectedToken.getTokenPosition().getPosX()+i;                            // per poter ottenere le caselle in cui
            for (int j=-2; j<3; j++){                                               // posso costruire
                provY = selectedToken.getTokenPosition().getPosY()+j;

                if ( (provX>=0 && provX <5) && (provY>=0 && provY<5) &&                       // la cella provv è dentro le dimensioni del battlefield
                        (!battlefield.getCell(provX,provY).getIsDome()) ) {        // non deve essere una cupola

                    for (Token t: enemyTokens) {
                        if (provX != t.getTokenPosition().getPosX() &&                 // il token non può costruire dove c'è un altro token
                                provY != t.getTokenPosition().getPosX()) {             // compreso sè stesso, quindi non può costruire sotto i piedi

                            buildableCells.add(battlefield.getCell(provX, provY));
                        }
                    }
                }
            }
        }
        return buildableCells;
    }

    @Override
    public void performBuild(Cell targetCell, Cell second_cell, Battlefield battlefield) throws CellHeightException, ReachHeightLimitException {
        battlefield.getCell(targetCell).incrementHeight();
        if(second_cell!=null){
            battlefield.getCell(second_cell).incrementHeight();
        }
    }
}
