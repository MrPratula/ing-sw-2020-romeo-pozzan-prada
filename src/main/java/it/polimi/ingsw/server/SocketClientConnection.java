package it.polimi.ingsw.server;


import it.polimi.ingsw.utils.Observable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * This should be (i think... maybe...) a connection
 * between a player and a server.
 * It extend Observable and implements ClientConnection and Runnable
 */
public class SocketClientConnection extends Observable implements ClientConnection, Runnable {

    private Socket socket;
    private ObjectOutputStream out;
    private Server server;

    private boolean active = true;

    /**
     * The constructor assign a server and a socket to
     * the connection
     * @param socket to bind to the server
     * @param server to bind to the socket. It is always the same (maybe)
     */

    public SocketClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }


    /**
     * Specify the method closeConnection of the
     * ClientConnection interface
     */
    @Override
    public synchronized void closeConnection() {
        send("Connection closed!");
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
    }

    /**
     * Specify the method asyncSend of the
     * ClientConnection interface.
     * Didn't understand WHO send a message and
     * WHO RECEIVE the message
     * @param message to be sent
     */
    @Override
    public void asyncSend(final Object message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }

    /**
     * Specify the run method of Runnable interface
     */
    @Override
    public void run() {
        Scanner in;
        String name;
        try{
            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            send("Welcome!\nWhat is your name?");
            String read = in.nextLine();
            name = read;
            server.lobby(this, name);
            while(isActive()){
                read = in.nextLine();
                notify(read);
            }
        } catch (IOException | NoSuchElementException e) {
            System.err.println("Error!" + e.getMessage());
        }finally{
            close();
        }
    }








    private synchronized void send(Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch(IOException e){
            System.err.println(e.getMessage());
        }

    }


    private synchronized boolean isActive(){
        return active;
    }



    private void close() {
        closeConnection();
        System.out.println("De-registering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

}
