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

        int port=0;
        int DEFAULT_PORT = 12345;

        if (args.length==2){

            for (int i=0; i<args.length; i++){

                switch (args[i]){

                    case "-port": {
                        try{
                            port = Integer.parseInt(args[i+1]);
                        } catch (Exception e){
                            port = 0;
                        }
                        break;
                    }
                }
            }
        }

        if (port==0)
            port = DEFAULT_PORT;

        try {
            server = Server.getInstance(port);
            server.run();

        } catch(IOException exception){
            System.err.println("Error in server launch!");
        }
    }
}