package it.polimi.ingsw.utils;

import it.polimi.ingsw.server.Server;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class Connection extends Observable<PlayerAction> implements Runnable{

    private final Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private final Server server;
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
                    System.err.println(name.toUpperCase()+" can't send object into socket!");
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
    private void close() {

        closeConnection();

        System.out.println("De-registering "+name.toUpperCase()+"...");
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
            System.err.println(name.toUpperCase()+" can't close his connection!");
        }
        active = false;
    }


    /**
     * Used into the server during the init first player to ask him how much player he want to play with.
     * @return the object received from socket if it is a playerAction.
     */
    public PlayerAction listenSocket() {
        try {
            return (PlayerAction) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(name.toUpperCase()+" can't read object from socket!");
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

                        // Check for the name to be not empty or too long or with spaces
                        if (!isAGoodName(name)){
                            needToLoop = true;
                            break;
                        }
                        // If it is a good name
                        else{
                            // Check for upper case to avoid having Lorenzo and lorenzo in the same game
                            if (n.toUpperCase().equals(name.toUpperCase())){
                                Pack pack2 = new Pack(Action.INVALID_NAME);
                                asyncSend(new ServerResponse(null, pack2));
                                needToLoop = true;
                                break;
                            }
                            else {
                                this.name = name;
                                needToLoop = false;
                            }
                        }
                    }
                // This is for empty waiting connection when the first player connect
                } else {
                    this.name = name;
                    needToLoop = false;
                }
            }

            // Add this connection associated to a player to the lobby
            final Connection thisConnection = this;

            try {
                server.lobby(thisConnection, name);
            } catch (Exception e) {
                System.err.println("Error in launch new thread into lobby");
            }

            // Start listening every request from the client
            while(isActive()){

                PlayerAction playerAction = (PlayerAction) objectInputStream.readObject();
                System.out.println(playerAction.getAction().getName().toUpperCase()+" from "+thisConnection.name);

                // notify the RemoteView(messageReceiver)
                notify(playerAction);
            }

        } catch(IOException | ClassNotFoundException e){
            System.err.println(name.toUpperCase()+" has disconnected!\n He will loose the game!");
            try {
                server.getModel().disconnected(name);
            } catch (IOException e1) {
                System.err.println("Can't make a player loose...");
            }
        } finally {
            close();
        }
    }


    /**
     * @param name string to check. It is the user input for his username.
     * @return true if it is not empty, too long(<=16) or with spaces.
     */
    public boolean isAGoodName(String name){

        if (name==null)
            return false;

        if (name.isEmpty())
            return false;

        if (name.contains(" "))
            return false;

        if (name.contains("\n"))
            return false;

        return name.length() <= 16;
    }
}