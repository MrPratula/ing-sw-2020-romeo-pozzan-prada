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
            server = Server.getInstance();
            server.run();
        } catch(IOException e){
            System.err.println("Impossible to start the server!\n" + e.getMessage());
        }

    }
}
