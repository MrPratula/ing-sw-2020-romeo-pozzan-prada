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
    private int savedToken;

    public View() {
    }

    protected Player getPlayer(){
        return player;
    }

    /**
     * It gets a string from player and divide it in two element separated by a comma.
     * @return a n-elements Array of Strings, where n is the number of comma+1 in the user input.
     */
    public String[] getUserInput(){
        Scanner in = new Scanner(System.in);
        String inputLine = in.nextLine();
        if (inputLine.toUpperCase().equals("NO"))
            return null;
        return inputLine.split(",");
    }


    /**
     * When needed, it returns the token that has just been selected from the user,
     * and even the not selected one (it can be useful).
     * @param serverResponse: the response passed through the observers from the server.
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

        int numberOfPlayers = serverResponse.getPack().getModelCopy().getAllPlayers().size();
        List<Player> players = serverResponse.getPack().getModelCopy().getAllPlayers();
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

        switch (serverResponse.getPack().getAction()) {


            // The first time a player connects it is asked for his name
            // This continue till the name is valid
            case WELCOME:
            case INVALID_NAME: {

                PlayerAction playerAction;

                // Print hello what is your name?
                System.out.println(serverResponse.getPack().getAction().toString());
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

                PlayerAction playerAction;

                System.out.println(serverResponse.getPack().getAction().toString());
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
            case WAIT_OTHER_PLAYERS_TO_CONNECT:
            case NUMBER_RECEIVED: {
                System.out.println(serverResponse.getPack().getAction().toString());
                break;
            }


            //The first choice is send by the server and contains player data
            case SELECT_YOUR_GOD_CARD_FROM_SERVER: {

                PlayerAction playerAction;

                this.player = serverResponse.getPack().getPlayer();

                System.out.println(serverResponse.getPack().getAction().toString());

                List<GodCard> godInGame = serverResponse.getPack().getGodCards();
                boolean needToLoop = true;
                String choice;

                while (needToLoop) {
                    System.out.println(serverResponse.getPack().getMessageInTurn());
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
                            player=serverResponse.getPack().getPlayer();
                            needToLoop = false;
                        }
                    }
                }
                break;
            }

            // The second player receive the god choice message, the 2nd and 3rd receive this with player data
            case WAIT_AND_SAVE_PLAYER_FROM_SERVER:{
                player = serverResponse.getPack().getPlayer();
                System.out.println(serverResponse.getPack().getAction().toString());
                break;
            }


            // TILL NOW ALL THE MESSAGES ARE BROADCAST-RECEIVED

            case SELECT_YOUR_GOD_CARD:{

                PlayerAction playerAction;

                Pack pack = serverResponse.getPack();

                // If the player is not in turn he is just notified to wait
                if (!player.getTokenColor().equals(serverResponse.getTurn())){
                    System.out.println(pack.getMessageOpponents());
                }
                // else he has to pick his god card
                else {
                    List<GodCard> godInGame = serverResponse.getPack().getGodCards();
                    boolean needToLoop = true;
                    String choice;

                    while (needToLoop) {
                        System.out.println(serverResponse.getPack().getMessageInTurn());
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

                }
                break;
            }

            // A player has to place his token, other wait
            case PLACE_YOUR_TOKEN:{

                PlayerAction playerAction;

                Pack pack = serverResponse.getPack();
                printCLI(pack.getModelCopy(), null);

                if (!player.getTokenColor().equals(serverResponse.getTurn())){
                    System.out.println(pack.getMessageOpponents());
                }
                else {
                    boolean needToLoop = true;
                    Scanner scanner = new Scanner(System.in);
                    String message;
                    String[] messageParsed;
                    Cell targetCell = null;

                    while (needToLoop) {

                        System.out.println(pack.getAction().toString());
                        printCLI(pack.getModelCopy(), null);

                        try {
                            message = scanner.nextLine();
                            messageParsed = message.split(",");

                            targetCell = pack.getModelCopy().getBattlefield().getCell(Integer.parseInt(messageParsed[0]), Integer.parseInt(messageParsed[1]));

                        } catch (Exception exception) {
                            targetCell = null;
                        }

                        if (targetCell!= null) {
                            needToLoop = false;
                        }
                    }
                    playerAction = new PlayerAction(Action.TOKEN_PLACED, this.player, null, null, 0, 0, targetCell, null, false, null);
                    notifyClient(playerAction);
                }
                break;
            }

            case PLAYER_LOST:
            case TOKEN_NOT_MOVABLE:
            case ASK_FOR_SELECT_TOKEN: {

                PlayerAction playerAction = null;
                Pack pack = serverResponse.getPack();

                // Update the player
                this.player = pack.getPlayer();

                if (!player.getTokenColor().equals(serverResponse.getTurn())){
                    printCLI(pack.getModelCopy(), null);
                    System.out.println(pack.getMessageOpponents());
                }
                else {

                    if (pack.getMessageInTurn() != null){
                        System.out.println(pack.getMessageInTurn());
                    }

                    boolean needToLoop = true;

                    while (needToLoop){
                        printCLI(pack.getModelCopy(), null);
                        System.out.print(serverResponse.getPack().getAction().getInfo());

                        try{
                            String[] inputs = getUserInput();

                            int selectedToken = getToken(inputs, player);

                            if (selectedToken != 0){
                                playerAction = new PlayerAction(Action.TOKEN_SELECTED, getPlayer(), null, null, selectedToken, 0, null, null, false, null);
                                savedToken = selectedToken;
                                needToLoop = false;
                            }
                        } catch (Exception e){
                            System.out.println("Your input wasn't correct!");
                        }
                    }
                    notifyClient(playerAction);
                }
                break;
            }


            case ASK_FOR_PROMETHEUS_POWER:{

                Pack pack = serverResponse.getPack();
                printCLI(pack.getModelCopy(), null);

                if (!player.getTokenColor().equals(serverResponse.getTurn())){
                    System.out.println(pack.getMessageOpponents());
                }
                else {

                    boolean needToLoop = true;
                    PlayerAction playerAction = null;
                    System.out.print(serverResponse.getPack().getAction().getInfo());

                    while (needToLoop){
                        try {

                            Scanner scanner = new Scanner(System.in);
                            String answer = scanner.nextLine().toUpperCase();

                            if (answer.equals("YES")) {
                                playerAction = new PlayerAction(Action.PROMETHEUS_ANSWER, player, null, null, 0, 0, null, null, true, null);
                                needToLoop = false;
                            }
                            else if (answer.equals("NO")) {
                                playerAction = new PlayerAction(Action.PROMETHEUS_ANSWER, player, null, null, 0, 0, null, null, false, null);
                                needToLoop = false;
                            }
                            else {
                                System.out.println("Please write yes or no...");
                            }

                        }catch (Exception e){
                            System.out.println("Please write yes or no...");
                            needToLoop = true;
                        }
                    }
                    notifyClient(playerAction);
                }
                break;
            }


            case GAME_OVER:{
                Pack pack = serverResponse.getPack();
                printCLI(pack.getModelCopy(), null);
                System.out.print(serverResponse.getPack().getAction().getInfo());
                System.out.println(pack.getMessageInTurn());
                break;
            }


            case ASK_FOR_WHERE_TO_MOVE:{

                Pack pack = serverResponse.getPack();
                printCLI(pack.getModelCopy(), pack.getValidMoves());

                if (!player.getTokenColor().equals(serverResponse.getTurn())){
                    System.out.println(pack.getMessageOpponents());
                }
                else{

                    boolean needToLoop = true;
                    PlayerAction playerAction = null;

                    while (needToLoop){

                        printCLI(pack.getModelCopy(), pack.getValidMoves());
                        System.out.println(pack.getAction().toString());

                        try{
                            String[] inputs = getUserInput();

                            Cell selectedCell = getCell(inputs, pack.getModelCopy().getBattlefield());

                            if (selectedCell != null){
                                playerAction = new PlayerAction(Action.WHERE_TO_MOVE_SELECTED, getPlayer(), null, null, savedToken, 0, selectedCell, null, false, null);
                                needToLoop = false;
                            }
                        } catch (Exception e){
                            System.out.println("Your input wasn't correct!");
                        }
                    }
                    notify(playerAction);
                }
                break;
            }


            case ASK_FOR_BUILD:{

                Pack pack = serverResponse.getPack();
                printCLI(pack.getModelCopy(), pack.getValidBuilds());

                if (!player.getTokenColor().equals(serverResponse.getTurn())){
                    System.out.println(pack.getMessageOpponents());
                }
                else{

                    boolean needToLoop = true;
                    PlayerAction playerAction = null;

                    // If i have demeter i have to pick 2 cell for build
                    if (player.getMyGodCard().equals(GodCard.DEMETER)){

                        Cell selectedCell = null;
                        Cell otherCell = null;

                        printCLI(pack.getModelCopy(), pack.getValidBuilds());
                        System.out.println(pack.getAction().toString());

                        while (needToLoop) {

                            try {
                                String[] inputs = getUserInput();

                                if (inputs==null && selectedCell!=null){
                                    playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, getPlayer(), null, null, savedToken, 0, selectedCell, null, false, null);
                                    savedToken = 0;
                                    needToLoop = false;
                                    break;
                                }

                                else if (otherCell==null && selectedCell!=null)
                                    otherCell=getCell(inputs, pack.getModelCopy().getBattlefield());

                                else if (selectedCell==null)
                                    selectedCell = getCell(inputs, pack.getModelCopy().getBattlefield());

                                if (selectedCell != null && otherCell!=null) {
                                    playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, getPlayer(), null, null, savedToken, 0, selectedCell, otherCell, true, null);
                                    savedToken = 0;
                                    needToLoop = false;
                                }
                                else {
                                    printCLI(pack.getModelCopy(), pack.getValidBuilds());
                                    System.out.println("Select where do you want to place your second build... (x,y)\nType 'no' if you don't want to build a second time!");
                                }
                            } catch (Exception e) {
                                printCLI(pack.getModelCopy(), pack.getValidBuilds());
                                System.out.println("Your input wasn't correct!");
                            }
                        }
                    }
                    // If not demeter only one build
                    else {
                        while (needToLoop) {

                            printCLI(pack.getModelCopy(), pack.getValidBuilds());
                            System.out.println(pack.getAction().toString());

                            try {
                                String[] inputs = getUserInput();

                                Cell selectedCell = getCell(inputs, pack.getModelCopy().getBattlefield());

                                if (selectedCell != null) {
                                    playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, getPlayer(), null, null, savedToken, 0, selectedCell, null, false, null);
                                    savedToken = 0;
                                    needToLoop = false;
                                }
                            } catch (Exception e) {
                                System.out.println("Your input wasn't correct!");
                            }
                        }
                    }
                    notify(playerAction);
                }
                break;
            }
        }
    }









    public int getToken(String[] inputs, Player player){

        int selectX, selectY;
        selectX = Integer.parseInt(inputs[0]);
        selectY = Integer.parseInt(inputs[1]);

        if (player.getToken1().getTokenPosition().getPosX() == selectX && player.getToken1().getTokenPosition().getPosY() == selectY){
            return player.getToken1().getId();
        }
        else if (player.getToken2().getTokenPosition().getPosX() == selectX && player.getToken2().getTokenPosition().getPosY() == selectY){
            return player.getToken2().getId();
        }
        else return 0;
    }



    public Cell getCell(String[] inputs, Battlefield battlefield){

        int selectX, selectY;
        selectX = Integer.parseInt(inputs[0]);
        selectY = Integer.parseInt(inputs[1]);

        try {
            return battlefield.getCell(selectX, selectY);
        } catch (CellOutOfBattlefieldException e) {
            return null;
        }
    }
























    /**
     * Here i print the CLI for the user, depending on the parameter validMoves;
     *   -if it is null, i print the normal battlefield with the tokens on, with
     *    the number on every cell representing the height of that one, X if dome,
     *    and the background in the color of the token on it (if present);
     *   -otherwise i print even a green backgrounds behind the cells in the ValidMoves param.
     *
     * @param validMoves: cells that have to be printed on a green background (can be null)
     */
     public void printCLI(ModelUtils modelCopy, List<Cell> validMoves) throws ReachHeightLimitException, CellOutOfBattlefieldException {

         Battlefield battlefield = modelCopy.getBattlefield();
         List<Player> allPlayers = modelCopy.getAllPlayers();

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
