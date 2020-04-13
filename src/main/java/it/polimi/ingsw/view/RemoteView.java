package it.polimi.ingsw.view;

import it.polimi.ingsw.exception.CellOutOfBattlefieldException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.Observer;

import java.util.Random;


/**
 * The remote view should be the View inside the server.
 * It acts like a normal view in MVC pattern for letting the game run inside a server.
 * It communicates via Socket with the View to let player know about changes.
 */
public class RemoteView extends View {

    private class MessageReceiver implements Observer<String> {

        @Override
        public void update(String message) {

            System.out.println("Received: " + message);  //stampa sul server la scelta del client
            int cont = 0;       //++
            try{
                switch(cont){
                    case 0:
                        Action action = Action.SELECT_TOKEN;
                        String[] inputs = message.split(",");
                        int posX = Integer.parseInt(inputs[0]);
                        int posY = Integer.parseInt(inputs[1]);
                        Token token;
                        if (getPlayer().getToken1().getTokenPosition().getPosX() == posX &&
                                getPlayer().getToken1().getTokenPosition().getPosY() == posY) {
                            token = getPlayer().getToken1();
                        }  else if (getPlayer().getToken2().getTokenPosition().getPosX() == posX &&
                                getPlayer().getToken2().getTokenPosition().getPosY() == posY) {
                            token = getPlayer().getToken2();
                        } else token = null;
                        try{
                            notifyController(getPlayer(),action,token,null);////////////////////
                        } catch (NullPointerException e){
                            System.out.println(e.getMessage());
                        } catch (CellOutOfBattlefieldException e) {
                            e.printStackTrace();//////////////////auto
                        }
                    case 1:


                }
                //////////////////.parseInput(message);/////////////////////////chiama il model
                //notifyController(playerAction);//////////////////////////////////////////////per valutare la partita in gioco
            } catch (IllegalArgumentException e) {
                connection.send("Error! Make your move");
            }
        }
    }

   // private Player player;
    private Connection connection;

 /*   public Player getPlayer(){
         return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
*/
    public RemoteView(Player player, String opponent, Connection c) {
        super(player);
    //    setPlayer(player);
        this.connection = c;
        c.addObserver(new MessageReceiver());
        //Here we assign randomly the GodCard to the player
        GodCard[] allGods = GodCard.values();
        Random rand = new Random();
        int index = rand.nextInt(allGods.length);
        GodCard godCard= allGods[index]; ///////////////////////////////problema: gestire godcard uguali
        c.send("Welcome "+ player.getUsername() +", your opponent is: "+ opponent+ ".\t\tThe GodCard we assign you for the game is "+godCard.name()+
                "! Here's his power: "+godCard.getInfo());   //+".\t\tWhere do you want to put your first token? (x,y):"
    }

    /**
     * It shows the copy of the model
     * @param model
     */
    @Override
    protected void showModel(Model model) {
        connection.send(model.getCopy() + "\tMake your move");////////
    }


}
