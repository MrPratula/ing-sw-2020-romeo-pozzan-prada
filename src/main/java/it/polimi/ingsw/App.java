package it.polimi.ingsw;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {

        System.out.println( "Hello World!" );

        List<Player> players = new ArrayList<Player>();

        Client client = new Client();
        players = client.recruitPlayers();

        client.startNewGame(players);


    }
}
