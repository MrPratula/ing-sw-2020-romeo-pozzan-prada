package it.polimi.ingsw.gui;

import it.polimi.ingsw.utils.Pack;
import javax.swing.*;
import java.awt.*;

public class GameOverDialog extends JDialog {


    public GameOverDialog(Pack pack) {

        setTitle("Game Over");
        setIconImage(Pics.GAMEOVERICON.getImageIcon().getImage());
        GameOverPanel p = new GameOverPanel();
        JLabel winner = new JLabel(pack.getMessageInTurn());
        winner.setAlignmentX(Component.CENTER_ALIGNMENT);
        winner.setHorizontalTextPosition(SwingConstants.CENTER);
        p.add(winner);
        add(p);
        setSize(1000,800);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }



}
