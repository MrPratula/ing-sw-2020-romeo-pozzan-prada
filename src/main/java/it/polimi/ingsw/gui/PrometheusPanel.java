package it.polimi.ingsw.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


/**
 * Panel that ask the user if he wants to use hig Prometheus Power,
 * because prometheus changes radically the turn
 */
public class PrometheusPanel extends JPanel {

    public PrometheusPanel(){
        super();
        setSize(350,200);
        setLayout(new GridLayout(1,2));
    }

    /**
     * Paints the background
     * @param g graphic
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        try {
            g.drawImage(new ImageIcon(ImageIO.read(getClass().getResource(Pics.ASKFORPROMETHEUSPOWER.getPath()))).getImage(),0,0,this.getWidth(),this.getHeight(),this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
