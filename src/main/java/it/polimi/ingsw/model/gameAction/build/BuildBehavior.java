package it.polimi.ingsw.model.gameAction.build;

import it.polimi.ingsw.model.*;

import java.util.List;


/**
 * This is the interface for the build behavior.
 * For more info about this interface read the Move's interface JavaDOC.
 */
public interface BuildBehavior {

    List<Cell> computeValidBuilds(Token selectedToken, Token otherToken, List<Token> enemyTokens, List<GodCard> enemyGodCards, Battlefield battlefield, List<Player> allPlayers);

    void performBuild(Cell targetCell, Cell second_cell, Battlefield battlefield);
}
