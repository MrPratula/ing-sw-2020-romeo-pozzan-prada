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
    private ButtonGroup buttonGroup;

    final static String startPath = "./src/main/images/godcards/";

    private final ImageIcon[] godcards = new ImageIcon[]{
        new ImageIcon(new File(startPath + "apollo.png").getAbsolutePath()),  //0
        new ImageIcon(new File(startPath + "artemis.png").getAbsolutePath()),   //1
        new ImageIcon(new File(startPath + "athena.png").getAbsolutePath()),  //2
        new ImageIcon(new File(startPath + "atlas.png").getAbsolutePath()),  //3
        new ImageIcon(new File(startPath + "chronuss.png").getAbsolutePath()), //4
        new ImageIcon(new File(startPath + "demeter.png").getAbsolutePath()),  //5
        new ImageIcon(new File(startPath + "hephaestus.png").getAbsolutePath()), //6
        new ImageIcon(new File(startPath + "hera.png").getAbsolutePath()), //7
        new ImageIcon(new File(startPath + "hestia.png").getAbsolutePath()),  //8
        new ImageIcon(new File(startPath + "limuss.png").getAbsolutePath()),   //9
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


    /*       GETTER       */

    public ButtonGroup getButtonGroup() {
        return buttonGroup;
    }

    public JButton getButtonGod1() {
        return buttonGod1;
    }

    public JButton getButtonGod2() {
        return buttonGod2;
    }


    /**
     * Window that shows two buttons with two gods:
     * the user must choose one on them.
     * @param godInGame: gods to choose between
     */
    public ChooseGodCardWindow(final JFrame mainFrame, List<GodCard> godInGame) {

        setTitle("Choose your GodCard for the game");

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,2));

        buttonGroup = new ButtonGroup();
        buttonGod1 = new JButton();
        buttonGod2 = new JButton();
        buttonGod1.setSize(400,700);
        buttonGod2.setSize(400,700);
        buttonGod1.setBorderPainted(false);
        buttonGod2.setBorderPainted(false);
        buttonGod1.setContentAreaFilled(false);
        buttonGod2.setContentAreaFilled(false);
        buttonGroup.add(buttonGod1);
        buttonGroup.add(buttonGod2);

        List<ImageIcon> godsToDisplay = selectGodsToDisplay(godInGame);
        buttonGod1.setIcon(godsToDisplay.get(0));
        buttonGod1.setActionCommand("You selected "+ godsToDisplay.get(0).getDescription());
        buttonGod2.setIcon(godsToDisplay.get(1));

        List<ImageIcon> textToDisplay = selectTextToDisplay(godInGame);
        buttonGod1.setRolloverIcon(textToDisplay.get(0));
        buttonGod2.setRolloverIcon(textToDisplay.get(1));

        buttonGod1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainFrame,e.getSource(),"You Selected: ",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(new File("./src/main/images/utils/done.png").getAbsolutePath()));
                dispose();
            }
        });
        buttonGod2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainFrame,e.getSource(),"You Selected: ",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(new File("./src/main/images/utils/done.png").getAbsolutePath()));
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
                case APOLLO:{ textToDisplay.add(godcards[14]); break;}
                case ARTEMIS:{ textToDisplay.add(godcards[15]); break;}
                case ATHENA:{ textToDisplay.add(godcards[16]); break;}
                case ATLAS:{ textToDisplay.add(godcards[17]); break;}
                case CHRONUS:{ textToDisplay.add(godcards[18]); break;}
                case DEMETER:{ textToDisplay.add(godcards[19]); break;}
                case HEPHAESTUS:{ textToDisplay.add(godcards[20]); break;}
                case HERA:{ textToDisplay.add(godcards[21]); break;}
                case HESTIA:{ textToDisplay.add(godcards[22]); break;}
                case LIMUS: {textToDisplay.add(godcards[23]);break;}
                case MINOTAUR:{ textToDisplay.add(godcards[24]); break;}
                case PAN:{ textToDisplay.add(godcards[25]); break;}
                case PROMETHEUS:{ textToDisplay.add(godcards[26]); break;}
                case ZEUS:{ textToDisplay.add(godcards[27]); break;}
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
                case APOLLO:{ godsToDisplay.add(godcards[0]); break;}
                case ARTEMIS:{ godsToDisplay.add(godcards[1]); break;}
                case ATHENA:{godsToDisplay.add(godcards[2]); break;}
                case ATLAS:{ godsToDisplay.add(godcards[3]); break;}
                case CHRONUS:{ godsToDisplay.add(godcards[4]); break;}
                case DEMETER:{ godsToDisplay.add(godcards[5]); break;}
                case HEPHAESTUS:{ godsToDisplay.add(godcards[6]); break;}
                case HERA:{ godsToDisplay.add(godcards[7]); break;}
                case HESTIA:{ godsToDisplay.add(godcards[8]); break;}
                case LIMUS: {godsToDisplay.add(godcards[9]);break;}
                case MINOTAUR:{ godsToDisplay.add(godcards[10]); break;}
                case PAN: {godsToDisplay.add(godcards[11]); break;}
                case PROMETHEUS:{ godsToDisplay.add(godcards[12]); break;}
                case ZEUS: {godsToDisplay.add(godcards[13]); break;}
            }
        }
        return godsToDisplay;
    }


}
