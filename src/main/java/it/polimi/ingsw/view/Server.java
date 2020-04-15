package it.polimi.ingsw.view;


import it.polimi.ingsw.exception.CellOutOfBattlefieldException;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.PlayerAction;
import it.polimi.ingsw.utils.ServerResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;

public class Server extends Observable<PlayerAction> {

    public void run() throws IOException, ClassNotFoundException {

        ServerSocket ss = new ServerSocket(7777);
        System.out.println("ServerSocket awaiting connections...");
        // blocking call, this will wait until a connection is attempted on this port.
        Socket socket = ss.accept();

        // get the input stream from the connected socket
        InputStream inputStream = socket.getInputStream();
        // create a DataInputStream so we can read data from it.
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

       PlayerAction playerAction;

        try{
            while (true) {
                playerAction = (PlayerAction) objectInputStream.readObject();
                notify(playerAction);
            }
        } catch(NoSuchElementException | CellOutOfBattlefieldException e){
            System.out.println("Connection closed from the client side");
        } finally {
            System.out.println("Closing sockets.");
            ss.close();
            socket.close();
        }
    }
}










