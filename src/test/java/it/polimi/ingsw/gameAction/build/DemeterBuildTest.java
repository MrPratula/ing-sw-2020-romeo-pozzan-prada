package it.polimi.ingsw.gameAction.build;

import it.polimi.ingsw.controller.CellHeightException;
import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.controller.ReachHeightLimitException;
import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

class DemeterBuildTest {

    Battlefield battlefield = null;
    Cell first_cell = null;
    Cell second_cell = null;
    int high_first_cell, high_second_cell;

    @BeforeEach
    void setUp() throws CellOutOfBattlefieldException {
        battlefield = new Battlefield();
        first_cell = battlefield.getCell(3,4);
        second_cell = battlefield.getCell(2,2);
        first_cell.setFree();
        second_cell.setFree();
    }

    @Test
    void DemeterperfomeBuild() throws CellHeightException, ReachHeightLimitException {
        first_cell.setHeight(1);
        second_cell.setHeight(2);
        high_first_cell = first_cell.getHeight();
        high_second_cell = second_cell.getHeight();

        assertEquals(high_first_cell, 1);
        assertEquals(high_second_cell,2);

        BuildContext thisBuild = new BuildContext(new DemeterBuild());
        thisBuild.executePerformBuild(first_cell, second_cell, battlefield);

        assertTrue(first_cell.getHeight()==high_first_cell+1);
        assertTrue(second_cell.getHeight()==high_second_cell+1);
    }

}