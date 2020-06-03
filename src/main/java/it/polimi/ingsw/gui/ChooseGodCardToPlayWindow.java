package it.polimi.ingsw.gui;

import it.polimi.ingsw.cli.GodCard;
import it.polimi.ingsw.cli.Model;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.PlayerAction;
import it.polimi.ingsw.utils.ServerResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ChooseGodCardToPlayWindow {
    SwingView swingView;

    List<GodCard> godInGame;
    List<GodCard> selectedGods = new ArrayList<>();

    int n;
    int cont;

    public ChooseGodCardToPlayWindow(final SwingView swingView, ServerResponse serverResponse) {

        this.swingView = swingView;
        godInGame = serverResponse.getPack().getGodCards();
        n= serverResponse.getPack().getNumberOfPlayers();
        cont=n;

        List<GodCard> godInGame = serverResponse.getPack().getGodCards();

        final JFrame mainFrame = new JFrame("Gods");

        GodButton[] buttonGod = new GodButton[14];
        ButtonGroup buttonGroup = new ButtonGroup();

        JPanel mainPanel = new JPanel(new GridLayout(2,7));

        final List<ImageIcon> godsToDisplay = selectGodsToDisplay(godInGame);
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
                        if(!selectedGods.contains(selectedGod.getGodCard())) {
                            selectedGods.add(selectedGod.getGodCard());
                            cont--;
                            if(cont==0){
                                StringBuilder godStringToSplit = new StringBuilder();
                                for (GodCard g : selectedGods) {
                                    godStringToSplit.append(g.name().toUpperCase()).append(" ");
                                }
                                //inutile swingView.setGodCardsForTheGame(godStringToSplit.toString());
                                JOptionPane.showMessageDialog(new JFrame(), "Ok! Thanks for the selections. The GodCards in game will be: "+godStringToSplit, "Selection confirmed", JOptionPane.INFORMATION_MESSAGE);
                                //System.out.println(godStringToSplit);
                                PlayerAction playerAction = new PlayerAction(Action.CHOSE_GOD_CARD, null, null, null, 0, 0, null, null, false, godStringToSplit.toString());
                                try {
                                    swingView.notifyClient(playerAction);
                                    mainFrame.dispose();
                                } catch (CellOutOfBattlefieldException | ReachHeightLimitException | CellHeightException | IOException | WrongNumberPlayerException | ImpossibleTurnException cellOutOfBattlefieldException) {
                                    cellOutOfBattlefieldException.printStackTrace();
                                }
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(new JFrame(),"God already selected!","Error",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    //non dovrebbe mai finire qua
                    else{
                        JOptionPane.showMessageDialog(new JFrame(),"Something went wrong!","Error",JOptionPane.ERROR_MESSAGE);
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

        //Frame che dice al giocatore cosa fare (cioè che dovrà scegliere 2 o 3 god)
        JOptionPane.showMessageDialog(new JFrame(), "Select the "+n+" GodCards for this game", "GodCard", JOptionPane.INFORMATION_MESSAGE);
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
                case APOLLO:{ textToDisplay.add(Pics.APOLLOTEXT.getImageIcon()); break;}
                case ARTEMIS:{ textToDisplay.add(Pics.ARTEMISTEXT.getImageIcon()); break;}
                case ATHENA:{ textToDisplay.add(Pics.ATHENATEXT.getImageIcon()); break;}
                case ATLAS:{ textToDisplay.add(Pics.ATLASTEXT.getImageIcon()); break;}
                case CHRONUS:{ textToDisplay.add(Pics.CHRONUSTEXT.getImageIcon()); break;}
                case DEMETER:{ textToDisplay.add(Pics.DEMETERTEXT.getImageIcon()); break;}
                case HEPHAESTUS:{ textToDisplay.add(Pics.HEPHAESTUSTEXT.getImageIcon()); break;}
                case HERA:{ textToDisplay.add(Pics.HERATEXT.getImageIcon()); break;}
                case HESTIA:{ textToDisplay.add(Pics.HESTIATEXT.getImageIcon()); break;}
                case LIMUS: {textToDisplay.add(Pics.LIMUSTEXT.getImageIcon());break;}
                case MINOTAUR:{ textToDisplay.add(Pics.MINOTAURTEXT.getImageIcon()); break;}
                case PAN:{ textToDisplay.add(Pics.PANTEXT.getImageIcon()); break;}
                case PROMETHEUS:{ textToDisplay.add(Pics.PROMETHEUSTEXT.getImageIcon()); break;}
                case ZEUS:{
                    textToDisplay.add(Pics.ZEUSTEXT.getImageIcon());
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
                case APOLLO:{ godsToDisplay.add(Pics.APOLLO.getImageIcon()); break;}
                case ARTEMIS:{ godsToDisplay.add(Pics.ARTEMIS.getImageIcon()); break;}
                case ATHENA:{ godsToDisplay.add(Pics.ATHENA.getImageIcon()); break;}
                case ATLAS:{ godsToDisplay.add(Pics.ATLAS.getImageIcon()); break;}
                case CHRONUS:{ godsToDisplay.add(Pics.CHRONUS.getImageIcon()); break;}
                case DEMETER:{ godsToDisplay.add(Pics.DEMETER.getImageIcon()); break;}
                case HEPHAESTUS:{ godsToDisplay.add(Pics.HEPHAESTUS.getImageIcon()); break;}
                case HERA:{ godsToDisplay.add(Pics.HERA.getImageIcon()); break;}
                case HESTIA:{ godsToDisplay.add(Pics.HESTIA.getImageIcon()); break;}
                case LIMUS: {godsToDisplay.add(Pics.LIMUS.getImageIcon());break;}
                case MINOTAUR:{ godsToDisplay.add(Pics.MINOTAUR.getImageIcon()); break;}
                case PAN:{ godsToDisplay.add(Pics.PAN.getImageIcon()); break;}
                case PROMETHEUS:{ godsToDisplay.add(Pics.PROMETHEUS.getImageIcon()); break;}
                case ZEUS:{ godsToDisplay.add(Pics.ZEUS.getImageIcon()); break;}
            }
        }
        return godsToDisplay;
    }

}

