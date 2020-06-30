package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * Main class to run a game and play it with a GUI
 */
public class ClientAppGUI {

    public static void main(String[] args) {

        Client client;
        String localHost = "127.0.0.1";
        String ip = null;
        int port = 0;


        boolean needToLoop = true;

        while (needToLoop) {

            if (args.length == 4) {
                try {
                    if (args[0].equals("-ip") && args[2].equals("-port")) {
                        ip = args[1];
                        try{
                            port = Integer.parseInt(args[3]);
                        }catch (InputMismatchException ex){
                            System.err.println("Not a valid port!");
                        }
                        needToLoop = false;
                    } else
                        throw new IllegalArgumentException("Please insert as first option '-ip' and as second option '-port'");
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
            //else throw new IllegalArgumentException("Please insert something like:\n' $ java -jar ClientGUI.jar (or ClientCli.jar) -ip YOUR_DYNAMIC_IP -port YOUR_PORT ");
            else {
                ip = localHost;
                port = 12345;
                needToLoop = false;
            }
        }

        try {
            client = new Client(ip, port);
            client.run(true);
        } catch (Exception e) {
            System.err.println("Can not start the client. Maybe a wrong ip?");
        }

    }
}
