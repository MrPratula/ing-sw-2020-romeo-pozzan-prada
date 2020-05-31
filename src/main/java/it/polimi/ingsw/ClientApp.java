package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.gui.GameFrame;

import java.io.IOException;

public class ClientApp {

    public static void main(String[] args) {

        new GameFrame();
        /*
        SwingView s = new SwingView();
        List<GodCard> l = new ArrayList<>(); l.add(GodCard.LIMUS); l.add(GodCard.CHRONUS);
        ChooseGodCardWindow c = new ChooseGodCardWindow(s.getMainFrame(),l);
        */

        Client client = new Client("127.0.0.1", 12345);

        try{
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }

    }
}
