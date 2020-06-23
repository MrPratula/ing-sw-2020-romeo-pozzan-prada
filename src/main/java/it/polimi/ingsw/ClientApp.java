package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.model.TokenColor;

import java.io.IOException;
import java.util.Scanner;

public class ClientApp {

    public static void main(String[] args) {

        Client client = null;
        String ip = "192.168.1.93";
        String localHostFede = "192.168.1.149";
        String localHostRichi = "192.168.1.7";

        boolean needToLoop = true;

        /*
        while(needToLoop){

            System.out.println("Insert a LAN ip... 192.168.1.XX");

            try {
                Scanner scanner = new Scanner(System.in);
                ip = scanner.nextLine();

                String[] ipArray = ip.split("\\.");
                if (ipArray[0].length()==3 && ipArray[1].length()==3 && ipArray[2].length()==1 && ipArray[3].length()<=3) {


                    client = new Client(ip, 12345);
                    needToLoop = false;
                }

            }catch (Exception e){

                System.out.println("Insert a LAN ip... 192.168.1.XX");
                needToLoop=true;
            }

        }
             */

        client = new Client(localHostFede, 12345);

        try{
            client.run();
        }catch (IOException e){
            System.err.println("Can not start the client. May be a wrong IP?");
        }
    }
}
