package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.CellHeightException;
import it.polimi.ingsw.controller.ReachHeightLimitException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CellTest {

    Cell cell = null;
    int height;
    int posX;
    int posY;

    @Before
    public void setUp() throws Exception {

        cell = new Cell(posX, posY);

        //    height = -1;
        //    height = 0;
        //    height = 2;
            height = 3;
        //    height = 4;

        cell.setHeight(height);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void incrementHeightTest() throws CellHeightException, ReachHeightLimitException {

        cell.incrementHeight();

        if (height>=0 && height<3) {
            System.out.println("height should be between 0 and 2 and it is "+cell.getHeight());
            System.out.println("if it is 3 it's because isDome should be false and it is "+cell.getIsDome());
            assertEquals(height+1, cell.getHeight());
        }
        else if (height==3) {
            System.out.println("height should be 3 and it is "+cell.getHeight());
            System.out.println("isDome should be true and it is "+cell.getIsDome());

            assertTrue(cell.getIsDome());
            assertEquals(height, cell.getHeight());
        }
        else{
            throw new CellHeightException (
                    String.format("Cell at (%d,%d) has height = %d out of range [0,3]",
                    cell.getPosX(), cell.getPosY(), cell.getHeight()));
        }
    }
}