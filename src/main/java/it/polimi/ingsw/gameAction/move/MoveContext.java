package it.polimi.ingsw.gameAction.move;

import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.GodCard;
import it.polimi.ingsw.model.Token;

import java.util.List;


/**
 * Here is where the model can chose what kind of move has to be called.
 * It will construct a MoveContext with a certain strategy and then call the correct method on this
 * strategy-kind MoveContext. It will call the corresponding method on the class the model has specified.
 */
public class MoveContext {

   private final MoveBehavior moveStrategy;

   public MoveContext(MoveBehavior moveStrategy) {
       this.moveStrategy = moveStrategy;
   }

   public List<Cell> executeValidMoves(Token selectedToken, Token otherToken, List<Token> enemyTokens, GodCard myGodCard, List<GodCard> enemyGodCards, Battlefield battlefield, List<Cell> moveToCheck) throws CellOutOfBattlefieldException {
       return moveStrategy.computeValidMoves(selectedToken, otherToken, enemyTokens, myGodCard, enemyGodCards, battlefield, moveToCheck);
   }

   public void executeMove (Token selectedToken, Token otherToken, List<Token> enemyTokens, Cell targetCell, List<GodCard> enemyGodCards, Battlefield battlefield) throws CellOutOfBattlefieldException {
       moveStrategy.performMove(selectedToken, otherToken, enemyTokens, targetCell, enemyGodCards, battlefield);
   }

}
