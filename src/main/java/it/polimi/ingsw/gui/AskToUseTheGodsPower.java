package it.polimi.ingsw.gui;

import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.utils.ServerResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AskToUseTheGodsPower extends JDialog{

    private final SwingView view;
    private JFrame mainframe;

    /**
     * JDialog that asks if the player wants to use his god's power
     * @param swingView
     * @param serverResponse
     */
    public AskToUseTheGodsPower(SwingView swingView, final ServerResponse serverResponse, final JFrame mainframe){

        this.view = swingView;
        this.mainframe = mainframe;

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
                    view.displayGui(view.getBattlefieldGUI(),serverResponse.getPack().getModelCopy(), serverResponse.getPack().getValidBuilds());
                    dispose();
                } catch (CellOutOfBattlefieldException ex) {
                    ex.printStackTrace();
                }
                //new GameFrame(serverResponse,view);
                //dispose(mainframe.getInnerMainPanel().getBattlefieldGUI());
            }
        });
        no_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    view.setWantToUsePower(false);
                    view.displayGui(view.getBattlefieldGUI(),serverResponse.getPack().getModelCopy(), serverResponse.getPack().getValidBuilds());
                    dispose();
                } catch (CellOutOfBattlefieldException ex) {
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
