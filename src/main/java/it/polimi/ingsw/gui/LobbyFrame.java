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
     * @param mainFrame
     */
    public LobbyFrame(JFrame mainFrame){
        lobbyFrame = new JFrame("Lobby");
        lobbyFrame.setSize(500,500);
        mainPanel = new JPanel();
        info = new JLabel("Wait for other players to connect");

        mainPanel.add(info);
        lobbyFrame.add(mainPanel);

        mainPanel.setBackground(Color.WHITE);
        lobbyFrame.setVisible(true);

    }

}
