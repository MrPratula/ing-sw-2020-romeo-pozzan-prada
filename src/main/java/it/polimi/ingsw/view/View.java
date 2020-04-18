package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.*;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public abstract class View extends Observable<PlayerAction> implements Observer<ServerResponse>  {

    private Player player;

    public View(Player player) {
        this.player = player;
    }

    protected Player getPlayer(){
        return player;
    }


    public void notifyRemoteController(PlayerAction playerAction) throws CellOutOfBattlefieldException, ReachHeightLimitException, CellHeightException, IOException, ImpossibleTurnException, WrongNumberPlayerException {
        notify(playerAction);
    }

    @Override
    public void update(ServerResponse serverResponse) throws ImpossibleTurnException, IOException, CellHeightException, WrongNumberPlayerException, ReachHeightLimitException, CellOutOfBattlefieldException {

        //prints the battlefield
        serverResponse.getModelCopy().getBattlefield().printCLI();

        //prints the message for the user
        System.out.print(serverResponse.getAction().getInfo());

        //wait
        Scanner in = new Scanner(System.in);
        String inputLine = in.nextLine();
        String[] inputs = inputLine.split(",");
        int posX = Integer.parseInt(inputs[0]);
        int posY = Integer.parseInt(inputs[1]);

        //calculates which token has been selected
        Token selectedToken, otherToken;
        if (getPlayer().getToken1().getTokenPosition().getPosX() == posX &&
                getPlayer().getToken1().getTokenPosition().getPosY() == posY) {
            selectedToken = getPlayer().getToken1();
            otherToken = getPlayer().getToken2();
        }  else
        if (getPlayer().getToken2().getTokenPosition().getPosX() == posX &&
                getPlayer().getToken2().getTokenPosition().getPosY() == posY) {
            selectedToken = getPlayer().getToken2();
            otherToken = getPlayer().getToken1();
        } else {
            selectedToken = null;
            otherToken = null;
        }

        //calculates which are the opponent players
        int numberOfPlayers = serverResponse.getModelCopy().getNumberOfPlayers();
        List<Player> players = serverResponse.getModelCopy().getBattlefield().getPlayers();
        Player opp1 = null, opp2 = null;
        if(numberOfPlayers==2){
            opp2 = null;
            if(players.get(0).equals(getPlayer()))  opp1 = players.get(1);
            else opp1 = players.get(0);
        }
        if(numberOfPlayers==3){                          ///si potrebbe fare meglio
            if(players.get(0).equals(getPlayer())){
                opp1 = players.get(1);
                opp2 = players.get(2);
            }
            else if(players.get(1).equals(getPlayer())){
                opp1 = players.get(0);
                opp2 = players.get(2);
            }
            else if(players.get(2).equals(getPlayer())){
                opp1 = players.get(1);
                opp2 = players.get(2);
            }
        }



        switch (serverResponse.getAction()) {

            case START_NEW_TURN:
                try{
                    //Cell cell = serverResponse.getModelCopy().getBattlefieldCopy().getCell(posX,posY);
                    Action action = Action.SELECT_TOKEN;
                    PlayerAction playerAction = new PlayerAction(action,getPlayer(),opp1,opp2,selectedToken,otherToken,null);
                    notifyRemoteController(playerAction);////////////////////

                } catch (NullPointerException e){
                    System.out.println(e.getMessage());
                } catch (CellOutOfBattlefieldException e) {
                    e.printStackTrace();//////////////////auto
                }
                break;

            case ASK_FOR_MOVE:
                try{
                    //cell is the position we want to move the token
                    Cell cell = serverResponse.getModelCopy().getBattlefieldCopy().getCell(posX,posY);
                   /* if( !serverResponse.getValidBuilds().contains(cell) ){  //non so se funziona la contains, al massimo facciamo il check con le pos
                        System.out.println("Error! You can't select this cell, try again! ");
                    }
                    */
                    Action action = Action.MOVE;
                    PlayerAction playerAction = new PlayerAction(action,getPlayer(),opp1,opp2,selectedToken,otherToken,cell);
                    notifyRemoteController(playerAction);////////////////////

                } catch (NullPointerException e){
                    System.out.println(e.getMessage());
                } catch (CellOutOfBattlefieldException e) {
                    e.printStackTrace();//////////////////auto
                }
                break;

            case ASK_FOR_BUILD:

                try{
                    //cell is the cell we want to increment
                    Cell cell = serverResponse.getModelCopy().getBattlefieldCopy().getCell(posX,posY);
                   /* if( !serverResponse.getValidBuilds().contains(cell) ){  //non so se funziona la contains, al massimo facciamo il check con le pos
                        System.out.println("Error! You can't select this cell, try again! ");
                    }
                    */
                    Action action = Action.BUILD;
                    PlayerAction playerAction = new PlayerAction(action,getPlayer(),opp1,opp2,selectedToken,otherToken,cell);
                    notifyRemoteController(playerAction);////////////////////

                } catch (NullPointerException e){
                    System.out.println(e.getMessage());
                } catch (CellOutOfBattlefieldException e) {
                    e.printStackTrace();//////////////////auto
                }

                break;
        }

    }




}
