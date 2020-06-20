package it.polimi.ingsw.gui;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.PlayerAction;
import it.polimi.ingsw.utils.ServerResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AskToUseTheGodsPower extends JDialog{

    private final SwingView view;
    Cell targetcell;

    /**
     * JDialog that asks if the player wants to use his god's power
     * @param swingView
     * @param serverResponse
     */
    public AskToUseTheGodsPower(final SwingView swingView, final ServerResponse serverResponse, final Cell targetcell){

        this.view = swingView;
        this.targetcell = targetcell;

        setTitle("GOD'S POWER");
        setLocationRelativeTo(null);
        setResizable(false);
        setIconImage(Pics.PLAYERICON.getImageIcon().getImage());
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
                    swingView.buildGod(serverResponse.getPack(),targetcell);
                    dispose();
                } catch (CellOutOfBattlefieldException | IOException | WrongNumberPlayerException | CellHeightException | ImpossibleTurnException | ReachHeightLimitException ex) {
                    ex.printStackTrace();
                }
            }
        });
        no_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PlayerAction playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, view.getPlayer(), null, null, view.getSavedToken(), 0, targetcell, null, false, null);
                    view.notifyClient(playerAction);
                    dispose();
                } catch (CellOutOfBattlefieldException | IOException | WrongNumberPlayerException | ReachHeightLimitException | CellHeightException | ImpossibleTurnException ex) {
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
