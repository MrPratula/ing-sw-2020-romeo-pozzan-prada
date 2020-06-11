package it.polimi.ingsw.gui;

import javax.swing.*;
import java.awt.*;


public class GodLabel extends JLabel {

    private ImageIcon img = Pics.ATHENAPANEL.getImageIcon();

    public GodLabel() {
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
