package it.polimi.ingsw.gameAction.build;

import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.cli.*;

import java.util.List;


/**
 * ATLAS
 *
 * Your Worker may build a dome at any level including the ground.
 * Note that a build with a dome on a level that is not 3 is not a Complete Tower.
 * This is to clarify the power of the god card CHRONUS.
 */
public class AtlasBuild implements BuildBehavior{


    /**
     * Atlas doesn't need this method.
     * It should never be called.
     */
    @Override
    public List<Cell> computeValidBuilds(Token selectedToken, Token otherToken, List<Token> enemyTokens, List<GodCard> enemyGodCards, Battlefield battlefield, List<Player> allPlayers) throws CellOutOfBattlefieldException {
        return null;
    }


    /**
     * Just set isDome parameter of the target cell True.
     * @param targetCell the cell where to build.
     * @param battlefield the model's battlefield.
     */
    @Override
    public void performBuild(Cell targetCell, Cell second_cell, Battlefield battlefield) {
        battlefield.getCell(targetCell).setIsDome();
    }
}
