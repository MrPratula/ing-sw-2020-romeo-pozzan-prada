package it.polimi.ingsw.model.gameAction.build;

import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;


/**
 * ATLAS
 *
 * Your Worker may build a dome at any level including the ground.
 * Note that a build with a dome on a level that is not 3 is not a Complete Tower.
 * This is to clarify the interaction with CHRONUS power.
 */
public class AtlasBuildTest {

    Cell targetCell = null;
    Battlefield battlefield = null;


    /**
     * Create a new battlefield and get a target cell to test.
     */
    @BeforeEach void setUp() {
        battlefield = new Battlefield();
        targetCell = battlefield.getCell(2,3);
    }


    /**
     * A cell with height 1 must have the same height but has to be a dome.
     */
    @Test void atlasPerformBuild1 () {

        targetCell.setHeight(1);
        assertFalse(targetCell.getIsDome());

        BuildContext thisBuild = new BuildContext(new AtlasBuild());
        thisBuild.executePerformBuild(targetCell, null, battlefield);

        assertTrue(targetCell.getIsDome());
        assertEquals(1, targetCell.getHeight());
    }


    /**
     * If I try to build on a dome nothing change.
     * It should be the controller that make this not possible.
     */
    @Test void atlasPerformBuildDome () {

        targetCell.setHeight(3);
        targetCell.setIsDome();

        BuildContext thisBuild = new BuildContext(new AtlasBuild());
        thisBuild.executePerformBuild(targetCell, null, battlefield);

        assertTrue(targetCell.getIsDome());
        assertEquals(3, targetCell.getHeight());
    }
}
