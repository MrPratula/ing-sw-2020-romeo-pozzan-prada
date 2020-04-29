package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.*;

import java.io.IOException;
import java.util.ArrayList;
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

    /**
     * When needed, it computes the input of the user, properly divided.
     * @return an Array of Strings, containing the  user input.
     */
    public String[] getUserInput(){
        Scanner in = new Scanner(System.in);
        String inputLine = in.nextLine();
        String[] inputs = inputLine.split(",");
        return inputs;
    }


    /**
     * When needed, it returns the token that has just been selected from the user,
     * and even the not selected one (it can be useful).
     * @param serverResponse: the response passed throught the observers from the server.
     * @param posX: the first input of the user, i.e. the column of the battlefield.
     * @param posY: the second input of the user, i.e. the row of the battlefield.
     * @return a list containing the selected and the not selected token.
     */
    public List<Token> computeTokens(ServerResponse serverResponse, int posX, int posY){

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

        List<Token> tokens = new ArrayList<>();
        tokens.add(selectedToken);
        tokens.add(otherToken);
        return tokens;
    }



    /**
     * When needed, it returns the opponent players(1 or 2) of the player associated to this view.
     * @param serverResponse: the response passed throught the observers from the server.
     * @return a list containing the opponent players.
     */
    public List<Player> computeOpponentPlayers (ServerResponse serverResponse){

        int numberOfPlayers = serverResponse.getModelCopy().getAllPlayers().size();
        List<Player> players = serverResponse.getModelCopy().getAllPlayers();
        Player opp1 = null, opp2 = null;
        if(numberOfPlayers==2){
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

        List<Player> opponents = new ArrayList<>();
        opponents.add(opp1);
        opponents.add(opp2);
        return opponents;
    }


    public void notifyRemoteController(PlayerAction playerAction) throws CellOutOfBattlefieldException, ReachHeightLimitException, CellHeightException, IOException, ImpossibleTurnException, WrongNumberPlayerException {
        notify(playerAction);
    }


    @Override
    public void update(ServerResponse serverResponse) throws ImpossibleTurnException, IOException, CellHeightException, WrongNumberPlayerException, ReachHeightLimitException, CellOutOfBattlefieldException {

        switch (serverResponse.getAction()) {


            case ASK_FOR_PROMETHEUS_POWER:
                //prints the battlefield
                serverResponse.getModelCopy().getBattlefield().printCLI();
                //prints the message for the user
                System.out.print(serverResponse.getAction().getInfo());
                //compute the user input, IN THIS CASE IT'S JUST yes/no
                Scanner in = new Scanner(System.in);
                String input = in.nextLine();

                if(input.equals("yes")){
                    System.out.println("Which token do you want to build with? (x,y)");
                    //compute the user input
                    int posX = Integer.parseInt(getUserInput()[0]);
                    int posY = Integer.parseInt(getUserInput()[1]);
                    //calculates which token has been selected
                    Token selectedToken = computeTokens(serverResponse, posX, posY).get(0);
                    Token otherToken = computeTokens(serverResponse, posX, posY).get(1);
                    //compute opponent players
                    Player opp1 = computeOpponentPlayers(serverResponse).get(0);
                    Player opp2 = computeOpponentPlayers(serverResponse).get(1);
                    try{
                        Action action = Action.PROMETHEUS_POWER;
                        PlayerAction playerAction = new PlayerAction(action,getPlayer(),opp1,opp2,selectedToken.getId(),otherToken.getId(),null,null, true);
                        notifyRemoteController(playerAction);////////////////////
                    } catch (NullPointerException e){
                        System.out.println(e.getMessage());
                    } catch (CellOutOfBattlefieldException e) {
                        e.printStackTrace();//////////////////auto
                    }
                }
                else if(input.equals("no")){
                    //prints the message for the user
                    System.out.println("Where do you want to move your token? (x,y)");
                            //System.out.print(serverResponse.getAction().getInfo());    errato
                    //compute the user input
                    int posX = Integer.parseInt(getUserInput()[0]);
                    int posY = Integer.parseInt(getUserInput()[1]);
                    //calculates which token has been selected
                    Token selectedToken = computeTokens(serverResponse, posX, posY).get(0);
                    Token otherToken = computeTokens(serverResponse, posX, posY).get(1);
                    //compute opponent players
                    Player opp1 = computeOpponentPlayers(serverResponse).get(0);
                    Player opp2 = computeOpponentPlayers(serverResponse).get(1);
                    try{
                        Action action = Action.SELECT_TOKEN;
                        PlayerAction playerAction = new PlayerAction(action,getPlayer(),opp1,opp2,selectedToken.getId(),otherToken.getId(),null,null, false);
                        notifyRemoteController(playerAction);
                    } catch (NullPointerException e){
                        System.out.println(e.getMessage());
                    } catch (CellOutOfBattlefieldException e) {
                        e.printStackTrace();//////////////////auto
                    }
                }
                else{
                    throw new IllegalArgumentException (); //dubbia gestione del wrong input
                }
                break;

            case START_NEW_TURN:                       //casi mergeati, l'user deve fare la stessa azione, quindi li ho accumunati
            case TOKEN_NOT_MOVABLE:
                //prints the battlefield
                serverResponse.getModelCopy().getBattlefield().printCLI();
                //prints the message for the user
                System.out.print(serverResponse.getAction().getInfo());
                //compute the user input
                int posX = Integer.parseInt(getUserInput()[0]);
                int posY = Integer.parseInt(getUserInput()[1]);
                //calculates which token has been selected
                Token selectedToken = computeTokens(serverResponse, posX, posY).get(0);
                Token otherToken = computeTokens(serverResponse, posX, posY).get(1);
                //compute opponent players
                Player opp1 = computeOpponentPlayers(serverResponse).get(0);
                Player opp2 = computeOpponentPlayers(serverResponse).get(1);
                try{
                    //cell is the cell we want to increment
                    Cell cell = serverResponse.getModelCopy().getBattlefield().getCell(posX,posY);
                    Action action = Action.SELECT_TOKEN;
                    PlayerAction playerAction = new PlayerAction(action,getPlayer(),opp1,opp2,selectedToken.getId(),otherToken.getId(),cell, null,false);
                    notifyRemoteController(playerAction);
                } catch (NullPointerException e){
                    System.out.println(e.getMessage());
                } catch (CellOutOfBattlefieldException e) {
                    e.printStackTrace();//////////////////auto
                }
                break;

            case ASK_FOR_MOVE:
                //prints the battlefield
                serverResponse.getModelCopy().getBattlefield().printCLI();
                //prints the message for the user
                System.out.print(serverResponse.getAction().getInfo());//compute the user input
                posX = Integer.parseInt(getUserInput()[0]);
                posY = Integer.parseInt(getUserInput()[1]);
                //calculates which token has been selected
                selectedToken = computeTokens(serverResponse, posX, posY).get(0);
                otherToken = computeTokens(serverResponse, posX, posY).get(1);
                //compute opponent players
                opp1 = computeOpponentPlayers(serverResponse).get(0);
                opp2 = computeOpponentPlayers(serverResponse).get(1);
                try{
                    //cell is the position we want to move the token
                    Cell cell = serverResponse.getModelCopy().getBattlefield().getCell(posX,posY);
                   /* if( !serverResponse.getValidBuilds().contains(cell) ){  //non so se funziona la contains, al massimo facciamo il check con le pos
                        System.out.println("Error! You can't select this cell, try again! ");
                    }
                    */
                    Action action = Action.MOVE;
                    PlayerAction playerAction = new PlayerAction(action,getPlayer(),opp1,opp2,selectedToken.getId(),otherToken.getId(),null, null,false);
                    notifyRemoteController(playerAction);////////////////////

                } catch (NullPointerException e){
                    System.out.println(e.getMessage());
                } catch (CellOutOfBattlefieldException e) {
                    e.printStackTrace();//////////////////auto
                }
                break;

            case ASK_FOR_BUILD:
                //prints the battlefield
                serverResponse.getModelCopy().getBattlefield().printCLI();
                //prints the message for the user
                System.out.print(serverResponse.getAction().getInfo());
                //compute the user input
                posX = Integer.parseInt(getUserInput()[0]);
                posY = Integer.parseInt(getUserInput()[1]);
                //calculates which token has been selected
                selectedToken = computeTokens(serverResponse, posX, posY).get(0);
                otherToken = computeTokens(serverResponse, posX, posY).get(1);
                //compute opponent players
                opp1 = computeOpponentPlayers(serverResponse).get(0);
                opp2 = computeOpponentPlayers(serverResponse).get(1);
                try{
                    //cell is the cell we want to increment
                    Cell cell = serverResponse.getModelCopy().getBattlefield().getCell(posX,posY);
                    Action action = Action.BUILD;
                    PlayerAction playerAction = new PlayerAction(action,getPlayer(),opp1,opp2,selectedToken.getId(),otherToken.getId(),cell, null,false);
                    notifyRemoteController(playerAction);////////////////////

                } catch (NullPointerException e){
                    System.out.println(e.getMessage());
                } catch (CellOutOfBattlefieldException e) {
                    e.printStackTrace();//////////////////auto
                }
                break;

            case NOT_YOUR_TURN:
                //prints the message for the user
                System.out.print(serverResponse.getAction().getInfo());

                //e poi??????????????????????????????????????
                break;

            case PLAYER_LOST:
                //prints the message for the user
                System.out.print(serverResponse.getAction().getInfo());

                //e poi??????????????????????????????????????
                break;

            case GAME_OVER:
                //prints the message for the user
                System.out.print(serverResponse.getAction().getInfo());

                //e poi??????????????????????????????????????
                break;

        }

    }


    /**
     * This method prints in stdout the battlefield when a player modifies it.
     * It prints a grey matrix with white numbers:
     *  -the numbers represents the cell height.
     *  -the color of the background of a cell represents the position of
     *   the tokens of the same colors.
     *  -if the height is X, it means that there is a dome.
     * @param battlefield: the board of the game
     * @param allPlayers: all the player in game
     * @throws CellOutOfBattlefieldException
     */
    public void printCLI(Battlefield battlefield, List<Player> allPlayers) throws CellOutOfBattlefieldException {

        System.out.print("\n");

        for (int y = 4; y > -1; y--) {
            // first of all, let's print the index of the battlefield
            System.out.print("\033[030m");          //white written
            System.out.print(y + " ");
            for (int x = 0; x < 5; x++) {
                // then we check if exists a token of any player in this position
                if (!battlefield.getCell(x,y).getThereIsPlayer()) {
                    System.out.print("\033[030m");          //black written
                    System.out.print("\033[047m");          //on white board
                    System.out.print("\033[047m");          //on a white board
                    System.out.print(" ");
                    if (battlefield.getCell(x,y).getHeight() < 3) {                     // if height is <=3 i print it
                        System.out.print(battlefield.getCell(x,y).getHeight());
                    } else {                                                      // else
                        if (!battlefield.getCell(x,y).getIsDome()) {
                            System.out.print(battlefield.getCell(x,y).getHeight());
                        } else {
                            System.out.print("X");
                        }
                    }
                    System.out.print(" ");
                } else {
                    for (Player p : allPlayers) {
                        if ( (p.getToken1().getTokenPosition().getPosX() == x &&
                              p.getToken1().getTokenPosition().getPosY() == y)  ||
                             (p.getToken2().getTokenPosition().getPosX() == x &&
                              p.getToken2().getTokenPosition().getPosY() == y)  ) {
                                System.out.print("\033[047m");          //on a white board
                                System.out.print("\033[030m");          //white written
                                TokenColor t = p.getTokenColor();
                                System.out.print(t.getEscape());        //on a board of the player color
                                System.out.print(" ");
                                System.out.print(battlefield.getCell(x,y).getHeight());
                                System.out.print(" ");
                                System.out.print("\033[047m");          //on a white board
                        }
                    }
                }
            }
            System.out.print("\033[039m");             //white written
            System.out.print("\033[049m");           //on a black board
            System.out.print("\n");
        }
        System.out.print("\033[030m");             //white written
        System.out.print("\033[049m");           //on a black board
        System.out.print("   0  1  2  3  4\n");
    }



    /**
     * The same as normal printCLI(), but he prints on
     * a green backgrounds all the cells in the ValidMoves param
     * @param validMoves: cells that have to be printed on a green background
     */
    public void printValidMovesCLI(Battlefield battlefield, List<Player> allPlayers, List<Cell> validMoves, Token selectedToken) throws CellOutOfBattlefieldException {

        System.out.print("\n");

        for (int y = 4; y > -1; y--) {
            // first of all, let's print the index of the battlefield
            System.out.print("\033[030m");          //white written
            System.out.print(y + " ");
            for (int x = 0; x < 5; x++) {

                if (validMoves.contains(battlefield.getCell(x,y))) {

                    System.out.print("\033[047m");          //on a white board
                    System.out.print("\033[030m");          //white written

                    System.out.print("\033[042m");               //on a board of VALIDMOVES color
                    System.out.print(" ");
                    System.out.print(battlefield.getCell(x,y).getHeight());
                    System.out.print(" ");

                    System.out.print("\033[047m");          //on a white board

                } else {
                    // then we check if exists a token of any player in this position
                    if (!battlefield.getCell(x,y).getThereIsPlayer()) {
                        System.out.print("\033[030m");          //black written
                        System.out.print("\033[047m");          //on white board
                        System.out.print("\033[047m");          //on a white board
                        System.out.print(" ");
                        if (battlefield.getCell(x,y).getHeight() < 3) {                     // if height is <=3 i print it
                            System.out.print(battlefield.getCell(x,y).getHeight());
                        } else {                                                      // else
                            if (!battlefield.getCell(x,y).getIsDome()) {
                                System.out.print(battlefield.getCell(x,y).getHeight());
                            } else {
                                System.out.print("X");
                            }
                        }
                        System.out.print(" ");
                    } else {
                        for (Player p : allPlayers) {
                            if ( (p.getToken1().getTokenPosition().getPosX() == x  &&
                                    p.getToken1().getTokenPosition().getPosY() == y)  ||
                                    (p.getToken2().getTokenPosition().getPosX() == x  &&
                                            p.getToken2().getTokenPosition().getPosY() == y)     ) {
                                System.out.print("\033[047m");          //on a white board
                                System.out.print("\033[030m");          //white written
                                TokenColor t = p.getTokenColor();
                                System.out.print(t.getEscape());        //on a board of the player color
                                System.out.print(" ");
                                System.out.print(battlefield.getCell(x,y).getHeight());
                                System.out.print(" ");
                                System.out.print("\033[047m");          //on a white board
                            }
                        }
                    }
                }
            }
            System.out.print("\033[039m");             //white written
            System.out.print("\033[049m");           //on a black board
            System.out.print("\n");
        }
        System.out.print("\033[030m");             //white written
        System.out.print("\033[049m");           //on a black board
        System.out.print("   0  1  2  3  4\n");
    }


}
