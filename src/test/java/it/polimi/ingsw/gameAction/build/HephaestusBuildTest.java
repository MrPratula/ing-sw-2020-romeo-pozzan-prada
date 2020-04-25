package it.polimi.ingsw.gameAction.build;

import it.polimi.ingsw.controller.CellHeightException;
import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.controller.ReachHeightLimitException;
import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

class HephaestusBuildTest {
    Battlefield battlefield;
    Cell first_cell;
    int high_first_cell;

    @BeforeEach
    void setUp() throws CellOutOfBattlefieldException {
        battlefield = new Battlefield();
        first_cell = battlefield.getCell(2,3);
        first_cell.setFree();
    }

    @Test
    void HephaestusperformeBuild() throws CellHeightException, ReachHeightLimitException {
        first_cell.setHeight(1);
        high_first_cell = first_cell.getHeight();

        assertEquals(high_first_cell,1);

        BuildContext thisBuild = new BuildContext(new HephaestusBuild());
        thisBuild.executePerformBuild(first_cell, null, battlefield);

        assertTrue(first_cell.getHeight()==high_first_cell+2);
    }
}