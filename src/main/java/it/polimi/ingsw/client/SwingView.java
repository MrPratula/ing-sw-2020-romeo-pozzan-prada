package it.polimi.ingsw.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SwingView extends View {

    private JFrame mainFrame;        //1
    private JPanel logoPanel;        //2
    private JLabel logoImage;        //2.1

    private JPanel startGamePanel;   //3
    private JButton playButton;      //3.1



    public SwingView(){

        mainFrame = new JFrame("Santorini");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        logoPanel = new JPanel(new BorderLayout(10,10));
        logoImage = new JLabel(new ImageIcon("",null));     //set santorini logo
        logoPanel.add(logoImage);

        startGamePanel = new JPanel(new BorderLayout(10,10));

        playButton = new JButton("PLAY");
        //playButton.setIcon(/*path*/);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SetUpDialog(mainFrame, SwingView.this);
            }
        });
        logoPanel.add(playButton, BorderLayout.PAGE_END);

        mainFrame.add(logoPanel);
        mainFrame.pack();
    }


    @Override
    public void run(){
        mainFrame.setVisible(true);
    }




}
