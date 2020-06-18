package it.polimi.ingsw.gui;

import it.polimi.ingsw.model.GodCard;
import it.polimi.ingsw.model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InnerMainPanel {

    private JPanel battlefieldPanel;
    private JPanel godPanel;
    //todo: delete
    private JLabel messageLabel  = new JLabel(Pics.WELCOMEMESSAGE.getImageIcon());
    private CellButton[][] battlefieldGUI = new CellButton[5][5];
    private List<ButtonHandler> battlefieldButtons = new ArrayList<>();
    private SwingView swingView;


    public InnerMainPanel(SwingView swingView) {
        this.swingView = swingView;
    }


    public JPanel createBattlefieldPanel(){

        battlefieldPanel = new JPanel();
        battlefieldPanel.setLayout(new GridLayout(5,5,0,0));

        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){

                //I create a button for every cell
                battlefieldGUI[i][j] = new CellButton(i,j);
                battlefieldGUI[i][j].setBorderPainted(false);
                battlefieldGUI[i][j].setContentAreaFilled(false);
                battlefieldGUI[i][j].getCell().setHeight(0);
                battlefieldGUI[i][j].setRolloverIcon(Pics.LEVEL0TEXT.getImageIcon());
                battlefieldPanel.add(battlefieldGUI[i][j]);
                ButtonHandler bh = new ButtonHandler(battlefieldGUI[i][j],swingView);
                battlefieldButtons.add(bh);
                battlefieldGUI[i][j].addActionListener(bh);
                battlefieldPanel.add(battlefieldGUI[i][j]);
            }
        }
        return battlefieldPanel;
    }

    public JLabel createMessageLabel() {
        return messageLabel;
    }

    public JPanel createGodPanel(final List<GodCard> godsInGame, final List<Player> allPlayers) {

        godPanel = new JPanel();
        godPanel.setLayout(new GridLayout(godsInGame.size(),1));

        for(int i = 0; i<godsInGame.size(); i++){

            final JLabel playerText = new JLabel(new ImageIcon(new File("./src/main/images/godcards/" + godsInGame.get(i).name().toLowerCase() + "Panel.png").getAbsolutePath()));

            //final GodLabel playerText = new GodLabel();
            //playerText.changeGodLabel(new ImageIcon(new File("./src/main/images/godcards/" + godsInGame.get(i).name().toLowerCase() + "Panel.png").getAbsolutePath()));
            final int finalI = i;
            playerText.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                        playerText.setText("Player " + allPlayers.get(finalI).getTokenColor().name() + " - " + allPlayers.get(finalI).getUsername().toUpperCase() );
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    playerText.setText(" ");
                }
            });
            godPanel.add(playerText);
        }

        return godPanel;
    }

    public CellButton[][] getBattlefieldGUI() {
        return battlefieldGUI;
    }

    public JLabel getMessageLabel() {
        return messageLabel;
    }
}
