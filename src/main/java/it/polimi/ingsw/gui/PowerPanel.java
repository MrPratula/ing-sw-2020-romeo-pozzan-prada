package it.polimi.ingsw.gui;

import javax.swing.*;
import java.awt.*;

public class PowerPanel extends JPanel {

    public PowerPanel() {
        super();
        setSize(350,200);
        setLayout(new GridLayout(1,2));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Pics.GODPOWER.getImageIcon().getImage(), 0,0,this.getWidth(),this.getHeight(),this);
    }
}


