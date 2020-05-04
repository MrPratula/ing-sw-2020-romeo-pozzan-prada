package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.server.Server;

import java.io.*;
import java.net.Socket;

public class Connection extends Observable<PlayerAction> implements Runnable{

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
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
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(serverResponse);
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

        asyncSend(new ServerResponse(Action.CONNECTION_CLOSE, null, null, null, null));

        try {
            socket.close();
        } catch (IOException ioException) {
            System.err.println(ioException.getMessage());
        }
        active = false;
    }


    /**
     * The override for the runnable interface.
     * It ask a client for his name, add him to the lobby
     * and start listening his messages and notify them to the message receiver.
     */
    @Override
    public void run() {
        try{
            // Get the input stream for PlayerAction and outputStream for ServerResponse
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            // Ask what is your name
            asyncSend(new ServerResponse(Action.WELCOME, null, null, null, null));

            // Receive and save the name
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            String name = ((PlayerAction)objectInputStream.readObject()).getArgs();
            this.name = name;

            // Add this connection associated to a player to the lobby
            server.lobby(this, name);

            // Start listening every request from the client
            while(isActive()){

                objectInputStream = new ObjectInputStream(inputStream);

                PlayerAction playerAction = (PlayerAction) objectInputStream.readObject();

                // notify the RemoteView(messageReceiver)
                notify(playerAction);
            }

        } catch(IOException | CellOutOfBattlefieldException | ImpossibleTurnException | ReachHeightLimitException | CellHeightException | WrongNumberPlayerException | ClassNotFoundException | InterruptedException e){
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
