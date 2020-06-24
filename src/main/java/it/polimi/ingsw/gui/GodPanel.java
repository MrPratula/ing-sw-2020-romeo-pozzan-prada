package it.polimi.ingsw.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Panel which displays the Gods
 */
public class GodPanel extends JPanel {

    public GodPanel() {
        setLayout((new GridLayout(2,7)));
        setPreferredSize(new Dimension(1800,1000));
    }

    public GodPanel(boolean bool) {
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        try {
            g.drawImage(new ImageIcon(ImageIO.read(getClass().getResource(Pics.COLUMNS.getPath()))).getImage(),0,0,this.getWidth(),this.getHeight(),this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
