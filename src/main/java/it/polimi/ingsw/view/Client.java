package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.ServerResponse;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;


public class Client extends Observable<ServerResponse> {

    Socket socket;
    InputStream inputStream;


    public Client(String ip, int port) throws IOException {

        this.socket = new Socket(ip, port);
        System.out.println("Connection established");
        inputStream = socket.getInputStream();
    }


    /**
     * It receive a ServerResponse and notify the view.
     * This happens while there is an active remote view. (?)
     */
    public void run() throws IOException {

        while (true) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                ServerResponse serverResponse = (ServerResponse)objectInputStream.readObject();
                notify(serverResponse);
            } catch(NoSuchElementException | ClassNotFoundException | CellOutOfBattlefieldException | ImpossibleTurnException | ReachHeightLimitException | CellHeightException | WrongNumberPlayerException e){
                System.out.println("Connection closed from the server side");
            } finally {
                inputStream.close();
                socket.close();
            }
        }
    }
}