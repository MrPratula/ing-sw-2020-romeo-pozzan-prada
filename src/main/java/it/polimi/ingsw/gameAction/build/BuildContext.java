package it.polimi.ingsw.gameAction.build;

import it.polimi.ingsw.controller.CellHeightException;
import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.controller.ReachHeightLimitException;
import it.polimi.ingsw.cli.*;

import java.util.List;


/**
 * This is the context for the build behavior.
 * For more info about a context read the MoveContext's JavaDOC.
 */
public class BuildContext {

    private BuildBehavior buildStrategy;

    public BuildContext(BuildBehavior buildStrategy) {
        this.buildStrategy = buildStrategy;
    }

    public List<Cell> executeValidBuilds(Token selectedToken, Token otherToken, List<Token> enemyTokens, List<GodCard> enemyGodCards, Battlefield battlefield, List<Player> allPlayers) throws CellOutOfBattlefieldException {
        return buildStrategy.computeValidBuilds(selectedToken, otherToken, enemyTokens, enemyGodCards, battlefield, allPlayers);
    }

    public void executePerformBuild(Cell targetCell, Cell second_cell, Battlefield battlefield) throws CellHeightException, ReachHeightLimitException {
        buildStrategy.performBuild(targetCell, second_cell, battlefield);
    }
}
