package it.polimi.ingsw.gameAction.build;

import it.polimi.ingsw.controller.CellHeightException;
import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.controller.ReachHeightLimitException;
import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Token;
import it.polimi.ingsw.model.TokenColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;



public class ZeusTest {

    Battlefield battlefield = null;
    Cell targetCell = null;
    int targetCellOldHeight;
    Token token = null;


    /**
     * Create a new battlefield, get a target cell to test.
     */
    @BeforeEach
    public void init() throws CellOutOfBattlefieldException {
        battlefield = new Battlefield();
        targetCell = battlefield.getCell(2,3);
        targetCell.setFree();
    }


    /**
     * The height of the target cell has to be the old height+1
     * and the target cell can be equal to the token position
     * @throws CellHeightException
     * @throws ReachHeightLimitException
     * @throws CellOutOfBattlefieldException
     */
    @Test
    public void ZeusPerformBuild() throws CellHeightException, ReachHeightLimitException, CellOutOfBattlefieldException {

        targetCell.setHeight(1);
        targetCellOldHeight = targetCell.getHeight();

        assertEquals(targetCellOldHeight,1);

        BuildContext thisBuild = new BuildContext(new ZeusBuild());
        thisBuild.executePerformBuild(targetCell,null,battlefield);

        assertTrue(targetCell.getHeight()==targetCellOldHeight+1);
    }


    /**
     * Here we test that Zeus can build even under itself, so we instanciate a token;
     * @throws CellHeightException
     * @throws ReachHeightLimitException
     * @throws CellOutOfBattlefieldException
     */
    @Test
    public void ZeusPerformBuildSamePosition() throws CellHeightException, ReachHeightLimitException, CellOutOfBattlefieldException {

        token = new Token(TokenColor.BLUE);
        token.setTokenPosition(targetCell);
        targetCell.setThereIsPlayer();

        targetCell.setHeight(1);
        targetCellOldHeight = targetCell.getHeight();

        assertEquals(targetCellOldHeight,1);

        BuildContext thisBuild = new BuildContext(new ZeusBuild());
        thisBuild.executePerformBuild(targetCell,null,battlefield);

        assertTrue(targetCell.getHeight()==2);

        assertEquals(targetCell,token.getTokenPosition());
    }


}


