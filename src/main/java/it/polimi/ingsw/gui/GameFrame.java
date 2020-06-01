package it.polimi.ingsw.gui;

import it.polimi.ingsw.cli.Cell;
import it.polimi.ingsw.cli.ModelUtils;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.ServerResponse;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Main Frame of the game for the Gui
 */
public class GameFrame extends JFrame {

    //main panel on the frame with the battlefield
    private JPanel mainPanel = new JPanel();

    //first inner panel with the battlefield
    private JPanel battlefieldPanel = new JPanel();

    //label where the server tell the player what he has to do
    private JLabel messageLabel = new JLabel("WELCOME! Messages will be displayed here");

    //button for every cell
    private CellButton[][] battlefieldGUI = new CellButton[5][5];

    private Action action;

    //buttonHandler for every button
    private List<ButtonHandler> battlefieldButtons = new ArrayList<>();

    private ModelUtils modelUtils; ///////////////////////////////////


    /**
     * Constructor of the main frame where the user will see the battlefield and can play on it
     */
    public GameFrame(/*ServerResponse serverResponse*/) {
        super("Battlefield");
        //this.serverResponse = serverResponse;
        setSize(800,800);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        battlefieldPanel.setLayout(new GridLayout(5,5,1,1));
        for(int j=4; j>-1 ; j--){
            for(int i=0; i<5; i++){
                //here i create a button for every cell
                battlefieldGUI[i][j] = new CellButton(i,j);
                battlefieldGUI[i][j].setBorderPainted(false);
                battlefieldGUI[i][j].setContentAreaFilled(false);
                battlefieldGUI[i][j].setSize(100,100);
                battlefieldGUI[i][j].setIcon(Pics.LEVEL0.getImage());
                battlefieldGUI[i][j].setBackground(Color.BLACK);
                battlefieldGUI[i][j].getCell().setHeight(0);

                battlefieldPanel.add(battlefieldGUI[i][j]);

                //here i add a listener to this button (owning a Cell)
                ButtonHandler bh = new ButtonHandler(battlefieldGUI[i][j],modelUtils, action /*,serverResponse*/);
                battlefieldButtons.add(bh);
                battlefieldGUI[i][j].addActionListener(bh);
            }
        }

        mainPanel.add(battlefieldPanel, BorderLayout.NORTH);
        mainPanel.add(messageLabel, BorderLayout.SOUTH);
        add(mainPanel);
        setVisible(true);
    }


    /*      GETTER       */

    public CellButton[][] getBattlefieldGUI() {
        return battlefieldGUI;
    }

    public List<ButtonHandler> getBattlefieldButtons() {
        return battlefieldButtons;
    }

    public JLabel getMessageLabel() {
        return messageLabel;
    }

    /*      SETTER       */

    public void setAction(Action action) {
        this.action = action;
    }
}
