package it.polimi.ingsw.gui;

import javax.swing.*;
import java.awt.*;

public class ConfirmButton extends JButton {

    public ConfirmButton(String text) {
        super(text);
        setBorderPainted(false);
        setContentAreaFilled(false);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(Pics.BUTTON.getImageIcon().getImage(),0,0,this.getWidth(),20,this);
    }
}
