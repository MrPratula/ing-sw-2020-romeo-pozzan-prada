package it.polimi.ingsw;

import it.polimi.ingsw.server.Server;

import java.io.IOException;


/**
 * This class creates the server and runs it
 * Only one server can run at the same time
 * The server will shut down when there are no players connected after a player connect
 */
public class ServerApp
{
    public static void main( String[] args ) {

        System.out.println("Hi I am the SERVER");
        Server server;

        try {
            server = Server.getInstance();
            server.run();

        } catch(IOException exception){
            System.err.println("Error in server launch!");
        }
    }
}