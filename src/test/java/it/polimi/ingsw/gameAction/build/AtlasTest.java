package it.polimi.ingsw.gameAction.build;

import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

public class AtlasTest {

    Cell targetCell = null;
    Battlefield battlefield = null;

    @BeforeEach
    public void setUp() throws CellOutOfBattlefieldException {
        battlefield = new Battlefield();
        Cell targetCell = battlefield.getCell(2,3);

    }


    @Test
    public void atlasPerformBuild () {

        targetCell.setHeight(1);
        assertTrue(targetCell.getIsDome());



    }




}
