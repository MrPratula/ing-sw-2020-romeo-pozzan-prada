package it.polimi.ingsw.model.gameAction.build;

import it.polimi.ingsw.model.*;

import java.util.List;

/**
 * HESTIA
 *
 * Your worker may build one additional time. The additional build can not be on a perimeter space
 */
public class HestiaBuild implements BuildBehavior {

    /**
     * Hestia doesn't need this method.
     * It should never be called.
     */
    @Override
    public List<Cell> computeValidBuilds(Token selectedToken, Token otherToken, List<Token> enemyTokens, List<GodCard> enemyGodCards, Battlefield battlefield, List<Player> allPlayers){
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
        if(second_cell!=null){
            battlefield.getCell(second_cell).incrementHeight();
        }
    }
}
