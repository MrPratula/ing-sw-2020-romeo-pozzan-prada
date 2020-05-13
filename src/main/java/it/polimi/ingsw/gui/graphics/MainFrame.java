package it.polimi.ingsw.gui.graphics;

import it.polimi.ingsw.client.SetUpDialog;
import it.polimi.ingsw.gui.SwingView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {


    private JPanel mainPanel = new JPanel();        //2

    private JLabel logoImage = new JLabel();        //2.1

    private JLabel startGameLabel = new JLabel();

    private JButton playButton = new JButton();      //3.1

    public MainFrame() {
        super("Santorini");
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);

        //mainPanel.setLayout();
        mainPanel.setSize(400, 400);
        logoImage.setIcon(new ImageIcon("C:\\Users\\feder\\OneDrive - Politecnico di Milano\\POLIMI\\IngSw\\risorse_grafiche\\used\\Santorini.png"));
        logoImage.setBounds(5,5,400,400);
        //TODO mainPanel.add(logoImage, BorderLayout.CENTER);

        //startGamePanel.setBorderLayout(10, 10));

        //ImageIcon buttonPlay = new ImageIcon("ButtonPlay.png");
        playButton.setSize(100, 100);
        playButton.setIcon(new ImageIcon("C:\\Users\\feder\\OneDrive - Politecnico di Milano\\POLIMI\\IngSw\\risorse_grafiche\\used\\button-play-normal.png"));
        startGameLabel.add(playButton);

        mainPanel.add(startGameLabel, BorderLayout.CENTER);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SetUpDialog(MainFrame.this /*, SwingView.this*/);
            }
        });

        add(mainPanel);
        pack();
        setVisible(true);
    }

}
