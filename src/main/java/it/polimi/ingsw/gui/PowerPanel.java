package it.polimi.ingsw.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


/**
 * Panel that ask the user if he wants to use hig god Power
 */
public class PowerPanel extends JPanel {

    public PowerPanel() {
        super();
        setSize(350,200);
        setLayout(new GridLayout(1,2));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            g.drawImage(new ImageIcon(ImageIO.read(getClass().getResource(Pics.GODPOWER.getPath()))).getImage(), 0,0,this.getWidth(),this.getHeight(),this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


