package it.polimi.ingsw.gameAction.move;

import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.cli.Battlefield;
import it.polimi.ingsw.cli.Cell;
import it.polimi.ingsw.cli.GodCard;
import it.polimi.ingsw.cli.Token;

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

        List<Cell> allMoves = new ArrayList<Cell>();

        int provX, provY;

        for (int i=-1; i<2; i++) {                                                                                       // ciclo di +-1 intorno alla posizione del token
            provX = selectedToken.getTokenPosition().getPosX() + i;                                                      // per poter ottenere le 8 caselle in cui
            for (int j = -1; j < 2; j++) {                                                                               // posso muovere
                provY = selectedToken.getTokenPosition().getPosY() + j;
                if ((provX >= 0 && provX < 5) && (provY >= 0 && provY < 5) &&                                            // la cella provv è dentro le dimensioni del battlefield
                        ( battlefield.getCell(provX, provY).getHeight()<=selectedToken.getTokenPosition().getHeight() ) &&            // NON PUO' SALIRE; QUINDI L'ALTEZZA DEVE ESSERE UGUALE O MINORE
                        (!battlefield.getCell(provX, provY).getIsDome())) {                                              // non deve essere una cupola
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
                allMovesToReturn.remove(battlefield.getCell(otherToken.getTokenPosition()));
            } catch (NullPointerException ignore){}
            try{
                for (Token enemyToken : enemyTokens) {
                    allMovesToReturn.remove(battlefield.getCell(enemyToken.getTokenPosition()));        // rimuovo la posizione dei token dei miei nemici
                }
            } catch (NullPointerException ignore){}
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
    public void performMove(Token selectedToken, Token otherToken, List<Token> enemyTokens, Cell targetCell, List<GodCard> enemyGodCards, Battlefield battlefield) {

        selectedToken.getTokenPosition().setFree();
        selectedToken.setTokenPosition(targetCell);
        selectedToken.getTokenPosition().setOccupied();

    }

}
