package it.polimi.ingsw.gameAction.build;

import it.polimi.ingsw.model.*;

import java.util.List;

public class DemeterBuild implements BuildBehavior {

    @Override
    public List<Cell> computeValidBuilds(Token selectedToken, Token otherToken, List<Token> enemyTokens, List<GodCard> enemyGodCards, Battlefield battlefield, List<Player> allPlayers) {
        return null;
    }

    @Override
    public void performBuild(Cell targetCell, Cell second_cell, Battlefield battlefield) {
        battlefield.getCell(targetCell).incrementHeight();
        if(second_cell != null) {
            battlefield.getCell(second_cell).incrementHeight();
        }
    }
}
