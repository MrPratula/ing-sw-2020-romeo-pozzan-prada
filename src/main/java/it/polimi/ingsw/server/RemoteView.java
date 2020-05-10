package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.utils.*;

import java.io.*;


public class RemoteView extends Observable<PlayerAction> implements Observer<ServerResponse> {

    private Player player;
    private Connection connection;
    private Server server;


    /**
     * Creates a Remote view with player and connection.
     * @param connection connection to receive and send objects.
     * @param player the player corresponding this remote view.
     */
    public RemoteView(Connection connection, Player player) throws IOException {
        this.player = player;
        this.connection = connection;
        connection.addObserver(new MessageReceiver());
    }

    /**
     *
     */
    public void setServer(Server server){
        this.server = server;
    }

    /**
     * The model notifies the remote view with a ServerResponse.
     * It is received here and it is sent asynchronously via the connection to the client.
     * @param serverResponse the object to send to the client.
     */
    @Override
    public void update(ServerResponse serverResponse) {
         connection.asyncSend(serverResponse);
    }


    /**
     * It notifies the controller with a playerAction.
     * The messageReceiver class receive the message and send it via the remote view
     * to the controller.
     * @param playerAction the action to send.
     */
    public void sendAction(PlayerAction playerAction) throws ImpossibleTurnException, IOException, CellHeightException, WrongNumberPlayerException, ReachHeightLimitException, CellOutOfBattlefieldException {


        // The first player who connect is requested to give the number of players
        if (playerAction.getAction().equals(Action.NUMBER_OF_PLAYERS)){

            // Double check for nasty client
            if (playerAction.getTokenMain() == 2 || playerAction.getTokenMain() == 3) {
                Server.setNumberOfPlayers(playerAction.getTokenMain());
                connection.asyncSend(new ServerResponse(Action.NUMBER_RECEIVED, null, null, null, null));
                server.wakeUp();
                System.out.println("waking all up".toUpperCase());
            }
            else {
                connection.asyncSend(new ServerResponse(Action.WRONG_NUMBER_OF_PLAYER, null, null, null, null));
            }





        }

            // In any other cases the RemoteView send the action to the controller
        else
            notify(playerAction);
    }


    /**
     * This class receive the PlayerAction from the connection and send those
     * to the controller. It is done by calling a method inside the remote view
     * because it has the notify method.
     */
    public class MessageReceiver implements Observer<PlayerAction> {

        @Override
        public void update(PlayerAction playerAction) throws CellOutOfBattlefieldException, CellHeightException, ReachHeightLimitException, WrongNumberPlayerException, ImpossibleTurnException, IOException {

            System.out.println(String.format("Received a %s from %s",
                    playerAction.getAction().toString(), connection.getName()));

            sendAction(playerAction);
        }
    }













}
