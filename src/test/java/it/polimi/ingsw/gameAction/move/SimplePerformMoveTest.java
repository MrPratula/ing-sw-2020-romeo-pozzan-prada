package it.polimi.ingsw.gameAction.move;


import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Token;
import it.polimi.ingsw.model.TokenColor;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * A token can move in all 8 Cells around himself with the following exceptions:
 * he can not occupy a cell where is another token (regardless if it's his own or not);
 * he can not move up on a height >1 of it's own;
 * he can not move on a dome;
 * he can not move out the battlefield.
 *
 */
public class SimplePerformMoveTest {

    Battlefield battlefield;
    Token selectedToken;
    Cell targetCell;


    /**
     * Create a battlefield and a token to test.
     * Set up some cell to test:
     * 1,1 height 1,
     * 1,2 height 2,
     * 2,2 height 3,
     * 3,2 height 3 that is a dome,
     * 3,1 height 0 that is a dome.
     * all other cell have height = 0.
     *
     * Test like:
     * from 2,1 to 3,1;
     * from 2,2 to 3,2;
     * from 2,1 to 1,2
     * are no tested because the controller make those kind of moves illegal.
     */
    @BeforeEach void setUp() {

        battlefield = new Battlefield();
        selectedToken = new Token(TokenColor.RED);

        battlefield.getCell(1,1).setHeight(1);
        battlefield.getCell(1,2).setHeight(2);
        battlefield.getCell(2,2).setHeight(3);
        battlefield.getCell(3,2).setHeight(3);
        battlefield.getCell(3,2).setIsDome();
        battlefield.getCell(3,1).setIsDome();
    }


    /**
     * Test a move from 0 to 0 height.
     */
    @Test void from0to0(){

        selectedToken.setTokenPosition(battlefield.getCell(2,1));
        battlefield.getCell(2,1).setOccupied();
        selectedToken.setOldHeight(0);

        targetCell=battlefield.getCell(2,0);

        MoveContext thisMove = new MoveContext(new SimpleMoves());
        thisMove.executeMove(selectedToken, null, null, targetCell, null, battlefield);

        Assert.assertFalse(battlefield.getCell(2,1).getThereIsPlayer());
        Assert.assertTrue(battlefield.getCell(2,0).getThereIsPlayer());
        Assert.assertEquals(0, selectedToken.getOldHeight());
        Assert.assertEquals(0, selectedToken.getTokenPosition().getHeight());
    }


    /**
     * Test a move from 0 to 1 height.
     */
    @Test void from0to1() {

        selectedToken.setTokenPosition(battlefield.getCell(2,1));
        battlefield.getCell(2,1).setOccupied();
        selectedToken.setOldHeight(0);

        targetCell=battlefield.getCell(1,1);

        MoveContext thisMove = new MoveContext(new SimpleMoves());
        thisMove.executeMove(selectedToken, null, null, targetCell, null, battlefield);

        Assert.assertFalse(battlefield.getCell(2,1).getThereIsPlayer());
        Assert.assertTrue(battlefield.getCell(1,1).getThereIsPlayer());
        Assert.assertEquals(0, selectedToken.getOldHeight());
        Assert.assertEquals(1, selectedToken.getTokenPosition().getHeight());
    }


    /**
     * Test a move from 3 to 0 height.
     */
    @Test void from3to0() {

        selectedToken.setTokenPosition(battlefield.getCell(2,2));
        battlefield.getCell(2,2).setOccupied();
        selectedToken.setOldHeight(2);

        targetCell=battlefield.getCell(2,1);

        MoveContext thisMove = new MoveContext(new SimpleMoves());
        thisMove.executeMove(selectedToken, null, null, targetCell, null, battlefield);

        Assert.assertFalse(battlefield.getCell(2,2).getThereIsPlayer());
        Assert.assertTrue(battlefield.getCell(2,1).getThereIsPlayer());
        Assert.assertEquals(3, selectedToken.getOldHeight());
        Assert.assertEquals(0, selectedToken.getTokenPosition().getHeight());
    }
}
