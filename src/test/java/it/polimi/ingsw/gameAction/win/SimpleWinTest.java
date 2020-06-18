package it.polimi.ingsw.gameAction.win;

import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Token;
import it.polimi.ingsw.model.TokenColor;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * A player win the game if he go from a level 2 build to a level 3 build.
 */
public class SimpleWinTest {

    Battlefield battlefield;
    Token movedToken;


    /**
     * Set up a battlefield and a token.
     */
    @BeforeEach void setUp() {
        battlefield = new Battlefield();
        movedToken = new Token(TokenColor.RED);
    }


    /**
     * Test a token up from 2 to 3.
     */
    @Test void from2to3() throws CellOutOfBattlefieldException {

        movedToken.setOldHeight(2);
        movedToken.setTokenPosition(battlefield.getCell(2,3));
        battlefield.getCell(2,3).setHeight(3);

        WinContext thisWin = new WinContext(new SimpleWin());
        boolean didIWin = thisWin.executeCheckWin(movedToken, null);

        Assert.assertTrue(didIWin);
    }


    /**
     * Test a token up from 1 to 2.
     */
    @Test void from1to2() throws CellOutOfBattlefieldException {

        movedToken.setOldHeight(1);
        movedToken.setTokenPosition(battlefield.getCell(2,3));
        battlefield.getCell(2,3).setHeight(2);

        WinContext thisWin = new WinContext(new SimpleWin());
        boolean didIWin = thisWin.executeCheckWin(movedToken, null);

        Assert.assertFalse(didIWin);
    }


    /**
     * Test a token up from 3 to 3.
     */
    @Test void from3to3() throws CellOutOfBattlefieldException {

        movedToken.setOldHeight(3);
        movedToken.setTokenPosition(battlefield.getCell(2,3));
        battlefield.getCell(2,3).setHeight(3);

        WinContext thisWin = new WinContext(new SimpleWin());
        boolean didIWin = thisWin.executeCheckWin(movedToken, null);

        Assert.assertFalse(didIWin);
    }


    /**
     * Test a token up from 3 to 3.
     */
    @Test void from3to0() throws CellOutOfBattlefieldException {

        movedToken.setOldHeight(3);
        movedToken.setTokenPosition(battlefield.getCell(2,3));
        battlefield.getCell(2,3).setHeight(0);

        WinContext thisWin = new WinContext(new SimpleWin());
        boolean didIWin = thisWin.executeCheckWin(movedToken, null);

        Assert.assertFalse(didIWin);
    }
}
