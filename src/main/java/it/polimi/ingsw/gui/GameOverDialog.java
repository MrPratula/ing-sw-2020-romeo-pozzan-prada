package it.polimi.ingsw.gui;

import it.polimi.ingsw.utils.Pack;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Dialog window showing winning or losing messages
 */
public class GameOverDialog extends JDialog {


    public GameOverDialog(boolean hasWin) throws IOException {

        setTitle("Game Ended");
        setIconImage(new ImageIcon(ImageIO.read(getClass().getResource(Pics.GAMEOVERICON.getPath()))).getImage());
        JPanel p;
        if(hasWin){
            p = new GameWonPanel();
        }
        else{
            p = new GameLostPanel();
        }
        add(p);
        setSize(1000,800);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }
}
