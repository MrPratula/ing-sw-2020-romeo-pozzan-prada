package it.polimi.ingsw.gameAction.build;

import it.polimi.ingsw.controller.CellHeightException;
import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.controller.ReachHeightLimitException;
import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.GodCard;
import it.polimi.ingsw.model.Token;

import java.util.List;

public class BuildContext {

    private BuildBehavior buildStrategy;

    public BuildContext(BuildBehavior buildStrategy) {
        this.buildStrategy = buildStrategy;
    }

    public List<Cell> executeValidBuilds(Token selectedToken, Token otherToken, List<Token> enemyTokens, List<GodCard> enemyGodCards, Battlefield battlefield) throws CellOutOfBattlefieldException {
        return buildStrategy.computeValidBuilds(selectedToken, otherToken, enemyTokens, enemyGodCards, battlefield);
    }

    public void executePerformBuild(Cell targetCell, Battlefield battlefield) throws CellHeightException, ReachHeightLimitException {
        buildStrategy.performBuild(targetCell, battlefield);
    }
}
