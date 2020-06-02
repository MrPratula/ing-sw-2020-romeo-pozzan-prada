package it.polimi.ingsw.gui;

import it.polimi.ingsw.cli.Battlefield;
import it.polimi.ingsw.cli.ModelUtils;
import it.polimi.ingsw.utils.Action;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class test extends JFrame {

    private ModelUtils modelUtils; ///////////////////////////////////

    private JLabel messageLabel = new JLabel("WELCOME! Messages will be displayed here");

    private CellButton[][] battlefieldGUI = new CellButton[5][5];

    private Action action;

    //buttonHandler for every button
    private List<ButtonHandler> battlefieldButtons = new ArrayList<>();


    public test() throws HeadlessException {

        setSize(1000,1000);

        setLayout(new BorderLayout());

        BattlefieldPanel b = new BattlefieldPanel();
        b.setLayout(new GridLayout(5,5,0,0));

        for(int j=4; j>-1 ; j--){
            for(int i=0; i<5; i++){
                //here i create a button for every cell
                battlefieldGUI[i][j] = new CellButton(i,j);
                battlefieldGUI[i][j].setBorderPainted(true);
                battlefieldGUI[i][j].setContentAreaFilled(false);
                //battlefieldGUI[i][j].setSize(200,200);
                battlefieldGUI[i][j].setIcon(Pics.LEVEL0.getImageIcon());
                battlefieldGUI[i][j].setBackground(Color.BLACK);
                battlefieldGUI[i][j].getCell().setHeight(0);

                b.add(battlefieldGUI[i][j]);

                //here i add a listener to this button (owning a Cell)
                ButtonHandler bh = new ButtonHandler(battlefieldGUI[i][j],modelUtils, action /*,serverResponse*/);
                battlefieldButtons.add(bh);
                battlefieldGUI[i][j].addActionListener(bh);
            }
        }

        JPanel godPanel = new JPanel(new BorderLayout(3,1));
        godPanel.setLayout(new GridLayout(3,1));
        JLabel player1 = new JLabel(Pics.APOLLO.getImageIcon());
        JLabel player2 = new JLabel(Pics.HERA.getImageIcon());
        JLabel player3 = new JLabel(Pics.LIMUS.getImageIcon());
        player1.setText("Player1");
        player2.setText("Player2");
        player3.setText("Player3");
        godPanel.add(player1);
        godPanel.add(player2);
        godPanel.add(player3);

        add(b,BorderLayout.CENTER);
        add(godPanel,BorderLayout.WEST);
        add(messageLabel, BorderLayout.SOUTH);

        setVisible(true);
        pack();


    }
}
