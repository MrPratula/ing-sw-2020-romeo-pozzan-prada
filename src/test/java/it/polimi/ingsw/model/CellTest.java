package it.polimi.ingsw.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CellTest {

    Cell cell = null;

    @Before
    public void setUp() throws Exception {
       cell = new Cell(1, 2);
    }

    @After
    public void tearDown() throws Exception {
        cell = null;
    }

    @Test
    public void incrementHeight() {
        Cell cell2 = new Cell (2, 3) ;

        




    }
}