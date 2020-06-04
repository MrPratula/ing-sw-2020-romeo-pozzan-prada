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

        setTitle("Select your nickname for this game");
        setResizable(false);
        setPreferredSize(new Dimension(600,300));
        setIconImage(Pics.PLAYERICON.getImageIcon().getImage());
        setBackground(Color.BLACK);

        this.view = swingView;

        //JFrame jf = new JFrame("Select your nickname for this game");
        //jf.setIconImage(Pics.PLAYERICON.getImageIcon().getImage());

        //Nickname's panel
        nicknamePanel = new NickNamePanel();

        //label for asking nickname
        JLabel nicknameLabel = new JLabel("<html><div style='text-align: center;'>" + "\nNickname:" + "</div></html>");
        nicknameLabel.setFont(new Font("Arial",Font.PLAIN,30));
        nicknameLabel.setBounds(10,20,80,25);
        nicknamePanel.add(nicknameLabel,BorderLayout.PAGE_START);

        //textfield for let the user type his nickname
        nicknameTextField = new JTextField(25);
        nicknameTextField.setBorder(BorderFactory.createEmptyBorder());
        nicknameTextField.setBounds(10,20,80,10);
        nicknameTextField.setOpaque(false);
        nicknameTextField.setSelectedTextColor(Color.BLACK);
        nicknamePanel.add(nicknameTextField);

        add(nicknamePanel,BorderLayout.CENTER);

        //the button to confirm the selection
        ConfirmButton confirmButton = new ConfirmButton("Confirm");
        confirmButton.setBounds(10,20,80,10);
        add(confirmButton, BorderLayout.PAGE_END);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!nicknameTextField.getText().trim().isEmpty()){
                    PlayerAction playerAction = new PlayerAction(Action.MY_NAME,null,null, null, 0, 0, null, null, false, nicknameTextField.getText());
                    try {
                        //System.out.println("Nome: " + nicknameTextField.getText());
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

        validate();
        pack();
        setVisible(true);

        //jf.setLocationRelativeTo(null);
        //jf.pack();
        //jf.setVisible(true);
    }


}
