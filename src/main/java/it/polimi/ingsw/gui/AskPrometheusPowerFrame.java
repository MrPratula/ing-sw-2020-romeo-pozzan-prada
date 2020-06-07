package it.polimi.ingsw.gui;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.PlayerAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AskPrometheusPowerFrame extends JDialog {

    private final SwingView view;

    public AskPrometheusPowerFrame(SwingView swingView) {

        this.view = swingView;

        setTitle("Do you want to use the power of prometheus?");
        setResizable(false);
        setPreferredSize(new Dimension(600,300));
        setBackground(Color.BLACK);


        JButton yes_button = new JButton("YES!");
        JButton no_button = new JButton("NO!");

        add(yes_button,BorderLayout.EAST);
        add(no_button, BorderLayout.WEST);

        yes_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               PlayerAction playerAction = new PlayerAction(Action.PROMETHEUS_ANSWER, view.getPlayer(), null, null, 0, 0, null, null, true, null);
                try {
                    view.notifyClient(playerAction);
                    AskPrometheusPowerFrame.this.dispose();
                } catch (CellOutOfBattlefieldException | ReachHeightLimitException | CellHeightException | IOException | ImpossibleTurnException | WrongNumberPlayerException e) {
                    e.printStackTrace();
                }
            }
        });

        no_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayerAction playerAction = new PlayerAction(Action.PROMETHEUS_ANSWER, view.getPlayer(), null, null, 0, 0, null, null, false, null);
                try {
                    view.notifyClient(playerAction);
                    AskPrometheusPowerFrame.this.dispose();
                } catch (CellOutOfBattlefieldException | ReachHeightLimitException | CellHeightException | IOException | ImpossibleTurnException | WrongNumberPlayerException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
