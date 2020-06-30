package it.polimi.ingsw.gui;

import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.PlayerAction;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


/**
 * JDialog that asks if the player wants to use prometheus' power
 */
public class AskPrometheusPowerWindow extends JDialog {

    private final SwingView view;

    /**
     * Creates the JDialog thats asks if the player wants to use prometheus' power
     * @param swingView gui view of mvc
     * @exception IOException if something goes wrong
     */
    public AskPrometheusPowerWindow(SwingView swingView) throws IOException {

        this.view = swingView;

        setTitle("Prometheus Power");
        setResizable(false);
        setIconImage(new ImageIcon(ImageIO.read(getClass().getResource(Pics.PLAYERICON.getPath()))).getImage());
        setPreferredSize(new Dimension(600,300));

        PrometheusPanel main = new PrometheusPanel();

        JButton yes_button = new JButton("YES");
        yes_button.setContentAreaFilled(false);
        yes_button.setBorderPainted(false);
        JButton no_button = new JButton("NO");
        no_button.setContentAreaFilled(false);
        no_button.setBorderPainted(false);

        yes_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               PlayerAction playerAction = new PlayerAction(Action.PROMETHEUS_ANSWER, view.getPlayer(), null, null, 0, 0, null, null, true, null);
                try {
                    view.setWantToUsePower(true);
                    view.notifyClient(playerAction);
                    AskPrometheusPowerWindow.this.dispose();
                } catch ( IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        no_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayerAction playerAction = new PlayerAction(Action.PROMETHEUS_ANSWER, view.getPlayer(), null, null, 0, 0, null, null, false, null);
                try {
                    view.setWantToUsePower(false);
                    view.notifyClient(playerAction);
                    AskPrometheusPowerWindow.this.dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        main.add(yes_button);
        main.add(no_button);

        add(main, BorderLayout.CENTER);

        pack();
        setVisible(true);
        final JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(dialog, "ATTENTION! If you can only move going up after the build, you will lose!", "ATTENTION", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ImageIO.read(getClass().getResource(Pics.ERRORICON.getPath()))));

    }
}
