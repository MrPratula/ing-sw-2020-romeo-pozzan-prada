package it.polimi.ingsw.server;


import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TokenColor;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.Connection;
import it.polimi.ingsw.utils.ServerResponse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server  {

    // This is for the Singleton pattern
    private static Server singleServer = null;
    private static int numberOfPlayers;

    private boolean firstTime = true;
    private static final int PORT = 12345;
    private ServerSocket serverSocket;

    private ExecutorService executor = Executors.newFixedThreadPool(128);

    // All the connection that are linked
    private List<Connection> connections = new ArrayList<Connection>();

    // All the connection active for a game session
    private Map<String, Connection> playingConnection = new HashMap<>();

    // All the connection in queue for a game
    private Map<String, Connection> waitingConnection = new HashMap<>();

    private Model model;
    private Controller controller;

    /**
     * Singleton constructor.
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


    /**
     * Set up the number of players.
     * It is received from the first remoteView.
     */
    public static void setNumberOfPlayers(int number) {
        numberOfPlayers = number;
    }


    /**
     * The servers starts and waits for clients to connect.
     * When a client join a server it is registered (save connection)
     * and it's connection is started in a asynchronous thread.
     */
    public void run() {

        System.out.println("Server listening on port: " + PORT);

        while(true){
            try {

                // Accept a client who requires for this port
                Socket socket = serverSocket.accept();
                // Crate a Connection for that specific client
                Connection connection = new Connection(socket, this);
                // Save this connection to the connections list
                registerConnection(connection);
                // Let's start the Connection run() method in an asynchronous thread
                executor.submit(connection);

            } catch (IOException e){
                System.err.println("Connection error!");
            }
        }
    }


    /**
     * Add a connection (which is unique for each client) to the connections list.
     * @param connection the connection to add.
     */
    private synchronized void registerConnection(Connection connection){
        connections.add(connection);
    }


    /**
     * It removes a Connection from the server.
     * The connection is removed from the list and removed from playingConnection.
     * @param connection the connection to remove.
     */
    public synchronized void deregisterConnection(Connection connection) throws IOException {
        connections.remove(connection);
        playingConnection.get(connection.getName()).closeConnection();
        playingConnection.remove(connection.getName());
    }


    /**
     * The lobby receives a connection and a name.
     * Those are put in waitingConnection.
     * When there are 2 or 3 players in the waiting connection,
     * the game is set up and it starts.
     */
    public synchronized void lobby(Connection connection, String name) throws IOException, InterruptedException {

        waitingConnection.put(name, connection);

        // Player 1 is always instantiated
        // Only the first one is asked for how many players
        // and till he answer the question nobody else can do this
        if (firstTime) {

            firstTime = false;
            numberOfPlayers = 0;

            List<String> keys = new ArrayList<>(waitingConnection.keySet());
            Connection c1 = waitingConnection.get(keys.get(0));

            Player player1 = new Player(c1.getName(), TokenColor.RED);
            RemoteView remoteView1 = new RemoteView(c1, player1);

            // Create the model and the controller for the current game
            model = new Model();
            controller = new Controller(model);

            // Add all the player to the list of all player in the model
            model.addPlayer(player1);

            // Link observer between model -> remoteView
            model.addObserver(remoteView1);

            // Link observer between remoteView(messageReceiver) -> Controller
            remoteView1.addObserver(controller);

            // Put player in playing connection list
            playingConnection.put(player1.getUsername(), c1);

            // Ask for how many players there will be in the game (2 or 3)
            c1.asyncSend(new ServerResponse(Action.HOW_MANY_PLAYERS, null, null, null, null));

            // Till the player 1 answer, the method is locked and nobody else can use this
            // to prevent the if check in the next if statement
            while (numberOfPlayers == 0){
                wait();
            }
            notifyAll();
        }

        // When the players are 2 or 3, based on the first player choice
        if (waitingConnection.size() == numberOfPlayers){

            // Get the name of the players and create their personal connections
            List<String> keys = new ArrayList<>(waitingConnection.keySet());
            Connection c2 = waitingConnection.get(keys.get(1));

            // Instance object for 3 players
            Connection c3;
            Player player3;
            RemoteView remoteView3;

            // Create the players with a name and a color
            Player player2 = new Player(c2.getName(), TokenColor.BLUE);

            // Create the remote view with a connection and a player
            RemoteView remoteView2 = new RemoteView(c2, player2);

            // Add all the player to the list of all player in the model

            model.addPlayer(player2);
            // Link observer between model -> remoteView
            model.addObserver(remoteView2);

            // Link observer between remoteView(messageReceiver) -> Controller
            remoteView2.addObserver(controller);

            // Put player in playing connection list
            playingConnection.put(player2.getUsername(), c2);

            // Set up all of this for a 3rd eventual player
            if (numberOfPlayers == 3) {
                c3 = waitingConnection.get(keys.get(2));
                player3 = new Player(c3.getName(), TokenColor.YELLOW);
                remoteView3 = new RemoteView(c3, player3);
                model.addPlayer(player3);
                model.addObserver(remoteView3);
                remoteView3.addObserver(controller);
                playingConnection.put(player3.getUsername(), c3);
            }

            // Clear the waiting connection
            waitingConnection.clear();

            // Create a list of god cards with lenght = number of players with random gods
            // than each player chose one of that



        }
    }
}










