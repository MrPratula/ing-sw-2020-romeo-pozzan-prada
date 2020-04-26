package it.polimi.ingsw.gameAction.move;

import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
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
    @Test void validMovesEmptyTest() throws CellOutOfBattlefieldException {

        battlefield = new Battlefield();

        selectedToken.setTokenPosition(battlefield.getCell(2,2));
        battlefield.getCell(2,2).setOccupied();

        enemyGodCards.add(GodCard.ATHENA);
        Model.athenaMovedUp(true);

        MoveContext thisMove = new MoveContext(new SimpleMoves());
        validMoves = thisMove.executeValidMoves(selectedToken, otherToken, enemyTokens, myGodCard, enemyGodCards, battlefield, null);

        if (enemyGodCards.contains(GodCard.ATHENA) && Model.isDidAthenaMovedUp()) {
            MoveContext thisMove2 = new MoveContext(new AthenaMoves());
            validMoves = thisMove2.executeValidMoves(selectedToken, null, null, null, null, null, validMoves);
        }
        Assert.assertEquals(8, validMoves.size());
    }


    /**
     * Test the TestBattlefield with some tokens on it.
     * It is added a build on 3,1 to test the move on the same height too.
     */
    @Test void validMovesReductionTest() throws CellOutOfBattlefieldException {

        battlefield = Utility.setUpForTest();

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

        if (enemyGodCards.contains(GodCard.ATHENA) && Model.isDidAthenaMovedUp()) {
            MoveContext thisMove2 = new MoveContext(new AthenaMoves());
            validMoves = thisMove2.executeValidMoves(selectedToken, null, null, null, null, null, validMoves);
        }
        Assert.assertEquals(2, validMoves.size());
        Assert.assertTrue(validMoves.contains(battlefield.getCell(3,1)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(3,3)));
    }
}
