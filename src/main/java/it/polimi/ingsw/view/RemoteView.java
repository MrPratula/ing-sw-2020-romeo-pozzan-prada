package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.Observer;


/**
 * The remote view should be the View inside the server.
 * It act like a normal view in MVC pattern for letting the game run inside a server.
 * It communicate via Socket with the View to let player know about changes.
 */
public class RemoteView extends View {


    private Connection connection;


    private class MessageReceiver implements Observer<String> {

        @Override
        public void update(String message) {
            System.out.println("Received: " + message);  //stampa sul server la scelta del client
            /**
             * here we need to recognize what type of
             * message we send, because till now we
             * don't send string but pre-made message that need to be distinguished
             */
            try{
                /**
                 * here the tris code split the input to get x-coords and y-coords to
                 * select where to put X or O
                 */
                Choice choice = Choice.parseInput(message);/////////////////////////chiama il model
                processChoice(choice);//////////////////////////////////////////////per valutare la partita in gioco
            } catch (IllegalArgumentException e) {
                connection.send("Error! Make your move");
            }
        }
    }


    public RemoteView(Player player, String opponent, Connection c) {
        super(player);
        this.connection = c;
        c.addObserver(new MessageReceiver());
        c.send("Welcome"+ player.getUsername() +", your opponent is:"+ opponent );
    }



    @Override
    protected void showModel(Model model) {
        connection.send(model.getCopy() + "\tMake your move");////////
    }



}
