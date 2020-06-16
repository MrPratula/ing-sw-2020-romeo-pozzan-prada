package it.polimi.ingsw.gui;

import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JPanel {

    public GameOverPanel() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Pics.GAMEENDED.getImageIcon().getImage(),0,0,this.getWidth(),this.getHeight(),this);
    }

}
