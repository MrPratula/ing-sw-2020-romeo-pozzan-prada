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
    //ServerResponse serverResponse;

    //main panel on the frame with the battlefield
    JPanel mainPanel = new JPanel();

    //first inner panel with the battlefield
    JPanel battlefieldPanel = new JPanel();

    //label where the server tell the player what he has to do
    JLabel messageLabel = new JLabel("Messages displayed here");

    //button for every cell
    CellButton[][] battlefieldGUI = new CellButton[5][5];

    private Action action;

    //buttonHandler for every button
    List<ButtonHandler> battlefieldButtons = new ArrayList<>();

    //path of the source with the images to display
    String startPath = "./src/main/images/buildings/";

    // Array with all the pics
    ImageIcon[] pics = new ImageIcon[]{
            new ImageIcon(new File(startPath + "level0.png").getAbsolutePath()),  //0
            new ImageIcon(new File(startPath + "level1.png").getAbsolutePath()),   //1
            new ImageIcon(new File(startPath + "level2.png").getAbsolutePath()),  //2
            new ImageIcon(new File(startPath + "level3.png").getAbsolutePath()),  //3
            new ImageIcon(new File(startPath + "levelDome.png").getAbsolutePath()), //4
            new ImageIcon(new File(startPath + "level0tokenRed.png").getAbsolutePath()),  //5
            new ImageIcon(new File(startPath + "level1tokenRed.png").getAbsolutePath()), //6
            new ImageIcon(new File(startPath + "level2tokenRed.png").getAbsolutePath()), //7
            new ImageIcon(new File(startPath + "level3tokenRed.png").getAbsolutePath()),  //8
            new ImageIcon(new File(startPath + "level0tokenBlue.png").getAbsolutePath()),   //9
            new ImageIcon(new File(startPath + "level1tokenBlue.png").getAbsolutePath()),  //10
            new ImageIcon(new File(startPath + "level2tokenBlue.png").getAbsolutePath()),  //11
            new ImageIcon(new File(startPath + "level3tokenBlue.png").getAbsolutePath()), //12
            new ImageIcon(new File(startPath + "level0tokenYellow.png").getAbsolutePath()), //13
            new ImageIcon(new File(startPath + "level1tokenYellow.png").getAbsolutePath()), //14
            new ImageIcon(new File(startPath + "level2tokenYellow.png").getAbsolutePath()), //15
            new ImageIcon(new File(startPath + "level3tokenYellow.png").getAbsolutePath()), //16
            new ImageIcon(new File(startPath + "level0Text.png").getAbsolutePath()),  //17
            new ImageIcon(new File(startPath + "level1Text.png").getAbsolutePath()), //18
            new ImageIcon(new File(startPath + "level2Text.png").getAbsolutePath()),//19
            new ImageIcon(new File(startPath + "level3Text.png").getAbsolutePath()), //20
            new ImageIcon(new File(startPath + "levelDomeText.png").getAbsolutePath()), //21
            new ImageIcon(new File(startPath + "level0ValidMove.png").getAbsolutePath()), //22
            new ImageIcon(new File(startPath + "level1ValidMove.png").getAbsolutePath()), //23
            new ImageIcon(new File(startPath + "level2ValidMove.png").getAbsolutePath()), //24
            new ImageIcon(new File(startPath + "level3ValidMove.png").getAbsolutePath()), //25
    };

    private ModelUtils modelUtils; ///////////////////////////////////

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
                battlefieldGUI[i][j].setIcon(pics[0]);
                battlefieldGUI[i][j].setBackground(Color.BLACK);
                //battlefieldGUI[i][j].getCell() = new Cell(i,j);
                battlefieldGUI[i][j].getCell().setHeight(0);

                battlefieldPanel.add(battlefieldGUI[i][j]);

                //here i add a listener to this button (owning a Cell)
                ButtonHandler bh = new ButtonHandler(battlefieldGUI[i][j], pics, modelUtils, action /*,serverResponse*/);
                battlefieldButtons.add(bh);
                battlefieldGUI[i][j].addActionListener(bh);
            }
        }

        mainPanel.add(battlefieldPanel, BorderLayout.NORTH);
        mainPanel.add(messageLabel, BorderLayout.SOUTH);
        add(mainPanel);
        setVisible(true);
    }


    public void setAction(Action action) {
        this.action = action;
    }
}
