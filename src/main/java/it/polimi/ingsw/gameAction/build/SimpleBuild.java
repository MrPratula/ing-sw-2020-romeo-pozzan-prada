package it.polimi.ingsw.gameAction.build;

import it.polimi.ingsw.controller.CellHeightException;
import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.controller.ReachHeightLimitException;
import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;


/**
 * A token can build in all 8 Cells around himself except
 * the cell's that are a dome,
 * the cell's where there is a player.
 */
public class SimpleBuild implements BuildBehavior{

    /**
     * Check the said-above conditions and create a list of valid cells.
     * @param selectedToken the token a player want to move,
     * @param otherToken the other player token.
     * @param enemyTokens a list bof all enemy tokens.
     * @param enemyGodCards a list of all enemy god cards.
     * @param battlefield the model's battlefield.
     * @return a list of cell in which a player can build.
     */
    @Override
    public List<Cell> computeValidBuilds(Token selectedToken, Token otherToken, List<Token> enemyTokens, List<GodCard> enemyGodCards, Battlefield battlefield, List<Player> allPlayers) throws CellOutOfBattlefieldException {

        int provX, provY;
        List<Cell> buildableCells = new ArrayList<Cell>();

        enemyTokens.add(selectedToken);
        if (otherToken!=null)
            enemyTokens.add(otherToken);

        for (int i=-1; i<2; i++){                                                   // ciclo di +-1 intorno alla posizione del token
            provX = selectedToken.getTokenPosition().getPosX()+i;                            // per poter ottenere le 8 caselle in cui
            for (int j=-1; j<2; j++){                                               // posso costruire
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


    /**
     * This just receive a Cell and call the incrementHeight method on that cell.
     * @param targetCell the cell to be incremented.
     * @param battlefield the model's battlefield.
     */
    @Override
    public void performBuild(Cell targetCell, Cell second_cell, Battlefield battlefield) throws CellHeightException, ReachHeightLimitException {
        battlefield.getCell(targetCell).incrementHeight();
    }
}
