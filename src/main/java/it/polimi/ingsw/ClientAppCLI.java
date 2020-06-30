package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;

import java.io.IOException;
import java.util.Scanner;


/**
 * Main class for running a game and play it with a CLI
 */
public class ClientAppCLI {

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
                        port = Integer.parseInt(args[3]);
                        needToLoop = false;
                    } else
                        throw new IllegalArgumentException("Please insert as first option '-ip' and as second option '-port'");
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
            //else throw new IllegalArgumentException("Please insert something like:\n' $ java -jar ClientGUI.jar (or ClientCli.jar) -ip YOUR_DYNAMIC_IP -port YOUR_PORT ");
            else {
                System.out.println("You are playing on localhost!");
                ip = localHost;
                port = 12345;
                needToLoop = false;
            }
        }

        try {
            client = new Client(ip, port);
            client.run(false);
        } catch (Exception e) {
            System.err.println("Can not start the client.");
        }

    }
}