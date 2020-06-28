package it.polimi.ingsw.gui;

import it.polimi.ingsw.model.GodCard;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.PlayerAction;
import it.polimi.ingsw.utils.ServerResponse;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChooseGodCardWindow extends JDialog {

    private final GodButton buttonGod1;
    private final GodButton buttonGod2;
    private final GodButton buttonGod3;
    private final SwingView view;



    /**
     * Window that shows two buttons with two gods:
     * the user must choose one on them.
     * @param swingView The player's swingview
     * @param serverResponse The current serverResponse
     * @exception IOException if something goes wrong
     */
    public ChooseGodCardWindow(final SwingView swingView, final ServerResponse serverResponse) throws IOException {

        this.view = swingView;
        List<GodCard> godInGame = serverResponse.getPack().getGodCards();

        final JFrame mainFrame = new JFrame("Which one of these GodCards do you want to use in this game?");
        mainFrame.setIconImage(new ImageIcon(ImageIO.read(getClass().getResource(Pics.GODICON.getPath()))).getImage());
        mainFrame.setPreferredSize(new Dimension(800,500));

        GodPanel mainPanel = new GodPanel(false);

        //just to initialise
        buttonGod3 = new GodButton(GodCard.MINOTAUR);

        if(godInGame.size()==2){
            mainPanel.setLayout(new GridLayout(1,2));
        }
        if(godInGame.size()==3){
            mainPanel.setLayout(new GridLayout(1,3));
        }

        buttonGod1 = new GodButton(godInGame.get(0));
        buttonGod2 = new GodButton(godInGame.get(1));
        if(godInGame.size()==3){
            buttonGod3.setGodCard(godInGame.get(2));
        }

        buttonGod1.setSize(400,700);
        buttonGod2.setSize(400,700);
        if(godInGame.size()==3){
            buttonGod3.setSize(400,700);
        }

        buttonGod1.setBorderPainted(false);
        buttonGod2.setBorderPainted(false);
        buttonGod1.setContentAreaFilled(false);
        buttonGod2.setContentAreaFilled(false);
        if(godInGame.size()==3){
            buttonGod3.setBorderPainted(false);
            buttonGod3.setContentAreaFilled(false);
        }

        final List<ImageIcon> godsToDisplay = selectGodsToDisplay(godInGame);
        buttonGod1.setIcon(godsToDisplay.get(0));
        buttonGod2.setIcon(godsToDisplay.get(1));
        if(godInGame.size()==3) {
            buttonGod3.setIcon(godsToDisplay.get(2));
        }

        List<ImageIcon> textToDisplay = selectTextToDisplay(godInGame);
        buttonGod1.setRolloverIcon(textToDisplay.get(0));
        buttonGod2.setRolloverIcon(textToDisplay.get(1));
        if(godInGame.size()==3) {
            buttonGod3.setRolloverIcon(textToDisplay.get(2));
        }

        buttonGod1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                PlayerAction playerAction = new PlayerAction(Action.CHOSE_GOD_CARD, view.getPlayer(), null, null, 0, 0, null, null, false,buttonGod1.getGodCard().name().toUpperCase());
                try {
                    final JDialog dialog = new JDialog();
                    dialog.setAlwaysOnTop(true);
                    JOptionPane.showMessageDialog(dialog,new ImageIcon(ImageIO.read(getClass().getResource("/" +((GodButton) e.getSource()).getGodCard().name().toLowerCase()+ ".png"))),"You Selected: "+((GodButton) e.getSource()).getGodCard().name(), JOptionPane.INFORMATION_MESSAGE,new ImageIcon(ImageIO.read(getClass().getResource(Pics.DONE.getPath()))));
                    swingView.notifyClient(playerAction);
                    mainFrame.dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                dispose();
            }
        });
        buttonGod2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                PlayerAction playerAction = new PlayerAction(Action.CHOSE_GOD_CARD, swingView.getPlayer(), null, null, 0, 0, null, null, false, buttonGod2.getGodCard().name());
                try {
                    final JDialog dialog = new JDialog();
                    dialog.setAlwaysOnTop(true);
                    JOptionPane.showMessageDialog(dialog,new ImageIcon(ImageIO.read(getClass().getResource("/" +((GodButton) e.getSource()).getGodCard().name().toLowerCase()+ ".png"))),"You Selected: "+((GodButton) e.getSource()).getGodCard().name(), JOptionPane.INFORMATION_MESSAGE,new ImageIcon(ImageIO.read(getClass().getResource(Pics.DONE.getPath()))));
                    swingView.notifyClient(playerAction);
                    mainFrame.dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                dispose();
            }
        });

        if(godInGame.size()==3) {
            buttonGod3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    PlayerAction playerAction = new PlayerAction(Action.CHOSE_GOD_CARD, swingView.getPlayer(), null, null, 0, 0, null, null, false, buttonGod3.getGodCard().name());
                    try {
                        final JDialog dialog = new JDialog();
                        dialog.setAlwaysOnTop(true);
                        JOptionPane.showMessageDialog(dialog,new ImageIcon(ImageIO.read(getClass().getResource("/" +((GodButton) e.getSource()).getGodCard().name().toLowerCase()+ ".png"))),"You Selected: "+((GodButton) e.getSource()).getGodCard().name(), JOptionPane.INFORMATION_MESSAGE,new ImageIcon(ImageIO.read(getClass().getResource(Pics.DONE.getPath()))));
                        swingView.notifyClient(playerAction);
                        mainFrame.dispose();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    dispose();
                }
            });
        }

        mainPanel.add(buttonGod1);
        mainPanel.add(buttonGod2);
        if(godInGame.size()==3) {
            mainPanel.add(buttonGod3);
        }

        mainFrame.add(mainPanel,BorderLayout.CENTER);

        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);
    }

    /**
     * Selects the text of the effect of the 2/3 gods in the game,
     * to be displayed when the mouse pass on the button
     * @param godInGame: current gods in game
     * @return power of the 2/3 gods of the game in text format
     */
    private List<ImageIcon> selectTextToDisplay(List<GodCard> godInGame) throws IOException {

        List<ImageIcon> textToDisplay = new ArrayList<>();

        for(GodCard g : godInGame) {
            switch (g){
                case APOLLO:{
                    textToDisplay.add(new ImageIcon(ImageIO.read(getClass().getResource(Pics.APOLLOTEXT.getPath()))));
                    break;
                }
                case ARTEMIS:{
                    textToDisplay.add(new ImageIcon(ImageIO.read(getClass().getResource(Pics.ARTEMISTEXT.getPath()))));
                    break;
                }
                case ATHENA:{
                    textToDisplay.add(new ImageIcon(ImageIO.read(getClass().getResource(Pics.ATHENATEXT.getPath()))));
                    break;
                }
                case ATLAS:{
                    textToDisplay.add(new ImageIcon(ImageIO.read(getClass().getResource(Pics.ATLASTEXT.getPath()))));
                    break;
                }
                case CHRONUS:{
                    textToDisplay.add(new ImageIcon(ImageIO.read(getClass().getResource(Pics.CHRONUSTEXT.getPath()))));
                    break;
                }
                case DEMETER:{
                    textToDisplay.add(new ImageIcon(ImageIO.read(getClass().getResource(Pics.DEMETERTEXT.getPath()))));
                    break;
                }
                case HEPHAESTUS:{
                    textToDisplay.add(new ImageIcon(ImageIO.read(getClass().getResource(Pics.HEPHAESTUSTEXT.getPath()))));
                    break;
                }
                case HERA:{
                    textToDisplay.add(new ImageIcon(ImageIO.read(getClass().getResource(Pics.HERATEXT.getPath()))));
                    break;
                }
                case HESTIA:{
                    textToDisplay.add(new ImageIcon(ImageIO.read(getClass().getResource(Pics.HESTIATEXT.getPath()))));
                    break;
                }
                case LIMUS: {
                    textToDisplay.add(new ImageIcon(ImageIO.read(getClass().getResource(Pics.LIMUSTEXT.getPath()))));
                    break;
                }
                case MINOTAUR:{
                    textToDisplay.add(new ImageIcon(ImageIO.read(getClass().getResource(Pics.MINOTAURTEXT.getPath()))));
                    break;
                }
                case PAN:{
                    textToDisplay.add(new ImageIcon(ImageIO.read(getClass().getResource(Pics.PANTEXT.getPath()))));
                    break;
                }
                case PROMETHEUS:{
                    textToDisplay.add(new ImageIcon(ImageIO.read(getClass().getResource(Pics.PROMETHEUSTEXT.getPath()))));
                    break;
                }
                case ZEUS:{
                    textToDisplay.add(new ImageIcon(ImageIO.read(getClass().getResource(Pics.ZEUSTEXT.getPath()))));
                    break;
                }
            }
        }
        return textToDisplay;
    }


    /**
     * Selects the images of the 2/3 gods in the game
     * @param godInGame: current gods in game
     * @return images of the 2/3 gods of the game
     */
    private List<ImageIcon> selectGodsToDisplay(List<GodCard> godInGame) throws IOException {

        List<ImageIcon> godsToDisplay = new ArrayList<>();

        for(GodCard g : godInGame) {
            switch (g){
                case APOLLO:{
                    godsToDisplay.add(new ImageIcon(ImageIO.read(getClass().getResource(Pics.APOLLO.getPath()))));
                    break;
                }
                case ARTEMIS:{
                    godsToDisplay.add(new ImageIcon(ImageIO.read(getClass().getResource(Pics.ARTEMIS.getPath()))));
                    break;
                }
                case ATHENA:{
                    godsToDisplay.add(new ImageIcon(ImageIO.read(getClass().getResource(Pics.ATHENA.getPath()))));
                    break;
                }
                case ATLAS:{
                    godsToDisplay.add(new ImageIcon(ImageIO.read(getClass().getResource(Pics.ATLAS.getPath()))));
                    break;
                }
                case CHRONUS:{
                    godsToDisplay.add(new ImageIcon(ImageIO.read(getClass().getResource(Pics.CHRONUS.getPath()))));
                    break;
                }
                case DEMETER:{
                    godsToDisplay.add(new ImageIcon(ImageIO.read(getClass().getResource(Pics.DEMETER.getPath()))));
                    break;
                }
                case HEPHAESTUS:{
                    godsToDisplay.add(new ImageIcon(ImageIO.read(getClass().getResource(Pics.HEPHAESTUS.getPath()))));
                    break;
                }
                case HERA:{
                    godsToDisplay.add(new ImageIcon(ImageIO.read(getClass().getResource(Pics.HERA.getPath()))));
                    break;
                }
                case HESTIA:{
                    godsToDisplay.add(new ImageIcon(ImageIO.read(getClass().getResource(Pics.HESTIA.getPath()))));
                    break;
                }
                case LIMUS: {
                    godsToDisplay.add(new ImageIcon(ImageIO.read(getClass().getResource(Pics.LIMUS.getPath()))));
                    break;
                }
                case MINOTAUR:{
                    godsToDisplay.add(new ImageIcon(ImageIO.read(getClass().getResource(Pics.MINOTAUR.getPath()))));
                    break;
                }
                case PAN:{
                    godsToDisplay.add(new ImageIcon(ImageIO.read(getClass().getResource(Pics.PAN.getPath()))));
                    break;
                }
                case PROMETHEUS:{
                    godsToDisplay.add(new ImageIcon(ImageIO.read(getClass().getResource(Pics.PROMETHEUS.getPath()))));
                    break;
                }
                case ZEUS:{
                    godsToDisplay.add(new ImageIcon(ImageIO.read(getClass().getResource(Pics.ZEUS.getPath()))));
                    break;
                }
            }
        }
        return godsToDisplay;
    }



}

