package it.polimi.ingsw;

import it.polimi.ingsw.server.Server;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * This class creates the server and runs it
 * Only one server can run at the same time
 * The server will shut down when there are no players connected after a player connect
 */
public class ServerApp {

    public static void main( String[] args ) {

        boolean needToLoop = true;
        int port = 12345;
        Server server;

        System.out.println("Hi I am the SERVER");

/*
        while(needToLoop) {

           if(args.length==2) {
               if (args[0].equals("-port")) {
                   try{
                       port = Integer.parseInt(args[1]);
                       needToLoop = false;
                   }catch (InputMismatchException ex){
                       System.err.println("Not a valid port!");
                   }
               }
               else throw new IllegalArgumentException("The first argument should be '-port' ");
           }
           //else throw new IllegalArgumentException("Please insert something like:\n' $ java -jar Server.jar -port YOUR_PORT '");

            else{
               port = 12345;
               needToLoop = false;
           }
        }
*/
        try {
            server = Server.getInstance();
            server.run(port);
        } catch(IOException exception){
            System.err.println("Error in server launch!");
        }
    }

}