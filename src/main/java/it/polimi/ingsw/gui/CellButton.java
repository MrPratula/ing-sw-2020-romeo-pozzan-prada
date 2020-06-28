package it.polimi.ingsw.gui;

import it.polimi.ingsw.model.Cell;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


/**
 * Gui button of the cell, which the user interacts
 * with by clicking, to select the move, build, ecc..
 */
public class CellButton extends JButton {

    /**
     * The owned cell of the button
     */
    private final Cell cell;

    /**
     * The starting image at level0
     */
    private ImageIcon img = new ImageIcon(ImageIO.read(getClass().getResource(Pics.LEVEL0.getPath())));

    public CellButton(int x, int y) throws IOException {
        this.cell = new Cell(x,y);
    }

    public Cell getCell(){
        return cell;
    }

    /**
     * Sets the image to be changed
     * @param img image
     */
    public void changeImageIcon(ImageIcon img) {
        this.img = img;
    }

    /**
     * Repaints correctly the image
     * @param g graphic
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img.getImage(),0,0,this.getWidth(), this.getHeight(),this);
        repaint();
        revalidate();
    }
}
