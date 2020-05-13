package it.polimi.ingsw.gui;

import it.polimi.ingsw.model.Battlefield;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main Frame of the Gui
 */
public class GameFrame extends JFrame {

    //main panel on the frame with the battlefield
    JPanel mainPanel = new JPanel();

    //first inner panel with the battlefield
    JPanel battlefieldPanel = new JPanel();

    //label where the server tell the player what he has to do
    JLabel messageLabel = new JLabel();

    //button for every cell
    CellButton[][] buttons = new CellButton[5][5];

    ButtonHandler buttonHandler = new ButtonHandler();

    ImageIcon level0 = new ImageIcon("level0.png");
    ImageIcon level1 = new ImageIcon("level1.png");
    ImageIcon level2 = new ImageIcon("level2.png");
    ImageIcon level3 = new ImageIcon("level3.png");
    ImageIcon dome = new ImageIcon("dome.png");
    ImageIcon tokenRed = new ImageIcon("tokenRed.png");
    ImageIcon tokenBlue = new ImageIcon("tokenBlue.png");
    ImageIcon tokenYellow = new ImageIcon("tokenYellow.png");

    private Battlefield battlefield; ///////////////////////////////////

    public JPanel getBattlefieldPanel() {
        return battlefieldPanel;
    }
    public CellButton[][] getButtons() {
        return buttons;
    }

    //starting position FIXME
    int prevI=3, prevJ=4;

    //Constructor of the main frame where the user will see the battlefield and can play on it

    public GameFrame(){
        super("Battlefield");
        setSize(900,900);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        battlefieldPanel.setLayout(new GridLayout(5,5,10,10));
        for(int i=0; i<5 ;i++){
            for(int j=0; j<5; j++){
                //here i create a button for every cell
                buttons[i][j] = new CellButton();
                buttons[i][j].setSize(100,100);
                putInitialBuild(buttons[i][j]);
                battlefieldPanel.add(buttons[i][j]);
                buttons[i][j].addActionListener(buttonHandler);
            }
        }

        //try to put a token
        buttons[prevI][prevJ].setIcon(tokenRed);

        mainPanel.add(battlefieldPanel, BorderLayout.NORTH);
        mainPanel.add(messageLabel, BorderLayout.SOUTH);
        add(mainPanel);
        setVisible(true);
    }


    private void putInitialBuild(CellButton cellButton) {
        //cellButton.setBounds(0,0,100,100);
        cellButton.setIcon(level0);
        cellButton.setBackground(Color.black);
    }


    private void processClick(int i, int j) {
        if(!isValidMove(i,j)) return;
        buttons[i][j].setIcon(tokenRed);
        buttons[prevI][prevJ].setIcon(level0);
        prevI = i;
        prevJ = j;
    }

    private boolean isValidMove(int i, int j) {
        return true;
        //TODO
    }


    private class ButtonHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            for(int i=0; i<5 ;i++) {
                for (int j = 0; j < 5; j++) {
                    if(source == buttons[i][j]){
                        processClick(i,j);
                        //FIXME
                    }
                }
            }
        }
    }


}
