package it.polimi.ingsw.gameAction.win;

import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.gameAction.Utility;
import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Token;
import it.polimi.ingsw.model.TokenColor;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * PAN
 *
 * You also win if your Worker moves down two or more levels.
 */
public class PanWinTest {

    Battlefield battlefield;
    Token movedToken;
    boolean didIWin;


    /**
     * Set up the default test battlefield for make some tests.
     */
    @BeforeEach void setUp() throws CellOutOfBattlefieldException {

        battlefield = Utility.setUpForTest1();
        movedToken = new Token(TokenColor.RED);
    }


    /**
     * Test if i go up from 2 to 3 as usual.
     */
    @Test void moveUpWinTest() throws CellOutOfBattlefieldException {

        movedToken.setTokenPosition(battlefield.getCell(2,3));
        battlefield.getCell(1,3).setOccupied();
        movedToken.setOldHeight(2);

        WinContext thisWin = new WinContext(new PanWin());
        didIWin = thisWin.executeCheckWin(movedToken, null);

        Assert.assertTrue(didIWin);
    }


    /**
     * Test if i go down from 3 to 0.
     */
    @Test void from3to0Test() throws CellOutOfBattlefieldException {

        movedToken.setTokenPosition(battlefield.getCell(3,3));
        battlefield.getCell(3,3).setOccupied();
        movedToken.setOldHeight(3);

        WinContext thisWin = new WinContext(new PanWin());
        didIWin = thisWin.executeCheckWin(movedToken, null);

        Assert.assertTrue(didIWin);
    }


    /**
     * Test if i go down from 2 to 0.
     */
    @Test void from2to0Test() throws CellOutOfBattlefieldException {

        movedToken.setTokenPosition(battlefield.getCell(0,3));
        battlefield.getCell(0,3).setOccupied();
        movedToken.setOldHeight(2);

        WinContext thisWin = new WinContext(new PanWin());
        didIWin = thisWin.executeCheckWin(movedToken, null);

        Assert.assertTrue(didIWin);
    }


    /**
     * Test if i go down from 1 to 0.
     */
    @Test void from1to0Test() throws CellOutOfBattlefieldException {

        movedToken.setTokenPosition(battlefield.getCell(0,2));
        battlefield.getCell(0,2).setOccupied();
        movedToken.setOldHeight(1);

        WinContext thisWin = new WinContext(new PanWin());
        didIWin = thisWin.executeCheckWin(movedToken, null);

        Assert.assertFalse(didIWin);
    }


    /**
     * Test if i move from 0 to 0.
     */
    @Test void from0to0Test() throws CellOutOfBattlefieldException {

        movedToken.setTokenPosition(battlefield.getCell(1,1));
        battlefield.getCell(1,1).setOccupied();
        movedToken.setOldHeight(0);

        WinContext thisWin = new WinContext(new PanWin());
        didIWin = thisWin.executeCheckWin(movedToken, null);

        Assert.assertFalse(didIWin);
    }
}
