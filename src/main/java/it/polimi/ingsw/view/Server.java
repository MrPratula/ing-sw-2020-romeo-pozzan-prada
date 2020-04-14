package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.Action;

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
    private List<Connection> connections = new ArrayList<Connection>();
    private Map<String, Connection> waitingConnection = new HashMap<>();
    private Map<Connection, Connection> playingConnection = new HashMap<>();

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
        System.out.println("Server listening on port: " + PORT);
        while(true){ //OGNI VOLTA CHE SI PARTE UN CLIENT , SI CONNETTE E GLI MANDA WHATS UR NAME
            try {
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket, this);
                registerConnection(connection);
                executor.submit(connection);
            } catch (IOException e){
                System.err.println("Connection error!");
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

    public synchronized void lobby(Connection c, String name){

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

            /**
             * When I create a player i have to assign them a battlefield
             * so i should create it here and pass the same battlefield to
             * all the player that share the same battlefield
             */
            Battlefield battlefield = new Battlefield();

            List<String> keys = new ArrayList<>(waitingConnection.keySet());
            Connection c1 = waitingConnection.get(keys.get(0));
            Connection c2 = waitingConnection.get(keys.get(1));

            /**
             * Here I create the player with a color and a battlefield
             */
            RemoteView player1 = new RemoteView( new Player(keys.get(0), TokenColor.RED, battlefield), keys.get(1), c1 );
            RemoteView player2 = new RemoteView( new Player(keys.get(1), TokenColor.BLUE, battlefield), keys.get(0), c2 );

            /**
             * Then I should create a remoteView,
             * but i don't know what it is.
             * Despite that I create a RemoteView class in the View package.
             *
             * Why do i need to pass both the keys.get() and the c1?
             */
            ///////////////////////////

            /**
             * Here is created the whole model I think,
             * so may be a good idea to put the battlefield here
             */
             Model model = new Model(battlefield);

            /**
             * and then pass the model to a controller, so he knows
             * what to control
             */
            Controller controller = new Controller(model);

            /**
             * then I have to bind the model with the view
             * so the model can update the view with his changes
             * and pass him the updated battlefield
             */
            //model.addObserver(player1);
            //model.addObserver(player2);

            /**
             * the same for the player view and the controller,
             * this one needs to be notified by the view when it want to do something
             */
            player1.addObserver(controller);
            player2.addObserver(controller);

            /**
             * definitely no idea what this is
             */
            playingConnection.put(c1, c2);
            playingConnection.put(c2, c1);

            /**
             * clear the waiting connection because the player
             * are added to playing connection
             */
            waitingConnection.clear();


            /**
             * ok this should be fine,
             * just need to adapt to our moves
             *
             * if i send multiple message to c1, they are received at the same time or sequentially?
             * can i make something like that?
             *
             *  c1.asyncSend(GameMessage.moveMessage);
             *  c1.asyncSend(GameMessage.buildMessage);
             *
             */

             c1.send((Object)model.getCopy());
             c2.send((Object)model.getCopy());
      /*      if(model.isPlayerTurn(player1)){
                c1.send(Action.MOVE);
                c2.send(Action.WAIT);
            } else {
                c2.send(Action.MOVE);
                c1.send(Action.WAIT);
            }
      */
            c1.send(Action.MOVE_TOKEN);
            //c2.send(Action.WAIT);
        }
    }


    /**
     * Register connection of a player
     * @param c ClientConnection associated at player to deregister
     */

    private synchronized void registerConnection(Connection c){
        connections.add(c);
    }



    /**
     * Deregister a ClientConnection of a player
     * @param c ClientConnection associated at player to deregister
     */
    public synchronized void deregisterConnection(Connection c) {
        connections.remove(c);
        Connection opponent = playingConnection.get(c);

        if(opponent != null) {
            opponent.closeConnection();
            playingConnection.remove(c);
            playingConnection.remove(opponent);
            //Iterator<String> iterator = waitingConnection.keySet().iterator();
            //while(iterator.hasNext()){
            //    if(waitingConnection.get(iterator.next())==c){
            //        iterator.remove();
            //    }
            //}
        }

    }
}










