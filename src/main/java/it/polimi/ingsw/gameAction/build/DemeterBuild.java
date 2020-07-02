package it.polimi.ingsw.gameAction.build;

import it.polimi.ingsw.model.*;

import java.util.List;

/**
 * DEMETER
 *
 * Your Worker may build one additional time, but not on the same space.
 */
public class DemeterBuild implements BuildBehavior {

    /**
     * Demeter doesn't need this method.
     * It should never be called.
     */
    @Override
    public List<Cell> computeValidBuilds(Token selectedToken, Token otherToken, List<Token> enemyTokens, List<GodCard> enemyGodCards, Battlefield battlefield, List<Player> allPlayers) {
        return null;
    }

    /**
     * Increment the two cells' height
     * @param targetCell Cell of the first build
     * @param second_cell Cell of the second build
     * @param battlefield the model's battlefield
     */
    @Override
    public void performBuild(Cell targetCell, Cell second_cell, Battlefield battlefield) {
        battlefield.getCell(targetCell).incrementHeight();
        if(second_cell != null) {
            battlefield.getCell(second_cell).incrementHeight();
        }
    }
}
