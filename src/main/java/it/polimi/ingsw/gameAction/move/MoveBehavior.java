package it.polimi.ingsw.gameAction.move;

import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.GodCard;
import it.polimi.ingsw.model.Token;

import java.util.List;


/**
 * Each GodCard that modify the move mechanics has to implement at least one of those methods.
 * For example Apollo has to implement both, one for calculate the different valid moves,
 * the last for handle the swap.
 * Artemis doesn't need the second one because once she calculate the valid moves, the move itself is the same
 * as the normal move.
 * She will implement the method because it has to be, but it is never called and it is empty.
 */
public interface MoveBehavior {

    public List<Cell> computeValidMoves(Token selectedToken, Token otherToken, List<Token> enemyTokens, GodCard myGodCard, List<GodCard> enemyGodCards, Battlefield battlefield) throws CellOutOfBattlefieldException;

    public void performMove(Token selectedToken, Token otherToken, List<Token> enemyTokens, Cell targetCell, List<GodCard> enemyGodCards, Battlefield battlefield) throws CellOutOfBattlefieldException;

}