package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.ClientConnection;
import it.polimi.ingsw.utils.Message;
import it.polimi.ingsw.utils.Observer;

public class RemoteView extends View {


    private ClientConnection clientConnection;

    public RemoteView(Player player, String opponent, ClientConnection c) {//list<string>
        super(player);
        this.clientConnection = c;
        c.addObserver(new MessageReceiver());
        c.asyncSend("Your opponent is: " + opponent);  //are
    }


    public class MessageReceiver implements Observer<Message> {

        @Override
        public void update(Message message) {
            System.out.println("Received: " + message);

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

                //String[] inputs = message.split(",");
                //handleMove(Integer.parseInt(inputs[0]), Integer.parseInt(inputs[1]));

            }catch(IllegalArgumentException e){
                clientConnection.asyncSend("Error!");
            }
        }


    }



}
