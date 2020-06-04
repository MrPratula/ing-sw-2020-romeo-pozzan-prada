package it.polimi.ingsw.gui;

import it.polimi.ingsw.cli.Cell;
import it.polimi.ingsw.cli.GodCard;
import it.polimi.ingsw.cli.ModelUtils;
import it.polimi.ingsw.cli.Player;
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
    private JLabel messageLabel = new JLabel(Pics.MESSAGEBG2.getImageIcon());

    //button for every cell
    private CellButton[][] battlefieldGUI = new CellButton[5][5];

    //buttonHandler for every button
    private List<ButtonHandler> battlefieldButtons = new ArrayList<>();

    private Action action;

    private ModelUtils modelUtils; ///////////////////////////////////

    private Player playerInTurn;


    /**
     * Constructor of the main frame where the user will see the battlefield and can play on it
     */
    public GameFrame(List<GodCard> godsInGame, ServerResponse serverResponse) {

        // handling the frame
        super("Battlefield");
        setIconImage(Pics.BATTLEFIELDICON.getImageIcon().getImage());
        //this.serverResponse = serverResponse;
        setSize(900,800);
        setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

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
        for(int j=4; j>-1 ; j--){
            for(int i=0; i<5; i++){
                //here i create a button for every cell
                battlefieldGUI[i][j] = new CellButton(i,j);
                battlefieldGUI[i][j].setBorderPainted(false);
                battlefieldGUI[i][j].setContentAreaFilled(false);
                //battlefieldGUI[i][j].setSize(200,200);
                battlefieldGUI[i][j].setIcon(Pics.LEVEL0.getImageIcon());
                //battlefieldGUI[i][j].setBackground(Color.BLACK);
                battlefieldGUI[i][j].getCell().setHeight(0);
                battlefieldPanel.add(battlefieldGUI[i][j]);
                //here i add a listener to this button (owning a Cell)
                ButtonHandler bh = new ButtonHandler(battlefieldGUI[i][j],modelUtils, action, serverResponse, playerInTurn);
                battlefieldButtons.add(bh);
                battlefieldGUI[i][j].addActionListener(bh);
            }
        }

        add(battlefieldPanel, BorderLayout.CENTER);
        add(messageLabel, BorderLayout.SOUTH);
        add(godPanel,BorderLayout.WEST);
        setVisible(true);
    }


    /*      GETTER       */

    public Action getAction() {
        return action;
    }

    public CellButton[][] getBattlefieldGUI() {
        return battlefieldGUI;
    }

    public List<ButtonHandler> getBattlefieldButtons() {
        return battlefieldButtons;
    }

    public JLabel getMessageLabel() {
        return messageLabel;
    }

    /*      SETTER       */

    public void setAction(Action action) {
        this.action = action;
    }

    public void setPlayerInTurn(Player player) {
        this.playerInTurn = player;
    }
}
