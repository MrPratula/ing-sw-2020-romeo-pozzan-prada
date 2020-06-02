package it.polimi.ingsw.server;


import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.cli.GodCard;
import it.polimi.ingsw.cli.Model;
import it.polimi.ingsw.cli.Player;
import it.polimi.ingsw.cli.TokenColor;
import it.polimi.ingsw.utils.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server  {

    // This is for the Singleton pattern
    private static Server singleServer = null;
    private static int numberOfPlayers;

    private boolean firstTime;
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
    Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    /**
     * Return the name of all player in waiting room to avoid have players with same name
     */
    public List<String> getPlayersName() {
        if (waitingConnection.isEmpty())
            return null;
        return new ArrayList<>(waitingConnection.keySet());
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
        firstTime = true;

        while(true){
            try {

                // Accept a client who requires for this port
                Socket socket = serverSocket.accept();
                // Create a Connection for that specific client
                Connection connection = new Connection(socket, this);
                // Save this connection in the connections list
                registerConnection(connection);
                // Let's start the Connection run() method in an asynchronous thread

                executor.submit(connection);

            } catch (IOException e){
                System.err.println("Connection error!");
                break;
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
    public synchronized void deregisterConnection(Connection connection) {
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
    public synchronized void lobby(Connection connection, String name) throws IOException {

        System.out.println(name.toUpperCase()+ " entered into the lobby");

        waitingConnection.put(name, connection);

        // Player 1 is always instantiated
        // Only the first one is asked for how many players
        // Others player go in wait
        if (firstTime) {

            firstTime = false;
            numberOfPlayers = -1;
            setUpFirstPlayer();

        } else {
            Pack pack = new Pack(Action.WAIT_OTHER_PLAYERS_TO_CONNECT);
            connection.asyncSend(new ServerResponse(null, pack));
        }

        // When the players are 2 or 3, based on the first player choice
        if (waitingConnection.size() == numberOfPlayers){

            // Get the name of the players and create their personal connections
            List<String> keys = new ArrayList<>(waitingConnection.keySet());

            Connection c2;
            Connection c3;
            Player player3;
            RemoteView remoteView3;

            // Remember the hash map do not enqueue the value, but it is put on top
            // That's the reason for strange values in keys.get(n)
            if (waitingConnection.size()==2) {
                c2 = waitingConnection.get(keys.get(0));
            }
            else {
                c2 = waitingConnection.get(keys.get(1));
            }

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
                c3 = waitingConnection.get(keys.get(0));
                player3 = new Player(c3.getName(), TokenColor.YELLOW);
                remoteView3 = new RemoteView(c3, player3);
                model.addPlayer(player3);
                model.addObserver(remoteView3);
                remoteView3.addObserver(controller);
                playingConnection.put(player3.getUsername(), c3);
            }
            // Set up all the remaining stuff for the game to start
            initGame();

            // Clear the waiting connection
            waitingConnection.clear();
        }
    }


    /**
     * The first player who join the lobby is asked for how much players he want to play.
     * When he answer other players can join the lobby.
     * Here are created the model, the controller and the remote view and all are linked up.
     */
    public void setUpFirstPlayer () throws IOException {

        List<String> keys = new ArrayList<>(waitingConnection.keySet());
        Connection c1 = waitingConnection.get(keys.get(0));

        Player player1 = new Player(c1.getName(), TokenColor.RED);
        RemoteView remoteView1 = new RemoteView(c1, player1);
        remoteView1.setServer(this);

        // Create the model (and battlefield) and the controller for the current game
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
        c1.asyncSend(new ServerResponse(null, new Pack(Action.HOW_MANY_PLAYERS)));

        // Receive a message from the first player
        PlayerAction playerAction = c1.listenSocket();

        // And loop it till the message is correct
        boolean needToLoop = true;
        while (needToLoop) {

            if (playerAction.getAction().equals(Action.NUMBER_OF_PLAYERS)) {

                // Double check for nasty client
                // Set the number and brake the loop
                if (playerAction.getTokenMain() == 2 || playerAction.getTokenMain() == 3) {
                    setNumberOfPlayers(playerAction.getTokenMain());
                    c1.asyncSend(new ServerResponse(null, new Pack(Action.NUMBER_RECEIVED)));
                    needToLoop = false;

                    // Cached a nasty client. It is not accepted
                } else {
                    c1.asyncSend(new ServerResponse(null, new Pack(Action.WRONG_NUMBER_OF_PLAYER)));
                }
            }
        }
    }


    /**
     * Set the turn on first player, than create a random list of 2 or 3 god cards
     * and update the turn to the second player.
     * Ask the second player what god he want to use and send a wait message to the 1st and 3rd players.
     */
    public void initGame() {

        model.setTurn(TokenColor.RED);

        List<GodCard> godsDeck = new ArrayList<>(Arrays.asList(GodCard.values()).subList(0, 14));

        List<String> keys = new ArrayList<>(waitingConnection.keySet());

        // Build a string with all the god cards
        StringBuilder text= new StringBuilder("There are the following Gods available:");
        for (GodCard god: godsDeck) {
            text.append("\n").append(god.name().toUpperCase());
            text.append("\n").append(god.toString());
        }

        // Remember the hash map do not enqueue the value, but it is put on top
        // That's the reason for strange values in keys.get(n)
        if (waitingConnection.size()==2) {
            Connection c1 = waitingConnection.get(keys.get(1));
            Connection c2 = waitingConnection.get(keys.get(0));

            List<Player> allPlayers = model.getAllPlayers();

            Pack player1Pack = new Pack(Action.CHOOSE_GOD_CARD_TO_PLAY);
            Pack player2Pack = new Pack(Action.WAIT_AND_SAVE_PLAYER_FROM_SERVER);

            player1Pack.setPlayer(allPlayers.get(1));
            player2Pack.setPlayer(allPlayers.get(0));

            player1Pack.setGodCards(godsDeck);
            player1Pack.setMessageInTurn(text.toString());
            player1Pack.setNumberOfPlayers(2);


            c1.asyncSend(new ServerResponse(null, player1Pack));
            c2.asyncSend(new ServerResponse(null, player2Pack));
        }
        else{
            Connection c1 = waitingConnection.get(keys.get(2));
            Connection c2 = waitingConnection.get(keys.get(1));
            Connection c3 = waitingConnection.get(keys.get(0));

            List<Player> allPlayers = model.getAllPlayers();

            Pack player1Pack = new Pack(Action.CHOOSE_GOD_CARD_TO_PLAY);
            Pack player2Pack = new Pack(Action.WAIT_AND_SAVE_PLAYER_FROM_SERVER);
            Pack player3Pack = new Pack(Action.WAIT_AND_SAVE_PLAYER_FROM_SERVER);

            player1Pack.setPlayer(allPlayers.get(2));
            player2Pack.setPlayer(allPlayers.get(1));
            player3Pack.setPlayer(allPlayers.get(0));

            player1Pack.setGodCards(godsDeck);
            player1Pack.setMessageInTurn(text.toString());
            player1Pack.setNumberOfPlayers(3);

            c1.asyncSend(new ServerResponse(null, player1Pack));
            c2.asyncSend(new ServerResponse(null, player2Pack));
            c3.asyncSend(new ServerResponse(null, player3Pack));
        }
    }


    /**
     * It receive the deck and the subDeck.
     * It draws a card from deck and add it to the subDeck.
     * @param godsDeck the deck.
     * @param godInGame the subDeck.
     */
    public void drawAGod (List<GodCard> godsDeck, List<GodCard>godInGame){

        int pick = new Random().nextInt(godsDeck.size());
        GodCard randomGod = godsDeck.get(pick);

        godInGame.add(randomGod);
        godsDeck.remove(randomGod);
    }

}
















