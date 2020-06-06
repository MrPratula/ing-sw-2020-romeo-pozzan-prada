package it.polimi.ingsw.gui;

import it.polimi.ingsw.cli.*;
import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.ServerResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Main Frame of the game for the Gui
 */
public class GameFrame extends JFrame {

    //first inner panel with the battlefield
    private BattlefieldPanel battlefieldPanel;

    private JPanel godPanel = new JPanel();

    //label where the server tell the player what he has to do
    private JLabel messageLabel = new JLabel(Pics.ASK_FOR_WHERE_TO_MOVE.getImageIcon());

    //button for every cell
    private CellButton[][] battlefieldGUI = new CellButton[5][5];

    //buttonHandler for every button
    private List<ButtonHandler> battlefieldButtons = new ArrayList<>();

    private final SwingView view;
    private final ServerResponse serverResponse;
    List<GodCard> godsInGame;
    List<Player> allPlayers;
    Player player;
    Battlefield battlefield;


    /**
     * Constructor of the main frame where the user will see the battlefield and can play on it
     */
    public GameFrame(ServerResponse serverResponse, SwingView swingView) throws CellOutOfBattlefieldException {

        // handling the frame
        super("Battlefield");
        setIconImage(Pics.BATTLEFIELDICON.getImageIcon().getImage());
        setSize(900,800);
        setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.view = swingView;
        this.serverResponse = serverResponse;
        this.godsInGame = serverResponse.getPack().getGodCards();
        this.allPlayers = serverResponse.getPack().getModelCopy().getAllPlayers();
        this.battlefield = serverResponse.getPack().getModelCopy().getBattlefield();

        // handling the godcard panel
        godPanel.setLayout(new GridLayout(3,1));
        for(int i = 0; i<godsInGame.size(); i++){
            final int j = i + 1;
            final JLabel player = new JLabel(new ImageIcon(new File("./src/main/images/godcards/" + godsInGame.get(i).name().toLowerCase() + ".png").getAbsolutePath()));
            player.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    player.setText("Player"+j);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    player.setText(" ");
                }
            });
            godPanel.add(player);
        }

        // handling the battlefield panel
        battlefieldPanel = new BattlefieldPanel();
        battlefieldPanel.setLayout(new GridLayout(5,5,0,0));
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                int height = battlefield.getCell(i,j).getHeight();
                boolean dome = battlefield.getCell(i,j).getIsDome();
                if(battlefield.getCell(i,j).getThereIsPlayer()){
                    for (Player p : allPlayers) {
                        if (p.getToken1() != null && p.getToken1().getTokenPosition()!=null) {
                            if (p.getToken1().getTokenPosition().equals(battlefield.getCell(i,j))) {
                                player = p;
                            }
                        }
                        if (p.getToken2().getTokenPosition() != null && p.getToken2().getTokenPosition()!=null) {                                 //if he has the first token
                            if (p.getToken2().getTokenPosition().equals(battlefield.getCell(i,j))) {   //i print his background correctly
                                player = p;
                            }
                        }
                    }
                }
                else{
                    player = null; }
                //I create a button for every cell
                battlefieldGUI[i][j] = new CellButton(i,j);
                battlefieldGUI[i][j].setBorderPainted(false);
                battlefieldGUI[i][j].setContentAreaFilled(false);
                battlefieldGUI[i][j].getCell().setHeight(height);
                battlefieldPanel.add(battlefieldGUI[i][j]);
                //I add a listener to this button (owning a Cell)
                ButtonHandler bh = new ButtonHandler(battlefieldGUI[i][j],serverResponse,view,this);
                battlefieldButtons.add(bh);
                battlefieldGUI[i][j].addActionListener(bh);
                battlefieldPanel.add(battlefieldGUI[i][j]);
                setIconCell(battlefieldGUI[i][j],height,dome,player);
            }
        }

        add(battlefieldPanel, BorderLayout.CENTER);
        add(messageLabel, BorderLayout.SOUTH);
        add(godPanel,BorderLayout.WEST);
        setVisible(true);
        getMessageLabel().setText("NOW "+serverResponse.getPack().getPlayer().getUsername().toUpperCase()+", SELECT A CELL TO "+Action.PLACE_YOUR_TOKEN.toString());
    }

    public void setIconCell(CellButton cell, int height, boolean dome, Player player){
        if(player == null) {
            if (!dome) {
                switch (height) {
                    case 0: {
                        cell.setIcon(Pics.LEVEL0.getImageIcon());
                        break; }
                    case 1: {
                        cell.setIcon(Pics.LEVEL1.getImageIcon());
                        break; }
                    case 2: {
                        cell.setIcon(Pics.LEVEL2.getImageIcon());
                        break; }
                    case 3: {
                        cell.setIcon(Pics.LEVEL3.getImageIcon());
                        break; }
                }
            } else {
                switch (height) {
                    case 0: {
                        cell.setIcon(Pics.LEVEL0DOME.getImageIcon());
                        break; }
                    case 1: {
                        cell.setIcon(Pics.LEVEL1DOME.getImageIcon());
                        break; }
                    case 2: {
                        cell.setIcon(Pics.LEVEL2DOME.getImageIcon());
                        break; }
                    case 3: {
                        cell.setIcon(Pics.LEVEL3DOME.getImageIcon());
                        break; }
                }
            }
        }
        else{
            TokenColor color = player.getTokenColor();
            switch (color){
                case RED:{
                    switch (height) {
                        case 0: {
                            cell.setIcon(Pics.LEVEL0TOKENRED.getImageIcon());
                            break; }
                        case 1: {
                            cell.setIcon(Pics.LEVEL1TOKENRED.getImageIcon());
                            break; }
                        case 2: {
                            cell.setIcon(Pics.LEVEL2TOKENRED.getImageIcon());
                            break; }
                        case 3: {
                            cell.setIcon(Pics.LEVEL3TOKENRED.getImageIcon());
                            break; }
                    }
                }
                case BLUE:{
                    switch (height) {
                        case 0: {
                            cell.setIcon(Pics.LEVEL0TOKENBLUE.getImageIcon());
                            break; }
                        case 1: {
                            cell.setIcon(Pics.LEVEL1TOKENBLUE.getImageIcon());
                            break; }
                        case 2: {
                            cell.setIcon(Pics.LEVEL2TOKENBLUE.getImageIcon());
                            break; }
                        case 3: {
                            cell.setIcon(Pics.LEVEL3TOKENBLUE.getImageIcon());
                            break; }
                    }
                }
                case YELLOW:{
                    switch (height) {
                        case 0: {
                            cell.setIcon(Pics.LEVEL0TOKENYELLOW.getImageIcon());
                            break; }
                        case 1: {
                            cell.setIcon(Pics.LEVEL1TOKENYELLOW.getImageIcon());
                            break; }
                        case 2: {
                            cell.setIcon(Pics.LEVEL2TOKENYELLOW.getImageIcon());
                            break; }
                        case 3: {
                            cell.setIcon(Pics.LEVEL3TOKENYELLOW.getImageIcon());
                            break; }
                    }
                }
            }
        }
    }


    /*      GETTER       */

    public CellButton[][] getBattlefieldGUI() {
        return battlefieldGUI;
    }

    public JLabel getMessageLabel() {
        return messageLabel;
    }
}
