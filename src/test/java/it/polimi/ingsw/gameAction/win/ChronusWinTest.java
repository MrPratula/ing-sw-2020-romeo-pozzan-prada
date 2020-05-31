package it.polimi.ingsw.gameAction.win;

import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.gameAction.Utility;
import it.polimi.ingsw.cli.Battlefield;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * CRONUS
 *
 * You also win when there are at least five Complete Towers on the board.
 * Note that if ATLAS build a dome on a level that is not 3, that tower is not a Complete Tower.
 */
public class ChronusWinTest {

    Battlefield battlefield;
    boolean didIWin;


    /**
     * Set up a starter Battlefield.
     */
    @BeforeEach void setUp() {
        battlefield = new Battlefield();
    }


    /**
     * Test with exactly 5 full towers.
     */
    @Test void fiveFullTowerTest() throws CellOutOfBattlefieldException {

        battlefield.getCell(4,0).setHeight(3);
        battlefield.getCell(4,0).setIsDome();
        battlefield.getCell(4,1).setHeight(3);
        battlefield.getCell(4,1).setIsDome();
        battlefield.getCell(4,2).setHeight(3);
        battlefield.getCell(4,2).setIsDome();
        battlefield.getCell(4,3).setHeight(3);
        battlefield.getCell(4,3).setIsDome();
        battlefield.getCell(4,4).setHeight(3);
        battlefield.getCell(4,4).setIsDome();

        WinContext thisWin = new WinContext(new ChronusWin());
        didIWin = thisWin.executeCheckWin(null, battlefield);

        Assert.assertTrue(didIWin);
    }


    /**
     * Test with more than 5 full towers.
     * Don't even know if this can happen (maybe not).
     */
    @Test void allFullTowerTest() throws CellOutOfBattlefieldException {

        for(int x=0; x<5; x++) {
            for(int y=0; y<5; y++) {
                battlefield.getCell(x,y).setHeight(3);
                battlefield.getCell(x,y).setIsDome();
            }
        }

        WinContext thisWin = new WinContext(new ChronusWin());
        didIWin = thisWin.executeCheckWin(null, battlefield);

        Assert.assertTrue(didIWin);
    }


    /**
     * Test with less than 5 full builds, but with some Atlas dome.
     * They are more than 5 and full builds less than 5.
     */
    @Test void AtlasDomeFullTowerFailTest() throws CellOutOfBattlefieldException {

        battlefield = Utility.setUpForTest1();

        battlefield.getCell(4,0).setIsDome();
        battlefield.getCell(4,1).setIsDome();
        battlefield.getCell(4,2).setIsDome();
        battlefield.getCell(4,3).setIsDome();
        battlefield.getCell(4,4).setIsDome();

        WinContext thisWin = new WinContext(new ChronusWin());
        didIWin = thisWin.executeCheckWin(null, battlefield);

        Assert.assertFalse(didIWin);
    }


    /**
     * Test with more than 5 full builds, but with some Atlas dome.
     * They are more than 5 and full builds more than 5.
     */
    @Test void AtlasDomeFullTowerSuccessTest() throws CellOutOfBattlefieldException {

        battlefield = Utility.setUpForTest1();

        battlefield.getCell(4,0).setHeight(3);
        battlefield.getCell(4,0).setIsDome();
        battlefield.getCell(4,1).setHeight(3);
        battlefield.getCell(4,1).setIsDome();
        battlefield.getCell(4,2).setHeight(3);
        battlefield.getCell(4,2).setIsDome();
        battlefield.getCell(4,3).setHeight(3);
        battlefield.getCell(4,3).setIsDome();

        battlefield.getCell(0,0).setIsDome();
        battlefield.getCell(0,1).setIsDome();
        battlefield.getCell(0,2).setIsDome();


        WinContext thisWin = new WinContext(new ChronusWin());
        didIWin = thisWin.executeCheckWin(null, battlefield);

        Assert.assertTrue(didIWin);
    }


    /**
     * Test with less than 5 full builds.
     * It is used the TestBattlefield.
     */
    @Test void lessThanFiveFullTowerTest() throws CellOutOfBattlefieldException {

        battlefield = Utility.setUpForTest1();

        WinContext thisWin = new WinContext(new ChronusWin());
        didIWin = thisWin.executeCheckWin(null, battlefield);

        Assert.assertFalse(didIWin);
    }
}
