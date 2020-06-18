package it.polimi.ingsw.gameAction.build;

import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
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
 * A token can build in all 8 Cells around himself except
 * the cell's that are a dome,
 * the cell's where there is a player.
 */
public class SimpleValidBuildTest {

    Battlefield battlefield;
    List<Token> enemyTokens;
    Token selectedToken;


    /**
     * This set up a standard battlefieldTest
     */
    @BeforeEach void setUp() throws CellOutOfBattlefieldException {

        battlefield = Utility.setUpForTest1();

        enemyTokens = new ArrayList<>();
        enemyTokens.add(new Token(TokenColor.RED));
        selectedToken = new Token(TokenColor.BLUE);
    }


    /**
     * This test the valid builds with almost each kind of limitation.
     */
    @Test void validBuildsTest() throws CellOutOfBattlefieldException {

        selectedToken.setTokenPosition(battlefield.getCell(2,2));
        battlefield.getCell(2,2).setOccupied();
        enemyTokens.get(0).setTokenPosition(battlefield.getCell(1,1));
        battlefield.getCell(1,1).setOccupied();

        List<Cell> validBuilds;

        BuildContext thisBuild = new BuildContext(new SimpleBuild());
        validBuilds = thisBuild.executeValidBuilds(selectedToken, null, enemyTokens, null, battlefield, null);

        Assert.assertEquals(5,validBuilds.size());
        Assert.assertTrue(validBuilds.contains(battlefield.getCell(3,1)));
        Assert.assertTrue(validBuilds.contains(battlefield.getCell(1,2)));
        Assert.assertTrue(validBuilds.contains(battlefield.getCell(1,3)));
        Assert.assertTrue(validBuilds.contains(battlefield.getCell(2,3)));
        Assert.assertTrue(validBuilds.contains(battlefield.getCell(3,3)));
    }
}
