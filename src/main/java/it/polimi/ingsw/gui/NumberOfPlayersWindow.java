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

        setLocationRelativeTo(null);
        setPreferredSize(new Dimension(600,300));

        //numberOfPlayersPanel's panel
        NumberOfPlayersPanel numberOfPlayersPanel = new NumberOfPlayersPanel();

        //box to let the user select 2 or 3
        numberOfPlayersBox = new JComboBox<>();
        numberOfPlayersBox.setBorder(BorderFactory.createEmptyBorder());
        numberOfPlayersBox.addItem(2);
        numberOfPlayersBox.addItem(3);
        numberOfPlayersPanel.add(numberOfPlayersBox,BorderLayout.PAGE_END);

        add(numberOfPlayersPanel,BorderLayout.CENTER);

        //the button to confirm the selection
        ConfirmButton confirmButton = new ConfirmButton("Confirm");
        confirmButton.setBounds(10,20,80,25);
        add(confirmButton, BorderLayout.PAGE_END);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayerAction playerAction = new PlayerAction(Action.NUMBER_OF_PLAYERS, null, null, null, (int)numberOfPlayersBox.getSelectedItem(), 0, null, null, false, null);
                try {
                    view.notifyClient(playerAction);
                    NumberOfPlayersWindow.this.dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        pack();
        setVisible(true);
    }
}
