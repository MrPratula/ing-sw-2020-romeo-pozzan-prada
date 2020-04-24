package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.CellHeightException;
import it.polimi.ingsw.controller.ReachHeightLimitException;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;

public class CellTest {

    Cell cell = null;


    @BeforeEach void setUp() throws Exception {

        cell = new Cell(2, 4);

    }

    @Test
    public void normalIncrementHeightTest() throws Exception {

        setUp();
        cell.setHeight(2);
        cell.incrementHeight();

        assertEquals(3, cell.getHeight());
        assertFalse(cell.getIsDome());
    }

    @Test
    public void incrementFrom3toDome() throws Exception {

        setUp();
        cell.setHeight(3);
        cell.incrementHeight();

        assertEquals(3, cell.getHeight());
        assertTrue(cell.getIsDome());
    }




}