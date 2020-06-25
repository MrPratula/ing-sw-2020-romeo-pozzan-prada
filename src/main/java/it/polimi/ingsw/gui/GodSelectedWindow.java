package it.polimi.ingsw.gui;

import it.polimi.ingsw.model.GodCard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * It displays a nice graphic of the selected gods for the game
 */
public class GodSelectedWindow extends JFrame{


    public GodSelectedWindow(String s) throws IOException {

        setTitle("These are the GodCards for the game:");
        setSize(600,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(ImageIO.read(getClass().getResource(Pics.PLAYERICON.getPath()))).getImage());

        List<GodCard> godsInGame = recognizeGodInGame(s);

        GodPanel selectedGodsPanel = new GodPanel(true);
        selectedGodsPanel.setLayout(new GridLayout(1, godsInGame.size()));

        for (GodCard godCard : godsInGame) {
            final JLabel playerText = new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("/" + godCard.name().toLowerCase() + "Panel.png"))));
            selectedGodsPanel.add(playerText);
        }

        ConfirmButton c = new ConfirmButton();
        c.setIcon(new ImageIcon(ImageIO.read(getClass().getResource(Pics.BUTTON.getPath()))));
        c.setBounds(10,20,80,25);
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
     * Links a string of god names separeted by commas
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
