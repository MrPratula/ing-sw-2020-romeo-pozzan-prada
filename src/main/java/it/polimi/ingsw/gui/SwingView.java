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

    public SwingView(){
        run();
    }

    //main: new swingView();

    @Override
    public void run(){

        mainFrame = new JFrame("Santorini");
        mainFrame.setResizable(true);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(500, 500);

        mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setSize(500, 400);

        logoImage = new JLabel();
        logoImage.setIcon(new ImageIcon("graphics\\Santorini.png"));  //non so se son cosi i path
        logoImage.setBounds(0,0,500,400);

        playButton = new JButton();
        playButton.setSize(100, 100);
        playButton.setIcon(new ImageIcon("\\graphics\\ButtonPlay.png"));
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
