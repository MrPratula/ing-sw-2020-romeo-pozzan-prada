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

    // Array with all the pics
    ImageIcon[] pics = new ImageIcon[8];


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
        setPicture(); //settiamo tutti i percorsi delle immagini
        for(int i=0; i<5 ;i++){
            for(int j=0; j<5; j++){
                //here i create a button for every cell
                buttons[i][j] = new CellButton();
                buttons[i][j].setSize(100,100);
                putInitialBuild(buttons[i][j],pics);
                battlefieldPanel.add(buttons[i][j]);
                buttons[i][j].addActionListener(buttonHandler);
            }
        }

        //try to put a token
        buttons[prevI][prevJ].setIcon(pics[5]);

        mainPanel.add(battlefieldPanel, BorderLayout.NORTH);
        mainPanel.add(messageLabel, BorderLayout.SOUTH);
        add(mainPanel);
        setVisible(true);
    }

    private void setPicture(){
        pics[0] = new ImageIcon("C:\\Users\\ricca\\Desktop\\gui\\level0.png");         //level 0
        pics[1] = new ImageIcon("C:\\Users\\ricca\\Desktop\\gui\\level1.png");         //level 1
        pics[2] = new ImageIcon("C:\\Users\\ricca\\Desktop\\gui\\level2.png");         //level 2
        pics[3] = new ImageIcon("C:\\Users\\ricca\\Desktop\\gui\\level3.png");         //level 3
        pics[4] = new ImageIcon("C:\\Users\\ricca\\Desktop\\gui\\dome.png");           //dome
        pics[5] = new ImageIcon("C:\\Users\\ricca\\Desktop\\gui\\tokenRed.png");       //token red
        pics[6] = new ImageIcon("C:\\Users\\ricca\\Desktop\\gui\\tokenBlue.png");      //token blue
        pics[7] = new ImageIcon("C:\\Users\\ricca\\Desktop\\gui\\tokenYellow.png");    //token yellow
    }

    private void incrementHeight(CellButton cellButton, ImageIcon[] pics){
        cellButton.setIcon(pics[1]);
    }


    private void putInitialBuild(CellButton cellButton, ImageIcon[] pics) {
        //cellButton.setBounds(0,0,100,100);
        cellButton.setIcon(pics[0]);
        cellButton.setBackground(Color.black);
    }


    private void processClick(int i, int j, ImageIcon[] pics) {
        if(!isValidMove(i,j)) return;
        buttons[i][j].setIcon(pics[5]);
        buttons[prevI][prevJ].setIcon(pics[0]);
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
                        processClick(i,j,pics);
                        incrementHeight(buttons[i][j],pics);
                        //FIXME
                    }
                }
            }
        }
    }

}
