package it.polimi.ingsw.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class WelcomeFrame {

    private final JFrame mainFrame;
    SwingView swingView;


    public WelcomeFrame(final SwingView swingView) {

        this.swingView = swingView;
        mainFrame = new JFrame("Santorini");
        mainFrame.setResizable(true);
        mainFrame.setIconImage(Pics.PLAYBUTTON.getImageIcon().getImage());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);

        JLabel logoImage = new JLabel(Pics.SANTORINI.getImageIcon());

        JButton playButton = new JButton(new ImageIcon(new File("./src/main/images/utils/buttonPlay.png").getAbsolutePath()));
        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NickNameWindow(swingView);
                mainFrame.dispose(); //close mainFrame.
            }
        });

        mainPanel.add(logoImage, BorderLayout.CENTER);
        mainPanel.add(playButton, BorderLayout.SOUTH);
        mainFrame.add(mainPanel, BorderLayout.CENTER);

        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}
