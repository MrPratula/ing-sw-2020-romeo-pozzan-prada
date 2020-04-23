package it.polimi.ingsw.gameAction.build;

import it.polimi.ingsw.controller.CellHeightException;
import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.controller.ReachHeightLimitException;
import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;



public class LimusBuild implements BuildBehavior {


    /**
     * This function is called right after the calculation of the valid builds.
     * Here we create a list of cells near Limus' tokens, except if it has height 3,
     * that the caller has to remove from the provvisory valid builds
     * @param : same as normal computeValidBuilds
     * @return a list oh cells that we have to remove from valid builds
     */
    @Override
    public List<Cell> computeValidBuilds(Token selectedToken, Token otherToken, List<Token> enemyTokens, List<GodCard> enemyGodCards, Battlefield battlefield, List<Player> allPlayers) throws CellOutOfBattlefieldException {

        List<Cell> toRemove = new ArrayList<>();

        for (Player p : allPlayers) {
            if(p.getMyGodCard().equals((GodCard.LIMUS))){                                           // se in gioco c'è limus
                List<Cell> limusTokensPosition = new ArrayList<>();                                 //metto le posizioni dei token di limus
                limusTokensPosition.add(p.getToken1().getTokenPosition());
                limusTokensPosition.add(p.getToken2().getTokenPosition());
                int provX, provY, i, j;

                for(Cell c : limusTokensPosition){
                    for(i=-1; i<2; i++){
                        provX = c.getPosX()+i;
                        for(j=-1; j<2; j++){
                            provY = c.getPosY()+j;
                            if(battlefield.getCell(provX,provY).getHeight()!=3){                      //puo costruire un dome tranquillamente
                                toRemove.add(battlefield.getCell(provX, provY));                      //lista di celle da rimuovere dalla validBuildsProvv nel chiamante
                            }
                        }
                    }
                    //else    puo costruire un dome tranquillamente
                }
            }
        }
        return toRemove;
    }

    @Override
    public void performBuild(Cell targetCell, Battlefield battlefield) throws CellHeightException, ReachHeightLimitException {
    }
}
