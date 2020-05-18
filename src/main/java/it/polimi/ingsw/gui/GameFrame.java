package it.polimi.ingsw.gui;

import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Cell;

import javax.swing.*;
import java.awt.*;


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


    // Array with all the pics
    ImageIcon level0 = new ImageIcon("C:\\Users\\ricca\\Desktop\\gui\\level0.png");
    ImageIcon tokerRed = new ImageIcon("C:\\Users\\ricca\\Desktop\\gui\\tokenRed.png");


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
        setSize(800,800);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        battlefieldPanel.setLayout(new GridLayout(5,5,10,10));
        for(int i=0; i<5 ;i++){
            for(int j=0; j<5; j++){
                //here i create a button for every cell
                buttons[i][j] = new CellButton();
                buttons[i][j].setSize(100,100);
                putInitialBuild(buttons[i][j],i,j);
                battlefieldPanel.add(buttons[i][j]);
                buttons[i][j].addActionListener(new ButtonHandler(buttons[i][j]));
            }
        }

        //try to put a token
        buttons[prevI][prevJ].setIcon(tokerRed);

        mainPanel.add(battlefieldPanel, BorderLayout.NORTH);
        mainPanel.add(messageLabel, BorderLayout.SOUTH);
        add(mainPanel);
        setVisible(true);
    }

    private void putInitialBuild(CellButton cellButton, int i, int j) {
        //cellButton.setBounds(0,0,100,100);
        cellButton.setIcon(level0);
        cellButton.setBackground(Color.black);
        cellButton.cell=new Cell(i,j);
        cellButton.cell.setHeight(0); //set heigh at 0
    }

    private boolean isValidMove(int i, int j) {
        return true;
        //TODO
    }

}
