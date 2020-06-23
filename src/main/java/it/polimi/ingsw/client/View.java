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


    /**
     * Create a new view and print SANTORINI with ascii art.
     */
    public View() {
        printSANTORINI();
    }

    protected Player getPlayer(){
        return player;
    }


    /**
     * Get a input from stdin and divide it when there is a comma.
     * @return if the input are 2 numbers separated with a comma a String[2] with those numbers in it.
     *         null else.
     */
    public String[] getUserInput(){
        Scanner in = new Scanner(System.in);
        String inputLine = in.nextLine();
        try{

            String[] arrayString = inputLine.split(",");
            String[] cellCoords = new String[2];
            if (Integer.parseInt(arrayString[0])>=0 && Integer.parseInt(arrayString[0])<5)
                cellCoords[0] = arrayString[0];
            else
                return null;

            if (Integer.parseInt(arrayString[1])>=0 && Integer.parseInt(arrayString[1])<5)
                cellCoords[1] = arrayString[1];
            else return null;

            return cellCoords;

        } catch (Exception e){
            return null;
        }
    }

    /**
     * Once a player has performed his move, it is notified to the client.
     * @param playerAction move to send to the client. He will send it into the socket.
     */
    public void notifyClient(PlayerAction playerAction) throws CellOutOfBattlefieldException, ReachHeightLimitException, CellHeightException, IOException, ImpossibleTurnException, WrongNumberPlayerException {
        notify(playerAction);
    }


    /**
     * This method moves the mechanic of the game: it receives the
     * response from the server, parse it to identify the kind of action and ask the user to insert the
     * correct input.
     * @param serverResponse: response received from the server containing al the necessary information.
     */
    @Override
    public void update(ServerResponse serverResponse) throws ImpossibleTurnException, IOException, CellHeightException, WrongNumberPlayerException, ReachHeightLimitException, CellOutOfBattlefieldException {

        //System.out.println("\nExecuting "+serverResponse.getPack().getAction().getName().toUpperCase());

        switch (serverResponse.getPack().getAction()) {

            // The first time a player connects it is asked for his name
            // This continue till the name is valid
            case WELCOME:
            case INVALID_NAME: {

                PlayerAction playerAction = null;

                // Print hello
                System.out.println(serverResponse.getPack().getAction().toString());

                boolean needToLoop = true;

                while (needToLoop){
                    System.out.println("Please type your name...");
                    Scanner scanner = new Scanner(System.in);
                    String name = scanner.nextLine();

                    if (isAGoodName(name)){
                        playerAction = new PlayerAction(Action.MY_NAME, null, null, null, 0, 0, null, null, false, name);
                        needToLoop=false;
                    }
                    else
                        needToLoop=true;
                }

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


            //The first player have to choose which god use in this game
            case CHOOSE_GOD_CARD_TO_PLAY: {

                PlayerAction playerAction;

                this.player = serverResponse.getPack().getPlayer();

                System.out.println(serverResponse.getPack().getAction().toString());

                List<GodCard> godInGame = serverResponse.getPack().getGodCards();
                List<GodCard> selectedGods = new ArrayList<>();

                boolean needToLoop = true;
                String choice;
                int godToSelect = serverResponse.getPack().getNumberOfPlayers();
                StringBuilder stringBuilder = new StringBuilder();

                while (needToLoop) {
                    System.out.println("\n"+serverResponse.getPack().getMessageInTurn()+"\n");
                    Scanner scanner = new Scanner(System.in);

                    // Check if the input is a valid string
                    try {
                        choice = scanner.nextLine();

                        for (GodCard god: godInGame) {
                            if (choice.toUpperCase().equals(god.name().toUpperCase())){
                                if(!selectedGods.contains(god)){
                                    System.out.println("Ohhh good choice!");
                                    godToSelect--;
                                    selectedGods.add(god);
                                }
                                else {
                                    System.out.println("You already choose this god. Please choose another one...");
                                }
                            }
                        }

                    } catch (InputMismatchException exception) {
                        System.out.println("Please insert a proper God name...");
                    }

                    if (godToSelect==0){
                        needToLoop = false;
                    }
                }

                for (GodCard god: selectedGods){
                    stringBuilder.append(god.name().toUpperCase()).append(",");
                }

                playerAction = new PlayerAction(Action.CHOSE_GOD_CARD, player, null, null, 0, 0, null, null, false, stringBuilder.toString());
                notifyClient(playerAction);
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
                        System.out.println(serverResponse.getPack().getAction().toString());
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
                                playerAction = new PlayerAction(Action.CHOSE_GOD_CARD, player, null, null, 0, 0, null, null, false, choice.toUpperCase());
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

                if (!player.getTokenColor().equals(serverResponse.getTurn())){
                    if (pack.getMessageInTurn()!=null)
                        System.out.println(pack.getMessageInTurn());
                    printCLI(pack.getModelCopy(), null);
                    System.out.println(pack.getMessageOpponents());
                }
                else {
                    if (pack.getPlayer()!=null)
                        this.player = pack.getPlayer();

                    boolean needToLoop = true;
                    Scanner scanner = new Scanner(System.in);
                    String message;
                    String[] messageParsed;
                    Cell targetCell = null;

                    if (pack.getMessageInTurn()!=null){
                        System.out.println(pack.getMessageInTurn());
                    }

                    while (needToLoop) {
                        printCLI(pack.getModelCopy(), null);
                        System.out.println(pack.getAction().toString());

                        try {
                            message = scanner.nextLine();
                            messageParsed = message.split(",");

                            targetCell = pack.getModelCopy().getBattlefield().getCell(Integer.parseInt(messageParsed[0]), Integer.parseInt(messageParsed[1]));

                            if (targetCell!= null && isFree(targetCell, pack.getModelCopy())) {
                                needToLoop = false;
                            }

                        } catch (Exception exception) {
                            targetCell = null;
                        }
                    }
                    playerAction = new PlayerAction(Action.TOKEN_PLACED, player, null, null, 0, 0, targetCell, null, false, null);
                    notifyClient(playerAction);
                }
                break;
            }


            case PLAYER_LOST:
            case TOKEN_NOT_MOVABLE:
            case ASK_FOR_SELECT_TOKEN: {

                PlayerAction playerAction = null;
                Pack pack = serverResponse.getPack();

                if (!player.getTokenColor().equals(serverResponse.getTurn())){
                    printCLI(pack.getModelCopy(), null);
                    System.out.println(pack.getMessageOpponents());
                }
                else {
                    // Update the player
                    this.player = pack.getPlayer();
                    if (pack.getMessageInTurn() != null){
                        System.out.println(pack.getMessageInTurn());
                    }

                    boolean needToLoop = true;

                    while (needToLoop){
                        printCLI(pack.getModelCopy(), null);
                        System.out.println(serverResponse.getPack().getAction().getInfo());

                        try{
                            String[] inputs = getUserInput();

                            int selectedToken = getToken(inputs, player);

                            if (selectedToken != 0){
                                playerAction = new PlayerAction(Action.TOKEN_SELECTED, player, null, null, selectedToken, 0, null, null, false, null);
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

                    PlayerAction playerAction;
                    System.out.println(serverResponse.getPack().getAction().getInfo());

                    try {

                        boolean wantToUsePower = askYesOrNot();

                        if (wantToUsePower)
                            playerAction = new PlayerAction(Action.PROMETHEUS_ANSWER, player, null, null, 0, 0, null, null, true, null);

                        else
                            playerAction = new PlayerAction(Action.PROMETHEUS_ANSWER, player, null, null, 0, 0, null, null, false, null);

                    }catch (Exception e){
                        playerAction = new PlayerAction(Action.PROMETHEUS_ANSWER, player, null, null, 0, 0, null, null, false, null);
                    }

                    notifyClient(playerAction);
                }
                break;
            }


            case ASK_FOR_WHERE_TO_MOVE:{

                Pack pack = serverResponse.getPack();

                if (!player.getTokenColor().equals(serverResponse.getTurn())){
                    printCLI(pack.getModelCopy(), pack.getValidMoves());
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

                            if (selectedCell != null && cellIsInValidCells(selectedCell, pack.getValidMoves())){

                                playerAction = new PlayerAction(Action.WHERE_TO_MOVE_SELECTED, player, null, null, savedToken, 0, selectedCell, null, false, null);
                                needToLoop = false;
                            }
                            else {
                                System.out.println("That cell is not a valid move!");
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

                if (!player.getTokenColor().equals(serverResponse.getTurn())){
                    printCLI(pack.getModelCopy(), pack.getValidBuilds());
                    System.out.println(pack.getMessageOpponents());
                }
                else {

                    // Refresh the player so the token position is updated
                    if (pack.getPlayer() != null)
                        this.player = pack.getPlayer();

                    boolean needToLoop = true;
                    PlayerAction playerAction = null;

                    switch (player.getMyGodCard()) {


                        // Pick 2 different cells
                        case DEMETER: {

                            Cell selectedCell = null;
                            Cell otherCell = null;

                            printCLI(pack.getModelCopy(), pack.getValidBuilds());
                            System.out.println(pack.getAction().toString());

                            // Choose the first build
                            while (needToLoop) {

                                try {
                                    String[] inputs = getUserInput();
                                    selectedCell = getCell(inputs, pack.getModelCopy().getBattlefield());

                                    if (selectedCell != null && cellIsInValidCells(selectedCell, pack.getValidBuilds())){
                                        needToLoop = false;
                                    }
                                    else {
                                        System.out.println("That cell is not a valid build!");
                                    }

                                } catch (Exception e) {
                                    printCLI(pack.getModelCopy(), pack.getValidBuilds());
                                    System.out.println("Your input wasn't correct!");
                                }
                            }
                            needToLoop = true;

                            // Choose the second build
                            while (needToLoop) {

                                System.out.println("Now select where you want to make your second build.\nRemember you can not choose the same cell.\nType 'no' or nothing if you don't want to make a second build.");

                                try {

                                    String[] inputs = getUserInput();

                                    if (inputs == null) {
                                        needToLoop = false;
                                        break;
                                    }

                                    otherCell = getCell(inputs, pack.getModelCopy().getBattlefield());

                                    if (otherCell != null && cellIsInValidCells(otherCell, pack.getValidBuilds())){
                                        if (!selectedCell.equals(otherCell))
                                            needToLoop = false;
                                        else
                                            System.out.println("You can't choose the same cell!");
                                    }
                                    else {
                                        System.out.println("That cell is not a valid build!");
                                    }

                                } catch (Exception e) {
                                    printCLI(pack.getModelCopy(), pack.getValidBuilds());
                                    System.out.println("Your input wasn't correct!");
                                }
                            }

                            if (otherCell != null) {
                                playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, player, null, null, savedToken, 0, selectedCell, otherCell, true, null);
                            } else {
                                playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, player, null, null, savedToken, 0, selectedCell, null, false, null);
                            }
                            savedToken = 0;
                            notify(playerAction);
                            break;
                        }


                        // Pick 2 cells. One and one not on to the perimeter
                        case HESTIA: {

                            Cell selectedCell = null;
                            Cell otherCell = null;

                            printCLI(pack.getModelCopy(), pack.getValidBuilds());
                            System.out.println(pack.getAction().toString());

                            // Choose the first build
                            while (needToLoop) {

                                try {
                                    String[] inputs = getUserInput();
                                    selectedCell = getCell(inputs, pack.getModelCopy().getBattlefield());

                                    if (selectedCell != null && cellIsInValidCells(selectedCell, pack.getValidBuilds())){
                                        needToLoop = false;
                                    }
                                    else {
                                        System.out.println("That cell is not a valid build!");
                                    }

                                } catch (Exception e) {
                                    printCLI(pack.getModelCopy(), pack.getValidBuilds());
                                    System.out.println("Your input wasn't correct!");
                                }
                            }
                            needToLoop = true;

                            // Choose the second build
                            while (needToLoop) {

                                System.out.println("Now select where you want to make your second build.\nRemember you can not choose a cell on the perimeter.\nType 'no' or nothing if you don't want to make a second build.");

                                try {

                                    String[] inputs = getUserInput();

                                    if (inputs == null) {
                                        needToLoop = false;
                                        break;
                                    }

                                    otherCell = getCell(inputs, pack.getModelCopy().getBattlefield());

                                    if (otherCell != null && cellIsInValidCells(otherCell, pack.getValidBuilds())){
                                        if (notPerimeterCell(otherCell))
                                            needToLoop = false;
                                        else
                                            System.out.println("The second cell can't be on a perimeter space!");
                                    }
                                    else {
                                        System.out.println("That cell is not a valid build!");
                                    }

                                } catch (Exception e) {
                                    printCLI(pack.getModelCopy(), pack.getValidBuilds());
                                    System.out.println("Your input wasn't correct!");
                                }
                            }

                            if (otherCell != null) {
                                playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, player, null, null, savedToken, 0, selectedCell, otherCell, true, null);
                            } else {
                                playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, player, null, null, savedToken, 0, selectedCell, null, false, null);
                            }
                            savedToken = 0;
                            notify(playerAction);
                            break;
                        }


                        // Ask if they want to use they're power
                        case HEPHAESTUS:
                        case ATLAS: {

                            String atlasMessage = "Do you want to build a dome here? [yes,any]";
                            String hephaestusMessage = "Do you want to build two times here? [yes,any]";

                            Cell selectedCell = null;

                            // Ask for a cell to build
                            while (needToLoop) {

                                printCLI(pack.getModelCopy(), pack.getValidBuilds());
                                System.out.println(pack.getAction().toString());


                                try {
                                    String[] inputs = getUserInput();

                                    selectedCell = getCell(inputs, pack.getModelCopy().getBattlefield());

                                    if (selectedCell != null && cellIsInValidCells(selectedCell, pack.getValidBuilds())){
                                        needToLoop = false;
                                    }
                                    else {
                                        System.out.println("That cell is not a valid build!");
                                    }

                                } catch (Exception e) {
                                    System.out.println("Your input wasn't correct!");
                                }
                            }

                            // If the cell is not height 3 i ask if he want to build a dome
                            if (pack.getModelCopy().getBattlefield().getCell(selectedCell).getHeight()<3) {
                                needToLoop = true;

                                while (needToLoop) {

                                    if (player.getMyGodCard().equals(GodCard.ATLAS))
                                        System.out.println(atlasMessage);
                                    if (player.getMyGodCard().equals(GodCard.HEPHAESTUS))
                                        System.out.println(hephaestusMessage);

                                    try {

                                        if (askYesOrNot()){
                                            playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, getPlayer(), null, null, savedToken, 0, selectedCell, null, true, null);
                                        }
                                        else {
                                            playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, getPlayer(), null, null, savedToken, 0, selectedCell, null, false, null);
                                        }
                                        needToLoop = false;

                                    } catch (Exception e) {
                                        System.out.println("Your input wasn't correct!");
                                    }
                                }
                            }
                            savedToken = 0;
                            notify(playerAction);

                            break;
                        }

                        // If other gods one normal build
                        default: {
                            while (needToLoop) {

                                printCLI(pack.getModelCopy(), pack.getValidBuilds());
                                System.out.println(pack.getAction().toString());

                                try {
                                    String[] inputs = getUserInput();

                                    Cell selectedCell = getCell(inputs, pack.getModelCopy().getBattlefield());

                                    if (selectedCell != null && cellIsInValidCells(selectedCell, pack.getValidBuilds())){
                                        needToLoop = false;
                                        playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, getPlayer(), null, null, savedToken, 0, selectedCell, null, false, null);
                                    }
                                    else {
                                        System.out.println("That cell is not a valid build!");
                                    }

                                } catch (Exception e) {
                                    System.out.println("Your input wasn't correct!");
                                }
                            }
                            if (!player.getMyGodCard().equals(GodCard.PROMETHEUS))
                                savedToken = 0;

                            notify(playerAction);
                        }
                    }
                }
                break;
            }


            case GAME_OVER:{

                Pack pack = serverResponse.getPack();
                printCLI(pack.getModelCopy(), pack.getValidBuilds());
                System.out.println(pack.getMessageInTurn());

                System.out.println("GAME OVER!");

                break;
            }
        }
    }


    /**
     * When a player insert an input for move a token, here is identified which token a player want to move.
     * @param inputs String[] that should contain something like ["2","3"].
     * @param player the player who gave the String[] input.
     * @return the token ID of that player in that position. 0 if something goes wrong.
     */
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


    /**
     * Get an input from a player and return the corresponding cell of the battlefield.
     * @param inputs String[] that should contain something like ["2","3"].
     * @param battlefield the model's battlefield copy received in server response.
     * @return the cell of the battlefield with the coordinates written in inputs. Null if something goes wrong.
     */
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
     * Ask if a target cell of the battlefield is free or not.
     * It is free if there is not a token on it nor a dome.
     * @param targetCell a cell we are asking for.
     * @param modelCopy model copy received from the server.
     * @return true if that cell is free, false else.
     */
    public boolean isFree(Cell targetCell, ModelUtils modelCopy){

        List<Player> allPlayers = modelCopy.getAllPlayers();

        for (Player p: allPlayers) {
            if (p.getToken1() != null) {
                if (p.getToken1().getTokenPosition() != null) {
                    if (targetCell.equals(p.getToken1().getTokenPosition()))
                        return false;
                }
            }
            if (p.getToken2() != null) {
                if (p.getToken2().getTokenPosition() != null) {
                    if (targetCell.equals(p.getToken2().getTokenPosition()))
                        return false;
                }
            }
        }

        Battlefield battlefield = modelCopy.getBattlefield();

        return !battlefield.getCell(targetCell).getIsDome();
    }


    /**
     * Since Hestia can't make her second build on a perimeter space, we check for this condition.
     * A perimeter space is a cell that has x==0 || x==4 || y==0 || y==4.
     * @param targetCell cell we are asking for.
     * @return true if that cell is not on a perimeter zone of the battlefield.
     */
    public boolean notPerimeterCell(Cell targetCell){
        return ((targetCell.getPosX()!=4 && targetCell.getPosY()!=4) && (targetCell.getPosX()!=0 && targetCell.getPosY()!=0));
    }


    /**
     * Get a user input and parse it.
     * @return true if he write something similar to yes, false else.
     */
    public boolean askYesOrNot(){

        Scanner in = new Scanner(System.in);
        String input;

        try {
            input = in.nextLine().toUpperCase();
        } catch (Exception e){
            return false;
        }
        return input.equals("Y") || input.equals("YE") || input.equals("YES") || input.equals("TRUE") || input.equals("SI");
    }


    /**
     * Tells if a cell got from user is a valid cell.
     * @param targetCell cell got from user.
     * @param validCells valid cells got from model.
     * @return true if target cell is in the valid cells.
     */
    public boolean cellIsInValidCells (Cell targetCell, List<Cell> validCells){

        if(targetCell==null || validCells == null || validCells.isEmpty()){
            return false;
        }

        for (Cell c: validCells){
            if (c.equals(targetCell))
                return true;
        }
        return false;
    }


    /**
     * @param name string to ckeck. It is the user input for his username.
     * @return true if it is not empty, too long(<=16) or with spaces.
     */
    public boolean isAGoodName(String name){

        if (name==null)
            return false;

        if (name.isEmpty())
            return false;

        if (name.contains(" "))
            return false;

        if (name.contains("\n"))
            return false;

        return name.length() <= 16;
    }


    /**
     * Here i print the CLI for the user, depending on the parameter validMoves;
     *   -if it is null, i print the normal battlefield with the tokens on, with
     *    the number on every cell representing the height of that one, X if dome,
     *    and the background in the color of the token on it (if present);
     *   -otherwise i print even a green backgrounds behind the cells in the ValidMoves param.
     *
     * @param validMoves: cells that have to be printed on a green background (can be null).
     * @param modelCopy: contains the the board of the game and the players in the game.
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
     * Auxiliary method to print the CLI, here i check the token position's.
     * @param battlefield: the board of the game.
     * @param allPlayers: the players in the game.
     * @param validMoves: cells that have to be printed on a green background (can be null).
     * @param x: position x of the battlefield.
     * @param y: position y of the battlefield.
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
     * the color of the token on that precise position.
     * @param battlefield: the board of the game.
     * @param x: position x of the battlefield.
     * @param y: position y of the battlefield.
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


    /**
     * Prints SANTORINI in ascii art.
     */
    public void printSANTORINI(){

        System.out.println();
        System.out.println();


        //first line
        printNBlueSpaces(6);
        printNBlueSpaces(6);
        printNBlueSpaces(3);
        printNResetSpaces(1);
        printNBlueSpaces(2);
        printNBlueSpaces(6);
        printNBlueSpaces(6);
        printNBlueSpaces(6);
        printNBlueSpaces(2);
        printNBlueSpaces(3);
        printNResetSpaces(1);
        printNBlueSpaces(2);
        printNBlueSpaces(2);
        System.out.println();

        //second line
        printNBlueSpaces(2);
        printNResetSpaces(4);
        printNBlueSpaces(2);
        printNBlueSpaces(2);
        printNBlueSpaces(4);
        printNBlueSpaces(2);
        printNResetSpaces(2);
        printNBlueSpaces(2);
        printNResetSpaces(2);
        printNBlueSpaces(2);
        printNBlueSpaces(2);
        printNBlueSpaces(2);
        printNBlueSpaces(2);
        printNBlueSpaces(2);
        printNBlueSpaces(4);
        printNBlueSpaces(2);
        printNBlueSpaces(2);
        System.out.println();

        //third line
        printNBlueSpaces(6);
        printNBlueSpaces(6);
        printNBlueSpaces(2);
        printNBlueSpaces(4);
        printNResetSpaces(2);
        printNBlueSpaces(2);
        printNResetSpaces(2);
        printNBlueSpaces(2);
        printNBlueSpaces(2);
        printNBlueSpaces(5);
        printNResetSpaces(1);
        printNBlueSpaces(2);
        printNBlueSpaces(2);
        printNBlueSpaces(4);
        printNBlueSpaces(2);
        System.out.println();

        //fourth line
        printNResetSpaces(4);
        printNBlueSpaces(2);
        printNBlueSpaces(2);
        printNBlueSpaces(2);
        printNBlueSpaces(2);
        printNResetSpaces(1);
        printNBlueSpaces(3);
        printNResetSpaces(2);
        printNBlueSpaces(2);
        printNResetSpaces(2);
        printNBlueSpaces(2);
        printNBlueSpaces(2);
        printNBlueSpaces(2);
        printNBlueSpaces(2);
        printNBlueSpaces(2);
        printNBlueSpaces(2);
        printNResetSpaces(1);
        printNBlueSpaces(3);
        printNBlueSpaces(2);
        System.out.println();

        //fifth line
        printNBlueSpaces(6);
        printNBlueSpaces(2);
        printNBlueSpaces(2);
        printNBlueSpaces(2);
        printNResetSpaces(2);
        printNBlueSpaces(2);
        printNResetSpaces(2);
        printNBlueSpaces(2);
        printNResetSpaces(2);
        printNBlueSpaces(6);
        printNBlueSpaces(2);
        printNBlueSpaces(2);
        printNBlueSpaces(2);
        printNBlueSpaces(2);
        printNResetSpaces(2);
        printNBlueSpaces(2);
        printNBlueSpaces(2);
        System.out.println();

        System.out.println();
    }


    /**
     * prints n times a space on the reset color background.
     * @param n times
     */
    public void printNResetSpaces(int n){

        for(int i = 0; i < n; i++) {
            System.out.print("\033[049m");
            //Reset
            System.out.print(" ");
        }
    }


    /**
     * Prints n times a space on a blue background.
     * @param n times
     */
    public void printNBlueSpaces(int n){

        for(int i = 0; i < n; i++) {
            System.out.print("\033[044m");
            System.out.print(" "); //2i
        }
        System.out.print("\033[049m");
        //Reset
        System.out.print("  ");
    }
}
