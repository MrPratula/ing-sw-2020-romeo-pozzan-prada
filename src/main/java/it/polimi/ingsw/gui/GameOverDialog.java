package it.polimi.ingsw.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

/**
 * Dialog window showing winning or losing messages
 */
public class GameOverDialog extends JFrame {


    /**
     * Creates the Dialog window showing winning or losing messages
     * @param swingView of the user
     * @param hasWin boolean that tell us to display YOU WON / YOU LOST
     * @throws IOException if the image is not correctly loaded
     */
    public GameOverDialog(SwingView swingView, boolean hasWin) throws IOException {

        setTitle("Game Ended  |  "+swingView.getPlayer().getUsername());
        setIconImage(new ImageIcon(ImageIO.read(getClass().getResource(Pics.LOST_ICON.getPath()))).getImage());
        JPanel p;
        if(hasWin){
            p = new GameWonPanel();
        }
        else{
            p = new GameLostPanel();
        }

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        add(p);

        setSize(1000,800);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
}
