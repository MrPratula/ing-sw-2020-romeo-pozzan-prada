package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;

import java.io.IOException;


/**
 * Main class for running a game and play it with a CLI
 */
public class ClientAppCLI {

    public static void main(String[] args) {

        Client client;

        String LOCAL_HOST = "127.0.0.1";
        int DEFAULT_PORT = 12345;

        String ip = null;
        int port = 0;

        if (args.length==4){

            for (int i=0; i<args.length; i++){

                switch (args[i]){

                    case "-ip" :{
                        try{
                            ip = args[i+1];
                        } catch (Exception e){
                            ip = null;
                        }
                        break;
                    }

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

        if (ip == null)
            ip = LOCAL_HOST;
        if (port == 0)
            port = DEFAULT_PORT;

        client = new Client(ip, port);

        System.out.println(String.format("Trying to connect to %s:%s", ip, port));

        try{
            client.run(false);
        }catch (IOException e){
            System.err.println("Can not start the client. May be a wrong IP?");
        }
    }
}