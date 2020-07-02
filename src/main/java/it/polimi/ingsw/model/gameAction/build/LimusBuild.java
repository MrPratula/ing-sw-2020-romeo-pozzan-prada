package it.polimi.ingsw.model.gameAction.build;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;


/**
 *  LIMUS
 *
 *  Opponents Workers can not build on spaces neighboring your workers,
 *  unless building a dome to create a Complete Tower");
 */
public class LimusBuild implements BuildBehavior {


    /**
     * This function is called right after the calculation of the valid builds.
     * Here we create a list of cells near Limus' tokens, except if it has height 3,
     * that the caller has to remove from the given valid builds.
     * @param selectedToken the token a player want to move,
     * @param otherToken the other player token.
     * @param enemyTokens a list of all enemy tokens.
     * @param enemyGodCards a list of all enemy god cards.
     * @return a list oh cells that we have to remove from valid builds
     */
    @Override
    public List<Cell> computeValidBuilds(Token selectedToken, Token otherToken, List<Token> enemyTokens, List<GodCard> enemyGodCards, Battlefield battlefield, List<Player> allPlayers) {

        List<Cell> toRemove = new ArrayList<>();

        for (Player p : allPlayers) {
            if(p.getMyGodCard().equals((GodCard.LIMUS))){
                List<Cell> limusTokensPosition = new ArrayList<>();
                limusTokensPosition.add(p.getToken1().getTokenPosition());
                limusTokensPosition.add(p.getToken2().getTokenPosition());
                int provX, provY, i, j;

                for(Cell c : limusTokensPosition){
                    for(i=-1; i<2; i++){
                        provX = c.getPosX()+i;
                        for(j=-1; j<2; j++){
                            provY = c.getPosY()+j;
                            if (0<=provX && provX<5 && 0<=provY && provY<5) {
                                if (battlefield.getCell(provX, provY).getHeight() != 3) {
                                    toRemove.add(battlefield.getCell(provX, provY));
                                }
                            }
                        }
                    }
                }
            }
        }
        return toRemove;
    }


    /**
     * Never called, because he has to do the simple performBuild
     */
    @Override
    public void performBuild(Cell targetCell, Cell second_cell, Battlefield battlefield) {
    }
}
