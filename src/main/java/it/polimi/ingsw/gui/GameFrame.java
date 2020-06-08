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
    private JLabel messageLabel = new JLabel(Pics.PLACE_YOUR_TOKEN.getImageIcon());

    //button for every cell
    private CellButton[][] battlefieldGUI = new CellButton[5][5];

    //buttonHandler for every button
    private List<ButtonHandler> battlefieldButtons = new ArrayList<>();

    private final SwingView view;
    private final ServerResponse serverResponse;
    List<GodCard> godsInGame;
    List<Player> allPlayers;
    Battlefield battlefield;
    private Boolean power;


    /**
     * Constructor of the main frame where the user will see the battlefield and can play on it
     */
    public GameFrame(ServerResponse serverResponse, SwingView swingView, Boolean wantPower) throws CellOutOfBattlefieldException {

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

        //to be deleted
        this.godsInGame = serverResponse.getPack().getGodCards();
        /*this.allPlayers = serverResponse.getPack().getModelCopy().getAllPlayers();
        this.battlefield = serverResponse.getPack().getModelCopy().getBattlefield();
        this.power = wantPower;
*/
        // handling the godcard panel
        godPanel.setLayout(new GridLayout(3,1));
        for(int i = 0; i<godsInGame.size(); i++){
            final int j = i + 1;
            final JLabel playertext = new JLabel(new ImageIcon(new File("./src/main/images/godcards/" + godsInGame.get(i).name().toLowerCase() + ".png").getAbsolutePath()));
            playertext.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    playertext.setText("Player"+j);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    playertext.setText(" ");
                }
            });
            godPanel.add(playertext);
        }

        // handling the battlefield panel
        battlefieldPanel = new BattlefieldPanel();
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
                ButtonHandler bh = new ButtonHandler(battlefieldGUI[i][j],serverResponse,view,this,i,j,power);
                battlefieldButtons.add(bh);
                battlefieldGUI[i][j].addActionListener(bh);
                battlefieldPanel.add(battlefieldGUI[i][j]);




                //to be deleted
     /*           int height = battlefield.getCell(i,j).getHeight();
                boolean dome = battlefield.getCell(i,j).getIsDome();

                if(battlefield.getCell(i,j).getThereIsPlayer()){
                    for (Player p : allPlayers) {
                        if (p.getToken1() != null && p.getToken1().getTokenPosition()!=null) {
                            if (p.getToken1().getTokenPosition().equals(battlefield.getCell(i,j))) {
                                setIconCell(battlefieldGUI[i][j],height,dome,p);
                            }
                        }
                        if (p.getToken2() != null && p.getToken2().getTokenPosition()!=null) {
                            if (p.getToken2().getTokenPosition().equals(battlefield.getCell(i,j))) {
                                setIconCell(battlefieldGUI[i][j],height,dome,p);
                            }
                        }
                    }
                }
                else{
                    setIconCell(battlefieldGUI[i][j],height,dome, null);
                }
                setRolloverIconCell(battlefieldGUI[i][j],height,dome);
*/




            }
        }

        add(battlefieldPanel, BorderLayout.CENTER);
        add(messageLabel, BorderLayout.SOUTH);
        add(godPanel,BorderLayout.WEST);
        setVisible(true);
        //getMessageLabel().setText("NOW "+serverResponse.getPack().getPlayer().getUsername().toUpperCase()+", SELECT A CELL TO "+serverResponse.getPack().getAction().toString());
    }


    /**
     * Updates the GUI updating che changed cells
     *
     * TODO : DELETE THIS
     *
     * @param serverResponse response from the server
     * @param wantPower boolean autoexplcative
     * @throws CellOutOfBattlefieldException
     */
    public void updateGui(ServerResponse serverResponse , boolean wantPower) throws CellOutOfBattlefieldException {

        this.godsInGame = serverResponse.getPack().getGodCards();
        this.allPlayers = serverResponse.getPack().getModelCopy().getAllPlayers();
        this.battlefield = serverResponse.getPack().getModelCopy().getBattlefield();
        this.power = wantPower;

        // Compare every cell to update new modified ones

        for(int i=0; i<5; i++) {
            for (int j = 0; j < 5; j++) {

                int height = battlefield.getCell(i,j).getHeight();
                boolean dome = battlefield.getCell(i,j).getIsDome();

                if(battlefield.getCell(i,j).getThereIsPlayer()){
                    for (Player p : allPlayers) {
                        if (p.getToken1() != null && p.getToken1().getTokenPosition()!=null) {
                            if (p.getToken1().getTokenPosition().equals(battlefield.getCell(i,j))) {
                                setIconCell(battlefieldGUI[i][j],height,dome,p);
                            }
                        }
                        if (p.getToken2() != null && p.getToken2().getTokenPosition()!=null) {
                            if (p.getToken2().getTokenPosition().equals(battlefield.getCell(i,j))) {
                                setIconCell(battlefieldGUI[i][j],height,dome,p);
                            }
                        }
                    }
                }
                else{
                    setIconCell(battlefieldGUI[i][j],height,dome, null);
                }
                setRolloverIconCell(battlefieldGUI[i][j],height,dome);

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



    private void setRolloverIconCell(CellButton cellButton, int height, boolean dome) {

        if (!dome) {
            switch (height) {
                case 0: {
                    cellButton.setRolloverIcon(Pics.LEVEL0TEXT.getImageIcon());
                    break; }
                case 1: {
                    cellButton.setRolloverIcon(Pics.LEVEL1TEXT.getImageIcon());
                    break; }
                case 2: {
                    cellButton.setRolloverIcon(Pics.LEVEL2TEXT.getImageIcon());
                    break; }
                case 3: {
                    cellButton.setRolloverIcon(Pics.LEVEL3TEXT.getImageIcon());
                    break; }
            }
        } else {
            switch (height) {
                case 0: {
                    cellButton.setRolloverIcon(Pics.LEVEL0DOMETEXT.getImageIcon());
                    break; }
                case 1: {
                    cellButton.setRolloverIcon(Pics.LEVEL1DOMETEXT.getImageIcon());
                    break; }
                case 2: {
                    cellButton.setRolloverIcon(Pics.LEVEL2DOMETEXT.getImageIcon());
                    break; }
                case 3: {
                    cellButton.setRolloverIcon(Pics.LEVEL3DOMETEXT.getImageIcon());
                    break; }
            }
        }
    }

    public void setIconCell(CellButton cellButton, int height, boolean dome, Player player){
        if(player == null) {
            if (!dome) {
                switch (height) {
                    case 0: {
                        cellButton.setIcon(Pics.LEVEL0.getImageIcon());
                        break; }
                    case 1: {
                        cellButton.setIcon(Pics.LEVEL1.getImageIcon());
                        break; }
                    case 2: {
                        cellButton.setIcon(Pics.LEVEL2.getImageIcon());
                        break; }
                    case 3: {
                        cellButton.setIcon(Pics.LEVEL3.getImageIcon());
                        break; }
                }
            } else {
                switch (height) {
                    case 0: {
                        cellButton.setIcon(Pics.LEVEL0DOME.getImageIcon());
                        break; }
                    case 1: {
                        cellButton.setIcon(Pics.LEVEL1DOME.getImageIcon());
                        break; }
                    case 2: {
                        cellButton.setIcon(Pics.LEVEL2DOME.getImageIcon());
                        break; }
                    case 3: {
                        cellButton.setIcon(Pics.LEVEL3DOME.getImageIcon());
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
                            cellButton.setIcon(Pics.LEVEL0TOKENRED.getImageIcon());
                            break; }
                        case 1: {
                            cellButton.setIcon(Pics.LEVEL1TOKENRED.getImageIcon());
                            break; }
                        case 2: {
                            cellButton.setIcon(Pics.LEVEL2TOKENRED.getImageIcon());
                            break; }
                        case 3: {
                            cellButton.setIcon(Pics.LEVEL3TOKENRED.getImageIcon());
                            break; }
                    }
                    break;
                }
                case BLUE:{
                    switch (height) {
                        case 0: {
                            cellButton.setIcon(Pics.LEVEL0TOKENBLUE.getImageIcon());
                            break; }
                        case 1: {
                            cellButton.setIcon(Pics.LEVEL1TOKENBLUE.getImageIcon());
                            break; }
                        case 2: {
                            cellButton.setIcon(Pics.LEVEL2TOKENBLUE.getImageIcon());
                            break; }
                        case 3: {
                            cellButton.setIcon(Pics.LEVEL3TOKENBLUE.getImageIcon());
                            break; }
                    }
                    break;
                }
                case YELLOW:{
                    switch (height) {
                        case 0: {
                            cellButton.setIcon(Pics.LEVEL0TOKENYELLOW.getImageIcon());
                            break; }
                        case 1: {
                            cellButton.setIcon(Pics.LEVEL1TOKENYELLOW.getImageIcon());
                            break; }
                        case 2: {
                            cellButton.setIcon(Pics.LEVEL2TOKENYELLOW.getImageIcon());
                            break; }
                        case 3: {
                            cellButton.setIcon(Pics.LEVEL3TOKENYELLOW.getImageIcon());
                            break; }
                    }
                    break;
                }
            }
        }
    }

}
