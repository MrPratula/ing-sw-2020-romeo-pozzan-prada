package it.polimi.ingsw;

import it.polimi.ingsw.server.Server;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class ServerApp
{
    public static void main( String[] args )
    {
        Server server;
        try {
            System.out.println("Server is starting up!");
            server = new Server();
            server.run();
        } catch (IOException e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        }
    }
}
