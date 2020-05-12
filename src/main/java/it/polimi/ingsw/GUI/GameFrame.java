package it.polimi.ingsw.GUI;

import it.polimi.ingsw.model.Battlefield;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    //main panel on the frame with the battlefield
    JPanel mainPanel = new JPanel();

    //first inner panel with the battlefield
    JPanel battlefieldPanel = new JPanel();

    //label where the server tell the player what he has to do
    JLabel messageLabel = new JLabel();

    CellButton[][] buttons = new CellButton[5][5];

    private Battlefield battlefield; ///////////////////////////////////

    /*    GETTERS    */
    public JPanel getBattlefieldPanel() {
        return battlefieldPanel;
    }
    public CellButton[][] getButtons() {
        return buttons;
    }

    /*    SETTERS    */
    public void setBattlefield(Battlefield battlefield){
        this.battlefield = battlefield;
    }

    /**
     * Constructor of the main frame where the user
     * will see the battlefield and can play on it
     */
    public GameFrame(){
        super("Battlefield");
        setSize(500,500);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        battlefieldPanel.setLayout(new GridLayout(5,5));
        for(int i=0; i<5 ;i++){
            for(int j=0; j<5; j++){
                //here i create a button for every cell
                buttons[i][j]=new CellButton();
                putInitialBuild(buttons[i][j]);
                battlefieldPanel.add(buttons[i][j]);
            }
        }
        mainPanel.add(battlefieldPanel, BorderLayout.NORTH);
        mainPanel.add(messageLabel, BorderLayout.SOUTH);
        add(mainPanel);
        setVisible(true);
        setUp(this);
    }


    private void putInitialBuild(CellButton cellButton) {
        cellButton.setIcon(new ImageIcon("C:\\Users\\feder\\OneDrive - Politecnico di Milano\\POLIMI\\IngSw\\risorse_grafiche\\used\\WarningSign.png", null));
        cellButton.getCell().setHeight(0);
    }


    private void setUp(JFrame gameFrame) {
        messageLabel.setText("Where do you want to put your first token"); //getMessage(serverResponse.getOutMessage());
    }

}
