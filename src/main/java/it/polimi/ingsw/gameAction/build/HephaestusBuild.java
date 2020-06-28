package it.polimi.ingsw.gameAction.build;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;


/**
 *  HEPHAESTUS
 *
 *  Your Worker may build one additional block (not dome) on top of your first block",
 */
public class HephaestusBuild implements BuildBehavior {


    /**
     *
     * @param selectedToken the token a player want to move,
     * @param otherToken the other player token.
     * @param enemyTokens a list of all enemy tokens.
     * @param enemyGodCards a list of all enemy god cards.
     * @param battlefield the model's battlefield.
     * @return a list of cell in which a player can build.
     */
    @Override
    public List<Cell> computeValidBuilds(Token selectedToken, Token otherToken, List<Token> enemyTokens, List<GodCard> enemyGodCards, Battlefield battlefield, List<Player> allPlayers) {

        int provX, provY;
        List<Cell> buildableCells = new ArrayList<>();

        enemyTokens.add(selectedToken);
        if (otherToken!=null)
            enemyTokens.add(otherToken);

        for (int i=-1; i<2; i++){
            provX = selectedToken.getTokenPosition().getPosX()+i;
            for (int j=-1; j<2; j++){
                provY = selectedToken.getTokenPosition().getPosY()+j;

                if ( (provX>=0 && provX <5) && (provY>=0 && provY<5) &&
                        (battlefield.getCell(provX,provY).getHeight()<=3 && !battlefield.getCell(provX,provY).getIsDome()) ) {

                    buildableCells.add(battlefield.getCell(provX, provY));
                }
            }
        }

        // Then all the cells where is present a token will be removed

        buildableCells.remove(selectedToken.getTokenPosition());

        if (otherToken!=null) buildableCells.remove(otherToken.getTokenPosition());

        for (Token t: enemyTokens) {
            buildableCells.remove(t.getTokenPosition());
        }

        return buildableCells;
    }

    /**
     * Here i have to increment twice
     * @param targetCell the cell to be incremented.
     * @param secondCell the other cell to be incremented.
     * @param battlefield the model's battlefield.
     */
    @Override
    public void performBuild(Cell targetCell, Cell secondCell, Battlefield battlefield) {
        battlefield.getCell(targetCell).incrementHeight();
        battlefield.getCell(targetCell).incrementHeight();
    }
}
