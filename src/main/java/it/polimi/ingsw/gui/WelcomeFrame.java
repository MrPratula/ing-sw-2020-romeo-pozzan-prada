package it.polimi.ingsw.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


/**
 * The first frame that invites the user to play,
 * he has just to click on PLAY
 */
public class WelcomeFrame {

    private final JFrame mainFrame;
    private SwingView swingView;

    /**
     * The frame opened when a Gui player is connected to the server
     * @param swingView The player's Swingview
     * @throws IOException if can't send object into the socket
     */
    public WelcomeFrame(final SwingView swingView) throws IOException {

        this.swingView = swingView;

        mainFrame = new JFrame("Santorini");
        mainFrame.setResizable(true);
        mainFrame.setIconImage(new ImageIcon(ImageIO.read(getClass().getResource(Pics.PLAYBUTTON.getPath()))).getImage());
        mainFrame.setPreferredSize(new Dimension(1100,800));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        LogoPanel mainPanel = new LogoPanel();

        JButton playButton = new JButton(new ImageIcon(ImageIO.read(getClass().getResource(Pics.PLAYBUTTON.getPath()))));
        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);
        playButton.setBorder(null);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new NickNameWindow(swingView);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                mainFrame.dispose();
            }
        });

        mainPanel.add(playButton, BorderLayout.SOUTH);
        mainFrame.add(mainPanel);

        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}
