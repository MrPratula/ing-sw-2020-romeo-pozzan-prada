package it.polimi.ingsw.gameAction.move;

import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;


/**
 * PROMETHEUS: If your Worker does not move up, it may build both before and after moving.
 */
public class PrometheusMove implements MoveBehavior {


    /**
     * This override doesn't allow the token to move up.
     * @param () the same as the normal computeValidMoves.
     * @return a list of cell in which a player can move his token. It is null if he can not move that token
     * @throws CellOutOfBattlefieldException if something goes wrong.
     */
    @Override
    public List<Cell> computeValidMoves(Token selectedToken, Token otherToken, List<Token> enemyTokens, GodCard myGodCard, List<GodCard> enemyGodCards, Battlefield battlefield, List<Cell> moveToCheck) throws CellOutOfBattlefieldException {

        List<Cell> allMoves = new ArrayList<>();

        boolean canIMoveUp = !Model.isDidPrometheusUsePower();

        int provX, provY;

        for (int i=-1; i<2; i++) {
            provX = selectedToken.getTokenPosition().getPosX() + i;
            for (int j = -1; j < 2; j++) {
                provY = selectedToken.getTokenPosition().getPosY() + j;

                // if the cell is inside the battlefield
                if ((provX >= 0 && provX < 5) && (provY >= 0 && provY < 5)) {

                    // If the cell is not a dome
                    if (!battlefield.getCell(provX, provY).getIsDome()) {

                        // If i can not move up
                        if (!canIMoveUp) {

                            // Simple valid moves without going up
                            if ((battlefield.getCell(provX, provY).getHeight() <= selectedToken.getTokenPosition().getHeight())) {
                                allMoves.add(battlefield.getCell(provX, provY));
                            }
                        }
                        // Simple move instead
                        else {
                            if (battlefield.getCell(provX, provY).getHeight() -selectedToken.getTokenPosition().getHeight() <= 1){
                                allMoves.add(battlefield.getCell(provX, provY));
                            }
                        }
                    }
                }
            }
        }

        List<Cell> allMovesToReturn = new ArrayList<>(allMoves);

        // Remove both my token position from valid moves
        try{
            allMovesToReturn.remove(battlefield.getCell(selectedToken.getTokenPosition()));
        } catch (NullPointerException ignore){}
        try{
            allMovesToReturn.remove(battlefield.getCell(otherToken.getTokenPosition()));
        } catch (NullPointerException ignore){}

        // Remove enemy tokens position from valid moves
        try{
            for (Token enemyToken : enemyTokens) {
                allMovesToReturn.remove(battlefield.getCell(enemyToken.getTokenPosition()));
            }
        } catch (NullPointerException ignore){}

        return allMovesToReturn;
    }


    /**
     * It handle the move.
     * Take the target position and set it for the to-be-moved token.
     * Set free the old position and set occupied the new position.
     * Set the moved token old height.
     * @param selectedToken the token a player want to move.
     * @param otherToken the player-in-turn other token.
     * @param enemyTokens a list of all enemy token.
     * @param targetCell the cell I want to move my token.
     * @param enemyGodCards a list of all enemy god cards.
     * @param battlefield the model's battlefield.
     */
    @Override
    public void performMove(Token selectedToken, Token otherToken, List<Token> enemyTokens, Cell targetCell, List<GodCard> enemyGodCards, Battlefield battlefield) {

        selectedToken.setOldHeight(battlefield.getCell(selectedToken.getTokenPosition()).getHeight());
        battlefield.getCell(selectedToken.getTokenPosition()).setFree();
        selectedToken.setTokenPosition(battlefield.getCell(targetCell));
        battlefield.getCell(selectedToken.getTokenPosition()).setOccupied();
    }
}
