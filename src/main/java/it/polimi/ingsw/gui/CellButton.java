package it.polimi.ingsw.gui;

import it.polimi.ingsw.cli.Cell;

import javax.swing.*;

public class CellButton extends JButton {

    private Cell cell;

    public CellButton(int x, int y) {
        this.cell = new Cell(x,y);
    }

    public Cell getCell(){
        return cell;
    }

}
