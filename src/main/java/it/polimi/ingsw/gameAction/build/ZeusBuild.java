package it.polimi.ingsw.gameAction.build;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;


/**
 * ZEUS:
 *
 * Your Worker may build a block under itself.
 */
public class ZeusBuild implements BuildBehavior {


    /**
     * Check the said-above conditions and create a list of valid cells.
     * @param () : the same as normal computeValidBuilds
     * @return a list of cell in which a player can build,
     * including the one of the actual selected token.
     */
    @Override
    public List<Cell> computeValidBuilds(Token selectedToken, Token otherToken, List<Token> enemyTokens, List<GodCard> enemyGodCards, Battlefield battlefield, List<Player> allPlayers) {

        int provX, provY;
        List<Cell> buildableCells = new ArrayList<>();

        if (otherToken!=null)
            enemyTokens.add(otherToken);

        for (int i=-1; i<2; i++){
            provX = selectedToken.getTokenPosition().getPosX()+i;
            for (int j=-1; j<2; j++){
                provY = selectedToken.getTokenPosition().getPosY()+j;

                if ( (provX>=0 && provX <5) && (provY>=0 && provY<5) &&
                        (!battlefield.getCell(provX,provY).getIsDome()) ) {
                    buildableCells.add(battlefield.getCell(provX, provY));
                }
            }
        }

        // Then all cell where is a token are removed
        if (otherToken!=null)
            buildableCells.remove(otherToken.getTokenPosition());

        try{
            for (Token t: enemyTokens) {
                buildableCells.remove(t.getTokenPosition());
            }
            //he can't build under himself if he's al level 3
            if(battlefield.getCell(selectedToken.getTokenPosition()).getHeight()==3)
                buildableCells.remove(selectedToken.getTokenPosition());
        } catch(NullPointerException ignore){}


        return buildableCells;
        }


    /**
     * This just receive a Cell and call the incrementHeight method on that cell.
     * The difference is that here i receive even the cell of the actual selected token.
     * @param targetCell the cell to be incremented.
     * @param battlefield the model's battlefield.
     */
    @Override
    public void performBuild(Cell targetCell, Cell second_cell, Battlefield battlefield) {
        battlefield.getCell(targetCell).incrementHeight();
    }
}
