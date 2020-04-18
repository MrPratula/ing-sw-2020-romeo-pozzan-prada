package it.polimi.ingsw;

import it.polimi.ingsw.view.Server;

import java.io.IOException;



/**
 * This class creates the server and runs it
 *
 */

public class ServerApp
{
    public static void main( String[] args ) {
        Server server;
        try {
            server = new Server();
            server.run();
        } catch(IOException | ClassNotFoundException e){
            System.err.println("Impossible to start the server!\n" + e.getMessage());
        }

    }
}
