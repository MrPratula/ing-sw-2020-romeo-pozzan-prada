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
 * Little frame to let the first user select
 * the number of players he wants to play with [2/3]
 */
public class NumberOfPlayersWindow extends JDialog{

    /**
     * Combo selection for 2 or 3 players
     */
    private final JComboBox<Integer> numberOfPlayersBox;
    private final SwingView view;


    /**
     * Dialog windows that opens when the first player connected has to choose
     * how many player he wants to play with
     * @param swingView The player's Swingview
     * @throws IOException if can't send object into the socket
     */
    public NumberOfPlayersWindow(SwingView swingView) throws IOException {

        this.view = swingView;

        setLocationRelativeTo(null);
        setPreferredSize(new Dimension(600,300));
        setIconImage(new ImageIcon(ImageIO.read(getClass().getResource(Pics.PLAYERICON.getPath()))).getImage());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                int result = JOptionPane.showConfirmDialog(NumberOfPlayersWindow.this, "Do you want to Exit? You will not enter in the game", "Exit Confirmation : ", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) NumberOfPlayersWindow.this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                else if (result == JOptionPane.NO_OPTION) NumberOfPlayersWindow.this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        });

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
        ConfirmButton confirmButton = new ConfirmButton();
        confirmButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource(Pics.BUTTON.getPath()))));
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
