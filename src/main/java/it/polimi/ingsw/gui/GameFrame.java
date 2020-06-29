package it.polimi.ingsw.gui;

import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.GodCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TokenColor;
import it.polimi.ingsw.utils.ServerResponse;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Main Frame of the game for the Gui, where the player can see
 * all the god cards in game and the relative powers, the tokens
 * on the field, read messages and play santorini!
 */
public class GameFrame extends JFrame {

    private final InnerMainPanel innerMainPanel;

    private final SwingView view;



    /**
     * Constructor of the main frame where the user
     * will see the battlefield and can play on it
     * @param serverResponse The current ServerResponse
     * @param swingView The player's SwingView
     * @exception IOException if something goes wrong
     */
    public GameFrame(ServerResponse serverResponse, SwingView swingView) throws IOException {

        super( " Battlefield | " + swingView.getPlayer().getUsername());
        this.view = swingView;

        setLayout(new BorderLayout());
        setIconImage(new ImageIcon(ImageIO.read(getClass().getResource(Pics.BATTLEFIELDICON.getPath()))).getImage());
        setSize(1000,950);
        setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));
        setResizable(true);
        setBackground(Color.BLACK);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                int result = JOptionPane.showConfirmDialog(GameFrame.this, "Do you want to Exit? You will lose if you exit", "Exit Confirmation : ", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) GameFrame.this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                else if (result == JOptionPane.NO_OPTION) GameFrame.this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        });

        List<Player> allPlayers = serverResponse.getPack().getModelCopy().getAllPlayers();
        List<GodCard> godsInGame = serverResponse.getPack().getGodCards();

        innerMainPanel = new InnerMainPanel(swingView);

        JPanel battlefieldPanel = innerMainPanel.createBattlefieldPanel();
        JPanel godPanel = innerMainPanel.createGodPanel(godsInGame, allPlayers);
        JLabel messageLabel = innerMainPanel.createMessageLabel();

        add(battlefieldPanel, BorderLayout.CENTER);
        add(messageLabel, BorderLayout.SOUTH);
        add(godPanel,BorderLayout.WEST);
        setVisible(true);
    }

    /*     GETTER      */

    public InnerMainPanel getInnerMainPanel(){
        return this.innerMainPanel;
    }

}
