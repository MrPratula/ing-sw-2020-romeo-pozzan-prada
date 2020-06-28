package it.polimi.ingsw.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


/**
 * Panel which displays that the player lost
 */
public class GameWonPanel extends JPanel {

    public GameWonPanel() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            g.drawImage(new ImageIcon(ImageIO.read(getClass().getResource(Pics.GAMEWON.getPath()))).getImage(),0,0,this.getWidth(),this.getHeight(),this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
