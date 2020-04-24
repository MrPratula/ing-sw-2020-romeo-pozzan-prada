package it.polimi.ingsw.gameAction.build;

import it.polimi.ingsw.controller.CellHeightException;
import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.controller.ReachHeightLimitException;
import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;

public class DemeterBuild implements BuildBehavior {

    @Override
    public List<Cell> computeValidBuilds(Token selectedToken, Token otherToken, List<Token> enemyTokens, List<GodCard> enemyGodCards, Battlefield battlefield, List<Player> allPlayers) throws CellOutOfBattlefieldException {
        return null;
    }

    @Override
    public void performBuild(Cell targetCell, Battlefield battlefield) throws CellHeightException, ReachHeightLimitException {

    }
}
