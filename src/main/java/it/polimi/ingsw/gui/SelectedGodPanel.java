package it.polimi.ingsw.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Panel displaying the gods in game
 */
public class SelectedGodPanel extends JPanel {

    public SelectedGodPanel() {
        super();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        try {
            g.drawImage(new ImageIcon(ImageIO.read(getClass().getResource(Pics.COLUMNS.getPath()))).getImage(),0,0,this.getWidth(),20,this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
