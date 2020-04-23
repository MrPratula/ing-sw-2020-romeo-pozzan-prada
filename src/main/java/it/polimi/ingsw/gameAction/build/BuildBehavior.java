package it.polimi.ingsw.gameAction.build;

import it.polimi.ingsw.controller.CellHeightException;
import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.controller.ReachHeightLimitException;
import it.polimi.ingsw.model.*;

import java.util.List;


/**
 * This is the interface for the build behavior.
 * For more info about this interface read the Move's interface JavaDOC.
 */
public interface BuildBehavior {

    public List<Cell> computeValidBuilds(Token selectedToken, Token otherToken, List<Token> enemyTokens, List<GodCard> enemyGodCards, Battlefield battlefield, List<Player> allPlayers) throws CellOutOfBattlefieldException;

    public void performBuild(Cell targetCell, Battlefield battlefield) throws CellHeightException, ReachHeightLimitException;
}
