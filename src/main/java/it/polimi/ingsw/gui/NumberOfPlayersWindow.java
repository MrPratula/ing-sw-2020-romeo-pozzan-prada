package it.polimi.ingsw.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NumberOfPlayersWindow extends JDialog{


    private JPanel numberOfPlayerPanel;
    private JLabel numberOfPlayersLabel;            //3.0
    private JComboBox<Integer> numberOfPlayersBox;  //3.1
    private JButton confirmButton;                  //4.0
    private SwingView view;


    /**
     * Inner class that handle the first action
     */
    private class ConfirmListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            NumberOfPlayersWindow.this.dispose(); //graphic standard
        }
    }


    public NumberOfPlayersWindow(SwingView swingView) {

        this.view = swingView;

        //numberOfPlayerPanel's panel
        numberOfPlayerPanel = new JPanel();
        numberOfPlayerPanel.setSize(350,200);
        numberOfPlayerPanel.setLayout(new BorderLayout(10,10));
        numberOfPlayerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //label for asking how many players does he want to play with
        numberOfPlayersLabel = new JLabel("How many players do you want to play with? ");
        numberOfPlayersLabel.setBounds(10,20,80,25);
        numberOfPlayerPanel.add(numberOfPlayersLabel, BorderLayout.PAGE_START);

        //box to let the user select 2 or 3
        numberOfPlayersBox = new JComboBox<>();
        numberOfPlayersBox.addItem(2);
        numberOfPlayersBox.addItem(3);
        numberOfPlayerPanel.add(numberOfPlayersBox);

        add(numberOfPlayerPanel,BorderLayout.PAGE_START);

        //the button to confirm the selection
        confirmButton = new JButton("Confirm");
        confirmButton.setBounds(10,20,80,25);
        confirmButton.addActionListener(new ConfirmListener());
        add(confirmButton, BorderLayout.PAGE_END);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LobbyFrame();
            }
        });

        setLocationRelativeTo(null);
        pack();
        setVisible(true);

    }

    public JComboBox<Integer> getNumberOfPlayersBox() {
        return numberOfPlayersBox;
    }


}
