package it.polimi.ingsw.gameAction.build;

import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Token;
import it.polimi.ingsw.model.TokenColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;



public class ZeusBuildTest {

    Battlefield battlefield = null;
    Cell targetCell = null;
    int targetCellOldHeight;
    Token token = null;


    /**
     * Create a new battlefield, get a target cell to test.
     */
    @BeforeEach
    public void init() {
        battlefield = new Battlefield();
        targetCell = battlefield.getCell(2,3);
        targetCell.setFree();
    }


    /**
     * This valid builds should even contain the cell under my token,
     * because zeus can build under himself
     */
    @Test
    public void ZeusValidMoves() {

        token = new Token(TokenColor.YELLOW);
        token.setTokenPosition(targetCell);
        targetCell.setOccupied();

        BuildContext thisBuild = new BuildContext(new ZeusBuild());
        List<Cell> validBuilds = thisBuild.executeValidBuilds(token, null, null, null, battlefield, null);

        assertTrue(validBuilds.contains(targetCell));
    }


    /**
     * The height of the target cell has to be the old height+1
     * and the target cell can be equal to the token position
     */
    @Test
    public void ZeusPerformBuild() {

        targetCell.setHeight(1);
        targetCellOldHeight = targetCell.getHeight();

        assertEquals(targetCellOldHeight,1);

        BuildContext thisBuild = new BuildContext(new ZeusBuild());
        thisBuild.executePerformBuild(targetCell,null,battlefield);

        assertEquals(targetCell.getHeight(), targetCellOldHeight + 1);
    }


    /**
     * Here we test that Zeus can build even under itself, so we instanciate a token;
     */
    @Test
    public void ZeusPerformBuildSamePosition() {

        token = new Token(TokenColor.BLUE);
        token.setTokenPosition(targetCell);
        targetCell.setOccupied();

        targetCell.setHeight(1);
        targetCellOldHeight = targetCell.getHeight();

        assertEquals(targetCellOldHeight,1);

        BuildContext thisBuild = new BuildContext(new ZeusBuild());
        thisBuild.executePerformBuild(targetCell,null,battlefield);

        assertEquals(2, targetCell.getHeight());

        assertEquals(targetCell,token.getTokenPosition());
    }
}


