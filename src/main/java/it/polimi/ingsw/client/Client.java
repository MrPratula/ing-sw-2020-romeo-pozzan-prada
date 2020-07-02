package it.polimi.ingsw.client;
import it.polimi.ingsw.gui.SwingView;
import it.polimi.ingsw.utils.*;

import java.io.*;
import java.net.Socket;

/**
 * The client is an object that communicate both with remote view via socket
 * and with view or swing view via observers.
 * It handle the connection client side and notify the view and send object into the socket
 */
public class Client extends Observable<ServerResponse> implements Observer<PlayerAction> {

    /**
     * The client port of the socket connection
     */
    private final int port;

    /**
     * The IPv4 address to communicate via socket
     */
    private final String ip;

    /**
     * The object of the socket used to send out to the server side
     */
    private ObjectOutputStream objectOutputStream;

    /**
     * Boolean value that tells if this client is active or not
     */
    private boolean active;


    /**
     * Create a Client with provided IP and port
     */
    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }


    /**
     * Tells if the client is active or not
     * @return true or false
     */
    public synchronized boolean isActive() {
        return this.active;
    }


    /**
     * Set active value to true or false
     * @param active true or false
     */
    public synchronized void setActive(boolean active) {
        this.active = active;
    }


    /**
     * It continues to receive messages from the server until it received a
     * GAME_OVER message
     * Not until a PLAYER_LOST arrives because a player who lost can spectate the other 2
     * @param objectInputStream where to receive the player action
     * @return the thread that listen to the server
     */
    public Thread asyncReadFromSocket(final ObjectInputStream objectInputStream) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    while (isActive()) {

                        Object serverResponse = objectInputStream.readObject();

                        if (serverResponse instanceof ServerResponse) {
                            notifyView((ServerResponse)serverResponse);
                        } else {
                            System.err.println("Received a not ServerResponse object!");
                            throw new IllegalArgumentException();
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("Connection closed, the server shut down!");
                    setActive(false);
                }
            }
        });
        thread.start();
        return thread;
    }


    /**
     * It creates a new thread and use it to send a playerAction via socket
     * @param playerAction action to send
     * @param objectOutputStream socket where to send
     */
    public void asyncSend(final PlayerAction playerAction, final ObjectOutputStream objectOutputStream){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    objectOutputStream.reset();
                    objectOutputStream.flush();
                    objectOutputStream.writeObject(playerAction);
                    objectOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * A client create a Socket to communicate and one Thread that listen to the socket while the connection is active
     * @throws IOException if the client can't read properly from the socket
     */
    public void run(boolean useGUI) throws IOException {

        Socket socket = new Socket(ip, port);
        System.out.println("Connection established!");
        setActive(true);

        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

        View view;

        // Create a view for this client and set up the observers
        if(!useGUI)
            view = new View();
        else
            view = new SwingView();

        view.addObserver(this);
        addObserver(view);

        try{
            Thread t0 = asyncReadFromSocket(objectInputStream);
            t0.join();

        } catch (InterruptedException e) {
            System.err.println("Connection closed from CLIENT side");
        } finally {
            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
        }
    }


    /**
     * The view receive the serverResponse and send it to the View
     * @param serverResponse the message to send to the view
     * @throws IOException if the object can't be sent into the socket
     */
    public void notifyView(ServerResponse serverResponse) throws IOException {
        notify(serverResponse);
    }


    /**
     * When a View has calculated the PlayerAction, it is
     * sent to the socket
     * @param playerAction the action to send
     */
    @Override
    public void update(PlayerAction playerAction) {
        asyncSend(playerAction, objectOutputStream);
    }
}