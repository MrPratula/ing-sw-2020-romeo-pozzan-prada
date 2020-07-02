package it.polimi.ingsw.gui;

import it.polimi.ingsw.model.GodCard;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.PlayerAction;
import it.polimi.ingsw.utils.ServerResponse;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Displays all the existing god available for a game,
 * and by passing the mouse over each one of them, it will be
 * displayed its power, in order to choose your favorite!
 */
public class ChooseGodCardToPlayWindow {

    /**
     * The player's swingview
     */
    final SwingView view;

    /**
     * List where save all of the goodcard
     */
    private final List<GodCard> selectedGods = new ArrayList<>();

    /**
     * The help to count how many god cards are selected
     */
    int n, cont;

    /**
     * creates the frame with all the god's images and powers
     * @param swingView gui view
     * @param serverResponse response of the server
     * @exception IOException if something goes wrong
     */
    public ChooseGodCardToPlayWindow(SwingView swingView, ServerResponse serverResponse) throws IOException {

        this.view = swingView;
        final SwingView view = swingView;
        List<GodCard> godInGame = serverResponse.getPack().getGodCards();
        n = serverResponse.getPack().getNumberOfPlayers();
        cont = n;

        final JFrame mainFrame = new JFrame("Gods");
        mainFrame.setIconImage(new ImageIcon(ImageIO.read(getClass().getResource(Pics.GODICON.getPath()))).getImage());

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                JOptionPane.showMessageDialog(mainFrame, "Select the godcards please", "You can't close this frame!",JOptionPane.INFORMATION_MESSAGE);
                mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        });

        GodButton[] buttonGod = new GodButton[14];
        ButtonGroup buttonGroup = new ButtonGroup();

        GodPanel mainPanel = new GodPanel();

        final List<ImageIcon> godsToDisplay = selectGodsToDisplay(godInGame);
        //godInGame.sort();
        final List<ImageIcon> textToDisplay = selectTextToDisplay(godInGame);

        for(int i=0; i<14; i++) {
            buttonGod[i] = new GodButton(godInGame.get(i));
            buttonGod[i].setBorderPainted(false);
            buttonGod[i].setContentAreaFilled(false);
            buttonGod[i].setIcon(godsToDisplay.get(i));
            buttonGod[i].setRolloverIcon(textToDisplay.get(i));

            buttonGod[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(cont!=0) {
                        GodButton selectedGod = (GodButton) e.getSource();
                        selectedGod.setBackground(Color.green);
                        if(!selectedGods.contains(selectedGod.getGodCard())) {
                            selectedGods.add(selectedGod.getGodCard());
                            cont--;
                            if(cont==0){
                                StringBuilder godStringToSplit = new StringBuilder();
                                for (GodCard g : selectedGods) {
                                    godStringToSplit.append(g.name().toUpperCase()).append(",");
                                }

                                try {
                                    new GodSelectedWindow(godStringToSplit.toString());

                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }

                                PlayerAction playerAction = new PlayerAction(Action.CHOSE_GOD_CARD, view.getPlayer(), null, null, 0, 0, null, null, false, godStringToSplit.toString());
                                try {
                                    view.notifyClient(playerAction);
                                    mainFrame.dispose();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                        else{
                            try {
                                JDialog jdialog = new JDialog();
                                jdialog.setAlwaysOnTop(true);
                                JOptionPane.showMessageDialog(jdialog,"God already selected!","Error",JOptionPane.ERROR_MESSAGE, new ImageIcon(ImageIO.read(getClass().getResource(Pics.ERRORICON.getPath()))));
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    }
                    //non dovrebbe mai finire qua
                    else{
                        try {
                            JDialog jdialog = new JDialog();
                            jdialog.setAlwaysOnTop(true);
                            JOptionPane.showMessageDialog(jdialog,"Something went wrong!","Error",JOptionPane.ERROR_MESSAGE, new ImageIcon(ImageIO.read(getClass().getResource(Pics.ERRORICON.getPath()))));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }

            });

            buttonGroup.add(buttonGod[i]);
            mainPanel.add(buttonGod[i]);
        }

        mainFrame.add(mainPanel,BorderLayout.CENTER);

        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);

        final JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(dialog, "Select the "+n+" GodCards for this game", "GodCard", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ImageIO.read(getClass().getResource(Pics.INFORMATIONICON.getPath()))));
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

