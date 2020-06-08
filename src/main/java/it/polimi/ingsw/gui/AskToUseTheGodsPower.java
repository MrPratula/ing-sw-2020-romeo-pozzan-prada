package it.polimi.ingsw.gui;

import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.utils.ServerResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AskToUseTheGodsPower extends JDialog{

    private final ServerResponse serverResponse;
    private final SwingView view;

    /**
     * JDialog thats asks if the player wants to use his god's power
     * @param swingView
     * @param serverResponse
     */
    public AskToUseTheGodsPower(SwingView swingView, final ServerResponse serverResponse){

        this.serverResponse = serverResponse;
        this.view = swingView;

        setTitle("GOD'S POWER");
        setLocationRelativeTo(null);
        setResizable(false);
        setIconImage(Pics.PLAYERICON.getImageIcon().getImage());
        setPreferredSize(new Dimension(600,300));

        PowerPanel panel = new PowerPanel();

        JButton yes_button = new JButton("YES!");
        JButton no_button = new JButton("NO!");
        yes_button.setBorderPainted(false);
        yes_button.setContentAreaFilled(false);
        no_button.setBorderPainted(false);
        no_button.setContentAreaFilled(false);

        yes_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new GameFrame(serverResponse,view,true);
                    dispose();
                } catch (CellOutOfBattlefieldException cellOutOfBattlefieldException) {
                    cellOutOfBattlefieldException.printStackTrace();
                }
            }
        });
        no_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new GameFrame(serverResponse,view,false);
                    dispose();
                } catch (CellOutOfBattlefieldException cellOutOfBattlefieldException) {
                    cellOutOfBattlefieldException.printStackTrace();
                }
            }
        });

        panel.add(yes_button);
        panel.add(no_button);
        add(panel,BorderLayout.CENTER);

        pack();
        setVisible(true);
    }
}
