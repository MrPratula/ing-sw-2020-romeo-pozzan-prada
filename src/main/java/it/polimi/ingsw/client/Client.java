package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TokenColor;
import it.polimi.ingsw.utils.*;

import java.io.*;
import java.net.Socket;


public class Client extends Observable<ServerResponse> implements Observer<PlayerAction> {

    private final int port;
    private final String ip;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    private boolean active;


    /**
     * Constructor.
     */
    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }


    /**
     * Tells if the client is active or not
     * @return true or false.
     */
    public synchronized boolean isActive() {
        return this.active;
    }


    /**
     * Set active true or false.
     * @param active true or false.
     */
    public synchronized void setActive(boolean active) {
        this.active = active;
    }


    /**
     * It continues to receive message from the server, till it is received a
     * GAME_OVER message.
     * Not a PLAYER_LOST because a player who lost can spectate the other 2.
     * @param objectInputStream where to receive the player action.
     * @return the thread that listen to the server.
     */
    public Thread asyncReadFromSocket(final ObjectInputStream objectInputStream) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    while (isActive()) {

                        Object serverResponse = objectInputStream.readObject();

                        // If the object is a server response, it is sent to the view
                        if (serverResponse instanceof ServerResponse) {
                            notifyView((ServerResponse)serverResponse);
                        } else {
                            throw new IllegalArgumentException();
                        }
                    }
                } catch (IOException | ImpossibleTurnException | ClassNotFoundException | CellHeightException | WrongNumberPlayerException | ReachHeightLimitException | CellOutOfBattlefieldException e) {
                    System.out.println("qualche tipo di errore strano mi fa chiudere il socket");
                    e.printStackTrace();
                    setActive(false);
                }
            }
        });
        thread.start();
        return thread;
    }


    /**
     * It creates a new thread and use it to send a playerAction via socket.
     * @param playerAction action to send.
     * @param objectOutputStream socket where to send.
     */
    public void asyncSend(final PlayerAction playerAction, final ObjectOutputStream objectOutputStream){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    objectOutputStream.reset();
                    objectOutputStream.writeObject(playerAction);
                    objectOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * A client create a Socket to communicate and
     * one Thread to always listen to the socket.
     */
    public void run() throws IOException {

        Socket socket = new Socket(ip, port);
        System.out.println("Connection established!");
        setActive(true);

        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());

        // Create a view for this client and set up the observers
        View view = new View();
        view.addObserver(this);
        addObserver(view);

        try{
            Thread t0 = asyncReadFromSocket(objectInputStream);
            t0.join();

        } catch (InterruptedException e) {
            System.out.println("Connection closed from CLIENT side");
        } finally {
            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
        }
    }


    /**
     * The view receive the serverResponse and send it to the View.
     * @param serverResponse the message to send to the view.
     */
    public void notifyView(ServerResponse serverResponse) throws ImpossibleTurnException, IOException, CellHeightException, WrongNumberPlayerException, ReachHeightLimitException, CellOutOfBattlefieldException {
        notify(serverResponse);
    }


    /**
     * When a View has calculated the PlayerAction, it is
     * sent to the socket.
     * @param playerAction the action to send.
     */
    @Override
    public void update(PlayerAction playerAction) {
        asyncSend(playerAction, objectOutputStream);
    }
}