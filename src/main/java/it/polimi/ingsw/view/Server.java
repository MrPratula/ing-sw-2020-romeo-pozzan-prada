package it.polimi.ingsw.view;


import it.polimi.ingsw.controller.RemoteController;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.PlayerAction;
import it.polimi.ingsw.utils.ServerResponse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Server  {


    private static Server singleServer = null;
    private final ServerSocket serverSocket;
    private static final int PORT = 12345;

    List<Player> allPlayers;
    int numberOfPlayers;


    /**
     * Singleton constructor. Return the one that exist.
     * If it do not exist it create a new one.
     * @return an instance of server.
     */
    public static Server getInstance() throws IOException {

        if (singleServer == null)
            singleServer = new Server();

        return singleServer;
    }


    /**
     * Private constructor that is called by the getInstance.
     */
    private Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }





    public void run() {

        System.out.println("Server has started up on port: " + PORT);

        while (true) {
            try {
                // Here the server get the socket.
                Socket socket = serverSocket.accept();

                RemoteController remoteController = new RemoteController(socket);

                // Create input stream and output stream
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                // Create object for input and output
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

                // Ask for name of first player
                ServerResponse serverResponse = new ServerResponse(Action.WELCOME, null, null, null, null);
                objectOutputStream.writeObject(serverResponse);

                // Receive first player and add him to the list
                PlayerAction playerAction = (PlayerAction) objectInputStream.readObject();
                allPlayers.add(playerAction.getPlayer());

                // Ask for how many players
                String message = String.format("Hi %s!",allPlayers.get(0).getUsername().toUpperCase());
                serverResponse = new ServerResponse(Action.HOW_MANY_PLAYERS, null, null, null, message);
                objectOutputStream.writeObject(serverResponse);

                // Receive how many players. This number is set in tokenMain field
                playerAction = (PlayerAction) objectInputStream.readObject();
                numberOfPlayers = playerAction.getTokenMain();






            } catch (IOException | ClassNotFoundException e){
                System.err.println("Connection error!");
            }
        }



    }








}










