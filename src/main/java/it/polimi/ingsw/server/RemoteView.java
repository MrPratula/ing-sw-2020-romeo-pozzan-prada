package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.cli.Player;
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
    public RemoteView(Connection connection, Player player) {
        this.player = player;
        this.connection = connection;
        connection.addObserver(new MessageReceiver());
    }

    public Player getPlayer(){
        return this.player;
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
            System.out.println("aooooo");
            sendAction(playerAction);
        }
    }













}
