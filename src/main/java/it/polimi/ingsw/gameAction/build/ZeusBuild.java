package it.polimi.ingsw.gameAction.build;

import it.polimi.ingsw.controller.CellHeightException;
import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.controller.ReachHeightLimitException;
import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.GodCard;
import it.polimi.ingsw.model.Token;

import java.util.ArrayList;
import java.util.List;

public class ZeusBuild implements BuildBehavior {



    /**
     * Check the said-above conditions and create a list of valid cells.
     * @param () : the same as normal computeValidBuilds
     * @return a list of cell in which a player can build, including the one of the actual selected token.
     */
    @Override
    public List<Cell> computeValidBuilds(Token selectedToken, Token otherToken, List<Token> enemyTokens, List<GodCard> enemyGodCards, Battlefield battlefield) throws CellOutOfBattlefieldException {
        List<Cell> validBuilds = new ArrayList<>();

            int provX, provY;
            List<Cell> buildableCells = new ArrayList<Cell>();

            if (otherToken!=null)  enemyTokens.add(otherToken);

            for (int i=-1; i<2; i++){                                                   // ciclo di +-1 intorno alla posizione del token
                provX = selectedToken.getTokenPosition().getPosX()+i;                   // per poter ottenere le 8 caselle in cui
                for (int j=-1; j<2; j++){                                               // posso costruire
                    provY = selectedToken.getTokenPosition().getPosY()+j;

                    if ( (provX>=0 && provX <5) && (provY>=0 && provY<5) &&                       // la cella provv è dentro le dimensioni del battlefield
                         (!battlefield.getCell(provX,provY).getIsDome())    ) {                   // non deve essere una cupola

                        for (Token t: enemyTokens) {
                            if (provX != t.getTokenPosition().getPosX() &&                 // il token non può costruire dove c'è un altro token
                                provY != t.getTokenPosition().getPosX()) {

                                buildableCells.add(battlefield.getCell(provX, provY));
                            }
                        }
                    }
                }
            }
            return buildableCells;
        }


    /**
     * This just receive a Cell and call the incrementHeight method on that cell.
     * The difference is that here i receive even the cell of the actual selected token.
     * @param targetCell the cell to be incremented.
     * @param battlefield the model's battlefield.
     */
    @Override
    public void performBuild(Cell targetCell, Battlefield battlefield) throws CellHeightException, ReachHeightLimitException {
        battlefield.getCell(targetCell).incrementHeight();
    }
}
