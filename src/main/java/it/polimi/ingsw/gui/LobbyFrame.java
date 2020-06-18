package it.polimi.ingsw.gui;

import javax.swing.*;
import java.awt.*;

public class LobbyFrame {

    JFrame lobbyFrame;
    JPanel mainPanel;
    JLabel info;

    /**
     * When a player set his name and number, ho goes to lobby
     * FIXME: da fare con solo il primo a aggiungerci gli altri
     */
    public LobbyFrame(String name){

        lobbyFrame = new JFrame("Lobby");
        lobbyFrame.setLocationRelativeTo(null);
        lobbyFrame.setSize(500,500);

        mainPanel = new JPanel();
        info = new JLabel(name);

        mainPanel.add(info);
        lobbyFrame.add(mainPanel);

        mainPanel.setBackground(Color.WHITE);
        lobbyFrame.setVisible(true);

    }

    /*
    public void join(Player player){

    }
    */
}
