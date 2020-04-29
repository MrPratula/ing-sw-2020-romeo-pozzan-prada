package it.polimi.ingsw.view;


import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.RemoteController;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.Connection;
import it.polimi.ingsw.utils.PlayerAction;
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


    private static Server singleServer = null;
    private final ServerSocket serverSocket;
    private static final int PORT = 12345;

    List<Player> allPlayers;
    int numberOfPlayers;

    private List<Connection> connections = new ArrayList<Connection>();
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private Map<Connection, Connection> playingConnection = new HashMap<>();
    private Map<String, Connection> waitingConnection = new HashMap<>();


    /**
     * Singleton constructor. Return the one that exist.
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


    public void run() {
        System.out.println("Server listening on port: " + PORT);
        while(true){
            try {
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket, this);
                registerConnection(connection);
                // fa partire la run della connection
                executor.submit(connection);
            } catch (IOException e){
                System.err.println("Connection error!");
            }
        }
    }

    private synchronized void registerConnection(Connection c){
        connections.add(c);
    }


    public synchronized void deregisterConnection(Connection c){
        connections.remove(c);
        Connection opponent = playingConnection.get(c);
        if(opponent != null){
            opponent.closeConnection();
            playingConnection.remove(c);
            playingConnection.remove(opponent);

        }
    }






    public synchronized void lobby(Connection c, String name) throws IOException {

        waitingConnection.put(name, c);

        if(waitingConnection.size() == 2){
            List<String> keys = new ArrayList<>(waitingConnection.keySet());
            Connection c1 = waitingConnection.get(keys.get(0));
            Connection c2 = waitingConnection.get(keys.get(1));
            RemoteView player1 = new RemoteView(c1, new Player(keys.get(0), TokenColor.RED));
            RemoteView player2 = new RemoteView(c2, new Player(keys.get(1), TokenColor.BLUE));
            Model model = new Model(new Battlefield());
            Controller controller = new Controller(model);
            model.addObserver(player1);
            model.addObserver(player2);
            player1.addObserver(controller);
            player2.addObserver(controller);
            playingConnection.put(c1, c2);
            playingConnection.put(c2, c1);
            waitingConnection.clear();
        }

    }



















    /*
    public void run() throws IOException, ClassNotFoundException {

        System.out.println("Server has started up on port: " + PORT);

        // Here the server get the socket.
        Socket socket = serverSocket.accept();





        // Create input stream and output stream
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        // Create object for input and output
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        // Ask for name of first player
        ServerResponse serverResponse = new ServerResponse(Action.WELCOME, null, null, null, null);
        objectOutputStream.writeObject(serverResponse);

        // Receive first player and add him to the list
        PlayerAction playerAction = (PlayerAction) objectInputStream.readObject();
        allPlayers.add(playerAction.getPlayer());

        // Ask for how many players
        String message = String.format("Hi %s!",allPlayers.get(0).getUsername().toUpperCase());
        serverResponse = new ServerResponse(Action.HOW_MANY_PLAYERS, null, null, null, message);
        objectOutputStream.writeObject(serverResponse);

        // Receive how many players. This number is set in tokenMain field
        playerAction = (PlayerAction) objectInputStream.readObject();
        numberOfPlayers = playerAction.getTokenMain();




        while (true) {


        }



    }
*/







}










