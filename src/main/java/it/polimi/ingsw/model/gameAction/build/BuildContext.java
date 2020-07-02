package it.polimi.ingsw.model.gameAction.build;

import it.polimi.ingsw.model.*;

import java.util.List;


/**
 * This is the context for the build behavior.
 * For more info about a context read the MoveContext's JavaDOC.
 */
public class BuildContext {

    private final BuildBehavior buildStrategy;

    public BuildContext(BuildBehavior buildStrategy) {
        this.buildStrategy = buildStrategy;
    }

    public List<Cell> executeValidBuilds(Token selectedToken, Token otherToken, List<Token> enemyTokens, List<GodCard> enemyGodCards, Battlefield battlefield, List<Player> allPlayers) {
        return buildStrategy.computeValidBuilds(selectedToken, otherToken, enemyTokens, enemyGodCards, battlefield, allPlayers);
    }

    public void executePerformBuild(Cell targetCell, Cell second_cell, Battlefield battlefield) {
        buildStrategy.performBuild(targetCell, second_cell, battlefield);
    }
}
