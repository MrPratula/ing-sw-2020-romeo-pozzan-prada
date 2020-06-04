package it.polimi.ingsw.gui;

import javax.swing.*;
import java.awt.*;

public class GodPanel extends JPanel {

    public GodPanel() {
        setLayout((new GridLayout(2,7)));
    }

    public GodPanel(boolean bool) {
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(Pics.COLUMNS.getImageIcon().getImage(),0,0,this.getWidth(),this.getHeight(),this);
    }
}
