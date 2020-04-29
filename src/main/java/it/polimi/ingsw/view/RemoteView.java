package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.PlayerAction;
import it.polimi.ingsw.utils.ServerResponse;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;


public class RemoteView extends Observable<PlayerAction> implements Observer<ServerResponse> {

    Socket socket;
    Player player;
    OutputStream outputStream;
    InputStream inputStream;


    /**
     * Create a Remote view and instance the input and output stream.
     * @param socket connection to create the stream.
     * @param player the player corresponding this remote view.
     */
    public RemoteView(Socket socket, Player player) throws IOException {
        this.socket = socket;
        this.player = player;
        this.outputStream = socket.getOutputStream();
        this.inputStream = socket.getInputStream();
    }


    /**
     * Here the RV receive a PlayerAction and send it to the controller.
     * This happen while there is an active client.
     */
    public void run() throws IOException, ClassNotFoundException, CellOutOfBattlefieldException, ReachHeightLimitException, CellHeightException, ImpossibleTurnException, WrongNumberPlayerException {
        while (true) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                PlayerAction playerAction = (PlayerAction)objectInputStream.readObject();
                notify(playerAction);
            } catch(NoSuchElementException e){
                System.out.println("Connection closed from the client side");
            } finally {
                inputStream.close();
                outputStream.close();
                socket.close();
            }
        }
    }


    /**
     * When the model terminate his process, it notify the remoteView with a ServerResponse.
     * It is sent via Socket to the Client.
     * @param serverResponse the object to send to the client.
     */
    @Override
    public void update(ServerResponse serverResponse) throws IOException {
         ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
         objectOutputStream.writeObject(serverResponse);
    }
}
