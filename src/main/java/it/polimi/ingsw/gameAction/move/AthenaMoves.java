package it.polimi.ingsw.gameAction.move;

import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.cli.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ATHENA
 *
 * If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn
 */
public class AthenaMoves implements MoveBehavior{


    /**
     * This is called if ATHENA exist, by her opponents.
     * It receive a list of valid moves and remove from that all the moves that make the token go up.
     * @param selectedToken token to check his height.
     * @param otherToken null
     * @param enemyTokens null
     * @param myGodCard null
     * @param enemyGodCards null
     * @param battlefield null
     * @param movesToCheck the moves to check for height.
     * @return the same movesToCheck with some elimination if they satisfy the ATHENA rule.
     * @throws CellOutOfBattlefieldException if something goes wrong.
     */
    @Override
    public List<Cell> computeValidMoves(Token selectedToken, Token otherToken, List<Token> enemyTokens, GodCard myGodCard, List<GodCard> enemyGodCards, Battlefield battlefield, List<Cell> movesToCheck) throws CellOutOfBattlefieldException {

        List<Cell> movesToReturn = new ArrayList<>(movesToCheck);


        for (Cell cell: movesToCheck) {
            if (selectedToken.getTokenPosition().getHeight() < cell.getHeight()) {
                movesToReturn.remove(cell);
            }
        }
        return movesToReturn;
    }


    /**
     * This works as the simple move, but it check for the height movement and if the token goes up it
     * change the values of didAthenaMovedUp, so once the enemy try to move,
     * their valid builds will be checked for limitations
     * @param selectedToken the token to move.
     * @param otherToken null.
     * @param enemyTokens null.
     * @param targetCell the cell to move.
     * @param enemyGodCards null.
     * @param battlefield null.
     * @throws CellOutOfBattlefieldException if something goes wrong.
     */
    @Override
    public void performMove(Token selectedToken, Token otherToken, List<Token> enemyTokens, Cell targetCell, List<GodCard> enemyGodCards, Battlefield battlefield) throws CellOutOfBattlefieldException {

        int oldHeight, newHeight;

        oldHeight = selectedToken.getTokenPosition().getHeight();
        selectedToken.getTokenPosition().setFree();
        selectedToken.setTokenPosition(targetCell);
        newHeight = selectedToken.getTokenPosition().getHeight();
        selectedToken.getTokenPosition().setOccupied();

        Model.athenaMovedUp(oldHeight - newHeight < 0);
    }
}
