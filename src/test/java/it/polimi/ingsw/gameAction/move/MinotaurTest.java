package it.polimi.ingsw.gameAction.move;

import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.model.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class MinotaurTest {

    Cell startingCell = null, targetCell = null, otherTokenCell = null;
    Token myToken = null, otherToken = null;
    Battlefield battlefield = null;
    List<Token> enemyTokens = null;
    List<Player> allPlayers = null;


    @BeforeEach
    public void init() throws CellOutOfBattlefieldException {

        battlefield = new Battlefield();

        startingCell = battlefield.getCell(2,2);
        myToken = new Token(TokenColor.BLUE);   //my token
        myToken.setTokenPosition(battlefield.getCell(2,2));
        battlefield.getCell(2,2).setOccupied();

        enemyTokens = new ArrayList<>();
       }



    /**
     * In the valid moves must be present even the opponent's token position,
     * because the cell behind that one is free, in order to host the puhed token.
     */
    @Test
    void minotaurValidMovesBehindCell1() throws CellOutOfBattlefieldException {

        otherToken = new Token(TokenColor.RED);
        otherToken.setTokenPosition(battlefield.getCell(1,2));
        enemyTokens.add(otherToken);
        otherToken.getTokenPosition().setOccupied();

        Cell behind = battlefield.getCell(0,2);
        behind.setFree();

        List<Cell> validMoves = new ArrayList<>();
        MoveContext thisMove = new MoveContext(new MinotaurMoves());
        validMoves = thisMove.executeValidMoves(myToken, null, enemyTokens, GodCard.MINOTAUR, null, battlefield, null);

        Assert.assertTrue(validMoves.contains(battlefield.getCell(1,2)));
        Assert.assertTrue(myToken.getTokenPosition().getThereIsPlayer());
        Assert.assertTrue(enemyTokens.get(0).getTokenPosition().getThereIsPlayer());
        Assert.assertFalse(behind.getThereIsPlayer());
    }


    /**
     * In the valid moves must NOT be present the opponent's token position,
     * because the cell behind that one is occupied.
     */
    @Test
    void minotaurValidMovesBehindCell2() throws CellOutOfBattlefieldException {

        otherToken = new Token(TokenColor.RED);
        otherToken.setTokenPosition(battlefield.getCell(1,2));
        enemyTokens.add(otherToken);
        otherToken.getTokenPosition().setOccupied();

        Cell behind = battlefield.getCell(0,2);
        behind.setOccupied();

        List<Cell> validMoves = new ArrayList<>();
        MoveContext thisMove = new MoveContext(new MinotaurMoves());
        validMoves = thisMove.executeValidMoves(myToken, null, enemyTokens, GodCard.MINOTAUR, null, battlefield, null);

        Assert.assertFalse(validMoves.contains(battlefield.getCell(1,2)));
        Assert.assertTrue(myToken.getTokenPosition().getThereIsPlayer());
        Assert.assertTrue(enemyTokens.get(0).getTokenPosition().getThereIsPlayer());
        Assert.assertTrue(behind.getThereIsPlayer());
    }


    /**
     * Here i test if the minotaur can perform a normal move;
     * in particular a move whose targetCell is a free cell,
     * not occupied by any token.
     */
    @Test
    public void minotaurPerformMoveNormally() throws CellOutOfBattlefieldException {

        targetCell = battlefield.getCell(2,3);
        targetCell.setFree();

        MoveContext thisMove = new MoveContext(new MinotaurMoves());
        thisMove.executeValidMoves(myToken, null, null , GodCard.MINOTAUR, null, battlefield, null);

        Assert.assertFalse(targetCell.getThereIsPlayer());
        Assert.assertEquals(myToken.getTokenPosition(),startingCell);
    }



    // The following tests go throught all the possible action that a minotaur can push //

    /**
     * Here i test an impossible move: pushing my other token.
     * The valid moves in this case shouldn't contain the target cell.
     */
    @Test
    public void minotaurValidMovesNotPushMyOtherToken() throws CellOutOfBattlefieldException {

        Cell freeCell = battlefield.getCell(0,2);
        freeCell.setFree();

        targetCell = battlefield.getCell(1,2);
        targetCell.setOccupied();
        otherToken = new Token(TokenColor.BLUE);
        otherToken.setTokenPosition(targetCell);

        MoveContext thisMove = new MoveContext(new MinotaurMoves());
        List<Cell> validMoves = thisMove.executeValidMoves(myToken, otherToken, null , GodCard.MINOTAUR, null, battlefield, null);

        Assert.assertFalse(validMoves.contains(targetCell));

        assertTrue(targetCell.getThereIsPlayer());   //rimane li
        assertEquals(otherToken.getTokenPosition(),targetCell);
        assertEquals(myToken.getTokenPosition(),startingCell);
        assertFalse(freeCell.getThereIsPlayer());
    }


    /**
     * Here i test an impossible move: pushing a token while the cell behind him is a dome.
     * The valid moves in this case shouldn't contain the target cell.
     */
    @Test
    public void minotaurValidMovesDomeBehind() throws CellOutOfBattlefieldException {

        Cell freeCell = battlefield.getCell(0,2);
        freeCell.setFree();
        freeCell.setIsDome();

        targetCell = battlefield.getCell(1,2);
        targetCell.setOccupied();

        otherToken = new Token(TokenColor.YELLOW);
        otherToken.setTokenPosition(targetCell);
        enemyTokens.add(otherToken);

        MoveContext thisMove = new MoveContext(new MinotaurMoves());
        List<Cell> validMoves = thisMove.executeValidMoves(myToken, null, enemyTokens , GodCard.MINOTAUR, null, battlefield, null);

        Assert.assertFalse(validMoves.contains(targetCell));

        assertTrue(targetCell.getThereIsPlayer());   //rimane li
        assertFalse(freeCell.getThereIsPlayer());    //rimane vuota
        assertTrue(freeCell.getIsDome());            //rimane dome
        assertEquals(otherToken.getTokenPosition().getPosX(),1);
        assertEquals(otherToken.getTokenPosition().getPosY(),2);
        assertEquals(myToken.getTokenPosition().getPosX(),2);
        assertEquals(myToken.getTokenPosition().getPosY(),2);
        assertTrue(startingCell.getThereIsPlayer());
    }


    /**
     * Here i test if the minotaur can perform a move using its special
     * power; in particular a move whose targetCell is an occupied by an
     * enemy token, which i have to push VERTICALLY to the next cell
     */
    @Test
    public void minotaurPerformMovePushingVertically() throws CellOutOfBattlefieldException {

        targetCell = battlefield.getCell(2,1);
        battlefield.getCell(2,1).setOccupied();
        otherToken = new Token(TokenColor.RED);
        otherToken.setTokenPosition(battlefield.getCell(2,1));
        enemyTokens.add(otherToken);

        MoveContext thisMove = new MoveContext(new MinotaurMoves());
        thisMove.executeValidMoves(myToken, null, enemyTokens , GodCard.MINOTAUR, null, battlefield, null);
        thisMove.executeMove(myToken,null,enemyTokens,targetCell,null,battlefield);

        Assert.assertEquals(otherToken.getTokenPosition().getPosX(),2);
        Assert.assertEquals(otherToken.getTokenPosition().getPosY(),0);
        Assert.assertTrue(battlefield.getCell(2,0).getThereIsPlayer());
        Assert.assertEquals(myToken.getTokenPosition().getPosX(),2);
        Assert.assertEquals(myToken.getTokenPosition().getPosY(),1);
        Assert.assertTrue(myToken.getTokenPosition().getThereIsPlayer());
    }


    /**
     * Here i test if the minotaur can perform a move using its special
     * power; in particular a move whose targetCell is an occupied by an
     * enemy token, which i have to push HORIZONTALLY to the next cell
     */
    @Test
    public void minotaurPerformMovePushingHorizontally() throws CellOutOfBattlefieldException {

        targetCell = battlefield.getCell(3,2);
        targetCell.setOccupied();
        otherToken = new Token(TokenColor.RED);
        otherToken.setTokenPosition(targetCell);
        enemyTokens.add(otherToken);

        MoveContext thisMove = new MoveContext(new MinotaurMoves());
        thisMove.executeMove(myToken,null,enemyTokens,targetCell,null,battlefield);

        Assert.assertFalse(battlefield.getCell(2,2).getThereIsPlayer());
        Assert.assertEquals(otherToken.getTokenPosition().getPosX(),4);
        Assert.assertEquals(otherToken.getTokenPosition().getPosY(),2);
        Assert.assertTrue(battlefield.getCell(4,2).getThereIsPlayer());
        Assert.assertEquals(myToken.getTokenPosition().getPosX(),3);
        Assert.assertEquals(myToken.getTokenPosition().getPosY(),2);
        Assert.assertTrue(myToken.getTokenPosition().getThereIsPlayer());
    }


    /**
     * Here i test if the minotaur can perform a move using its special
     * power; in particular a move whose targetCell is an occupied by an
     * enemy token, which i have to push DIAGONALLY to the next cell
     */
    @Test
    public void minotaurPerformMovePushingDiagonally() throws CellOutOfBattlefieldException {

        targetCell = battlefield.getCell(1,1);
        targetCell.setOccupied();
        otherToken = new Token(TokenColor.YELLOW);
        otherToken.setTokenPosition(targetCell);
        enemyTokens.add(otherToken);

        MoveContext thisMove = new MoveContext(new MinotaurMoves());
        thisMove.executeValidMoves(myToken, null, enemyTokens , GodCard.MINOTAUR, null, battlefield, null);
        thisMove.executeMove(myToken,null,enemyTokens,targetCell,null,battlefield);

        Assert.assertTrue(battlefield.getCell(0,0).getThereIsPlayer());
        Assert.assertEquals(otherToken.getTokenPosition().getPosX(),0);
        Assert.assertEquals(otherToken.getTokenPosition().getPosY(),0);
        Assert.assertTrue(battlefield.getCell(1,1).getThereIsPlayer());
        Assert.assertEquals(myToken.getTokenPosition().getPosX(),1);
        Assert.assertEquals(myToken.getTokenPosition().getPosY(),1);
        Assert.assertFalse(battlefield.getCell(2,2).getThereIsPlayer());
    }



}

