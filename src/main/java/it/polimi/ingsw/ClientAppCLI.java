package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;

import java.io.IOException;


/**
 * Main class for running a game and play it with a CLI
 */
public class ClientAppCLI {

    public static void main(String[] args) {

        Client client;

        String localHost = "127.0.0.1";

        client = new Client(localHost, 12345);

        try{
            client.run(false);
        }catch (IOException e){
            System.err.println("Can not start the client. May be a wrong IP?");
        }
    }
}