package it.polimi.ingsw.gui;

import it.polimi.ingsw.cli.Cell;

import javax.swing.*;
import java.awt.*;

public class CellButton extends JButton {

    private final Cell cell;

    public CellButton(int x, int y) {
        this.cell = new Cell(x,y);
    }

    public Cell getCell(){
        return cell;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Pics.LEVEL0.getImageIcon().getImage(),0,0,this.getWidth(), this.getHeight(),this);
    }

}
