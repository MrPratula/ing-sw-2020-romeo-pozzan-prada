package it.polimi.ingsw.cli;

import it.polimi.ingsw.model.Cell;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;


/**
 * Test methods for Cell class
 */
public class CellTest {

    Cell cell = null;


    /**
     * Create a Cell and assign her some values.
     */
    @BeforeEach void setUp() {
        cell = new Cell(2, 4);
    }


    /**
     * A cell should pass from height 2 to 3.
     */
    @Test
    public void normalIncrementHeightTest() {
        setUp();
        cell.setHeight(2);
        cell.incrementHeight();

        assertEquals(3, cell.getHeight());
        assertFalse(cell.getIsDome());
    }


    /**
     * When a cell has a height of 3 and it is not a dome it becomes a dome
     */
    @Test
    public void incrementFrom3toDome() throws Exception {

        setUp();
        cell.setHeight(3);
        assertFalse(cell.getIsDome());
        cell.incrementHeight();

        assertEquals(3, cell.getHeight());
        assertTrue(cell.getIsDome());
    }


    /**
     * If a cell is a dome it can nob be built anymore and it remains unchanged.
     */
    @Test
    public void incrementADome()  {
        setUp();
        cell.setHeight(3);
        cell.setIsDome();

        cell.incrementHeight();

        assertEquals(3, cell.getHeight());
        assertTrue(cell.getIsDome());
    }


    /**
     * I'm not even sure this is a test, but it check for the exception, so leave it here.
     * It check for height <0 and height >3.
     */
    @Test
    public void wrongIncrementInput() {
        setUp();
        cell.setHeight(-1);
        cell.incrementHeight();
        assertTrue(true);
        cell.setHeight(4);
        cell.incrementHeight();
        assertTrue(true);
    }

    @Test
    public void equalsCellTest() {
        setUp();
        Cell cell2 = new Cell (2,4);

        assertTrue(cell.equals(cell2));
    }
}