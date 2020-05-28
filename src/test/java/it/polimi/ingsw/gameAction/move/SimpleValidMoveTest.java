package it.polimi.ingsw.gameAction.move;

import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.cli.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SimpleValidMoveTest {

    Battlefield battlefield;
    Token selectedToken, otherToken;
    List<Token> enemyTokens;


    /**
     * Set up all the argument needed to call a validMoves function.
     * The tokens are created but their position is not set yet.
     */
    @BeforeEach void setUp() {
        battlefield = new Battlefield();
        selectedToken = new Token(TokenColor.RED);
        otherToken = new Token(TokenColor.RED);
        enemyTokens = new ArrayList<>();
        enemyTokens.add(new Token(TokenColor.BLUE));
        enemyTokens.add(new Token(TokenColor.YELLOW));
    }


    /**
     * This test a token with nobody around him.
     */
    @Test void validMovesOnlyToken() throws CellOutOfBattlefieldException {
        battlefield = new Battlefield();

        selectedToken.setTokenPosition(battlefield.getCell(3,3));
        selectedToken.getTokenPosition().setOccupied();

        List<Cell> validMoves;

        MoveContext thisMove = new MoveContext(new SimpleMoves());
        validMoves = thisMove.executeValidMoves(selectedToken, otherToken, enemyTokens, null, null, battlefield, null);

        Assert.assertEquals(8, validMoves.size());
        Assert.assertTrue(validMoves.contains(battlefield.getCell(2,2)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(2,3)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(2,4)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(3,2)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(3,4)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(4,2)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(4,3)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(4,4)));
        Assert.assertFalse(validMoves.contains(battlefield.getCell(3,3)));
    }





    /**
     * This test if a token is near a perimeter space.
     */
    @Test void validMovesNearPerimeter() throws CellOutOfBattlefieldException {
        battlefield = new Battlefield();

        selectedToken.setTokenPosition(battlefield.getCell(0,0));
        selectedToken.getTokenPosition().setOccupied();

        List<Cell> validMoves;

        MoveContext thisMove = new MoveContext(new SimpleMoves());
        validMoves = thisMove.executeValidMoves(selectedToken, otherToken, enemyTokens, null, null, battlefield, null);

        Assert.assertEquals(3, validMoves.size());
        Assert.assertTrue(validMoves.contains(battlefield.getCell(0,1)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(1,0)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(1,1)));
    }


    /**
     * This test a token with some domes around him.
     */
    @Test void validMovesWithDome() throws CellOutOfBattlefieldException {
        battlefield = new Battlefield();

        selectedToken.setTokenPosition(battlefield.getCell(2,2));
        selectedToken.getTokenPosition().setOccupied();
        battlefield.getCell(2,1).setIsDome();
        battlefield.getCell(3,2).setIsDome();
        battlefield.getCell(1,3).setIsDome();

        List<Cell> validMoves;

        MoveContext thisMove = new MoveContext(new SimpleMoves());
        validMoves = thisMove.executeValidMoves(selectedToken, otherToken, enemyTokens, null, null, battlefield, null);

        Assert.assertEquals(5, validMoves.size());
        Assert.assertTrue(validMoves.contains(battlefield.getCell(1,1)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(3,1)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(1,2)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(2,3)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(3,3)));
    }


    /**
     * Test for my token in a centered position near one other mine token
     * and 2 different enemies token.
     */
    @Test void validMoves2enemy2mine() throws CellOutOfBattlefieldException {

        selectedToken.setTokenPosition(battlefield.getCell(1,1));
        selectedToken.getTokenPosition().setOccupied();
        otherToken.setTokenPosition(battlefield.getCell(2,2));
        otherToken.getTokenPosition().setOccupied();
        enemyTokens.get(0).setTokenPosition(battlefield.getCell(0,0));
        enemyTokens.get(0).getTokenPosition().setOccupied();
        enemyTokens.get(1).setTokenPosition(battlefield.getCell(1,2));
        enemyTokens.get(1).getTokenPosition().setOccupied();

        List<Cell> validMoves;

        MoveContext thisMove = new MoveContext(new SimpleMoves());
        validMoves = thisMove.executeValidMoves(selectedToken, otherToken, enemyTokens, null, null, battlefield, null);

        Assert.assertEquals(5, validMoves.size());
        Assert.assertTrue(validMoves.contains(battlefield.getCell(1,0)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(2,0)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(2,1)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(0,1)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(0,2)));
        Assert.assertFalse(validMoves.contains(battlefield.getCell(0,0)));
        Assert.assertFalse(validMoves.contains(battlefield.getCell(1,1)));
        Assert.assertFalse(validMoves.contains(battlefield.getCell(2,2)));
        Assert.assertFalse(validMoves.contains(battlefield.getCell(1,2)));
    }


    /**
     * Test for cell too height and dome.
     */
    @Test void validMovesHeight() throws CellOutOfBattlefieldException {

        battlefield.getCell(1,1).setHeight(1);
        battlefield.getCell(1,2).setHeight(2);
        battlefield.getCell(2,2).setHeight(3);
        battlefield.getCell(2,1).setHeight(1);
        battlefield.getCell(3,2).setHeight(3);
        battlefield.getCell(3,2).setIsDome();
        battlefield.getCell(3,1).setIsDome();

        selectedToken.setTokenPosition(battlefield.getCell(2,1));

        List<Cell> validMoves;

        MoveContext thisMove = new MoveContext(new SimpleMoves());
        validMoves = thisMove.executeValidMoves(selectedToken, otherToken, enemyTokens, null, null, battlefield, null);

        Assert.assertEquals(5, validMoves.size());
        Assert.assertTrue(validMoves.contains(battlefield.getCell(1,2)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(1,1)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(1,0)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(2,0)));
        Assert.assertTrue(validMoves.contains(battlefield.getCell(3,0)));
    }
}
