package it.polimi.ingsw;

import it.polimi.ingsw.cli.GodCard;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.gui.GameFrame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientApp {

    public static void main(String[] args) {

        Client client = new Client("127.0.0.1", 12345);

        List<GodCard> godsInGame = new ArrayList<>();
        godsInGame.add(GodCard.CHRONUS);
        godsInGame.add(GodCard.ATHENA);
        godsInGame.add(GodCard.MINOTAUR);

        new GameFrame(godsInGame);

      //  new test();
        try{
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }

    }
}
