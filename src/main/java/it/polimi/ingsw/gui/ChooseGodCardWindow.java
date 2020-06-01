package it.polimi.ingsw.gui;

import it.polimi.ingsw.cli.GodCard;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.PlayerAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChooseGodCardWindow extends JDialog {

    final static String startPath = "./src/main/images/godcards/";
    private final JPanel mainPanel;
    private final GodButton buttonGod1, buttonGod2;
    private ButtonGroup buttonGroup;
    private SwingView view;


    /*       GETTER       */

    public JButton getButtonGod1() {
        return buttonGod1;
    }

    public JButton getButtonGod2() {
        return buttonGod2;
    }

    public ButtonGroup getButtonGroup() {
        return buttonGroup;
    }

    /**
     * Window that shows two buttons with two gods:
     * the user must choose one on them.
     * @param godInGame: gods to choose between
     */
    public ChooseGodCardWindow(final JFrame mainFrame, final SwingView swingView, List<GodCard> godInGame) {

        this.view = swingView;

        setTitle("Which one of these GodCards do you want to use in this game?");

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,2));

        buttonGroup = new ButtonGroup();
        buttonGod1 = new GodButton(godInGame.get(0));
        buttonGod2 = new GodButton(godInGame.get(1));
        buttonGod1.setSize(400,700);
        buttonGod2.setSize(400,700);
        buttonGod1.setBorderPainted(false);
        buttonGod2.setBorderPainted(false);
        buttonGod1.setContentAreaFilled(false);
        buttonGod2.setContentAreaFilled(false);
        buttonGroup.add(buttonGod1);
        buttonGroup.add(buttonGod2);

        final List<ImageIcon> godsToDisplay = selectGodsToDisplay(godInGame);
        buttonGod1.setIcon(godsToDisplay.get(0));
        buttonGod2.setIcon(godsToDisplay.get(1));

        List<ImageIcon> textToDisplay = selectTextToDisplay(godInGame);
        buttonGod1.setRolloverIcon(textToDisplay.get(0));
        buttonGod2.setRolloverIcon(textToDisplay.get(1));

        buttonGod1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //buttonGod1.setActionCommand(buttonGod1.getGodCard().name());
                PlayerAction playerAction = new PlayerAction(Action.CHOSE_GOD_CARD, ChooseGodCardWindow.this.view.getPlayer(), null, null, 0, 0, null, null, false,buttonGod1.getGodCard().name());
                try {
                    JOptionPane.showMessageDialog(mainFrame,new ImageIcon(new File(startPath +((GodButton) e.getSource()).getGodCard().name().toLowerCase()+ ".png").getAbsolutePath()),"You Selected: "+((GodButton) e.getSource()).getGodCard().name(), JOptionPane.INFORMATION_MESSAGE,new ImageIcon(new File("./src/main/images/utils/done.png").getAbsolutePath()));
                    swingView.notifyClient(playerAction);
                } catch (CellOutOfBattlefieldException | ReachHeightLimitException | CellHeightException | IOException | ImpossibleTurnException | WrongNumberPlayerException cellOutOfBattlefieldException) {
                    cellOutOfBattlefieldException.printStackTrace();
                }

                dispose();
            }
        });
        buttonGod2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //buttonGod2.setActionCommand(buttonGod1.getGodCard().name());
                PlayerAction playerAction = new PlayerAction(Action.CHOSE_GOD_CARD, ChooseGodCardWindow.this.view.getPlayer(), null, null, 0, 0, null, null, false,buttonGod2.getGodCard().name());
                try {
                    JOptionPane.showMessageDialog(mainFrame,new ImageIcon(new File(startPath +((GodButton) e.getSource()).getGodCard().name().toLowerCase()+ ".png").getAbsolutePath()),"You Selected: "+((GodButton) e.getSource()).getGodCard().name(), JOptionPane.INFORMATION_MESSAGE,new ImageIcon(new File("./src/main/images/utils/done.png").getAbsolutePath()));
                    swingView.notifyClient(playerAction);
                } catch (CellOutOfBattlefieldException | ReachHeightLimitException | CellHeightException | IOException | ImpossibleTurnException | WrongNumberPlayerException cellOutOfBattlefieldException) {
                    cellOutOfBattlefieldException.printStackTrace();
                }

                dispose();
            }
        });

        mainPanel.add(buttonGod1);
        mainPanel.add(buttonGod2);
        add(mainPanel,BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    /**
     * Selects the text of the effect of the 2 gods in the game,
     * to be displayed when the mouse pass on the button
     * @param godInGame: current gods in game
     * @return power of the 2 gods of the game in text format
     */
    private List<ImageIcon> selectTextToDisplay(List<GodCard> godInGame) {

        List<ImageIcon> textToDisplay = new ArrayList<>();

        for(GodCard g : godInGame) {
            switch (g){
                case APOLLO:{ textToDisplay.add(Pics.APOLLOTEXT.getImage()); break;}
                case ARTEMIS:{ textToDisplay.add(Pics.ARTEMISTEXT.getImage()); break;}
                case ATHENA:{ textToDisplay.add(Pics.ATHENATEXT.getImage()); break;}
                case ATLAS:{ textToDisplay.add(Pics.ATLASTEXT.getImage()); break;}
                case CHRONUS:{ textToDisplay.add(Pics.CHRONUSTEXT.getImage()); break;}
                case DEMETER:{ textToDisplay.add(Pics.DEMETERTEXT.getImage()); break;}
                case HEPHAESTUS:{ textToDisplay.add(Pics.HEPHAESTUSTEXT.getImage()); break;}
                case HERA:{ textToDisplay.add(Pics.HERATEXT.getImage()); break;}
                case HESTIA:{ textToDisplay.add(Pics.HESTIATEXT.getImage()); break;}
                case LIMUS: {textToDisplay.add(Pics.LIMUSTEXT.getImage());break;}
                case MINOTAUR:{ textToDisplay.add(Pics.MINOTAURTEXT.getImage()); break;}
                case PAN:{ textToDisplay.add(Pics.PANTEXT.getImage()); break;}
                case PROMETHEUS:{ textToDisplay.add(Pics.PROMETHEUSTEXT.getImage()); break;}
                case ZEUS:{
                    textToDisplay.add(Pics.ZEUSTEXT.getImage());
                    break;
                }
            }
        }
        return textToDisplay;
    }


    /**
     * Selects the images of the 2 gods in the game
     * @param godInGame: current gods in game
     * @return images of the 2 gods of the game
     */
    private List<ImageIcon> selectGodsToDisplay(List<GodCard> godInGame) {

        List<ImageIcon> godsToDisplay = new ArrayList<>();

        for(GodCard g : godInGame) {
            switch (g){
                case APOLLO:{ godsToDisplay.add(Pics.APOLLO.getImage()); break;}
                case ARTEMIS:{ godsToDisplay.add(Pics.ARTEMIS.getImage()); break;}
                case ATHENA:{ godsToDisplay.add(Pics.ATHENA.getImage()); break;}
                case ATLAS:{ godsToDisplay.add(Pics.ATLAS.getImage()); break;}
                case CHRONUS:{ godsToDisplay.add(Pics.CHRONUS.getImage()); break;}
                case DEMETER:{ godsToDisplay.add(Pics.DEMETER.getImage()); break;}
                case HEPHAESTUS:{ godsToDisplay.add(Pics.HEPHAESTUS.getImage()); break;}
                case HERA:{ godsToDisplay.add(Pics.HERA.getImage()); break;}
                case HESTIA:{ godsToDisplay.add(Pics.HESTIA.getImage()); break;}
                case LIMUS: {godsToDisplay.add(Pics.LIMUS.getImage());break;}
                case MINOTAUR:{ godsToDisplay.add(Pics.MINOTAUR.getImage()); break;}
                case PAN:{ godsToDisplay.add(Pics.PAN.getImage()); break;}
                case PROMETHEUS:{ godsToDisplay.add(Pics.PROMETHEUS.getImage()); break;}
                case ZEUS:{ godsToDisplay.add(Pics.ZEUS.getImage()); break;}
            }
        }
        return godsToDisplay;
    }


}
