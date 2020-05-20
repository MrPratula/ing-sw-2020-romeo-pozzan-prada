package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class View extends Observable<PlayerAction> implements Observer<ServerResponse>  {

    private Player player;

    public View() {
    }

    protected Player getPlayer(){
        return player;
    }

    /**
     * When needed, it computes the input of the user, properly divided.
     * @return an Array of Strings, containing the user input.
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
        if(numberOfPlayers==3){                             ///si potrebbe fare meglio
            for(int i=0; i<3; i++){
                if(players.get(i).equals(getPlayer())){
                    opp1 = players.get(1);
                    opp2 = players.get(2);
                }
            }
        }

        List<Player> opponents = new ArrayList<>();
        opponents.add(opp1);
        opponents.add(opp2);
        return opponents;
    }


    public void notifyClient(PlayerAction playerAction) throws CellOutOfBattlefieldException, ReachHeightLimitException, CellHeightException, IOException, ImpossibleTurnException, WrongNumberPlayerException {
        notify(playerAction);
    }

    /**
     * QUI LA VIEW DEVE FARE COSE, PROBABILMENTE SOLO CHIAMARE UPDATE
     */
    public void run(){

    }


    /**
     * This method moves the mechanic of the game: it receives the
     * response from the server, and creates the respective action of the player.
     * @param serverResponse: response from the server containing al the necessary informations
     */
    @Override
    public void update(ServerResponse serverResponse) throws ImpossibleTurnException, IOException, CellHeightException, WrongNumberPlayerException, ReachHeightLimitException, CellOutOfBattlefieldException {

        PlayerAction playerAction;
        int posX, posY;

        switch (serverResponse.getAction()) {


            // The first time a player connects it is asked for his name
            // This continue till the name is valid
            case WELCOME:
            case INVALID_NAME: {
                // Print hello what is your name?
                System.out.println(serverResponse.getAction().toString());
                Scanner scanner = new Scanner(System.in);
                String name = scanner.nextLine();
                playerAction = new PlayerAction(Action.MY_NAME, null, null, null, 0, 0, null, null, false, name);
                notifyClient(playerAction);
                break;
            }


            // Only the first player is asked for how many player he wants to play with
            // This continue till a player insert 2 or 3. This value is stored in token main field of PlayerAction
            // The Server check for nasty client too
            case HOW_MANY_PLAYERS:
            case WRONG_NUMBER_OF_PLAYER: {

                System.out.println(serverResponse.getAction().toString());
                int numberOfPlayers;

                while (true) {
                    Scanner scanner = new Scanner(System.in);

                    // Check if the input is an int
                    try {
                        numberOfPlayers = scanner.nextInt();
                    } catch (InputMismatchException exception) {
                        numberOfPlayers = 0;
                    }

                    // Check if the input is 2 or 3
                    if (numberOfPlayers == 2 || numberOfPlayers == 3)
                        break;
                    System.out.println("Please insert 2 players or 3 players...");
                }

                playerAction = new PlayerAction(Action.NUMBER_OF_PLAYERS, null, null, null, numberOfPlayers, 0, null, null, false, null);
                notifyClient(playerAction);
                break;
            }


            // Just tell the client the server is not popped
            // When the first player answer how much players there will be, waiting for the players to connect
            case NUMBER_RECEIVED: {
                System.out.println("Please wait for the player to begin...");
                break;
            }


            // Second and third player must wait till the first say how much player there will be in the game
            // If someone try to connect before the first player answered, so the lobby is locked
            case WAIT_PLEASE:{
                System.out.println(serverResponse.getAction().toString());
                break;
            }


            /*
             * Each time a player has to wait that other player make a choice
             * There are different messages if a player has to wait for another player move or build
             * This is called when a player has to wait for another one to pick his god card
             */
            case WAIT_OTHER_PLAYER_MOVE:{

                if (serverResponse.getOutMessage()!=null){
                    System.out.println(serverResponse.getOutMessage());
                }

                if (serverResponse.getModelCopy()!=null){
                    printCLI(serverResponse.getModelCopy().getBattlefield(), serverResponse.getModelCopy().getAllPlayers(),null);
                }

                System.out.println("Another player is making his choice.\nPlease wait your turn...");
                break;
            }


            case SELECT_YOUR_GOD_CARD: {

                this.player = serverResponse.getPlayer();

                System.out.println("Please choose a God Card you want to use for this game.");

                List<GodCard> godInGame = serverResponse.getGodCards();
                boolean needToLoop = true;
                String choice;

                while (needToLoop) {
                    System.out.println(serverResponse.getOutMessage());
                    Scanner scanner = new Scanner(System.in);

                    // Check if the input is a valid string
                    try {
                        choice = scanner.nextLine();
                    } catch (InputMismatchException exception) {
                        choice = "error";
                    }

                    // Check if the string is a valid god name
                    for (GodCard god: godInGame) {
                        if (choice.toUpperCase().equals(god.name().toUpperCase())){
                            System.out.println("Ohhh good choice!");
                            playerAction = new PlayerAction(Action.CHOSE_GOD_CARD, this.player, null, null, 0, 0, null, null, false, choice.toUpperCase());
                            notifyClient(playerAction);
                            needToLoop = false;
                        }
                    }
                }
                break;
            }


            case PLACE_YOUR_TOKEN:{

                if (serverResponse.getOutMessage()!=null){
                    System.out.println(serverResponse.getOutMessage());
                }

                boolean needToLoop = true;
                Scanner scanner = new Scanner(System.in);
                String message;
                String[] messageParsed;
                Cell targetCell = null;

                while (needToLoop) {

                    System.out.println(serverResponse.getAction().toString());
                    printCLI(serverResponse.getModelCopy().getBattlefield(), serverResponse.getModelCopy().getAllPlayers(),null);


                    try {
                        message = scanner.nextLine();
                        messageParsed = message.split(",");

                        targetCell = serverResponse.getModelCopy().getBattlefield().getCell(Integer.parseInt(messageParsed[0]), Integer.parseInt(messageParsed[1]));

                    } catch (Exception exception) {
                        targetCell = null;
                    }

                    if (targetCell!= null) {
                        needToLoop = false;
                    }
                }

                playerAction = new PlayerAction(Action.TOKEN_PLACED, this.player, null, null, 0, 0, targetCell, null, false, null);
                notifyClient(playerAction);


                break;
            }





            case SET_UP: {
                //prints the battlefield
                printCLI(serverResponse.getModelCopy().getBattlefield(), serverResponse.getModelCopy().getAllPlayers(),null);

                //prints the message for the user
                System.out.print(serverResponse.getAction().getInfo());
                //compute the user input
                String[] inputs = getUserInput();
                TokenColor tc;
                switch (serverResponse.getOutMessage()) {
                    case "TokenColor.RED":
                        tc = TokenColor.RED;
                    case "TokenColor.BLUE":
                        tc = TokenColor.BLUE;
                    default:
                        tc = TokenColor.YELLOW;
                }
                Token token = new Token(tc);
                token.setId(1);///////////////////modificare
                token.setTokenPosition(serverResponse.getModelCopy().getBattlefield().getCell(Integer.parseInt(inputs[0]), Integer.parseInt(inputs[1])));
                try {
                    playerAction = new PlayerAction(Action.TOKEN_SET_UP, getPlayer(), null, null, token.getId(), 0, null, null, false, null);
                    notifyClient(playerAction);////////////////////
                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                } catch (CellOutOfBattlefieldException e) {
                    e.printStackTrace();//////////////////auto
                }
                break;
            }

            case ASK_FOR_PROMETHEUS_POWER:{
                //prints the battlefield
                printCLI(serverResponse.getModelCopy().getBattlefield(), serverResponse.getModelCopy().getAllPlayers(),null);
                //prints the message for the user
                System.out.print(serverResponse.getAction().getInfo());
                //compute the user input, IN THIS CASE IT'S JUST yes/no
                Scanner in = new Scanner(System.in);
                String input = in.nextLine();

                if (input.equals("yes")) {
                    System.out.println("Which token do you want to build with? (x,y)");
                    //compute the user input
                    String[] inputs = getUserInput();
                    //calculates which token has been selected
                    List<Token> tokens = computeTokens(serverResponse, Integer.parseInt(inputs[0]), Integer.parseInt(inputs[1]));
                    //compute opponent players
                    List<Player> opponentPlayers = computeOpponentPlayers(serverResponse);
                    try {
                        playerAction = new PlayerAction(Action.PROMETHEUS_POWER, getPlayer(), opponentPlayers.get(0), opponentPlayers.get(1), tokens.get(0).getId(), tokens.get(1).getId(), null, null, true, null);
                        notifyClient(playerAction);////////////////////
                    } catch (NullPointerException e) {
                        System.out.println(e.getMessage());
                    } catch (CellOutOfBattlefieldException e) {
                        e.printStackTrace();//////////////////auto
                    }
                } else if (input.equals("no")) {
                    //prints the message for the user
                    System.out.println("Where do you want to move your token? (x,y)");
                    //System.out.print(serverResponse.getAction().getInfo());    errato
                    //compute the user input
                    String[] inputs = getUserInput();
                    //calculates which token has been selected
                    List<Token> tokens = computeTokens(serverResponse, Integer.parseInt(inputs[0]), Integer.parseInt(inputs[1]));
                    //compute opponent players
                    List<Player> opponentPlayers = computeOpponentPlayers(serverResponse);
                    try {
                        playerAction = new PlayerAction(Action.SELECT_TOKEN, getPlayer(), opponentPlayers.get(0), opponentPlayers.get(1), tokens.get(0).getId(), tokens.get(1).getId(), null, null, false, null);
                        notifyClient(playerAction);
                    } catch (NullPointerException e) {
                        System.out.println(e.getMessage());
                    } catch (CellOutOfBattlefieldException e) {
                        e.printStackTrace();//////////////////auto
                    }
                } else {
                    throw new IllegalArgumentException(); //dubbia gestione del wrong input
                }
                break;
            }

            case START_NEW_TURN:                       //casi mergeati, l'user deve fare la stessa azione, quindi li ho accumunati
            case TOKEN_NOT_MOVABLE: {
                //prints the battlefield
                printCLI(serverResponse.getModelCopy().getBattlefield(), serverResponse.getModelCopy().getAllPlayers(),null);

                //prints the message for the user
                System.out.print(serverResponse.getAction().getInfo());
                //compute the user input
                String[] inputs = getUserInput();
                //calculates which token has been selected
                List<Token> tokens = computeTokens(serverResponse, Integer.parseInt(inputs[0]), Integer.parseInt(inputs[1]));
                //compute opponent players
                List<Player> opponentPlayers = computeOpponentPlayers(serverResponse);
                try {
                    //cell is the cell we want to increment
                    Cell cell = serverResponse.getModelCopy().getBattlefield().getCell(Integer.parseInt(inputs[0]), Integer.parseInt(inputs[1]));
                    playerAction = new PlayerAction(Action.SELECT_TOKEN, getPlayer(), opponentPlayers.get(0), opponentPlayers.get(1), tokens.get(0).getId(), tokens.get(1).getId(), cell, null, false, null);
                    notifyClient(playerAction);
                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                } catch (CellOutOfBattlefieldException e) {
                    e.printStackTrace();//////////////////auto
                }
                break;
            }

            case ASK_FOR_MOVE: {
                //prints the battlefield
                printCLI(serverResponse.getModelCopy().getBattlefield(), serverResponse.getModelCopy().getAllPlayers(),null);

                //prints the message for the user
                System.out.print(serverResponse.getAction().getInfo());//compute the user input
                String[] inputs = getUserInput();
                //calculates which token has been selected
                List<Token> tokens = computeTokens(serverResponse, Integer.parseInt(inputs[0]), Integer.parseInt(inputs[1]));
                //compute opponent players
                List<Player> opponentPlayers = computeOpponentPlayers(serverResponse);
                try {
                    //cell is the position we want to move the token
                    Cell cell = serverResponse.getModelCopy().getBattlefield().getCell(Integer.parseInt(inputs[0]), Integer.parseInt(inputs[1]));
                   /* if( !serverResponse.getValidBuilds().contains(cell) ){  //non so se funziona la contains, al massimo facciamo il check con le pos
                        System.out.println("Error! You can't select this cell, try again! ");
                    }
                    */
                    playerAction = new PlayerAction(Action.MOVE, getPlayer(), opponentPlayers.get(0), opponentPlayers.get(1), tokens.get(0).getId(), tokens.get(1).getId(), null, null, false, null);
                    notifyClient(playerAction);////////////////////

                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                } catch (CellOutOfBattlefieldException e) {
                    e.printStackTrace();//////////////////auto
                }
                break;
            }

            case ASK_FOR_BUILD: {
                //prints the battlefield
                printCLI(serverResponse.getModelCopy().getBattlefield(), serverResponse.getModelCopy().getAllPlayers(),null);

                //prints the message for the user
                System.out.print(serverResponse.getAction().getInfo());
                //compute the user input
                String[] inputs = getUserInput();
                //calculates which token has been selected
                List<Token> tokens = computeTokens(serverResponse, Integer.parseInt(inputs[0]), Integer.parseInt(inputs[1]) );
                //compute opponent players
                List<Player> opponentPlayers = computeOpponentPlayers(serverResponse);
                try {
                    //cell is the cell we want to increment
                    Cell cell = serverResponse.getModelCopy().getBattlefield().getCell(Integer.parseInt(inputs[0]), Integer.parseInt(inputs[1]));
                    playerAction = new PlayerAction(Action.BUILD, getPlayer(), opponentPlayers.get(0), opponentPlayers.get(1), tokens.get(0).getId(), tokens.get(1).getId(), cell, null, false, null);
                    notifyClient(playerAction);////////////////////

                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                } catch (CellOutOfBattlefieldException e) {
                    e.printStackTrace();//////////////////auto
                }
                break;
            }

            case NOT_YOUR_TURN: {
                //prints the message for the user
                System.out.print(serverResponse.getAction().getInfo());

                //e poi??????????????????????????????????????
                break;
            }

            case PLAYER_LOST: {
                //prints the message for the user
                System.out.print(serverResponse.getAction().getInfo());

                //e poi??????????????????????????????????????
                break;
            }

            case GAME_OVER: {
                //prints the message for the user
                System.out.print(serverResponse.getAction().getInfo());

                //e poi??????????????????????????????????????
                break;
            }

        }

    }


    /**
     * Here i print the CLI for the user, depending on the parameter validMoves;
     *   -if it is null, i print the normal battlefield with the tokens on, with
     *    the number on every cell representing the height of that one, X if dome,
     *    and the background in the color of the token on it (if present);
     *   -otherwise i print even a green backgrounds behind the cells in the ValidMoves param.
     *
     * @param battlefield: the board of the game
     * @param allPlayers: the players in the game
     * @param validMoves: cells that have to be printed on a green background (can be null)
     * @throws ReachHeightLimitException
     * @throws CellOutOfBattlefieldException
     */
     public void printCLI(Battlefield battlefield, List<Player> allPlayers, List<Cell> validMoves) throws ReachHeightLimitException, CellOutOfBattlefieldException {

        System.out.print("\n");

        for (int y = 4; y > -1; y--) {

            System.out.print("\033[030m");          //white written
            System.out.print(y + " ");
            for (int x = 0; x < 5; x++) {
                if (validMoves != null){
                    if (validMoves.contains(battlefield.getCell(x,y))) {

                        System.out.print("\033[047m");          //on a white board
                        System.out.print("\033[030m");          //white written
                        System.out.print("\033[042m");          //on a board of GREEN color
                        System.out.print(" ");
                        System.out.print(battlefield.getCell(x, y).getHeight());
                        System.out.print(" ");
                        System.out.print("\033[047m");          //on a white board
                    }
                    else  printInnerCLI(battlefield,allPlayers,validMoves,x,y);
                }
                else  printInnerCLI(battlefield,allPlayers, null,x,y);
            }
            System.out.print("\033[039m");             //white written
            System.out.print("\033[049m");             //on a black board
            System.out.print("\n");
        }
        System.out.print("\033[030m");             //white written
        System.out.print("\033[049m");             //on a black board
        System.out.print("   0  1  2  3  4\n");
    }


    /**
     * Auxiliary method to print the cli, here i check the token position's
     * @param battlefield: the board of the game
     * @param allPlayers: the players in the game
     * @param validMoves: cells that have to be printed on a green background (can be null)
     * @param x: position x of the battlefield
     * @param y: position y of the battlefield
     * @throws CellOutOfBattlefieldException
     * @throws ReachHeightLimitException
     */
    private void printInnerCLI(Battlefield battlefield, List<Player> allPlayers, List<Cell> validMoves, int x, int y) throws CellOutOfBattlefieldException, ReachHeightLimitException {

        // we check if exists a token of any player in this position
        if (!battlefield.getCell(x, y).getThereIsPlayer()) {
            System.out.print("\033[030m");          //black written
            System.out.print("\033[047m");          //on white board
            System.out.print("\033[047m");          //on a white board
            System.out.print(" ");
            if (battlefield.getCell(x, y).getIsDome()) {
                System.out.print("X");
            } else {                                                      // else
                if (battlefield.getCell(x, y).getHeight() <= 3) {
                    System.out.print(battlefield.getCell(x, y).getHeight());
                } else {
                    throw new ReachHeightLimitException("Illegal height");
                }
            }
            System.out.print(" ");
        }
        else {
            for (Player p : allPlayers) {
                if (p.getToken1().getTokenPosition() != null && p.getToken1().getTokenPosition()!=null) {                                  //if he has the first token
                    if (p.getToken1().getTokenPosition().equals(battlefield.getCell(x,y))) {    //i print his background correctly
                        colorBackground(battlefield, x, y, p);
                    }
                }
                if (p.getToken2().getTokenPosition() != null && p.getToken2().getTokenPosition()!=null) {                                 //if he has the first token
                    if (p.getToken2().getTokenPosition().equals(battlefield.getCell(x,y))) {   //i print his background correctly
                        colorBackground(battlefield, x, y, p);
                    }
                }
            }
        }
    }

    /**
     * Auxiliary method to color the background according to
     * the color of the token on that precise position
     * @param battlefield: the board of the game
     * @param x: position x of the battlefield
     * @param y: position y of the battlefield
     */
    private void colorBackground(Battlefield battlefield, int x, int y, Player p) throws CellOutOfBattlefieldException {
        System.out.print("\033[047m");          //on a white board
        System.out.print("\033[030m");          //white written
        TokenColor t = p.getTokenColor();
        System.out.print(t.getEscape());        //on a board of the player color
        System.out.print(" ");
        System.out.print(battlefield.getCell(x, y).getHeight());
        System.out.print(" ");
        System.out.print("\033[047m");          //on a white board
    }


}
