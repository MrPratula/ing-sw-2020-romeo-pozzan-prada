package it.polimi.ingsw.client;

import it.polimi.ingsw.gui.LobbyFrame;
import it.polimi.ingsw.gui.SwingView;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.PlayerAction;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SetUpDialog extends JDialog{


    private static final long serialVersionUID = 1L;
    //private JPanel mainPanel;                       //1
    private JLabel nicknameLabel;                   //2.0
    private JTextField nicknameTextField;           //2.1
    private JLabel numberOfPlayersLabel;            //3.0
    private JComboBox<Integer> numberOfPlayersBox;  //3.1
    private JButton confirmButton;                  //4.0
    private View view;

    private JPanel nicknamePanel;
    private JPanel numberOfPlayerPanel;


    /**
     * Inner class that handle the first action
     */
    private class ConfirmListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            //create a playerAction in which [int tokenMain = numberofplayers] and [String args = nickname]
            PlayerAction playerAction = new PlayerAction(Action.INITIALISED,null,null,null,(Integer)numberOfPlayersBox.getSelectedItem(),0,null,null,false,nicknameTextField.getText());
            //////////////TODO: send action to server
            SetUpDialog.this.dispose(); //graphic standard
        }
    }

    /**
     * Dialog windows that opens when a player click on PLAY
     * and here he has to put his name and number(FIXME)
     * @param mainFrame
     * @param swingView
     */
    public SetUpDialog(final JFrame mainFrame, SwingView swingView) {

        super(mainFrame, "Player Login");
        this.view = swingView;

        //Nickname's panel
        nicknamePanel = new JPanel();
        nicknamePanel.setSize(350,200);
        nicknamePanel.setLayout(new BorderLayout(10,10));
        nicknamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //label for asking nickname
        nicknameLabel = new JLabel("Nickname");
        nicknameLabel.setBounds(10,20,80,25);
        nicknamePanel.add(nicknameLabel,BorderLayout.PAGE_START);
        //textfield for let the user type his nickname
        nicknameTextField = new JTextField(20);
        nicknameTextField.setBounds(10,20,80,25);
        nicknamePanel.add(nicknameTextField);

        //numberOfPlayerPanel's panel
        numberOfPlayerPanel = new JPanel();
        numberOfPlayerPanel.setSize(350,200);
        numberOfPlayerPanel.setLayout(new BorderLayout(10,10));
        numberOfPlayerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //label for asking how many players does he want to play with
        numberOfPlayersLabel = new JLabel("How many players do you want to play with? ");
        numberOfPlayersLabel.setBounds(10,20,80,25);
        numberOfPlayerPanel.add(numberOfPlayersLabel, BorderLayout.PAGE_START);
        //textfield for let the user type the number
        numberOfPlayersBox = new JComboBox<>();
        numberOfPlayersBox.addItem(2);
        numberOfPlayersBox.addItem(3);
        numberOfPlayerPanel.add(numberOfPlayersBox);

        nicknamePanel.add(numberOfPlayerPanel,BorderLayout.PAGE_END);
        add(nicknamePanel,BorderLayout.PAGE_START);

        //the button to confirm the selection
        confirmButton = new JButton("Confirm");
        confirmButton.setBounds(10,20,80,25);
        confirmButton.addActionListener(new ConfirmListener());
        add(confirmButton, BorderLayout.PAGE_END);
        pack();

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LobbyFrame(mainFrame);
            }
        });
        //setMinimumSize(new Dimension(300, 30));
        setVisible(true);

        /*
        //the main panel
        mainPanel = new JPanel();
        mainPanel.setSize(350,200);
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //label for asking nickname
        nicknameLabel = new JLabel("Nickname");
        nicknameLabel.setBounds(10,20,80,25);
        mainPanel.add(nicknameLabel,BorderLayout.PAGE_START);
        //textfield for let the user type his nickname
        nicknameTextField = new JTextField(20);
        nicknameTextField.setBounds(10,20,80,25);
        mainPanel.add(nicknameTextField);

        //label for asking how many players does he want to play with
        numberOfPlayersLabel = new JLabel("How many players do you want to play with? ");
        numberOfPlayersLabel.setBounds(10,20,80,25);
        mainPanel.add(numberOfPlayersLabel, BorderLayout.PAGE_START);
        //textfield for let the user type the number
        numberOfPlayersBox = new JComboBox<>();
        numberOfPlayersBox.addItem(2);
        numberOfPlayersBox.addItem(3);
        mainPanel.add(numberOfPlayersBox);

        //the button to confirm the selection
        confirmButton = new JButton("Confirm");
        confirmButton.setBounds(10,20,80,25);
        confirmButton.addActionListener(new ConfirmListener());
        mainPanel.add(confirmButton, BorderLayout.PAGE_END);
        add(mainPanel);
        pack();

        setMinimumSize(new Dimension(300, 30));
        setVisible(true);

        */

    }









}
