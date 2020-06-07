package it.polimi.ingsw.gui;

import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.utils.ServerResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AskToUseTheGodsPower {

    private final ServerResponse serverResponse;
    private final SwingView view;

    public AskToUseTheGodsPower(SwingView swingView, final ServerResponse serverResponse){

        this.serverResponse = serverResponse;
        this.view = swingView;

        final JFrame mainframe = new JFrame("GOD'S POWER");
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Do you want to use your god's power?");
        JButton yes_buttton = new JButton("YES!");
        JButton no_button = new JButton("NO!");

        yes_buttton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new GameFrame(serverResponse,view,true);
                    mainframe.dispose();
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
                    mainframe.dispose();
                } catch (CellOutOfBattlefieldException cellOutOfBattlefieldException) {
                    cellOutOfBattlefieldException.printStackTrace();
                }
            }
        });

        mainframe.add(label, BorderLayout.PAGE_START);
        panel.add(yes_buttton,BorderLayout.WEST);
        panel.add(no_button,BorderLayout.EAST);
        mainframe.add(panel,BorderLayout.PAGE_END);
        mainframe.pack();
        mainframe.setVisible(true);
    }
}
