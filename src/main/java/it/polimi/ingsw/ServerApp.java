package it.polimi.ingsw;

import it.polimi.ingsw.gui.GameFrame;
import it.polimi.ingsw.gui.SwingView;
import it.polimi.ingsw.server.Server;


import java.io.IOException;


/**
 * This class creates the server and runs it
 */

public class ServerApp
{
    public static void main( String[] args ) {

        // servono per far partire la gui ( nelle due cose che abbiamo fatto )
        // ovviamente non è questo il loro posto, è solo per testare.
        new GameFrame();
        //SwingView s = new SwingView();

        System.out.println("Hi I am the SERVER");
        Server server;

        try {

            // The Server is not created by new Server because it is Singleton, so
            // it calls the getInstance Method that create a server if it doesn't exist
            server = Server.getInstance();
            server.run();

        } catch(IOException exception){
            System.err.println("Impossible to start the server!");
            System.err.println(exception.getMessage());
        }
    }
}
