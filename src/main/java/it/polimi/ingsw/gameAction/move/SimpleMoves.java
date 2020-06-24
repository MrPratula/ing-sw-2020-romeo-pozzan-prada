package it.polimi.ingsw.gameAction.move;

import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.GodCard;
import it.polimi.ingsw.model.Token;

import java.util.ArrayList;
import java.util.List;


/**
 * SIMPLE COMPUTE VALID MOVES
 *
 * This method check for normal moves that a token can perform.
 * All other variants of this works such as this one with some extra details.
 * Those modification are specified in their JavaDOC.
 * All that is not specified, works exactly like this one.
 */
public class SimpleMoves implements MoveBehavior {


    /**
     * A token can move in all 8 Cells around himself with the following exceptions:
     * he can not occupy a cell where is another token (regardless if it's his own or not);
     * he can not move up on a height >1 of it's own;
     * he can not move on a dome;
     * he can not move out the battlefield.
     * @param selectedToken the token a player want to move.
     * @param otherToken the player-in-turn other token.
     * @param enemyTokens a list of all enemy token.
     * @param myGodCard the player-in-turn god card.
     * @param enemyGodCards a list of all enemy god cards.
     * @param battlefield the model's battlefield.
     * @return a list of cell in which a player can move his token. It is null if he can not move that token
     */
    @Override
    public List<Cell> computeValidMoves(Token selectedToken, Token otherToken, List<Token> enemyTokens, GodCard myGodCard, List<GodCard> enemyGodCards, Battlefield battlefield, List<Cell> moveToCheck){

        List<Cell> allMoves = new ArrayList<>();

        int provX, provY;

        // Cycle around my token position to get the 8 cell in which i can move
        for (int i=-1; i<2; i++) {
            provX = selectedToken.getTokenPosition().getPosX() + i;
            for (int j = -1; j < 2; j++) {
                provY = selectedToken.getTokenPosition().getPosY() + j;

                // Then it is added to the list if it is inside the battlefield
                if ((provX >= 0 && provX < 5) && (provY >= 0 && provY < 5)){
                    // if the different height is legal
                    if ((battlefield.getCell(provX, provY).getHeight() -selectedToken.getTokenPosition().getHeight() <= 1)){
                        // if it is not a dome
                        if (!battlefield.getCell(provX, provY).getIsDome())
                            allMoves.add(battlefield.getCell(provX, provY));
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
     * Just take the target position and set it for the to-be-moved token.
     * Need to set free the old position and set occupied the new position.
     * @param selectedToken the token a player want to move.
     * @param otherToken the player-in-turn other token.
     * @param enemyTokens a list of all enemy token.
     * @param targetCell the cell I want to move my token.
     * @param enemyGodCards a list of all enemy god cards.
     * @param battlefield the model's battlefield.
     */
    @Override
    public void performMove(Token selectedToken, Token otherToken, List<Token> enemyTokens, Cell targetCell, List<GodCard> enemyGodCards, Battlefield battlefield) {

        // Set token's old height
        selectedToken.setOldHeight(battlefield.getCell(selectedToken.getTokenPosition()).getHeight());

        // Set free old position
        selectedToken.getTokenPosition().setFree();

        // Set new token position
        selectedToken.setTokenPosition(targetCell);

        // Set new position occupied
        selectedToken.getTokenPosition().setOccupied();
    }
}



