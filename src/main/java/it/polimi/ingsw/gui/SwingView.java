package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.SetUpDialog;
import it.polimi.ingsw.client.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SwingView extends View {

    private JFrame mainFrame;        //1

    private JPanel mainPanel;        //2

    private JLabel logoImage;        //2.1

    private JLabel startGameLabel;

    private JButton playButton;      //3.1


    //main: new swingView();

    //@Override
    public SwingView(){

        mainFrame = new JFrame("Santorini");
        mainFrame.setResizable(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLACK);

        logoImage = new JLabel();
        logoImage.setIcon(new ImageIcon("C:\\Users\\ricca\\IdeaProjects\\ing-sw-2020-romeo-pozzan-prada\\src\\main\\java\\it\\polimi\\ingsw\\gui\\graphics\\Santorini.png"));

        playButton = new JButton();
        playButton.setIcon(new ImageIcon("C:\\Users\\ricca\\IdeaProjects\\ing-sw-2020-romeo-pozzan-prada\\src\\main\\java\\it\\polimi\\ingsw\\gui\\graphics\\ButtonPlay.png"));
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SetUpDialog(mainFrame, SwingView.this);
            }
        });

        //startGameLabel = new JLabel();
        //startGameLabel.add(playButton);

        mainPanel.add(logoImage, BorderLayout.CENTER);
        mainPanel.add(playButton, BorderLayout.SOUTH);

        mainFrame.add(mainPanel, BorderLayout.CENTER);

        mainFrame.pack();
        mainFrame.setVisible(true);
    }



}
