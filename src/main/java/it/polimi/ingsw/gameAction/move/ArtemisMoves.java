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
     * It works as simple move, but for each cell of simple move it compute another simple move and add those cell to
     * valid moves.
     * Other cell restriction such as height limitation or cell occupied still applies.
     */
    @Override
    public List<Cell> computeValidMoves(Token selectedToken, Token otherToken, List<Token> enemyTokens, GodCard myGodCard, List<GodCard> enemyGodCards, Battlefield battlefield, List<Cell> moveToCheck) throws CellOutOfBattlefieldException {

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
                    if ((battlefield.getCell(provX, provY).getHeight() - selectedToken.getTokenPosition().getHeight() <= 1)){
                        // if it is not a dome
                        if (!battlefield.getCell(provX, provY).getIsDome())
                            allMoves.add(battlefield.getCell(provX, provY));
                    }
                }
            }
        }
        // Remove my token position.
        allMoves.remove(battlefield.getCell(selectedToken.getTokenPosition()));

        // Remove my other token position.
        try{
            allMoves.remove(battlefield.getCell(otherToken.getTokenPosition()));
        }catch(NullPointerException ignore){}

        // Remove enemies token positions.
        for (Token enemyToken : enemyTokens) {
            try{
                allMoves.remove(battlefield.getCell(enemyToken.getTokenPosition()));
            }catch (NullPointerException ignore) {}
        }

        // Now I have normal validMoves. For each of them I can move again
        List<Cell> allMovesArtemis = new ArrayList<>(allMoves);

        // Scan for each cell of valid moves
        for (Cell cell: allMoves) {
            // Cycle around each cell of valid moves to get all the potential 25 valid moves
            for (int i=-1; i<2; i++) {
                provX = cell.getPosX() + i;
                for (int j = -1; j < 2; j++) {
                    provY = cell.getPosY() + j;

                    // The cell is added if it is inside the battlefield
                    if ((provX >= 0 && provX < 5) && (provY >= 0 && provY < 5)) {
                        // if the height difference is legal
                        if ((battlefield.getCell(provX, provY).getHeight() - cell.getHeight() <= 1)) {
                            // if it is not a dome
                            if (!battlefield.getCell(provX, provY).getIsDome()) {
                                // if it is not already in the list
                                if (!allMovesArtemis.contains(battlefield.getCell(provX,provY)))
                                    allMovesArtemis.add(battlefield.getCell(provX, provY));
                            }
                        }
                    }
                }
            }

            // Remove my token position.
            allMovesArtemis.remove(battlefield.getCell(selectedToken.getTokenPosition()));

            // Remove my other token position.
            try{
                allMovesArtemis.remove(battlefield.getCell(otherToken.getTokenPosition()));
            }catch(NullPointerException ignore){}

            // Remove enemies token positions.
            for (Token enemyToken : enemyTokens) {
                try{
                    allMovesArtemis.remove(battlefield.getCell(enemyToken.getTokenPosition()));
                }catch (NullPointerException ignore) {}
            }
        }
        return allMovesArtemis;
    }


    /**
     * This is not needed because the move is the same as the simple move.
     * Though I have to write this because of the interface, but this is never called (hopefully).
     */
    @Override
    public void performMove(Token selectedToken, Token otherToken, List<Token> enemyTokens, Cell targetCell, List<GodCard> enemyGodCards, Battlefield battlefield) {

    }
}
