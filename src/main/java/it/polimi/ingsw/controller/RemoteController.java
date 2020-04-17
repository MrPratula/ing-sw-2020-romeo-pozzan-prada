package it.polimi.ingsw.controller;

import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.PlayerAction;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RemoteController implements Observer<PlayerAction> {

    ObjectOutputStream objectOutputStream;

    public RemoteController() throws IOException {
        this.objectOutputStream = run();
    }

    private ObjectOutputStream run() throws IOException {

        Socket socket = new Socket("localhost", 7777);
        System.out.println("Connected!");

        // get the output stream from the socket.
        OutputStream outputStream = socket.getOutputStream();
        // create an object output stream from the output stream so we can send an object through it
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        return objectOutputStream;

    }

    @Override
    public void update(PlayerAction playerAction) throws IOException {
        objectOutputStream.writeObject(playerAction);
    }
}
