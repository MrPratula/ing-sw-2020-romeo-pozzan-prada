package it.polimi.ingsw.gui;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.PlayerAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class NumberOfPlayersWindow extends JDialog{


    private final JComboBox<Integer> numberOfPlayersBox;
    private final SwingView view;

    public NumberOfPlayersWindow(SwingView swingView) {

        this.view = swingView;

        //numberOfPlayerPanel's panel
        JPanel numberOfPlayerPanel = new JPanel();
        numberOfPlayerPanel.setSize(350,200);
        numberOfPlayerPanel.setLayout(new BorderLayout(10,10));
        numberOfPlayerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //label for asking how many players does he want to play with
        JLabel numberOfPlayersLabel = new JLabel("How many players do you want to play with? ");
        numberOfPlayersLabel.setBounds(10,20,80,25);
        numberOfPlayerPanel.add(numberOfPlayersLabel, BorderLayout.PAGE_START);

        //box to let the user select 2 or 3
        numberOfPlayersBox = new JComboBox<>();
        numberOfPlayersBox.addItem(2);
        numberOfPlayersBox.addItem(3);
        numberOfPlayerPanel.add(numberOfPlayersBox);

        add(numberOfPlayerPanel,BorderLayout.PAGE_START);

        //the button to confirm the selection
        JButton confirmButton = new JButton("Confirm");
        confirmButton.setBounds(10,20,80,25);
        add(confirmButton, BorderLayout.PAGE_END);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayerAction playerAction = new PlayerAction(Action.NUMBER_OF_PLAYERS, null, null, null, (int)numberOfPlayersBox.getSelectedItem(), 0, null, null, false, null);
                try {
                    view.notifyClient(playerAction);
                    NumberOfPlayersWindow.this.dispose();
                } catch (CellOutOfBattlefieldException | WrongNumberPlayerException | ImpossibleTurnException | IOException | CellHeightException | ReachHeightLimitException cellOutOfBattlefieldException) {
                    cellOutOfBattlefieldException.printStackTrace();
                }
            }
        });

        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }
}
