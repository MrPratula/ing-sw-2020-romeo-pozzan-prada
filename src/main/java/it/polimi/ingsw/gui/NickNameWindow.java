package it.polimi.ingsw.gui;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.PlayerAction;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class NickNameWindow extends JDialog{

    private final JTextField nicknameTextField;
    private final NickNamePanel nicknamePanel;
    private final SwingView view;


    /**
     * Dialog windows that opens when a player click on PLAY
     * and here he has to put his name
     */
    public NickNameWindow(SwingView swingView) {

        this.view = swingView;

        setTitle("Select your nickname for this game");
        setResizable(true);
        setPreferredSize(new Dimension(600,300));
        setLocationRelativeTo(null);
        setIconImage(Pics.PLAYERICON.getImageIcon().getImage());
        setBackground(Color.BLACK);

        //Nickname's panel
        nicknamePanel = new NickNamePanel();

        //textfield to let the user type his nickname
        nicknameTextField = new JTextField(15);
        //nicknameTextField.setBorder(BorderFactory.createEmptyBorder());
        nicknameTextField.setHorizontalAlignment(JTextField.CENTER);
        nicknameTextField.setBounds(130,130,60,50);
        nicknameTextField.setPreferredSize(new Dimension(250,50));
        nicknameTextField.setMaximumSize(new Dimension(70,10));
        nicknameTextField.setMinimumSize(new Dimension(50,10));
        //nicknameTextField.setOpaque(false);
        nicknameTextField.setSelectedTextColor(Color.BLACK);

        nicknamePanel.add(nicknameTextField, BorderLayout.SOUTH);

        //the button to confirm the selection
        ConfirmButton confirmButton = new ConfirmButton("Confirm");
        confirmButton.setBounds(10,20,30,10);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!nicknameTextField.getText().trim().isEmpty()){
                    PlayerAction playerAction = new PlayerAction(Action.MY_NAME,null,null, null, 0, 0, null, null, false, nicknameTextField.getText());
                    try {
                        view.notifyClient(playerAction);
                        NickNameWindow.this.dispose();
                    } catch (CellOutOfBattlefieldException | WrongNumberPlayerException | ImpossibleTurnException | IOException | CellHeightException | ReachHeightLimitException ex) {
                        ex.printStackTrace();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(new JFrame(), "You have to type a name!", "Error", JOptionPane.ERROR_MESSAGE, Pics.ERRORICON.getImageIcon());
                }
            }
        });

        add(nicknamePanel,BorderLayout.CENTER);
        add(confirmButton, BorderLayout.PAGE_END);


        validate();
        pack();
        setVisible(true);

    }


}
