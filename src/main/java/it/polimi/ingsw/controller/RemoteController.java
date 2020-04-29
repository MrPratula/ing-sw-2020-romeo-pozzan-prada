package it.polimi.ingsw.controller;

import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.PlayerAction;

import java.io.*;
import java.net.Socket;

public class RemoteController implements Observer<PlayerAction> {

    Socket socket;
    OutputStream outputStream;
    ObjectOutputStream objectOutputStream;


    public RemoteController(Socket socket) throws IOException {
        this.socket = socket;
        this.outputStream = socket.getOutputStream();
        this.objectOutputStream = new ObjectOutputStream(outputStream);
    }


    @Override
    public void update(PlayerAction playerAction) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(playerAction);
    }
}
