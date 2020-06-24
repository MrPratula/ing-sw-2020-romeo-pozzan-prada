package it.polimi.ingsw.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LogoPanel extends JPanel {

    public LogoPanel() {
        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            g.drawImage(new ImageIcon(ImageIO.read(getClass().getResource(Pics.OLYMPUS.getPath()))).getImage(),0,0, this.getWidth(), this.getHeight(),this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
