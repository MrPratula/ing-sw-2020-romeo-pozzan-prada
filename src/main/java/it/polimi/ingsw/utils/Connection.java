package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Connection extends Observable<PlayerAction> implements Runnable{

    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private Server server;
    private String name;

    private boolean active = true;


    /**
     * A connection is created with a socket and a server.
     */
    public Connection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
    }


    /**
     * @return the name of the player who use this connection.
     */
    public String getName() {
        return this.name;
    }


    /**
     * Tells if a socket is active or not.
     */
    private synchronized boolean isActive(){
        return active;
    }


    /**
     * It handle a asynchronous thread that send a ServerResponse.
     * @param serverResponse the object to send via socket.
     */
    public void asyncSend(final ServerResponse serverResponse){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    send(serverResponse);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * This is the effective send that is called in asyncSend.
     * @param serverResponse the object to send.
     */
    public void send(ServerResponse serverResponse) throws IOException {
        objectOutputStream.reset();
        objectOutputStream.writeObject(serverResponse);
        objectOutputStream.flush();
    }


    /**
     * Call the closeConnection
     * and deregister the connection from the server.
     */
    private void close() throws IOException {

        closeConnection();

        System.out.println("De-registering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }


    /**
     * Close the socket of a connection.
     * Here the connection is effectively closed.
     */
    public synchronized void closeConnection(){

        Pack pack = new Pack(Action.CONNECTION_CLOSE);
        asyncSend(new ServerResponse(null, pack));

        try {
            socket.close();
        } catch (IOException ioException) {
            System.err.println(ioException.getMessage());
        }
        active = false;
    }


    public PlayerAction listenSocket() {
        try {
            return (PlayerAction) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }




    /**
     * The override for the runnable interface.
     * It ask a client for his name, add him to the lobby
     * and start listening his messages and notify them to the message receiver.
     */
    @Override
    public void run() {
        try{

            // Save where to send and where to receive
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            // Ask what is your name
            Pack pack = new Pack(Action.WELCOME);
            asyncSend(new ServerResponse(null, pack));

            //Continue to ask this till he insert a valid name
            boolean needToLoop = true;
            while (needToLoop) {

                // Receive and save the name
                PlayerAction playerAction = (PlayerAction) objectInputStream.readObject();
                String name = playerAction.getArgs();

                // Check for the same name into waiting connection
                List<String> names = server.getPlayersName();
                if (names != null) {

                    for (String n: names) {
                        // Check for upper case to avoid having Lorenzo and lorenzo in the same game
                        if (n.toUpperCase().equals(name.toUpperCase())){
                            Pack pack2 = new Pack(Action.INVALID_NAME);
                            asyncSend(new ServerResponse(null, pack2));
                        }
                        else {
                            this.name = name;
                            needToLoop = false;
                        }
                    }
                // This is for empty waiting connection
                } else {
                    this.name = name;
                    needToLoop = false;
                }
            }

            // Add this connection associated to a player to the lobby
            final Connection thisConnection = this;

            try {
                server.lobby(thisConnection, name);
            } catch (IOException e) {
                System.err.println("Error in launch new thread into lobby");
                e.printStackTrace();
            }

            // Start listening every request from the client
            while(isActive()){

                PlayerAction playerAction = (PlayerAction) objectInputStream.readObject();

                // notify the RemoteView(messageReceiver)
                System.out.println(playerAction.getAction().getName().toUpperCase()+" from "+thisConnection.name);
                notify(playerAction);
            }

        } catch(IOException | CellOutOfBattlefieldException | ImpossibleTurnException | ReachHeightLimitException | CellHeightException | WrongNumberPlayerException | ClassNotFoundException e){
            System.err.println(e.getMessage());
        } finally {
            try {
                close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
