package it.polimi.ingsw.gameAction.win;


import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.gameAction.Utility;
import it.polimi.ingsw.cli.Battlefield;
import it.polimi.ingsw.cli.GodCard;
import it.polimi.ingsw.cli.Token;
import it.polimi.ingsw.cli.TokenColor;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * HERA
 *
 * An opponent can not win by moving on to a perimeter space.
 */
public class HeraWinTest {

    boolean didIWin;
    Token movedToken;
    List<GodCard> enemyGodCards;
    Battlefield battlefield;


    /**
     * Set up the default TestBattlefield and add a build height 3 to the perimeter.
     * Hera doesn't need this but normal win does.
     */
    @BeforeEach void setUp() throws CellOutOfBattlefieldException {

        battlefield = Utility.setUpForTest1();
        battlefield.getCell(1,4).setHeight(3);
        enemyGodCards = new ArrayList<>();
        enemyGodCards.add(GodCard.HERA);
        movedToken = new Token(TokenColor.RED);
    }


    /**
     * Test if a player would win normally,
     * but with hera in play he doesn't.
     */
    @Test void goingUpToPerimeterTest() throws CellOutOfBattlefieldException {

        movedToken.setOldHeight(2);
        movedToken.setTokenPosition(battlefield.getCell(1,4));

        WinContext thisWin = new WinContext(new SimpleWin());
        didIWin = thisWin.executeCheckWin(movedToken, null);

        // Here a player should win
        Assert.assertTrue(didIWin);

        if (enemyGodCards.contains(GodCard.HERA)){
            if (didIWin){
                WinContext thisWin2 = new WinContext(new HeraWin());
                didIWin = thisWin2.executeCheckWin(movedToken, null);
            }
        }
        Assert.assertFalse(didIWin);
    }


    /**
     * Test if a player win going up not to the perimeter.
     */
    @Test void goingUpNotToPerimeterTest() throws CellOutOfBattlefieldException {

        movedToken.setOldHeight(2);
        movedToken.setTokenPosition(battlefield.getCell(2,3));

        WinContext thisWin = new WinContext(new SimpleWin());
        didIWin = thisWin.executeCheckWin(movedToken, null);

        // Here a player should win
        Assert.assertTrue(didIWin);

        if (enemyGodCards.contains(GodCard.HERA)){
            if (didIWin){
                WinContext thisWin2 = new WinContext(new HeraWin());
                didIWin = thisWin2.executeCheckWin(movedToken, null);
            }
        }
        Assert.assertTrue(didIWin);
    }


    /**
     * Test if Pan would win normally,
     * but with hera in play he doesn't.
     */
    @Test void goingDownToPerimeterTest() throws CellOutOfBattlefieldException {

        movedToken.setOldHeight(2);
        movedToken.setTokenPosition(battlefield.getCell(0,3));

        WinContext thisWin = new WinContext(new PanWin());
        didIWin = thisWin.executeCheckWin(movedToken, null);

        // Here Apollo should win
        Assert.assertTrue(didIWin);

        if (enemyGodCards.contains(GodCard.HERA)){
            if (didIWin){
                WinContext thisWin2 = new WinContext(new HeraWin());
                didIWin = thisWin2.executeCheckWin(movedToken, null);
            }
        }
        Assert.assertFalse(didIWin);
    }
}
