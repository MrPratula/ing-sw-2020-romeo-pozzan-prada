package it.polimi.ingsw.gui;

import javax.swing.*;
import java.awt.*;

public class LogoPanel extends JPanel {

    public LogoPanel() {
        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Pics.MAINBG.getImageIcon().getImage(),0,0, this.getWidth(), this.getHeight(),this);
    }
}
