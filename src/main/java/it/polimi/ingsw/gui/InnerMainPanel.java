package it.polimi.ingsw.gui;

import it.polimi.ingsw.model.GodCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TokenColor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates al the necessary graphic tools for the main Gameframe,
 * and passes them to the actual GameFrame, which display them
 * in a nice way for the user.
 */
public class InnerMainPanel {

    private JPanel battlefieldPanel;
    private JPanel godPanel;
    private JLabel messageLabel  = new JLabel(new ImageIcon(ImageIO.read(getClass().getResource(Pics.WELCOMEMESSAGE.getPath()))));
    private CellButton[][] battlefieldGUI = new CellButton[5][5];
    private SwingView swingView;


    public InnerMainPanel(SwingView swingView) throws IOException {
        this.swingView = swingView;
    }

    /**
     * Creates the battlefield panel for the GameFrame,
     * creating a 5x5 matrix containing CellButtons,
     * and decores them as default
     * @return the battlefield containing cellbuttons
     * @throws IOException in case of image errors in loading
     */
    public JPanel createBattlefieldPanel() throws IOException {

        battlefieldPanel = new JPanel();
        battlefieldPanel.setLayout(new GridLayout(5,5,0,0));

        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){

                //I create a button for every cell
                battlefieldGUI[i][j] = new CellButton(i,j);
                battlefieldGUI[i][j].setBorderPainted(false);
                battlefieldGUI[i][j].setContentAreaFilled(false);
                battlefieldGUI[i][j].getCell().setHeight(0);
                battlefieldGUI[i][j].setRolloverIcon(new ImageIcon(ImageIO.read(getClass().getResource(Pics.LEVEL0TEXT.getPath()))));
                battlefieldPanel.add(battlefieldGUI[i][j]);
                ButtonHandler bh = new ButtonHandler(battlefieldGUI[i][j],swingView);
                battlefieldGUI[i][j].addActionListener(bh);
                battlefieldPanel.add(battlefieldGUI[i][j]);
            }
        }
        return battlefieldPanel;
    }

    /**
     * It creates the default Message Label, that will
     * be updated and repainted while the actions on
     * the game changes. Here is where i tell the user what he must do
     * @return the message Label
     */
    public JLabel createMessageLabel() {
        return messageLabel;
    }

    /**
     * It creates the God Panel in a nice Layout on the left side of the GameFrame,
     * displaying the gods that will partecipate to the game
     * @param godsInGame GodCard(s) in game
     * @param allPlayers Player(s) in game
     * @return the god panel
     */
    public JPanel createGodPanel(final List<GodCard> godsInGame, final List<Player> allPlayers) throws IOException {

        godPanel = new JPanel();
        godPanel.setLayout(new GridLayout(godsInGame.size(),1));



        for(int i = 0; i<godsInGame.size(); i++){

            final int finalI = i;

            final JButton playerText = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/" + godsInGame.get(i).name().toLowerCase() + "Panel.png"))));
            playerText.setContentAreaFilled(false);
            playerText.setBorderPainted(false);
            playerText.setBorder(BorderFactory.createEmptyBorder());

            playerText.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {

                    Color color = selectPlayerColor(allPlayers.get(finalI).getTokenColor());
                    playerText.setBackground(color);
                    playerText.setOpaque(true);
                    if(color.equals(Color.BLUE) || color.equals(Color.RED))
                        playerText.setForeground(Color.WHITE);
                    else
                        playerText.setForeground(Color.BLACK);
                    playerText.setText(allPlayers.get(finalI).getUsername().toUpperCase() );

                }
                @Override
                public void mouseExited(MouseEvent e) {
                    playerText.setText(" ");
                }
            });

            playerText.addActionListener( new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    JDialog dialog = new JDialog();
                    dialog.setAlwaysOnTop(true);
                    try {
                        JOptionPane.showMessageDialog(dialog, godsInGame.get(finalI).name(),"Here is your God Power",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(ImageIO.read(getClass().getResource("/" + godsInGame.get(finalI).name().toLowerCase() + "Text.png"))) );
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });

            //playerText.add(transparentButton);
            godPanel.add(playerText);
        }

        return godPanel;
    }

    /**
     * Selects the right color to be the background of the player's
     * label, corresponding on the tokencolor of his tokens
     * @param tokenColor color of the token
     * @return the right @color
     */
    private Color selectPlayerColor(TokenColor tokenColor) {

        switch (tokenColor){
            case RED:{
                return Color.RED;
            }
            case BLUE:{
                return Color.BLUE;
            }
            case YELLOW:{
                return Color.YELLOW;
            }
            default:{
                System.err.println("Impossible color error");
                return Color.BLACK;
            }
        }
    }


    /*     GETTER     */

    public CellButton[][] getBattlefieldGUI() {
        return battlefieldGUI;
    }

    public JLabel getMessageLabel() {
        return messageLabel;
    }



}
