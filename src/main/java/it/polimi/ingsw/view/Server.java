package it.polimi.ingsw.view;


import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
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

    List<Player> allPlayers = new ArrayList<>();
    //int numberOfPlayers;

    private List<Connection> connections = new ArrayList<Connection>();
    private ExecutorService executor = Executors.newFixedThreadPool(128);

    private Map<Connection, Connection> playingConnection2 = new HashMap<>();
    private Map<Connection, Map<Connection,Connection>> playingConnection3 = new HashMap<>();  // or inside: Connection,Map<Connection,Connection>
    private Map<String,Connection> waitingConnection2 = new HashMap<>();
    private Map<String,Connection> waitingConnection3 = new HashMap<>();



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

    /**
     * The servers starts and waits for clients joining the game
     */
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

    public synchronized void deregisterConnection(Connection c) throws IOException {
        connections.remove(c);                                     //rimuove dalla lista delle connessioni
        if(playingConnection2.containsKey(c)) {                    //sta stava anche giocando
            Connection opponent = playingConnection2.get(c);       //trovo il nemico
            if (opponent != null) {
                opponent.closeConnection();
                playingConnection2.remove(c);
                playingConnection2.remove(opponent);
            }
        }
        else if(playingConnection3.containsKey(c)){
   /*da fare

            Map<Connection,Connection> newMap = new HashMap<>();   //le due connessioni contro cui stava giocando

            Map<Connection,Connection> opponents = playingConnection3.get(c);   //le due connessioni contro cui stava giocando
            if(opponents.size()==2)  playingConnection3.remove(c);              //continunano a giocare in due, forse dovrei mettere null
            if(opponents.size()<2){
                for(Map.Entry<Connection,Connection> entry : opponents.entrySet()){
                    if(entry.getKey()!=null) entry.
                    playingConnection3.remove(opponents);
                    playingConnection3.remove(opponent);
                }
     */
                //cosi funziona ma non possiamo

            /* List<String> aux = new ArrayList<>();
                HashMap<String, String> newMap = new HashMap<>();
                for( Map.Entry<String, HashMap<String,String>> entry : map.entrySet() ) {
                    String key = entry.getKey();
                    if (!key.equals(s)) {
                        aux.add(key);
                    }
                }
                newMap.put(aux.get(0), aux.get(1));
                newMap.put(aux.get(1), aux.get(0));
                return newMap;
            */

            }
   //     }

    }
    /**
     * Lobby of the game, where the players join each other.
     * Here we group the players based how many players they
     * want to play with, 2/3, and the first group to reach
     * the necessary number, starts the game.
     * @param c: the connection just added to the lobby.
     * @param name: name of the player just added
     * @throws IOException
     */
    public synchronized void lobby(Connection c, String name, int numberOfPlayers) throws IOException {

        if(numberOfPlayers==2){
            waitingConnection2.put(name, c);
            if(waitingConnection2.size()==2) {
                startGame2Players(waitingConnection2);
            }
        }

        else if(numberOfPlayers==3){
            waitingConnection3.put(name,c);
            if(waitingConnection3.size()==3) {
                startGame3Players(waitingConnection3);
            }
        }
        else {
            throw new IllegalArgumentException("Impossible number!");  //cambiare maybe
        }
    }

    /**
     * Initialises and creates all the necessary modules and structures for a game with 2 players.
     * @param waitingConnection2: map containing the players which have to play
     * @throws IOException
     */
    private void startGame2Players(Map<String,Connection> waitingConnection2) throws IOException {

        List<String> keys = new ArrayList<>(waitingConnection2.keySet());
        Connection c1 = waitingConnection2.get(keys.get(0));
        Connection c2 = waitingConnection2.get(keys.get(1));
        RemoteView player1 = new RemoteView(c1, new Player(keys.get(0), TokenColor.RED));
        RemoteView player2 = new RemoteView(c2, new Player(keys.get(1), TokenColor.BLUE));
        Model model = new Model(new Battlefield());
        Controller controller = new Controller(model);
        model.addObserver(player1);
        model.addObserver(player2);
        player1.addObserver(controller);
        player2.addObserver(controller);
        playingConnection2.put(c1, c2);
        playingConnection2.put(c2, c1);
        waitingConnection2.clear();

        //forse
        player1.update(new ServerResponse(Action.SET_UP,null,null,null,null));
        player2.update(new ServerResponse(Action.NOT_YOUR_TURN,null,null,null,null));
    }


    /**
     * Initialises and creates all the necessary modules and structures for a game with 3 players.
     * @param waitingConnection3: map containing the players which have to play
     * @throws IOException
     */
    private void startGame3Players(final Map<String,Connection> waitingConnection3) throws IOException {

        List<String> keys = new ArrayList<>(waitingConnection3.keySet());
        final Connection c1 = waitingConnection3.get(keys.get(0));
        final Connection c2 = waitingConnection3.get(keys.get(1));
        final Connection c3 = waitingConnection3.get(keys.get(2));
        RemoteView player1 = new RemoteView(c1, new Player(keys.get(0), TokenColor.RED));
        RemoteView player2 = new RemoteView(c2, new Player(keys.get(1), TokenColor.BLUE));
        RemoteView player3 = new RemoteView(c3, new Player(keys.get(2), TokenColor.YELLOW));
        Model model = new Model(new Battlefield());
        Controller controller = new Controller(model);
        model.addObserver(player1);
        model.addObserver(player2);
        model.addObserver(player3);
        player1.addObserver(controller);
        player2.addObserver(controller);
        player3.addObserver(controller);
        playingConnection3.put(c1, new HashMap<Connection,Connection>());
        playingConnection3.get(c1).put(c2,c3);
        playingConnection3.put(c2, new HashMap<Connection,Connection>());
        playingConnection3.get(c2).put(c1,c3);
        playingConnection3.put(c3, new HashMap<Connection,Connection>());
        playingConnection3.get(c3).put(c1,c2);
        waitingConnection3.clear();


        player1.update(new ServerResponse(Action.SET_UP, model.getCopy(),null,null,"Tokencolor.RED"));
        player2.update(new ServerResponse(Action.NOT_YOUR_TURN,null,null,null,null));
        player3.update(new ServerResponse(Action.NOT_YOUR_TURN,null,null,null,null));

    }

/*      SEGUITO QUESTO:

public static void main(String args[]) {

    HashMap<String, HashMap<String, Object>> map = new HashMap<String, HashMap<String,Object>>();
    map.put("key", new HashMap<String, Object>());
    map.get("key").put("key2", "val2");

    System.out.println(map.get("key").get("key2"));
}*/













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










