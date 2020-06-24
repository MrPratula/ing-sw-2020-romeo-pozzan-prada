package it.polimi.ingsw.gui;

import javax.swing.*;
import java.awt.*;

public class PrometheusPanel extends JPanel {

    public PrometheusPanel(){
        super();
        setSize(350,200);
        setLayout(new GridLayout(1,2));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.drawImage(Pics.ASKFORPROMETHEUSPOWER.getImageIcon().getImage(),0,0,this.getWidth(),this.getHeight(),this);
    }
}
