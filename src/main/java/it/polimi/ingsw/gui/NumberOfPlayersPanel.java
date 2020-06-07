package it.polimi.ingsw.gui;

import javax.swing.*;
import java.awt.*;

public class NumberOfPlayersPanel extends JPanel {

    public NumberOfPlayersPanel() {
        super();
        setSize(350,200);
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Pics.NUMBEROFPLAYERS.getImageIcon().getImage(),0,0,this.getWidth(),this.getHeight(),this);
    }
}
