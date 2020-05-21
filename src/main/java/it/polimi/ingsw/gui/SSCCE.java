package it.polimi.ingsw.gui;

import java.awt.*;
import javax.swing.*;

public class SSCCE extends JPanel
{
    public SSCCE()
    {
        setLayout( new OverlayLayout(this) );

        JButton child = new JButton( new ImageIcon("./src/main/images/level0.png") );
        child.setAlignmentX(JButton.CENTER_ALIGNMENT);
        child.setAlignmentY(JButton.CENTER_ALIGNMENT);

        JButton parent = new JButton( new ImageIcon("./src/main/images/level1.png") );
        parent.setAlignmentX(JButton.CENTER_ALIGNMENT);
        parent.setAlignmentY(JButton.CENTER_ALIGNMENT);

        add(child);
        add(parent);
    }

    @Override
    public boolean isOptimizedDrawingEnabled()
    {
        return false;
    }

    private static void createAndShowGUI()
    {
        JFrame frame = new JFrame("SSCCE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new SSCCE());
        frame.setLocationByPlatform( true );
        frame.pack();
        frame.setVisible( true );
    }

    public static void main(String[] args)
    {
        //EventQueue.invokeLater( () -> createAndShowGUI() );

        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowGUI();
            }
        });

    }
}
