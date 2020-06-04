package it.polimi.ingsw.gui;

import javax.swing.*;
import java.awt.*;

public class NickNamePanel extends JPanel {

    public NickNamePanel(boolean b){

    }

    public NickNamePanel() {
        setPreferredSize(new Dimension(600,200));
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(Pics.DIALOGBG.getImageIcon().getImage(),0,0,this.getWidth(),this.getHeight(),this);
    }

}
