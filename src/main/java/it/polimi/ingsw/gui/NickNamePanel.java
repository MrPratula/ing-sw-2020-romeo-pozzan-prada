package it.polimi.ingsw.gui;

import javax.swing.*;
import java.awt.*;

public class NickNamePanel extends JPanel {

    public NickNamePanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(Pics.NICKNAME.getImageIcon().getImage(),0,0,this.getWidth(),this.getHeight(),this);
    }

}
