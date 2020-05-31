package it.polimi.ingsw.gameAction.move;

import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.cli.*;

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
    public List<Cell> computeValidMoves(Token selectedToken, Token otherToken, List<Token> enemyTokens, GodCard myGodCard, List<GodCard> enemyGodCards, Battlefield battlefield, List<Cell> moveToCheck) throws CellOutOfBattlefieldException {

        List<Cell> allMoves = new ArrayList<Cell>();

        int provX, provY;

        for (int i=-1; i<2; i++) {                                                   // ciclo di +-1 intorno alla posizione del token
            provX = selectedToken.getTokenPosition().getPosX() + i;                            // per poter ottenere le 8 caselle in cui
            for (int j = -1; j < 2; j++) {                                               // posso muovere
                provY = selectedToken.getTokenPosition().getPosY() + j;
                if ((provX >= 0 && provX < 5) && (provY >= 0 && provY < 5) &&                     // la cella provv è dentro le dimensioni del battlefield
                        (battlefield.getCell(provX, provY).getHeight() -              // l'altezza della cella provv -
                                selectedToken.getTokenPosition().getHeight() <= 1) &&            // l'altezza del token <= 1
                        (!battlefield.getCell(provX, provY).getIsDome())) {         // non deve essere una cupola
                    allMoves.add(battlefield.getCell(provX, provY));
                }
            }
        }

        List<Cell> allMovesToReturn = new ArrayList<>(allMoves);

        for (Cell validCell: allMoves) {
            try{
                allMovesToReturn.remove(battlefield.getCell(selectedToken.getTokenPosition()));     // rimuovo le posizioni dei miei token
            } catch (NullPointerException ignore){}
            try{
                allMovesToReturn.remove(battlefield.getCell(otherToken.getTokenPosition()));        // e no quelle dei miei avversari
            } catch (NullPointerException ignore){}

        }
        return allMovesToReturn;
    }


    /**
     *  Here is handled the swap. It takes the to-be-moved token's position and the target-token position and swap them.
     * @param () the same as the simple perform move.
     */
    @Override
    public void performMove(Token selectedToken, Token otherToken, List<Token> enemyTokens, Cell targetCell, List<GodCard> enemyGodCards, Battlefield battlefield) {

        Cell selectedTokenPosition = selectedToken.getTokenPosition();
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
            selectedToken.setTokenPosition(swapToken.getTokenPosition());
            selectedToken.setOldHeight(selectedTokenPosition.getHeight());

            swapToken.setOldHeight(swapToken.getTokenPosition().getHeight());
            swapToken.setTokenPosition(selectedTokenPosition);

            selectedToken.getTokenPosition().setOccupied();
            swapToken.getTokenPosition().setOccupied();
        }
    }
}
