package it.polimi.ingsw.server;


import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.GameMessage;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class Server {

    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private Map<String, ClientConnection> waitingConnection = new HashMap<>();
    private Map<ClientConnection, ClientConnection> playingConnection = new HashMap<>();

    /**
     * Server's constructor
     * @throws IOException if can not launch a server
     */
    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }


    /**
     * Start a server
     */
    public void run(){
        while(true){
            try {
                Socket newSocket = serverSocket.accept();
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket, this);
                executor.submit(socketConnection);
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }


    /**
     * Waiting room for a player while other player are connecting
     * The players object are created right there
     * and added to a map
     *
     * @param c ClientConnection
     * @param name name of the player
     */

    public synchronized void lobby(ClientConnection c, String name){

        /**
         * When I create a player i have to assign them a battlefield
         * so i should create it here and pass the same battlefield to
         * all the player that share the same battlefield
         */
        Battlefield battlefield = new Battlefield();

        /**
         * whiting connection is a map of <PlayerName, ClientConnection>
         * it bind a player through his name to a single connection to the server
         */
        waitingConnection.put(name, c);

        /**
         * this happen when 2 players are added to waitingConnection map
         * keys.get(0) is the name of the player
         * keys.get(1) is the connection of the player
         */
        if (waitingConnection.size() == 2) {
            List<String> keys = new ArrayList<>(waitingConnection.keySet());
            ClientConnection c1 = waitingConnection.get(keys.get(0));
            ClientConnection c2 = waitingConnection.get(keys.get(1));

            /**
             * Here I create the player with a color and a battlefield
             */
            Player player1 = new Player(keys.get(0), TokenColor.RED, battlefield);
            Player player2 = new Player(keys.get(0), TokenColor.BLUE, battlefield);

            /**
             * Then I should create a remoteView,
             * but i don't know what it is.
             * Despite that I create a RemoteView class in the View package.
             *
             * Why do i need to pass both the keys.get() and the c1?
             */
            View player1View = new RemoteView(player1, keys.get(1), c1);
            View player2View = new RemoteView(player2, keys.get(0), c2);

            /**
             * Here is created the whole model I think,
             * so may be a good idea to put the battlefield here
             */
            Game game = new Game(battlefield);

            /**
             * and then pass the model to a controller, so he knows
             * what to control
             */
            Controller controller = new Controller(game);

            /**
             * then I have to bind the model with the view
             * so the model can update the view with his changes
             * and pass him the updated battlefield
             */
            game.addObserver(player1View);
            game.addObserver(player2View);

            /**
             * the same for the player view and the controller,
             * this one needs to be notified by the view when it want to do something
             */
            player1View.addObserver(controller);
            player2View.addObserver(controller);

            /**
             * definitely no idea what this is
             */
            playingConnection.put(c1, c2);
            playingConnection.put(c2, c1);

            /**
             * nor idea for this
             */
            waitingConnection.clear();


            /**
             * maybe this is the way the model get his copy of the battlefield,
             * but why he ask for this and it is not pushed by the notify when
             * it changes?
             */
            c1.asyncSend(game.getBoardCopy());
            c2.asyncSend(game.getBoardCopy());

            /**
             * ok this should be fine,
             * just need to adapt to our moves
             */
            if(game.isPlayerTurn(player1)){
                c1.asyncSend(GameMessage.moveMessage);
                c2.asyncSend(GameMessage.waitMessage);
            } else {
                c2.asyncSend(GameMessage.moveMessage);
                c1.asyncSend(GameMessage.waitMessage);
            }
        }
    }


    /**
     * Deregister a ClientConnection of a player
     * @param c ClientConnection associated at player to deregister
     */
    public synchronized void deregisterConnection(ClientConnection c) {
        ClientConnection opponent = playingConnection.get(c);
        if(opponent != null) {
            opponent.closeConnection();
        }
        playingConnection.remove(c);
        playingConnection.remove(opponent);
        Iterator<String> iterator = waitingConnection.keySet().iterator();
        while(iterator.hasNext()){
            if(waitingConnection.get(iterator.next())==c){
                iterator.remove();
            }
        }
    }
}










