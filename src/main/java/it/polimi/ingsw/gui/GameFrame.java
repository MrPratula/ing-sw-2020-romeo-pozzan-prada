package it.polimi.ingsw.gui;

import it.polimi.ingsw.cli.*;
import it.polimi.ingsw.utils.ServerResponse;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;


/**
 * Main Frame of the game for the Gui
 */
public class GameFrame extends JFrame {


    private InnerMainPanel innerMainPanel;

    private final SwingView view;
    private final ServerResponse serverResponse;
    private List<GodCard> godsInGame;
    //List<Player> allPlayers;
    //Battlefield battlefield;



    /**
     * Constructor of the main frame where the user will see the battlefield and can play on it
     */
    public GameFrame(ServerResponse serverResponse, SwingView swingView) {

        super( " Battlefield | " + swingView.getPlayer().getUsername());
        setLayout(new BorderLayout());
        setIconImage(Pics.BATTLEFIELDICON.getImageIcon().getImage());
        setSize(1000,950);
        setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));
        setResizable(true);
        setBackground(Color.BLACK);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                int result = JOptionPane.showConfirmDialog(GameFrame.this, "Do you want to Exit? You will automatically lose if you exit", "Exit Confirmation : ", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) GameFrame.this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                else if (result == JOptionPane.NO_OPTION) GameFrame.this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        });

        this.view = swingView;
        this.serverResponse = serverResponse;
        this.godsInGame = serverResponse.getPack().getGodCards();

        //to be deleted
        /*      this.allPlayers = serverResponse.getPack().getModelCopy().getAllPlayers();
        this.battlefield = serverResponse.getPack().getModelCopy().getBattlefield();
        this.power = wantPower;
        */

        innerMainPanel = new InnerMainPanel(swingView);

        JPanel battlefieldPanel = innerMainPanel.createBattlefieldPanel();
        JPanel godPanel = innerMainPanel.createGodPanel(godsInGame);
        JLabel messageLabel = innerMainPanel.createMessageLabel();

        add(battlefieldPanel, BorderLayout.CENTER);
        add(messageLabel, BorderLayout.SOUTH);
        add(godPanel,BorderLayout.WEST);
        setVisible(true);
    }


    /*
     * Updates the GUI updating che changed cells
     *
     * TODO : DELETE THIS
     *
     * @param serverResponse response from the server
     * @param wantPower boolean autoexplcative
     * @throws CellOutOfBattlefieldException
     */
    /*    public void updateGui(ServerResponse serverResponse , boolean wantPower) throws CellOutOfBattlefieldException {

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
*/

    /*     GETTER      */

    public InnerMainPanel getInnerMainPanel(){
        return this.innerMainPanel;
    }


    public void setRolloverIconCell(CellButton cellButton, int height, boolean dome) {

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
