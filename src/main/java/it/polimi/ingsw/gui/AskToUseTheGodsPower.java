package it.polimi.ingsw.gui;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.PlayerAction;
import it.polimi.ingsw.utils.ServerResponse;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AskToUseTheGodsPower extends JDialog{

    private final SwingView view;


    /**
     * JDialog that asks if the player wantw to use his god's power
     * @param swingView gui view
     * @param serverResponse response of the server
     * @param targetCell cell selected
     */
    public AskToUseTheGodsPower(final SwingView swingView, final ServerResponse serverResponse, final Cell targetCell) throws IOException {

        this.view = swingView;

        setTitle("GOD'S POWER");
        setLocationRelativeTo(null);
        setResizable(false);
        setIconImage(new ImageIcon(ImageIO.read(getClass().getResource(Pics.PLAYERICON.getPath()))).getImage());
        setPreferredSize(new Dimension(600,300));

        PowerPanel panel = new PowerPanel();

        JButton yes_button = new JButton("YES!");
        JButton no_button = new JButton("NO!");
        yes_button.setBorderPainted(false);
        yes_button.setContentAreaFilled(false);
        no_button.setBorderPainted(false);
        no_button.setContentAreaFilled(false);

        yes_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    view.setWantToUsePower(true);
                    view.displayGui(view.getBattlefieldGUI(),serverResponse.getPack().getModelCopy(), swingView.getCurrentValidBuilds());
                    swingView.buildGod(serverResponse.getPack(), targetCell);
                    dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        no_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PlayerAction playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, view.getPlayer(), null, null, view.getSavedToken(), 0, targetCell, null, false, null);
                    view.notifyClient(playerAction);
                    dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        panel.add(yes_button);
        panel.add(no_button);
        add(panel,BorderLayout.CENTER);

        pack();
        setVisible(true);
    }


}

