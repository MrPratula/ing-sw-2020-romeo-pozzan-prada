package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.cli.Player;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.cli.*;
import it.polimi.ingsw.utils.*;
import it.polimi.ingsw.utils.Action;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;
import java.io.File;


public class SwingView extends View {

    private JFrame mainFrame;        //1
    private JPanel mainPanel;        //2
    private JButton playButton;      //3.1
    private JLabel logoImage;

    private GameFrame gameFrame;
    private CellButton[][] battlefieldGUI;

    private Player player;
    private int savedToken;
    private String godcardsForTheGame;
    private List<GodCard> godsInGame = new ArrayList<>();


    /**
     * Constructor of the client view with Swing GUI
     */
    public SwingView(){
    }


    /*     GETTER      */

    protected Player getPlayer(){
        return player;
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public CellButton[][] getBattlefieldGUI() {
        return battlefieldGUI;
    }

    public void setBattlefieldGUI(CellButton[][] battlefieldGUI) {
        this.battlefieldGUI = battlefieldGUI;
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

    /**
     * Calls the notify to make update the observers
     * @param playerAction
     * @throws CellOutOfBattlefieldException
     * @throws ReachHeightLimitException
     * @throws CellHeightException
     * @throws IOException
     * @throws ImpossibleTurnException
     * @throws WrongNumberPlayerException
     */
    public void notifyClient(PlayerAction playerAction) throws CellOutOfBattlefieldException, ReachHeightLimitException, CellHeightException, IOException, ImpossibleTurnException, WrongNumberPlayerException {
        notify(playerAction);
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
            case WELCOME:{

                new WelcomeFrame(this);
                break;
            }

            case INVALID_NAME: {

                JOptionPane.showMessageDialog(new JFrame(),"Your name is invalid","Error", JOptionPane.ERROR_MESSAGE);  //posso anche mettere un'immagine error
                new NickNameWindow(this);
                break;
            }

            // Only the first player is asked for how many player he wants to play with
            // This value is stored in token main field of PlayerAction
            case HOW_MANY_PLAYERS:
            case WRONG_NUMBER_OF_PLAYER: {

                new NumberOfPlayersWindow(this);
                break;
            }

            // Just tell the client the server is not popped
            // When the first player answer how much players there will be, waiting for the players to connect
            case WAIT_OTHER_PLAYERS_TO_CONNECT:
            case NUMBER_RECEIVED: {

                JOptionPane.showMessageDialog(new JFrame(),serverResponse.getPack().getAction().toString(),"NUMBER_RECEIVED", JOptionPane.INFORMATION_MESSAGE);  //posso anche mettere un'immagine error
                break;
            }

            //The first choice is send by the server and contains player data
            case CHOOSE_GOD_CARD_TO_PLAY: {

                this.player = serverResponse.getPack().getPlayer();
                new ChooseGodCardToPlayWindow(this, serverResponse);

                break;
            }

            // The second player receives the god choice message, the 2nd and 3rd receive this with player data
            case WAIT_AND_SAVE_PLAYER_FROM_SERVER:{

                player = serverResponse.getPack().getPlayer();
                JOptionPane.showMessageDialog(new JFrame(),serverResponse.getPack().getAction().toString(),"WAIT_AND_SAVE_PLAYER_FROM_SERVER", JOptionPane.INFORMATION_MESSAGE);  //posso anche mettere un'immagine error
                break;
            }


            // TILL NOW ALL THE MESSAGES ARE BROADCAST-RECEIVED

            case SELECT_YOUR_GOD_CARD:{

                Pack pack = serverResponse.getPack();

                // If the player is not in turn he is just notified to wait
                if (!player.getTokenColor().equals(serverResponse.getTurn())){
                    JOptionPane.showMessageDialog(new JFrame(),pack.getMessageOpponents(),"NOT YOUR TURN!! PLEASE WAIT!", JOptionPane.WARNING_MESSAGE);
                }
                // else he has to pick his god card
                else {
                    new ChooseGodCardWindow(this, serverResponse);
                }
                break;
            }

            // A player has to place his token, other wait
            case PLACE_YOUR_TOKEN:{

                Pack pack = serverResponse.getPack();

                if (!player.getTokenColor().equals(serverResponse.getTurn())){
                    JOptionPane.showMessageDialog(new JFrame(),pack.getMessageOpponents(),"NOT YOUR TURN!! PLEASE WAIT", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    this.player = serverResponse.getPack().getPlayer();
                    JOptionPane.showMessageDialog(new JFrame(),pack.getAction().getName().toUpperCase(),"YOUR TURN, ", JOptionPane.WARNING_MESSAGE);
                    new GameFrame(serverResponse,this);
                }
                break;
            }

            case PLAYER_LOST:
            case TOKEN_NOT_MOVABLE:
            case ASK_FOR_SELECT_TOKEN: {

                Pack pack = serverResponse.getPack();

                if (!player.getTokenColor().equals(serverResponse.getTurn())){
                    JOptionPane.showMessageDialog(new JFrame(),pack.getMessageOpponents(),"NOT YOUR TURN!! PLEASE WAIT!", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    this.player = pack.getPlayer();
                    JOptionPane.showMessageDialog(new JFrame(), pack.getAction().getName().toUpperCase(), "YOUR TURN, ", JOptionPane.WARNING_MESSAGE);
                    new GameFrame(serverResponse, this);
                }
                break;
            }


            case ASK_FOR_PROMETHEUS_POWER:{

                Pack pack = serverResponse.getPack();

                if (!player.getTokenColor().equals(serverResponse.getTurn())){
                    System.out.println(pack.getMessageOpponents());
                }
                else {
                    new AskPrometheusPowerFrame(this);
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

                if (!player.getTokenColor().equals(serverResponse.getTurn())){
                    JOptionPane.showMessageDialog(new JFrame(), pack.getMessageOpponents(), "NOT YOUR TURN",JOptionPane.INFORMATION_MESSAGE);
                    displayGui(pack.getModelCopy(), pack.getValidMoves());
                    gameFrame.getMessageLabel().setIcon(Pics.NOT_YOUR_TURN.getImageIcon());
                }
                else{

                    boolean needToLoop = true;
                    PlayerAction playerAction = null;

                    while (needToLoop){

                        displayGui(pack.getModelCopy(), pack.getValidMoves());
                        gameFrame.getMessageLabel().setIcon(Pics.ASK_FOR_WHERE_TO_MOVE.getImageIcon());

                        try{
                            //GET CEL ON THE FRAME
                        } catch (Exception e){
                            JOptionPane.showMessageDialog(new JFrame("error"), pack.getAction().toString(),"Your input wasn't correct!", JOptionPane.ERROR_MESSAGE);
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
                                JOptionPane.showMessageDialog(new JFrame("error"), pack.getAction().toString(),"Your input wasn't correct!", JOptionPane.ERROR_MESSAGE);
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

    /**
     * Links a string of god names into the real
     * GodCards corresponding to them
     * @param godcardsForTheGame: String of god names
     * @return GodCards in game
     */
    private List<GodCard> recognizeGodInGame(String godcardsForTheGame) {

        List<GodCard> godsSeparated = new ArrayList<>();
        String[] godNamesInGame = godcardsForTheGame.split(" ");
        int numberOfGodsInGame = godNamesInGame.length;
        GodCard[] allGods = GodCard.values();
        int i = 0, j = 0;
        while(i < numberOfGodsInGame){
            while(!godNamesInGame[i].equals(allGods[j].name())){
                j++;
            }
            godsSeparated.add(allGods[j]);
            i++;
        }
        return godsSeparated;
    }


    //public void updateGui(ModelUtils modelCopy, List<Cell> validMoves){ }

    /**
     * Here i print the GUI for the user, depending on the parameter validMoves;
     *   -if it is null, i print the normal battlefield with the tokens on
     *   -otherwise i print even a green backgrounds behind the cells in the ValidMoves param.
     * @param validMoves: cells that have to be printed on a green background (can be null)
     * @param modelCopy: contains the the board of the game and the players in the game
     */
    public void displayGui(ModelUtils modelCopy, List<Cell> validMoves) throws CellOutOfBattlefieldException {

        Battlefield battlefield = modelCopy.getBattlefield();
        List<Player> allPlayers = modelCopy.getAllPlayers();

        for (int y = 4; y > -1; y--) {

            for (int x = 0; x < 5; x++) {

                if (validMoves != null) {
                    if (validMoves.contains(battlefield.getCell(x, y))) {
                        battlefieldGUI[x][y].getCell().setHeight(battlefield.getCell(x, y).getHeight());
                        battlefieldGUI[x][y].setIcon(selectIcon(null, battlefield.getCell(x, y),true));
                        battlefieldGUI[x][y].setRolloverIcon(selectRolloverIcon(battlefield.getCell(x,y)));
                    }
                    else{
                        displayInnerGui(battlefield, allPlayers, y, x);
                    }
                }
                else{
                    displayInnerGui(battlefield, allPlayers, y, x);
                }
            }
        }
    }

    /**
     * Auxiliary method to print the GUI, here i check the token position's
     * @param battlefield: the board of the game
     * @param allPlayers: the players in the game
     * @param x: position x of the battlefield
     * @param y: position y of the battlefield
     */
    public void displayInnerGui(Battlefield battlefield, List<Player> allPlayers, int y, int x) throws CellOutOfBattlefieldException {

        // we check if exists a token of any player in this position
        if(!battlefield.getCell(x, y).getThereIsPlayer()){
            battlefieldGUI[x][y].getCell().setHeight(battlefield.getCell(x,y).getHeight());
            battlefieldGUI[x][y].setIcon(selectIcon(null,battlefield.getCell(x,y),false));
            battlefieldGUI[x][y].setRolloverIcon(selectRolloverIcon(battlefield.getCell(x,y)));
        }
        else{
            for(Player p : allPlayers) {
                if (p.getToken1().getTokenPosition() != null && p.getToken1().getTokenPosition()!=null) {                                  //if he has the first token
                    if (p.getToken1().getTokenPosition().equals(battlefield.getCell(x, y))) {
                        battlefieldGUI[x][y].getCell().setHeight(battlefield.getCell(x, y).getHeight());
                        battlefieldGUI[x][y].setIcon(selectIcon(p, battlefield.getCell(x, y),false));
                        battlefieldGUI[x][y].setRolloverIcon(selectRolloverIcon(battlefield.getCell(x,y)));
                    }
                }
                if (p.getToken2().getTokenPosition() != null && p.getToken2().getTokenPosition()!=null) {                                  //if he has the first token
                    if (p.getToken2().getTokenPosition().equals(battlefield.getCell(x, y))) {
                        battlefieldGUI[x][y].getCell().setHeight(battlefield.getCell(x, y).getHeight());
                        battlefieldGUI[x][y].setIcon(selectIcon(p, battlefield.getCell(x, y),false));
                        battlefieldGUI[x][y].setRolloverIcon(selectRolloverIcon(battlefield.getCell(x,y)));
                    }
                }
            }
        }
    }

    /**
     * It selects the right text image depending on the characteristic
     * @param cell: the cell in wich i have to put the icon
     * @return ImageIcon of the cell to display
     */
    public ImageIcon selectRolloverIcon(Cell cell) {

        String startPath = "./src/main/images/buildings/";
        //display basic text  height
        return switchOnHeight(cell.getHeight(),startPath,"level0Text.png","level1Text.png","level2Text.png","level3Text.png","levelDomeText.png");
    }

    /**
     * It selects the right image depending on the characteristics
     * @param player: if it's not null, that one is the player on that cell
     * @param cell: the cell in wich i have to put the icon
     * @return ImageIcon of the cell to display
     */
    public ImageIcon selectIcon(Player player, Cell cell, boolean iHaveToDisplayValidMoves) {

        String startPath = "./src/main/images/buildings/";
        ImageIcon toReturn = new ImageIcon();

        //display basic height with no players on it
        if(player == null) {
            if(iHaveToDisplayValidMoves) return switchOnHeight(cell.getHeight(),startPath,"level0.png","level1.png","level2.png","level3.png","levelDome.png");
            else return switchOnHeight(cell.getHeight(),startPath,"level0ValidMove.png","level1ValidMove.png","level2ValidMove.png","level3ValidMove.png","levelDome.png");

        }
        //display height with currespundant player on it
        else{
            switch(player.getTokenColor()){
                case RED:{
                    return switchOnHeight(cell.getHeight(),startPath,"level0tokenRed.png","level1tokenRed.png","level2tokenRed.png","level3tokenRed.png","levelDome.png");
                }
                case BLUE:{
                    return switchOnHeight(cell.getHeight(),startPath,"level0tokenBlue.png","level1tokenBlue.png","level2tokenBlue.png","level3tokenBlue.png","levelDome.png");
                }
                case YELLOW:{
                    return switchOnHeight(cell.getHeight(),startPath,"level0tokenYellow.png","level1tokenYellow.png","level2tokenYellow.png","level3tokenYellow.png","levelDome.png");
                }
            }
        }
        return toReturn;
    }

    /**
     * Switch on dynamic images to choose which one has to be displayed
     * @param height of this cell
     * @param startPath starting path of images
     * @param str0 in case of level 0
     * @param str1 in case of level 1
     * @param str2 in case of level 2
     * @param str3 in case of level 3
     * @param str4 in case of level dome
     * @return
     */
    public ImageIcon switchOnHeight(int height, String startPath, String str0, String str1, String str2, String str3, String str4) {

        switch (height) {
            case 0: {
               return new ImageIcon(new File(startPath + str0).getAbsolutePath());
            }
            case 1: {
                return new ImageIcon(new File(startPath + str1).getAbsolutePath());
            }
            case 2: {
                return new ImageIcon(new File(startPath + str2).getAbsolutePath());
            }
            case 3: {
                return new ImageIcon(new File(startPath + str3).getAbsolutePath());
            }
            default: { //dome
                return new ImageIcon(new File(startPath + str4).getAbsolutePath());
            }
        }
    }

    public int getToken(int posx, int posy, Player player){

        int selectX, selectY;
        selectX = posx;
        selectY = posy;

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

    public void setGodCardsForTheGame(String g) {
        this.godcardsForTheGame = g;
    }

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

    public void SavedToken(int selectedToken){
        savedToken = selectedToken;
    }
}
