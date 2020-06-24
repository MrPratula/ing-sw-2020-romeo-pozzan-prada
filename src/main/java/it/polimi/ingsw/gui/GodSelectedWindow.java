package it.polimi.ingsw.gui;

import it.polimi.ingsw.model.GodCard;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * It displays a nice graphic of the selected gods for the game
 */
public class GodSelectedWindow extends JFrame{


    public GodSelectedWindow(String s) {

        setTitle("These are the GodCards for the game:");
        setSize(600,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(Pics.PLAYERICON.getImageIcon().getImage());

        List<GodCard> godsInGame = recognizeGodInGame(s);

        JPanel selectedGodsPanel = new JPanel();
        selectedGodsPanel.setLayout(new GridLayout(1, godsInGame.size()));

        for (GodCard godCard : godsInGame) {
            final JLabel playerText = new JLabel(new ImageIcon(new File("./src/main/images/godcards/" + godCard.name().toLowerCase() + "Panel.png").getAbsolutePath()));
            selectedGodsPanel.add(playerText);
        }

        ConfirmButton c = new ConfirmButton("OK");
        c.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GodSelectedWindow.this.dispose();
            }
        });

        add(selectedGodsPanel);
        add(c, BorderLayout.PAGE_END);

        pack();
        setVisible(true);
    }


    /**
     * Links a string of god names separeted by spaces
     * into the real GodCards corresponding to them
     * @param godCardsForTheGame: String of god names
     * @return GodCards in game
     */
    private List<GodCard> recognizeGodInGame(String godCardsForTheGame) {

        List<GodCard> godsSeparated = new ArrayList<>();
        String[] godNamesInGame = godCardsForTheGame.split(",");
        GodCard[] allGods = GodCard.values();
        int i = 0, j = 0;
        while(i < godNamesInGame.length){
            while(!godNamesInGame[i].toUpperCase().equals(allGods[j].name().toUpperCase())){
                j++;
            }
            godsSeparated.add(allGods[j]);
            i++;
            j = 0;
        }
        return godsSeparated;
    }
}
