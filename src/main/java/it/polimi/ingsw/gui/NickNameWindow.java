package it.polimi.ingsw.gui;

import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.PlayerAction;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


/**
 * Frame for recognising the user
 */
public class NickNameWindow extends JDialog{

    private final JTextField nicknameTextField;
    private final NickNamePanel nicknamePanel;
    private final SwingView view;


    /**
     * Dialog windows that opens when a player click on PLAY
     * and here he has to put his name
     */
    public NickNameWindow(SwingView swingView) throws IOException {

        this.view = swingView;

        setTitle("Select your nickname for this game");
        setResizable(true);
        setPreferredSize(new Dimension(600,300));
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(ImageIO.read(getClass().getResource(Pics.PLAYERICON.getPath()))).getImage());
        setBackground(Color.BLACK);

        //Nickname's panel
        nicknamePanel = new NickNamePanel();

        //textfield to let the user type his nickname
        nicknameTextField = new JTextField(15);
        nicknameTextField.setHorizontalAlignment(JTextField.CENTER);
        nicknameTextField.setBounds(130,130,60,50);
        nicknameTextField.setPreferredSize(new Dimension(250,50));
        nicknameTextField.setMaximumSize(new Dimension(70,10));
        nicknameTextField.setMinimumSize(new Dimension(50,10));
        nicknameTextField.setSelectedTextColor(Color.BLACK);

        nicknamePanel.add(nicknameTextField, BorderLayout.SOUTH);

        //the button to confirm the selection
        ConfirmButton confirmButton = new ConfirmButton("Confirm");
        confirmButton.setBounds(10,20,30,10);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name = nicknameTextField.getText();
                String validOrNot = isAGoodName(name);

                if(validOrNot.equals("Ok")){

                    PlayerAction playerAction = new PlayerAction(Action.MY_NAME,null,null, null, 0, 0, null, null, false, name);
                    try {
                        view.notifyClient(playerAction);
                        NickNameWindow.this.dispose();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                else {

                    try {
                        final JDialog dialog = new JDialog();
                        dialog.setAlwaysOnTop(true);
                        JOptionPane.showMessageDialog(dialog, validOrNot, "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ImageIO.read(getClass().getResource(Pics.ERRORICON.getPath()))));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        add(nicknamePanel,BorderLayout.CENTER);
        add(confirmButton, BorderLayout.PAGE_END);

        validate();
        pack();
        setVisible(true);
    }


    /**
     * @param name string to ckeck. It is the user input for his username.
     * @return true if it is not empty, too long(<=16) or with spaces.
     */
    public String isAGoodName(String name){

        StringBuilder error = new StringBuilder("Error! ");

        if (name==null) {
            error.append("Null name! ");
            return error.toString();
        }

        if (name.isEmpty()) {
            error.append("Empty name! ");
            return error.toString();
        }

        if(name.length()>20){
            error.append("Your name is too long! ");
            return error.toString();
        }

        if (name.contains("\n")) {
            error.append("Invalid name! ");
            return error.toString();
        }

        /*   let them do this
        if (name.contains(" ")) {
            error.append("It contains an empty space! ");
            error.append("Please retry...");
            return error.toString();
        } */

        return "Ok";
    }


}
