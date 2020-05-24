package it.polimi.ingsw.gui;

import it.polimi.ingsw.model.GodCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ChooseGodCardWindow extends JDialog {

    private JPanel mainPanel;
    private JButton buttonGod1, buttonGod2;

    String startPath = "./src/main/images/godcards/";

    ImageIcon[] godcards = new ImageIcon[]{
            new ImageIcon(new File(startPath + "apollo.png").getAbsolutePath()),  //0
            new ImageIcon(new File(startPath + "artemis.png").getAbsolutePath()),   //1
            new ImageIcon(new File(startPath + "athena.png").getAbsolutePath()),  //2
            new ImageIcon(new File(startPath + "atlas.png").getAbsolutePath()),  //3
            new ImageIcon(new File(startPath + "chronus.png").getAbsolutePath()), //4
            new ImageIcon(new File(startPath + "demeter.png").getAbsolutePath()),  //5
            new ImageIcon(new File(startPath + "hephaestus.png").getAbsolutePath()), //6
            new ImageIcon(new File(startPath + "hera.png").getAbsolutePath()), //7
            new ImageIcon(new File(startPath + "hestia.png").getAbsolutePath()),  //8
            new ImageIcon(new File(startPath + "limus.png").getAbsolutePath()),   //9
            new ImageIcon(new File(startPath + "minotaur.png").getAbsolutePath()),  //10
            new ImageIcon(new File(startPath + "pan.png").getAbsolutePath()),  //11
            new ImageIcon(new File(startPath + "prometheus.png").getAbsolutePath()), //12
            new ImageIcon(new File(startPath + "zeus.png").getAbsolutePath()), //13
            new ImageIcon(new File(startPath + "apolloText.png").getAbsolutePath()),  //+ 0
            new ImageIcon(new File(startPath + "artemisText.png").getAbsolutePath()),   //1
            new ImageIcon(new File(startPath + "athenaText.png").getAbsolutePath()),  //2
            new ImageIcon(new File(startPath + "atlasText.png").getAbsolutePath()),  //3
            new ImageIcon(new File(startPath + "chronusText.png").getAbsolutePath()), //4
            new ImageIcon(new File(startPath + "demeterText.png").getAbsolutePath()),  //5
            new ImageIcon(new File(startPath + "hephaestusText.png").getAbsolutePath()), //6
            new ImageIcon(new File(startPath + "heraText.png").getAbsolutePath()), //7
            new ImageIcon(new File(startPath + "hestiaText.png").getAbsolutePath()),  //8
            new ImageIcon(new File(startPath + "limusText.png").getAbsolutePath()),   //9
            new ImageIcon(new File(startPath + "minotaurText.png").getAbsolutePath()),  //10
            new ImageIcon(new File(startPath + "panText.png").getAbsolutePath()),  //11
            new ImageIcon(new File(startPath + "prometheusText.png").getAbsolutePath()), //12
            new ImageIcon(new File(startPath + "zeusText.png").getAbsolutePath()), //13
    };

    /**
     * Window that shows two buttons with two gods:
     * the user must choose one on them.
     * @param godInGame: gods to choose between
     */
    public ChooseGodCardWindow(List<GodCard> godInGame) {

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,2));

        List<ImageIcon> godsToDisplay = selectGodsToDisplay(godInGame);
        buttonGod1.setIcon(godsToDisplay.get(0));
        buttonGod2.setIcon(godsToDisplay.get(1));

        List<ImageIcon> textToDisplay = selectTextToDisplay(godInGame);
        buttonGod1.setRolloverIcon(textToDisplay.get(0));
        buttonGod2.setRolloverIcon(textToDisplay.get(1));

        buttonGod1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonGod2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
            }
        });

        mainPanel.add(buttonGod1);
        mainPanel.add(buttonGod2);

        pack();
        setVisible(true);
    }

    private List<ImageIcon> selectTextToDisplay(List<GodCard> godInGame) {

        List<ImageIcon> textToDisplay = new ArrayList<>();
        int i = 0;

        for(GodCard g : godInGame) {
            switch (g){
                case APOLLO: textToDisplay.set(i,godcards[14]);
                case ARTEMIS: textToDisplay.set(i,godcards[15]);
                case ATHENA:textToDisplay.set(i,godcards[16]);
                case ATLAS: textToDisplay.set(i,godcards[17]);
                case CHRONUS: {textToDisplay.set(i,godcards[18]);break;}
                case DEMETER: textToDisplay.set(i,godcards[19]);
                case HEPHAESTUS: textToDisplay.add(godcards[20]);
                case HERA: textToDisplay.set(i,godcards[21]);
                case HESTIA: textToDisplay.set(i,godcards[22]);
                case LIMUS: {textToDisplay.add(godcards[23]);break;}
                case MINOTAUR: textToDisplay.set(i,godcards[24]);
                case PAN: textToDisplay.set(i,godcards[25]);
                case PROMETHEUS: textToDisplay.set(i,godcards[26]);
                case ZEUS: textToDisplay.set(i,godcards[27]);
            }
            i++;
        }

        return textToDisplay;
    }


    /**
     * Selects the images of the 2 gods in the game
     * @param godInGame: current gods
     * @return images of the 2 gods of the game
     */
    private List<ImageIcon> selectGodsToDisplay(List<GodCard> godInGame) {

        List<ImageIcon> godsToDisplay = new ArrayList<>();
        int i = 0;

        for(GodCard g : godInGame) {
            switch (g){
                case APOLLO: godsToDisplay.set(i,godcards[0]);
                case ARTEMIS: godsToDisplay.set(i,godcards[1]);
                case ATHENA:godsToDisplay.set(i,godcards[2]);
                case ATLAS: godsToDisplay.set(i,godcards[3]);
                case CHRONUS:{ godsToDisplay.add(godcards[4]);break;}
                case DEMETER: godsToDisplay.set(i,godcards[5]);
                case HEPHAESTUS: godsToDisplay.set(i,godcards[6]);
                case HERA: godsToDisplay.set(i,godcards[7]);
                case HESTIA: godsToDisplay.set(i,godcards[8]);
                case LIMUS: {godsToDisplay.add(godcards[9]);break;}
                case MINOTAUR: godsToDisplay.set(i,godcards[10]);
                case PAN: godsToDisplay.set(i,godcards[11]);
                case PROMETHEUS: godsToDisplay.set(i,godcards[12]);
                case ZEUS: godsToDisplay.set(i,godcards[13]);
            }
            i++;
        }
        return godsToDisplay;
    }


}
