package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.gui.ChooseGodCardWindow;
import it.polimi.ingsw.gui.SwingView;
import it.polimi.ingsw.cli.GodCard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientApp {

    public static void main(String[] args) {

<<<<<<< HEAD
        /*
        SwingView s = new SwingView();
        List<GodCard> l = new ArrayList<>(); l.add(GodCard.LIMUS); l.add(GodCard.CHRONUS);
        ChooseGodCardWindow c = new ChooseGodCardWindow(s.getMainFrame(),l);
        */
=======
        SwingView s = new SwingView();
        List<GodCard> l = new ArrayList<>(); l.add(GodCard.LIMUS); l.add(GodCard.CHRONUS);
        ChooseGodCardWindow c = new ChooseGodCardWindow(s.getMainFrame(),l);

        //NPE    System.out.println(c.getButtonGroup().getSelection().getActionCommand());
>>>>>>> c6f74a73a1240b3b2d2662f8927b1de52eaaef37

        Client client = new Client("127.0.0.1", 12345);

        try{
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }

    }
}
