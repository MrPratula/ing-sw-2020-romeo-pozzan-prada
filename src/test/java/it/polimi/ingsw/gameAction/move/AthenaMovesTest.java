package it.polimi.ingsw.gameAction.move;

import it.polimi.ingsw.gameAction.Utility;
import it.polimi.ingsw.model.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * ATHENA
 *
 * If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn
 */
public class AthenaMovesTest {

    List<Cell> validMoves;
    Token selectedToken, otherToken;
    Battlefield battlefield;
    List<Token> enemyTokens;
    GodCard myGodCard;
    List<GodCard> enemyGodCards;
    Cell targetCell;


    /**
     * Create an empty list of valid moves and a token.
     */
    @BeforeEach void setUp() {
        validMoves = new ArrayList<>();
        selectedToken = new Token(TokenColor.RED);
        otherToken = new Token(TokenColor.RED);
        enemyTokens = new ArrayList<>();
        enemyGodCards = new ArrayList<>();
    }


    /**
     * Test an empty battlefield with normal moves.
     */
    @Test void validMovesEmptyTest() {

        battlefield = new Battlefield();

        selectedToken.setTokenPosition(battlefield.getCell(2,2));
        battlefield.getCell(2,2).setOccupied();

        enemyGodCards.add(GodCard.ATHENA);
        Model.athenaMovedUp(true);

        MoveContext thisMove = new MoveContext(new SimpleMoves());
        validMoves = thisMove.executeValidMoves(selectedToken, otherToken, enemyTokens, myGodCard, enemyGodCards, battlefield, null);

        if (enemyGodCards.contains(GodCard.ATHENA) && Model.isAthenaMovedUp()) {
            MoveContext thisMove2 = new MoveContext(new AthenaMoves());
            validMoves = thisMove2.executeValidMoves(selectedToken, null, null, null, null, battlefield, validMoves);
        }
        Assert.assertEquals(8, validMoves.size());
    }


    /**
     * Test the TestBattlefield with some tokens on it.
     * It is added a build on 3,1 to test the move on the same height too.
     */
    @Test void validMovesReductionTest() {

        battlefield = Utility.setUpForTest1();

        selectedToken.setTokenPosition(battlefield.getCell(2,2));
        battlefield.getCell(2,2).setOccupied();

        otherToken.setTokenPosition(battlefield.getCell(1,2));
        battlefield.getCell(1,2).setOccupied();

        enemyTokens.add(new Token(TokenColor.BLUE));
        enemyTokens.get(0).setTokenPosition(battlefield.getCell(1,1));
        battlefield.getCell(1,1).setOccupied();

        battlefield.getCell(3,1).setHeight(1);

        enemyGodCards.add(GodCard.ATHENA);
        Model.athenaMovedUp(true);

        MoveContext thisMove = new MoveContext(new SimpleMoves());
        validMoves = thisMove.executeValidMoves(selectedToken, otherToken, enemyTokens, myGodCard, enemyGodCards, battlefield, null);

        if (enemyGodCards.contains(GodCard.ATHENA) && Model.isAthenaMovedUp()) {
            MoveContext thisMove2 = new MoveContext(new AthenaMoves());
            validMoves = thisMove2.executeValidMoves(selectedToken, null, null, null, null, battlefield, validMoves);
        }
        Assert.assertEquals(2, validMoves.size());
        Assert.assertTrue(validMoves.contains(battlefield.getCell(3,1)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(3,3)));
    }


    /**
     * Test if athena moves up and the value in the model is correctly changed.
     */
    @Test void performMoveMovingUpTest() {

        battlefield = Utility.setUpForTest1();
        selectedToken.setTokenPosition(battlefield.getCell(2,2));
        targetCell = battlefield.getCell(1,3);
        battlefield.getCell(1,3).setOccupied();

        MoveContext thisMove = new MoveContext(new AthenaMoves());
        thisMove.executeMove(selectedToken, otherToken, enemyTokens, targetCell, enemyGodCards, battlefield);

        Assert.assertTrue(Model.isAthenaMovedUp());
        Assert.assertTrue(battlefield.getCell(1,3).getThereIsPlayer());
        Assert.assertFalse(battlefield.getCell(2,2).getThereIsPlayer());
        Assert.assertTrue(selectedToken.getTokenPosition().equals(battlefield.getCell(1,3)));
    }


    /**
     * Test if athena moves down and the value in the model is correctly changed.
     */
    @Test void performMoveMovingDownTest() {

        battlefield = Utility.setUpForTest1();
        selectedToken.setTokenPosition(battlefield.getCell(2,2));
        targetCell = battlefield.getCell(3,1);
        battlefield.getCell(3,1).setOccupied();

        MoveContext thisMove = new MoveContext(new AthenaMoves());
        thisMove.executeMove(selectedToken, otherToken, enemyTokens, targetCell, enemyGodCards, battlefield);

        Assert.assertFalse(Model.isAthenaMovedUp());
        Assert.assertTrue(battlefield.getCell(3,1).getThereIsPlayer());
        Assert.assertFalse(battlefield.getCell(2,2).getThereIsPlayer());
        Assert.assertTrue(selectedToken.getTokenPosition().equals(battlefield.getCell(3,1)));
    }


    /**
     * Test if athena moves on the same level and the value in the model is correctly changed.
     */
    @Test void performMoveMovingSameHeightTest() {

        battlefield = Utility.setUpForTest1();
        selectedToken.setTokenPosition(battlefield.getCell(2,2));
        targetCell = battlefield.getCell(1,2);
        battlefield.getCell(1,2).setOccupied();

        MoveContext thisMove = new MoveContext(new AthenaMoves());
        thisMove.executeMove(selectedToken, otherToken, enemyTokens, targetCell, enemyGodCards, battlefield);

        Assert.assertFalse(Model.isAthenaMovedUp());
        Assert.assertTrue(battlefield.getCell(1,2).getThereIsPlayer());
        Assert.assertFalse(battlefield.getCell(2,2).getThereIsPlayer());
        Assert.assertTrue(selectedToken.getTokenPosition().equals(battlefield.getCell(1,2)));
    }
}
