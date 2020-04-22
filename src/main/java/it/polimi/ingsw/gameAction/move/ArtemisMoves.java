package it.polimi.ingsw.gameAction.move;

import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.GodCard;
import it.polimi.ingsw.model.Token;

import java.util.ArrayList;
import java.util.List;


/**
 * ARTEMIS: Your Worker may move one additional time, but not back to its initial space
 */
public class ArtemisMoves implements MoveBehavior {


    /**
     * Just scan between -2 and +2 around the token position instead of -1 and +1.
     * Other cell restriction such as height limitation or cell occupied still applies.
     */
    @Override
    public List<Cell> computeValidMoves(Token selectedToken, Token otherToken, List<Token> enemyTokens, GodCard myGodCard, List<GodCard> enemyGodCards, Battlefield battlefield) throws CellOutOfBattlefieldException {

        List<Cell> allMoves = new ArrayList<Cell>();
        int provX, provY;

        for (int i=-2; i<3; i++) {                                                   // ciclo di +-1 intorno alla posizione del token
            provX = selectedToken.getTokenPosition().getPosX() + i;                            // per poter ottenere le 8 caselle in cui
            for (int j = -2; j < 3; j++) {                                               // posso muovere
                provY = selectedToken.getTokenPosition().getPosY() + j;
                if ((provX >= 0 && provX < 5) && (provY >= 0 && provY < 5) &&                     // la cella provv è dentro le dimensioni del battlefield
                        (battlefield.getCell(provX, provY).getHeight() -              // l'altezza della cella provv -
                                selectedToken.getTokenPosition().getHeight() <= 1) &&            // l'altezza del token <= 1
                        (!battlefield.getCell(provX, provY).getIsDome())) {         // non deve essere una cupola
                    allMoves.add(battlefield.getCell(provX, provY));
                }
            }
        }
        for (Cell validCell: allMoves) {
            allMoves.remove(battlefield.getCell(selectedToken.getTokenPosition()));         // rimuovo la posizione in cui sono
            allMoves.remove(battlefield.getCell(otherToken.getTokenPosition()));            // rimuovo la posizione del mio altro token

            for (Token enemyToken : enemyTokens) {
                allMoves.remove(battlefield.getCell(enemyToken.getTokenPosition()));        // rimuovo la posizione dei token dei miei nemici
            }
        }
        return allMoves;
    }


    /**
     * This is not needed because the move is the same as the simple move.
     * Though I have to write this because of the interface, but this is never called (hopefully).
     */
    @Override
    public void performMove(Token selectedToken, Token otherToken, List<Token> enemyTokens, Cell targetCell, List<GodCard> enemyGodCards, Battlefield battlefield) {

    }
}
