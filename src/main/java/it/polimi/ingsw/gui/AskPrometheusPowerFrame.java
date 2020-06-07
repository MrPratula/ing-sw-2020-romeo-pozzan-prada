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

        setTitle("Prometheus Power");
        setResizable(false);
        setIconImage(Pics.PLAYERICON.getImageIcon().getImage());
        setPreferredSize(new Dimension(600,300));

        PrometheusPanel main = new PrometheusPanel();

        JButton yes_button = new JButton("YES");
        yes_button.setContentAreaFilled(false);
        yes_button.setBorderPainted(false);
        JButton no_button = new JButton("NO");
        no_button.setContentAreaFilled(false);
        no_button.setBorderPainted(false);


        yes_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               PlayerAction playerAction = new PlayerAction(Action.PROMETHEUS_ANSWER, view.getPlayer(), null, null, 0, 0, null, null, true, null);
                try {
                    view.notifyClient(playerAction);
                    AskPrometheusPowerFrame.this.dispose();
                } catch (CellOutOfBattlefieldException | ReachHeightLimitException | CellHeightException | IOException | ImpossibleTurnException | WrongNumberPlayerException ex) {
                    ex.printStackTrace();
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
                } catch (CellOutOfBattlefieldException | ReachHeightLimitException | CellHeightException | IOException | ImpossibleTurnException | WrongNumberPlayerException ex) {
                    ex.printStackTrace();
                }
            }
        });

        main.add(yes_button);
        main.add(no_button);

        add(main, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }




}
