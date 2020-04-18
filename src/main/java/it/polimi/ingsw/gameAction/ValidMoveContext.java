package it.polimi.ingsw.gameAction;

import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.GodCard;
import it.polimi.ingsw.model.Token;

import java.util.List;

public class ValidMoveContext {

   private MoveBehavior moveStrategy;

   public ValidMoveContext(MoveBehavior moveStrategy) {
       this.moveStrategy = moveStrategy;
   }

   public List<Cell> executeValidMoves(Token selectedToken, Token otherToken, List<Token> enemyTokens, GodCard myGodCard, List<GodCard> enemyGodCards, Battlefield battlefield) throws CellOutOfBattlefieldException {
       return moveStrategy.computeValidMoves(selectedToken, otherToken, enemyTokens, myGodCard, enemyGodCards, battlefield);
   }

}
