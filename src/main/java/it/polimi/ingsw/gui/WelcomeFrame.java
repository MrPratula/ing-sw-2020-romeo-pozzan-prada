package it.polimi.ingsw.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class WelcomeFrame {

    private final JFrame mainFrame;
    private SwingView swingView;


    public WelcomeFrame(final SwingView swingView) {

        this.swingView = swingView;

        mainFrame = new JFrame("Santorini");
        mainFrame.setResizable(true);
        mainFrame.setIconImage(Pics.PLAYBUTTON.getImageIcon().getImage());
        mainFrame.setPreferredSize(new Dimension(800,800));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        LogoPanel mainPanel = new LogoPanel();

        JButton playButton = new JButton(Pics.PLAYBUTTON.getImageIcon());
        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);
        playButton.setBorder(BorderFactory.createEmptyBorder());
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NickNameWindow(swingView);
                mainFrame.dispose();
            }
        });

        mainPanel.add(playButton, BorderLayout.PAGE_END);
        mainFrame.add(mainPanel);

        mainFrame.setLocationRelativeTo(null);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}
