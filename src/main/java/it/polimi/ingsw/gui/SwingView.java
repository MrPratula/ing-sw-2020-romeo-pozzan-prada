package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.SetUpDialog;
import it.polimi.ingsw.client.View;
import it.polimi.ingsw.model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;


public class SwingView extends View {

    private JFrame mainFrame;        //1
    private JPanel mainPanel;        //2
    private JPanel logoPanel;        //2.1
    private JButton playButton;      //3.1
    private JLabel logoImage;

    private Player player;


    /**
     * Constructor of the client view with Swing GUI
     */
    public SwingView(/*Player p*/){

/*  modo1

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        final JLayeredPane layeredPane = new JLayeredPane();
        contentPane.add(layeredPane,BorderLayout.CENTER);

        final JPanel btnPane = new JPanel(new GridBagLayout());
        btnPane.setOpaque(false);

        JButton btn = new JButton(new ImageIcon(new File("./src/main/graphics/ButtonPlay.png").getAbsolutePath()));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        btnPane.add(btn,gbc);

        final JPanel lblPane = new JPanel(new GridBagLayout());
        //lblPane.setBackground(Color.CYAN);

        JLabel lbl = new JLabel(new ImageIcon(new File("./src/main/graphics/Santorini.png").getAbsolutePath()));
        gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        lblPane.add(lbl,gbc);

        layeredPane.add(btnPane,0);
        layeredPane.add(lblPane,1);
        layeredPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                lblPane.setBounds(0, 0, layeredPane.getWidth(), layeredPane.getHeight());
                btnPane.setBounds(0, 0, layeredPane.getWidth(), layeredPane.getHeight());
            }
        });


        frame.setSize(300,200);
        frame.setVisible(true);
*/
/* modo 2
        mainFrame = new JFrame("Santorini");
        mainFrame.setResizable(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout( new OverlayLayout(mainFrame) );

        mainPanel = new JPanel();
        //mainPanel.setBackground(Color.WHITE);

        playButton = new JButton(new ImageIcon(new File("./src/main/graphics/ButtonPlay.png").getAbsolutePath()));
        playButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        playButton.setAlignmentY(JButton.CENTER_ALIGNMENT);

        mainPanel.add(playButton);

        JPanel over = new JPanel();
        LayoutManager overlay = new OverlayLayout(over);
        over.setLayout(overlay);

        logoPanel = new JPanel();
        logoPanel.add(new JLabel(new ImageIcon(new File("./src/main/graphics/Santorini.png").getAbsolutePath())));

        over.add(mainPanel);
        over.add(logoPanel);

        mainPanel.setOpaque(false);

        mainFrame.add(over);
*/

        //this.player = p;

        mainFrame = new JFrame("Santorini");
        mainFrame.setResizable(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);

        logoImage = new JLabel(new ImageIcon(new File("./src/main/images/Santorini.png").getAbsolutePath()));

        playButton = new JButton(new ImageIcon(new File("./src/main/images/ButtonPlay.png").getAbsolutePath()));
        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SetUpDialog(mainFrame, SwingView.this);
            }
        });

        mainPanel.add(logoImage, BorderLayout.CENTER);
        mainPanel.add(playButton, BorderLayout.SOUTH);

        mainFrame.add(mainPanel, BorderLayout.CENTER);


        mainFrame.pack();
        mainFrame.setVisible(true);
    }



}
