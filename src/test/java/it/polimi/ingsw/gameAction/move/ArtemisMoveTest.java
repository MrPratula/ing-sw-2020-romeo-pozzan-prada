package it.polimi.ingsw.gameAction.move;


import it.polimi.ingsw.gameAction.Utility;
import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Token;
import it.polimi.ingsw.model.TokenColor;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * ARTEMIS
 *
 * Your Worker may move one additional time, but not back to its initial space
 */
public class ArtemisMoveTest {

    Battlefield battlefield;
    List<Cell> validMoves;
    Token selectedToken;
    Token otherToken;
    List<Token> enemyTokens;


    /**
     * Set up an empty battlefield and create some tokens.
     */
    @BeforeEach void setUp() {

    selectedToken = new Token(TokenColor.RED);
    otherToken = new Token(TokenColor.RED);
    enemyTokens = new ArrayList<>();
    enemyTokens.add(new Token(TokenColor.BLUE));
    enemyTokens.add(new Token(TokenColor.YELLOW));
    }


    /**
     * Test with an empty battlefield if all the cell except the initial one are legal moves.
     * The token is in the center of the battlefield.
     */
    @Test void twentyFiveValidMoves() {

        battlefield = new Battlefield();

        selectedToken.setTokenPosition(battlefield.getCell(2,2));
        battlefield.getCell(2,2).setOccupied();

        MoveContext thisMove = new MoveContext(( new ArtemisMoves()));
        validMoves = thisMove.executeValidMoves(selectedToken, otherToken, enemyTokens, null, null, battlefield, null);

        Assert.assertEquals(24, validMoves.size());

        // ValidMoves contains all the battlefield cells, except the initial position (2,2).
        for(int x=0; x<5; x++) {
            for(int y=0; y<5; y++) {
                if(x!=2 && y!=2) {
                    Assert.assertTrue(validMoves.contains(battlefield.getCell(x,y)));
                }
            }
        }
    }


    /**
     * Test with some tokens and builds around.
     * The battlefield is the BattlefieldTest.
     */
    @Test void validMovesTest() {

        battlefield = Utility.setUpForTest1();

        selectedToken.setTokenPosition(battlefield.getCell(2,2));
        battlefield.getCell(2,2).setOccupied();

        otherToken.setTokenPosition(battlefield.getCell(1,2));
        battlefield.getCell(1,2).setOccupied();

        enemyTokens.get(0).setTokenPosition(battlefield.getCell(1,1));
        battlefield.getCell(1,1).setOccupied();

        MoveContext thisMove = new MoveContext(( new ArtemisMoves()));
        validMoves = thisMove.executeValidMoves(selectedToken, otherToken, enemyTokens, null, null, battlefield, null);

        Assert.assertEquals(17, validMoves.size());
        Assert.assertTrue(validMoves.contains(battlefield.getCell(0,2)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(0,3)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(0,4)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(1,3)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(1,4)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(2,0)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(2,3)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(2,4)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(3,0)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(3,1)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(3,3)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(3,4)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(4,0)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(4,1)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(4,2)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(4,3)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(4,4)));
    }
}
