package it.polimi.ingsw.gui;

import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.PlayerAction;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;


/**
 * Frame for recognising the user from its name,
 * which will be his username for the game
 */
public class NickNameWindow extends JDialog{


    /**
     * Textfield where he has to put his name
     */
    private final JTextField nicknameTextField;
    private final SwingView view;


    /**
     * Dialog windows that opens when a player click on PLAY
     * and here he has to put his name
     * @param swingView The player's Swingview
     * @throws IOException if can't send object into the socket
     */
    public NickNameWindow(SwingView swingView) throws IOException {

        this.view = swingView;

        setTitle("Select your nickname for this game");
        setResizable(true);
        setPreferredSize(new Dimension(600,300));
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(ImageIO.read(getClass().getResource(Pics.PLAYERICON.getPath()))).getImage());
        setBackground(Color.BLACK);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                int result = JOptionPane.showConfirmDialog(NickNameWindow.this, "Do you want to Exit? You will not enter in the game", "Exit Confirmation : ", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) NickNameWindow.this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                else if (result == JOptionPane.NO_OPTION) NickNameWindow.this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        });

        //Nickname's panel
        NickNamePanel nicknamePanel = new NickNamePanel();

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
        ConfirmButton confirmButton = new ConfirmButton();
        confirmButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource(Pics.BUTTON.getPath()))));
        confirmButton.setBounds(10,20,50,20);

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
     * @param name string to check. It is the user input for his username.
     * @return true if it is not empty, too long(<=16), not null, not invalid.
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
