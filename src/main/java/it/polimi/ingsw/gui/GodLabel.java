package it.polimi.ingsw.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class GodLabel extends JLabel {

    private ImageIcon img = new ImageIcon(ImageIO.read(getClass().getResource(Pics.ATHENAPANEL.getPath())));

    public GodLabel() throws IOException {
        super();
    }

    public void changeGodLabel(ImageIcon img) {
        this.img = img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img.getImage(),0,0,this.getWidth(),this.getHeight(),this);
        repaint();
        revalidate();
    }
}
