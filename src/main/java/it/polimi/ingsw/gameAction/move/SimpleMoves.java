package it.polimi.ingsw.gameAction.move;

import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
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
     * @throws CellOutOfBattlefieldException if something goes wrong.
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

        try{
            allMoves.remove(battlefield.getCell(selectedToken.getTokenPosition()));         // rimuovo la posizione in cui sono
        }catch(NullPointerException ignore){}
        try{
            allMoves.remove(battlefield.getCell(otherToken.getTokenPosition()));            // rimuovo la posizione del mio altro token
        }catch(NullPointerException ignore){}

        for (Token enemyToken : enemyTokens) {
            try{
                allMoves.remove(battlefield.getCell(enemyToken.getTokenPosition()));        // rimuovo la posizione dei token dei miei nemici
            }catch (NullPointerException ignore) {}
        }

        return allMoves;
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
    public void performMove(Token selectedToken, Token otherToken, List<Token> enemyTokens, Cell targetCell, List<GodCard> enemyGodCards, Battlefield battlefield, boolean didAthenaMovedUp) {

        selectedToken.getTokenPosition().setFree();
        selectedToken.setOldHeight(selectedToken.getTokenPosition().getHeight());

        selectedToken.setTokenPosition(targetCell);
        selectedToken.getTokenPosition().setOccupied();

    }

}



