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

import javax.swing.*;


public class SwingView extends View {

    private int contPlaceToken = 0 , getContPlaceToken2 = 0;
    private GameFrame gameFrame = null;
    private CellButton[][] battlefieldGUI; //maybe delete
    private ServerResponse currentServerResponse;

    private Player player;
    private int savedToken;
    private String godcardsForTheGame;
    private List<GodCard> godsInGame = new ArrayList<>();

    private int count = 2;
    private Cell first_cell;
    Boolean power = null;


    /**
     * Constructor of the client view with Swing GUI
     */
    public SwingView(){
    }

    public void setWantToUsePower(Boolean power){this.power = power;}


    /*     GETTER      */

    protected Player getPlayer(){
        return player;
    }

    public CellButton[][] getBattlefieldGUI() {
        return this.gameFrame.getInnerMainPanel().getBattlefieldGUI();
    }

    public ServerResponse getCurrentServerResponse() {
        return currentServerResponse;
    }

    public void setBattlefieldGUI(CellButton[][] battlefieldGUI) {
        this.battlefieldGUI = battlefieldGUI;
    }

    public Boolean wantToUsePower(){return power;}


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
    public void update(ServerResponse serverResponse) throws CellOutOfBattlefieldException {

        this.currentServerResponse = serverResponse;

        switch (serverResponse.getPack().getAction()) {

            // The first time a player connects it is asked for his name
            // This continue till the name is valid
            case WELCOME:{

                new WelcomeFrame(this);
                break;
            }

            case INVALID_NAME: {

                JOptionPane.showMessageDialog(new JFrame(),"Your name is invalid","Error", JOptionPane.ERROR_MESSAGE, Pics.ERRORICON.getImageIcon());
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

                JOptionPane.showMessageDialog(new JFrame(),serverResponse.getPack().getAction().toString(),"NUMBER_RECEIVED", JOptionPane.INFORMATION_MESSAGE, Pics.INFORMATIONICON.getImageIcon());
                break;
            }

            //The first choice is sent by the server and contains player data
            case CHOOSE_GOD_CARD_TO_PLAY: {

                this.player = serverResponse.getPack().getPlayer();
                new ChooseGodCardToPlayWindow(this, serverResponse);
                break;
            }

            // The second player receives the god choice message, the 2nd and 3rd receive this with player data
            case WAIT_AND_SAVE_PLAYER_FROM_SERVER:{

                player = serverResponse.getPack().getPlayer();
                JOptionPane.showMessageDialog(new JFrame(),serverResponse.getPack().getAction().toString(),"WAIT_AND_SAVE_PLAYER_FROM_SERVER", JOptionPane.INFORMATION_MESSAGE, Pics.INFORMATIONICON.getImageIcon());
                break;
            }


            // SINCE NOW ALL THE MESSAGES ARE BROADCAST-RECEIVED

            case SELECT_YOUR_GOD_CARD:{

                Pack pack = serverResponse.getPack();

                // If the player is not in turn he is just notified to wait
                if (!this.player.getTokenColor().equals(serverResponse.getTurn())){
                    JOptionPane.showMessageDialog(new JFrame(),pack.getMessageOpponents(),"NOT YOUR TURN!! PLEASE WAIT!", JOptionPane.WARNING_MESSAGE, Pics.ERRORICON.getImageIcon());
                }
                // else he has to pick his god card
                else {
                    new ChooseGodCardWindow(this, serverResponse);
                }
                break;
            }

            // A player has to place his token, other wait
            // Here the gameframe is initalised
            case PLACE_YOUR_TOKEN:{

                Pack pack = serverResponse.getPack();

                if (!player.getTokenColor().equals(serverResponse.getTurn())){
                    //se non è null quindi se ha già posizionato i token, si aggiorna la board
                    if(this.gameFrame != null){
                        displayGui(getBattlefieldGUI(), serverResponse.getPack().getModelCopy(), null);
                        this.gameFrame.getInnerMainPanel().getMessageLabel().setIcon(Pics.PLACE_YOUR_TOKEN.getImageIcon());
                    }
                    //altrimenti crea la board così può vedere dove l'altro posiziona i token
                    //per fare una cosa fatta bene dobbiamo disabilitare i bottoni a questo player
                    else{
                        this.gameFrame = new GameFrame(serverResponse,this);
                        this.battlefieldGUI = getBattlefieldGUI();
                        contPlaceToken++;
                    }
                    JOptionPane.showMessageDialog(new JFrame(), pack.getMessageOpponents(), "NOT YOUR TURN! PLEASE WAIT", JOptionPane.WARNING_MESSAGE, Pics.ERRORICON.getImageIcon());
                }
                else {
                    this.player = serverResponse.getPack().getPlayer();
                    // here i initialise the Game Frame that will last till the end of the game
                    //new GameFrame(serverResponse,this);
                    if(contPlaceToken == 0){
                        JOptionPane.showMessageDialog(new JFrame(),pack.getAction().getName().toUpperCase(),"IT'S YOUR TURN, ", JOptionPane.WARNING_MESSAGE, Pics.INFORMATIONICON.getImageIcon());
                        this.gameFrame = new GameFrame(serverResponse,this);
                        this.battlefieldGUI = getBattlefieldGUI();
                        contPlaceToken++;
                    }
                    else{
                        JOptionPane.showMessageDialog(new JFrame(),pack.getAction().getName().toUpperCase(),"AGAIN YOUR TURN, ", JOptionPane.WARNING_MESSAGE, Pics.INFORMATIONICON.getImageIcon());
                        displayGui(getBattlefieldGUI(), serverResponse.getPack().getModelCopy(), null);
                    }
                }
                break;
            }

            case PLAYER_LOST:
            case TOKEN_NOT_MOVABLE:
            case ASK_FOR_SELECT_TOKEN: {

                Pack pack = serverResponse.getPack();

                if (!player.getTokenColor().equals(serverResponse.getTurn())){
                    JOptionPane.showMessageDialog(new JFrame(),pack.getMessageOpponents(),"NOT YOUR TURN!! PLEASE WAIT!", JOptionPane.WARNING_MESSAGE, Pics.ERRORICON.getImageIcon());
                    displayGui(getBattlefieldGUI(), serverResponse.getPack().getModelCopy(), null);
                }
                else {
                    this.player = pack.getPlayer();
                    JOptionPane.showMessageDialog(new JFrame(), pack.getAction().getName().toUpperCase(), "YOUR TURN, ", JOptionPane.WARNING_MESSAGE, Pics.INFORMATIONICON.getImageIcon());

                    //new GameFrame(serverResponse,this,null);

                    displayGui(getBattlefieldGUI(), serverResponse.getPack().getModelCopy(), null);
                    //gameFrame.setVisible(true);

                    //this.gameFrame.updateGui(serverResponse, false);
                    //new GameFrame(serverResponse, this, null);
                }
                break;
            }


            case ASK_FOR_PROMETHEUS_POWER:{

                Pack pack = serverResponse.getPack();

                if (!player.getTokenColor().equals(serverResponse.getTurn())){
                    System.out.println(pack.getMessageOpponents());
                }
                else {
                    new AskPrometheusPowerWindow(this);
                }
                break;
            }


            case GAME_OVER:{
                Pack pack = serverResponse.getPack();
                JOptionPane.showMessageDialog(new JFrame(), pack.getAction().getName().toUpperCase(), "GAME OVER ", JOptionPane.WARNING_MESSAGE, Pics.GAMEOVERICON.getImageIcon());
                System.out.print(serverResponse.getPack().getAction().getInfo());
                System.out.println(pack.getMessageInTurn());
                break;
            }


            case ASK_FOR_WHERE_TO_MOVE:{

                Pack pack = serverResponse.getPack();

                if (!player.getTokenColor().equals(serverResponse.getTurn())){
                    JOptionPane.showMessageDialog(new JFrame(), pack.getMessageOpponents(), "NOT YOUR TURN",JOptionPane.INFORMATION_MESSAGE, Pics.ERRORICON.getImageIcon());
                    displayGui(getBattlefieldGUI(), serverResponse.getPack().getModelCopy(), null);
                }
                else{
                    JOptionPane.showMessageDialog(new JFrame(), pack.getAction().getName().toUpperCase(), "YOUR TURN, ", JOptionPane.WARNING_MESSAGE, Pics.INFORMATIONICON.getImageIcon());
                    //this.gameFrame.updateGui(serverResponse, false);
                    displayGui(getBattlefieldGUI(), serverResponse.getPack().getModelCopy(), null);
                    //new GameFrame(serverResponse,this,null);
                }
                break;
            }


            case ASK_FOR_BUILD:{

                Pack pack = serverResponse.getPack();

                if (!player.getTokenColor().equals(serverResponse.getTurn())){
                    displayGui(getBattlefieldGUI(), serverResponse.getPack().getModelCopy(), null);
                    JOptionPane.showMessageDialog(new JFrame(), pack.getMessageOpponents(), "NOT YOUR TURN",JOptionPane.INFORMATION_MESSAGE, Pics.ERRORICON.getImageIcon());
                }
                else{
                    this.player = pack.getPlayer();
                    new AskToUseTheGodsPower(this, serverResponse,this.gameFrame);
                }
                break;
            }
        }
    }




    /**
     * Here i print the GUI for the user, depending on the parameter validMoves;
     *   -if it is null, i print the normal battlefield with the tokens on
     *   -otherwise i print even a green backgrounds behind the cells in the ValidMoves param.
     * @param validMoves: cells that have to be printed on a green background (can be null)
     * @param modelCopy: contains the the board of the game and the players in the game
     */
    public void displayGui(CellButton[][] battlefieldGUI, ModelUtils modelCopy, List<Cell> validMoves) throws CellOutOfBattlefieldException {

        Battlefield newBattlefield = modelCopy.getBattlefield();
        List<Player> allPlayers = modelCopy.getAllPlayers();

        for (int y = 4; y > -1; y--) {

            for (int x = 0; x < 5; x++) {

                if (validMoves != null) {
                    if (validMoves.contains(newBattlefield.getCell(x, y))) {
                        battlefieldGUI[x][y].getCell().setHeight(newBattlefield.getCell(x, y).getHeight());
                        battlefieldGUI[x][y].changeImageIcon(selectIcon(null, newBattlefield.getCell(x, y),false, true));
                        battlefieldGUI[x][y].setRolloverIcon(selectRolloverIcon(newBattlefield.getCell(x,y),false));
                    }
                    else{
                        displayInnerGui(battlefieldGUI, newBattlefield, allPlayers, y, x);
                    }
                }
                else{
                    displayInnerGui(battlefieldGUI, newBattlefield, allPlayers, y, x);
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
    public void displayInnerGui(CellButton[][] battlefieldGUI, Battlefield battlefield, List<Player> allPlayers, int y, int x) throws CellOutOfBattlefieldException {

        // we check if exists a token of any player in this position
        if(!battlefield.getCell(x, y).getThereIsPlayer()){
            // we check if it is dome
            if(battlefield.getCell(x, y).getIsDome()){

                battlefieldGUI[x][y].getCell().setHeight(battlefield.getCell(x, y).getHeight());
                battlefieldGUI[x][y].getCell().setIsDome();
                battlefieldGUI[x][y].changeImageIcon(selectIcon(null, battlefield.getCell(x, y), true,false));
                battlefieldGUI[x][y].setRolloverIcon(selectRolloverIcon(battlefield.getCell(x, y), true));
            }
            else {
                battlefieldGUI[x][y].getCell().setHeight(battlefield.getCell(x, y).getHeight());
                battlefieldGUI[x][y].changeImageIcon(selectIcon(null, battlefield.getCell(x, y), false,false));
                battlefieldGUI[x][y].setRolloverIcon(selectRolloverIcon(battlefield.getCell(x, y),false));
            }
        }
        else{
            for(Player p : allPlayers) {
                if (p.getToken1().getTokenPosition() != null && p.getToken1().getTokenPosition()!=null) {                                  //if he has the first token
                    if (p.getToken1().getTokenPosition().equals(battlefield.getCell(x, y))) {
                        battlefieldGUI[x][y].getCell().setHeight(battlefield.getCell(x, y).getHeight());
                        battlefieldGUI[x][y].changeImageIcon(selectIcon(p, battlefield.getCell(x, y),false, false));
                        battlefieldGUI[x][y].setRolloverIcon(selectRolloverIcon(battlefield.getCell(x,y),false));
                    }
                }
                if (p.getToken2().getTokenPosition() != null && p.getToken2().getTokenPosition()!=null) {                                  //if he has the first token
                    if (p.getToken2().getTokenPosition().equals(battlefield.getCell(x, y))) {
                        battlefieldGUI[x][y].getCell().setHeight(battlefield.getCell(x, y).getHeight());
                        battlefieldGUI[x][y].changeImageIcon(selectIcon(p, battlefield.getCell(x, y),false, false));
                        battlefieldGUI[x][y].setRolloverIcon(selectRolloverIcon(battlefield.getCell(x,y),false));
                    }
                }
            }
        }
    }

    /**
     * It selects the right text image depending on the characteristic
     * @param cell: the cell in wich i have to put the icon
     * @param isDome dome or not
     * @return ImageIcon of the cell to display
     */
    public ImageIcon selectRolloverIcon(Cell cell, boolean isDome) {

        //display dome text  height
        if(isDome)
            return switchOnHeight(cell.getHeight(), Pics.LEVEL0DOMETEXT, Pics.LEVEL1DOMETEXT, Pics.LEVEL2DOMETEXT, Pics.LEVEL3DOMETEXT);
        //display basic text  height
        else
            return switchOnHeight(cell.getHeight(), Pics.LEVEL0TEXT, Pics.LEVEL1TEXT, Pics.LEVEL2TEXT, Pics.LEVEL3TEXT);
    }

    /**
     * It selects the right image depending on the characteristics
     * @param player: if it's not null, that one is the player on that cell
     * @param cell: the cell in wich i have to put the icon
     * @param iHaveToDisplayValidMoves auto explicative
     * @return ImageIcon of the cell to display
     */
    public ImageIcon selectIcon(Player player, Cell cell, boolean isDome, boolean iHaveToDisplayValidMoves) {

        ImageIcon toReturn = new ImageIcon();

        //player not present
        if(player == null) {
            //valid moves to display
            if(iHaveToDisplayValidMoves)
                return switchOnHeight(cell.getHeight(), Pics.LEVEL0VALIDMOVE, Pics.LEVEL1VALIDMOVE, Pics.LEVEL2VALIDMOVE, Pics.LEVEL3VALIDMOVE);
            else {
                if(isDome)
                    return switchOnHeight(cell.getHeight(), Pics.LEVEL0DOME, Pics.LEVEL1DOME, Pics.LEVEL2DOME, Pics.LEVEL3DOME);
                else
                    return switchOnHeight(cell.getHeight(), Pics.LEVEL0, Pics.LEVEL1, Pics.LEVEL2, Pics.LEVEL3);
            }
        }

        //player present
        else{
            switch(player.getTokenColor()){
                case RED:{
                    if(!iHaveToDisplayValidMoves)
                        return switchOnHeight(cell.getHeight(), Pics.LEVEL0TOKENRED, Pics.LEVEL1TOKENRED, Pics.LEVEL2TOKENRED, Pics.LEVEL3TOKENRED);
                    else
                        return switchOnHeight(cell.getHeight(), Pics.LEVEL0TOKENREDVALIDMOVE, Pics.LEVEL1TOKENREDVALIDMOVE, Pics.LEVEL2TOKENREDVALIDMOVE, Pics.LEVEL3TOKENREDVALIDMOVE);
                }
                case BLUE:{
                    if(!iHaveToDisplayValidMoves)
                        return switchOnHeight(cell.getHeight(), Pics.LEVEL0TOKENBLUE, Pics.LEVEL1TOKENBLUE, Pics.LEVEL2TOKENBLUE, Pics.LEVEL3TOKENBLUE);
                    else
                        return switchOnHeight(cell.getHeight(), Pics.LEVEL0TOKENBLUEVALIDMOVE, Pics.LEVEL1TOKENBLUEVALIDMOVE, Pics.LEVEL2TOKENBLUEVALIDMOVE, Pics.LEVEL3TOKENBLUEVALIDMOVE);
                }
                case YELLOW:{
                    if(!iHaveToDisplayValidMoves)
                        return switchOnHeight(cell.getHeight(), Pics.LEVEL0TOKENYELLOW, Pics.LEVEL1TOKENYELLOW, Pics.LEVEL2TOKENYELLOW, Pics.LEVEL3TOKENYELLOW);
                    else
                        return switchOnHeight(cell.getHeight(), Pics.LEVEL0TOKENYELLOWVALIDMOVE, Pics.LEVEL1TOKENYELLOWVALIDMOVE, Pics.LEVEL2TOKENYELLOWVALIDMOVE, Pics.LEVEL3TOKENYELLOWVALIDMOVE);
                }
            }
        }
        return toReturn;
    }

    /**
     * Switch on dynamic images to choose which one has to be displayed
     * @param height of this cell
     * @return the right imageicon, or error icon
     */
    public ImageIcon switchOnHeight(int height, Pics p1, Pics p2, Pics p3, Pics p4) {

            switch (height) {
                case 0: {
                    return p1.getImageIcon();
                }
                case 1: {
                    return p2.getImageIcon();
                }
                case 2: {
                    return p3.getImageIcon();
                }
                case 3: {
                    return p4.getImageIcon();
                }
                default: { //error
                    return Pics.ERRORICON.getImageIcon();
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

    public Cell getCell(int posx, int posy, Battlefield battlefield){

        int selectX, selectY;
        selectX = posx;
        selectY = posy;

        try {
            return battlefield.getCell(selectX, selectY);
        } catch (CellOutOfBattlefieldException e) {
            return null;
        }
    }

    public boolean notPerimeterCell(Cell targetCell){
        return ((targetCell.getPosX()!=4 && targetCell.getPosY()!=4) && (targetCell.getPosX()!=0 && targetCell.getPosY()!=0));
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

    public int GetSavedToken(){
        return savedToken;
    }

    public void buildGod(Cell targetcell) throws ImpossibleTurnException, IOException, CellHeightException, WrongNumberPlayerException, ReachHeightLimitException, CellOutOfBattlefieldException {

        count --;
        switch (player.getMyGodCard()) {
            case DEMETER: {
                if (count == 1) {
                    first_cell = targetcell;
                    break;
                }
                if (count == 0) {
                    count = 2;
                    PlayerAction playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, player, null, null, savedToken, 0, first_cell, targetcell, true, null);
                    notifyClient(playerAction);
                    first_cell = null;
                    break;
                }
                break;
            }
            case HESTIA:{
                if(count == 1){
                    first_cell = targetcell;
                    break;
                }
                if(count == 0){
                    count = 2;
                    PlayerAction playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, player, null, null, savedToken, 0, first_cell, targetcell, true, null);
                    notifyClient(playerAction);
                    first_cell = null;
                    break;
                }
            }
        }
    }
}
