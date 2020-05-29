package it.polimi.ingsw.gui;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.PlayerAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class NickNameWindow extends JDialog{

    //private static final long serialVersionUID = 1L;
    private JPanel nicknamePanel;
    private JLabel nicknameLabel;                   //2.0
    private JTextField nicknameTextField;           //2.1
    private JButton confirmButton;                  //4.0
    private SwingView view;



    public JTextField getNicknameTextField() {
        return nicknameTextField;
    }

    /**
     * Dialog windows that opens when a player click on PLAY
     * and here he has to put his name and number(FIXME)
     * @param swingView
     */
    public NickNameWindow(final SwingView swingView) {

        this.view = swingView;

        //Nickname's panel
        nicknamePanel = new JPanel();
        nicknamePanel.setSize(350,200);
        nicknamePanel.setLayout(new BorderLayout(10,10));
        nicknamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //label for asking nickname
        nicknameLabel = new JLabel("Nickname:");
        nicknameLabel.setBounds(10,20,80,25);
        nicknamePanel.add(nicknameLabel,BorderLayout.PAGE_START);

        //textfield for let the user type his nickname
        nicknameTextField = new JTextField(20);
        nicknameTextField.setBounds(10,20,80,25);
        nicknamePanel.add(nicknameTextField);

        add(nicknamePanel,BorderLayout.PAGE_START);

        //the button to confirm the selection
        confirmButton = new JButton("Confirm");
        confirmButton.setBounds(10,20,80,25);
        add(confirmButton, BorderLayout.PAGE_END);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!nicknameTextField.getText().trim().isEmpty()){
                    PlayerAction playerAction = new PlayerAction(Action.MY_NAME,null,null, null, 0, 0, null, null, false, nicknameTextField.getText());
                    try {
                        System.out.println("Nome: " + nicknameTextField.getText());
                        view.notifyClient(playerAction);
                        NickNameWindow.this.dispose();
                    } catch (CellOutOfBattlefieldException | WrongNumberPlayerException | ImpossibleTurnException | IOException | CellHeightException | ReachHeightLimitException cellOutOfBattlefieldException) {
                        cellOutOfBattlefieldException.printStackTrace();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(new JFrame(), "You have to type a name!", "Error", JOptionPane.ERROR_MESSAGE);  //posso anche mettere un'immagine error
                }
            }
        });

        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

}
