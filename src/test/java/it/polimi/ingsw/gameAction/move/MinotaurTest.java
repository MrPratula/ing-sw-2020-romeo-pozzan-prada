package it.polimi.ingsw.gameAction.move;

import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.model.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


public class MinotaurTest {

    Cell startingCell = null, targetCell = null, otherTokenCell = null;
    Token myToken = null, otherToken = null;
    Battlefield battlefield = null;
    List<Token> enemyTokens;


    @BeforeEach
    public void init() throws CellOutOfBattlefieldException {

        battlefield = new Battlefield();

        startingCell = battlefield.getCell(2,2);
        startingCell.setOccupied();
        myToken = new Token(TokenColor.BLUE);   //my token
        myToken.setTokenPosition(startingCell);

        enemyTokens = new ArrayList<>();
       }



    /**
     * In the valid moves must be present even the opponent's token position.
     */
   /* @Test
    void minotaurValidMoves() throws CellOutOfBattlefieldException {

        //otherToken = new Token(TokenColor.RED);
        enemyTokens.add(new Token(TokenColor.RED));
        enemyTokens.get(0).setTokenPosition(battlefield.getCell(2,3));
        battlefield.getCell(2,3).setOccupied();
        //otherToken.setTokenPosition(battlefield.getCell(2,3));

        Cell behind = battlefield.getCell(4,2);
        behind.setFree();

        List<Cell> validMoves = new ArrayList<>();
        MoveContext thisMove = new MoveContext(new MinotaurMoves());
        validMoves = thisMove.executeValidMoves(myToken, null, enemyTokens, null, null, battlefield, null);

        Assert.assertTrue(validMoves.contains(battlefield.getCell(2,3)));
        //Assert.assertTrue(myToken.getTokenPosition().getThereIsPlayer());
        Assert.assertTrue(enemyTokens.get(0).getTokenPosition().getThereIsPlayer());
    }
*/

    /**
     * Here i test if the minotaur can perform a normal move;
     * in particular a move whose targetCell is a free cell,
     * not occupied by any token.
     */
    @Test
    public void minotaurPerformMoveNormally() throws CellOutOfBattlefieldException {

        targetCell = battlefield.getCell(2,3);

        MoveContext thisMove = new MoveContext(new ApolloMoves());
        thisMove.executeValidMoves(myToken, null, null , GodCard.MINOTAUR, null, battlefield, null);
    }

    /**
     * Here i test an impossible move: cell out of the battlefield
     */
  /*  @Test
    public void minotaurPerformImpossibleMove() throws CellOutOfBattlefieldException {

        targetCell = battlefield.getCell(3,6);

        MoveContext thisMove = new MoveContext(new ApolloMoves());
        thisMove.executeValidMoves(myToken, null, null , GodCard.MINOTAUR, null, battlefield, null);

        //assertThrow(CellOutOfBattlefieldException);
    }
*/


    // The following tests go throught all the possible action that a minotaur can push

    /**
     * Here i test an impossible move: pushing my other token;
     */
    @Test
    public void minotaurPerformImpossiblePush() throws CellOutOfBattlefieldException {

        Cell freeCell = battlefield.getCell(0,2);
        freeCell.setFree();

        targetCell = battlefield.getCell(1,2);
        targetCell.setOccupied();
        otherToken = new Token(TokenColor.BLUE);
        otherToken.setTokenPosition(targetCell);

        MoveContext thisMove = new MoveContext(new ApolloMoves());
        thisMove.executeValidMoves(myToken, otherToken, null , GodCard.MINOTAUR, null, battlefield, null);

        assertTrue(targetCell.getThereIsPlayer());   //rimane li
        assertEquals(otherToken.getTokenPosition(),targetCell);
        assertEquals(myToken.getTokenPosition(),startingCell);
        assertTrue(!freeCell.getThereIsPlayer());
    }


    /**
     * Here i test if the minotaur can perform a move using its special
     * power; in particular a move whose targetCell is an occupied by an
     * enemy token, which i have to push VERTICALLY to the next cell
     */
    @Test
    public void minotaurPerformMovePushingVertically() throws CellOutOfBattlefieldException {

        targetCell = battlefield.getCell(2,1);
        battlefield.getCell(2,2).setOccupied();
        otherToken = new Token(TokenColor.RED);
        otherToken.setTokenPosition(targetCell);
        enemyTokens.add(otherToken);

        MoveContext thisMove = new MoveContext(new ApolloMoves());
        thisMove.executeValidMoves(myToken, null, enemyTokens , GodCard.MINOTAUR, null, battlefield, null);

    }


    /**
     * Here i test if the minotaur can perform a move using its special
     * power; in particular a move whose targetCell is an occupied by an
     * enemy token, which i have to push HORIZONTALLY to the next cell
     */
    @Test
    public void minotaurPerformMovePushingHorizontally() throws CellOutOfBattlefieldException {

        targetCell = battlefield.getCell(3,2);
        battlefield.getCell(2,2).setOccupied();
        otherToken = new Token(TokenColor.RED);
        otherToken.setTokenPosition(targetCell);
        enemyTokens.add(otherToken);

        MoveContext thisMove = new MoveContext(new ApolloMoves());
        thisMove.executeValidMoves(myToken, null, enemyTokens , GodCard.MINOTAUR, null, battlefield, null);

    }


    /**
     * Here i test if the minotaur can perform a move using its special
     * power; in particular a move whose targetCell is an occupied by an
     * enemy token, which i have to push DIAGONALLY to the next cell
     */
    @Test
    public void minotaurPerformMovePushingDiagonally() throws CellOutOfBattlefieldException {

        targetCell = battlefield.getCell(1,1);
        battlefield.getCell(2,2).setOccupied();
        otherToken = new Token(TokenColor.YELLOW);
        otherToken.setTokenPosition(targetCell);
        enemyTokens.add(otherToken);

        MoveContext thisMove = new MoveContext(new ApolloMoves());
        thisMove.executeValidMoves(myToken, null, enemyTokens , GodCard.MINOTAUR, null, battlefield, null);

    }



}

