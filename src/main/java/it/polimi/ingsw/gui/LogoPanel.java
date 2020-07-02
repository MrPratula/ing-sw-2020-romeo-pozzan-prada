package it.polimi.ingsw.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


/**
 * Initial frame in which the user has to click PLAY to start the game
 */
public class LogoPanel extends JPanel {

    public LogoPanel() {
        setLayout(new BorderLayout());
    }

    /**
     * Paints the background
     * @param g graphic
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            g.drawImage(new ImageIcon(ImageIO.read(getClass().getResource(Pics.MAINBG.getPath()))).getImage(),0,0, this.getWidth(), this.getHeight(),this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
