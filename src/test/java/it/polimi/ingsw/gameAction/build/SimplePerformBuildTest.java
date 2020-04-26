package it.polimi.ingsw.gameAction.build;

import it.polimi.ingsw.controller.CellHeightException;
import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.controller.ReachHeightLimitException;
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
 * This just receive a Cell and call the incrementHeight method on that cell.
 */
public class SimplePerformBuildTest {

    Battlefield battlefield;
    List<Token> enemyTokens;
    Token selectedToken;


    /**
     * This set up a standard battlefieldTest.
     * It also set up 2 different tokens in 2,2 and 1,1.
     */
    @BeforeEach void setUp() throws CellOutOfBattlefieldException {

        battlefield = Utility.setUpForTest();

        enemyTokens = new ArrayList<>();
        enemyTokens.add(new Token(TokenColor.RED));
        selectedToken = new Token(TokenColor.BLUE);

        selectedToken.setTokenPosition(battlefield.getCell(2,2));
        battlefield.getCell(2,2).setOccupied();
        enemyTokens.get(0).setTokenPosition(battlefield.getCell(1,1));
        battlefield.getCell(1,1).setOccupied();
    }


    /**
     *  Perform a build on a cell height 0.
     */
    @Test void groundBuildTest() throws CellOutOfBattlefieldException, CellHeightException, ReachHeightLimitException {

        Cell targetCell = battlefield.getCell(3,1);

        Assert.assertFalse(battlefield.getCell(targetCell).getIsDome());

        BuildContext thisBuild = new BuildContext(new SimpleBuild());
        thisBuild.executePerformBuild(targetCell, null, battlefield);

        Assert.assertEquals(1, battlefield.getCell(targetCell).getHeight());
        Assert.assertFalse(battlefield.getCell(targetCell).getIsDome());
    }


    /**
     *  Perform a build on a cell height 1.
     */
    @Test void level1BuildTest() throws CellOutOfBattlefieldException, CellHeightException, ReachHeightLimitException {

        Cell targetCell = battlefield.getCell(1,2);

        Assert.assertFalse(battlefield.getCell(targetCell).getIsDome());

        BuildContext thisBuild = new BuildContext(new SimpleBuild());
        thisBuild.executePerformBuild(targetCell, null, battlefield);

        Assert.assertEquals(2, battlefield.getCell(targetCell).getHeight());
        Assert.assertFalse(battlefield.getCell(targetCell).getIsDome());
    }


    /**
     *  Perform a build on a cell height 1.
     */
    @Test void level2BuildTest() throws CellOutOfBattlefieldException, CellHeightException, ReachHeightLimitException {

        Cell targetCell = battlefield.getCell(1,3);

        Assert.assertFalse(battlefield.getCell(targetCell).getIsDome());

        BuildContext thisBuild = new BuildContext(new SimpleBuild());
        thisBuild.executePerformBuild(targetCell, null, battlefield);

        Assert.assertEquals(3, battlefield.getCell(targetCell).getHeight());
        Assert.assertFalse(battlefield.getCell(targetCell).getIsDome());
    }


    /**
     *  Perform a build on a cell height 1.
     */
    @Test void level3BuildTest() throws CellOutOfBattlefieldException, CellHeightException, ReachHeightLimitException {

        Cell targetCell = battlefield.getCell(2,3);

        Assert.assertFalse(battlefield.getCell(targetCell).getIsDome());

        BuildContext thisBuild = new BuildContext(new SimpleBuild());
        thisBuild.executePerformBuild(targetCell, null, battlefield);

        Assert.assertEquals(3, battlefield.getCell(targetCell).getHeight());
        Assert.assertTrue(battlefield.getCell(targetCell).getIsDome());
    }
}
