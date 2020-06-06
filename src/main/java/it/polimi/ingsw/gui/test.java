package it.polimi.ingsw.gui;

import it.polimi.ingsw.cli.*;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.Pack;
import it.polimi.ingsw.utils.ServerResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class test extends JFrame {

    private ModelUtils modelUtils; ///////////////////////////////////

    private JLabel messageLabel = new JLabel(Pics.MESSAGEBG2.getImageIcon());

    private CellButton[][] battlefieldGUI = new CellButton[5][5];

    private JPanel godPanel = new JPanel();

    private Action action;

    //buttonHandler for every button
    private List<ButtonHandler> battlefieldButtons = new ArrayList<>();


    public test(List<GodCard> godsInGame, ServerResponse serverResponse) throws HeadlessException {

        setPreferredSize(new Dimension(950,1000));

        setLayout(new BorderLayout());

        BattlefieldPanel b = new BattlefieldPanel();
        b.setLayout(new GridLayout(5,5,0,0));

        for(int j=4; j>-1 ; j--){
            for(int i=0; i<5; i++){
                //here i create a button for every cell
                battlefieldGUI[i][j] = new CellButton(i,j);
                battlefieldGUI[i][j].setBorderPainted(true);
                battlefieldGUI[i][j].setContentAreaFilled(false);
                //battlefieldGUI[i][j].setSize(200,200);
                battlefieldGUI[i][j].setIcon(Pics.LEVEL0.getImageIcon());
                battlefieldGUI[i][j].setBackground(Color.BLACK);
                battlefieldGUI[i][j].getCell().setHeight(0);

                b.add(battlefieldGUI[i][j]);

                //here i add a listener to this button (owning a Cell)
                ButtonHandler bh = new ButtonHandler(battlefieldGUI[i][j], serverResponse ,new SwingView(),new JFrame());
                battlefieldButtons.add(bh);
                battlefieldGUI[i][j].addActionListener(bh);
            }
        }

        // handling the godcard panel
        godPanel.setLayout(new GridLayout(3,1));
        for(int i = 0; i<godsInGame.size(); i++){
            final int j = i + 1;
            final JLabel player = new JLabel(new ImageIcon(new File("./src/main/images/godcards/" + godsInGame.get(i).name().toLowerCase() + ".png").getAbsolutePath()));
            player.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    player.setText("Player"+j);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    player.setText(" ");
                }
            });
            godPanel.add(player);
        }

        add(b,BorderLayout.CENTER);
        add(godPanel,BorderLayout.WEST);
        add(messageLabel, BorderLayout.SOUTH);

        setVisible(true);
        pack();


    }
}
