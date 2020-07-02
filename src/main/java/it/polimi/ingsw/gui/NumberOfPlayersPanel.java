package it.polimi.ingsw.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


/**
 * Little frame to let the first user select
 * the number of players he wants to play with [2/3]
 */
public class NumberOfPlayersPanel extends JPanel {


    public NumberOfPlayersPanel() {
        super();
        setSize(350,200);
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    /**
     * Paints the background
     * @param g graphic
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            g.drawImage(new ImageIcon(ImageIO.read(getClass().getResource(Pics.NUMBEROFPLAYERS.getPath()))).getImage(),0,0,this.getWidth(),this.getHeight(),this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
