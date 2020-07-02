package it.polimi.ingsw.model.gameAction.move;

import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.GodCard;
import it.polimi.ingsw.model.Token;

import java.util.ArrayList;
import java.util.List;


/**
 * APOLLO
 *
 * Your Worker may move into an opponent Worker’s space by forcing their Worker to the space yours just vacated.
 */
public class ApolloMoves implements MoveBehavior {


    /**
     * It just do not remove opponent's token from the valid moves.
     * The swap is handled by the performMove.
     */
    @Override
    public List<Cell> computeValidMoves(Token selectedToken, Token otherToken, List<Token> enemyTokens, GodCard myGodCard, List<GodCard> enemyGodCards, Battlefield battlefield, List<Cell> moveToCheck) {

        List<Cell> allMoves = new ArrayList<>();

        int provX, provY;

        for (int i=-1; i<2; i++) {
            provX = selectedToken.getTokenPosition().getPosX() + i;
            for (int j = -1; j < 2; j++) {
                provY = selectedToken.getTokenPosition().getPosY() + j;
                if ((provX >= 0 && provX < 5) && (provY >= 0 && provY < 5) &&
                        (battlefield.getCell(provX, provY).getHeight() -
                                selectedToken.getTokenPosition().getHeight() <= 1) &&
                        (!battlefield.getCell(provX, provY).getIsDome())) {
                    allMoves.add(battlefield.getCell(provX, provY));
                }
            }
        }

        List<Cell> allMovesToReturn = new ArrayList<>(allMoves);

        // Remove both my token position from valid moves and do not remove enemy tokens position
        try{
            allMovesToReturn.remove(battlefield.getCell(selectedToken.getTokenPosition()));
        } catch (NullPointerException ignore){}
        try{
            allMovesToReturn.remove(battlefield.getCell(otherToken.getTokenPosition()));
        } catch (NullPointerException ignore){}

        return allMovesToReturn;
    }


    /**
     *  Here is handled the swap. It takes the to-be-moved token's position and the target-token position and swap them.
     * @param () the same as the simple perform move.
     */
    @Override
    public void performMove(Token selectedToken, Token otherToken, List<Token> enemyTokens, Cell targetCell, List<GodCard> enemyGodCards, Battlefield battlefield) {

        Token swapToken = null;

        if (targetCell.getThereIsPlayer()) {

            for(Token t: enemyTokens) {
                if (t.getTokenPosition().equals(targetCell)){
                    swapToken = t;
                    break;
                }
            }
        }

        // Simple move
        if (swapToken==null){
            selectedToken.getTokenPosition().setFree();
            selectedToken.setOldHeight(selectedToken.getTokenPosition().getHeight());

            selectedToken.setTokenPosition(targetCell);
            selectedToken.getTokenPosition().setOccupied();
        }
        // Handle the swap
        else{
            // Set old height for both token
            selectedToken.setOldHeight(battlefield.getCell(selectedToken.getTokenPosition()).getHeight());
            swapToken.setOldHeight(battlefield.getCell(swapToken.getTokenPosition()).getHeight());

            // Change the position
            Cell provCell = battlefield.getCell(selectedToken.getTokenPosition());

            selectedToken.setTokenPosition(swapToken.getTokenPosition());
            swapToken.setTokenPosition(battlefield.getCell(provCell));

            // Set both occupied
            battlefield.getCell(selectedToken.getTokenPosition()).setOccupied();
            battlefield.getCell(swapToken.getTokenPosition()).setOccupied();
        }
    }
}
