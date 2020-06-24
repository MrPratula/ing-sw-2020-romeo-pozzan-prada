package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.*;
import it.polimi.ingsw.utils.Action;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;


/**
 * Class that implements the user view of the game,
 * if he selects to use the Graphic User Interface
 */
public class SwingView extends View {

    private Player player;

    private int contPlaceToken = 0;
    private GameFrame gameFrame = null;
    private ServerResponse currentServerResponse;
    private List<Cell> currentValidBuilds = null;

    private int savedToken;
    private int count = 2;
    private Cell firstCell;
    Boolean power = null;
    private CellButton[][] battlefieldGUI;


    /**
     * Constructor of the client view with Swing GUI
     */
    public SwingView(){
    }


    /*     SETTER     */

    public void setFirstCell(Cell targetcell){
        this.firstCell = targetcell;
    }

    public void setWantToUsePower(Boolean power){
        this.power = power;
    }

    public void setValidBuilds(List<Cell>valid){
        currentValidBuilds = valid;
    }

    public void savedToken(int selectedToken){
        savedToken = selectedToken;
    }


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

    public List<Cell> getCurrentValidBuilds(){
        return currentValidBuilds;
    }

    public int getSavedToken(){
        return savedToken;
    }


    public Boolean wantToUsePower(){
        return power;
    }



    /**
     * When needed, it returns the token that has just been selected from the user,
     * and even the not selected one (it can be useful).
     * @param posX: the first input of the user, i.e. the column of the battlefield.
     * @param posY: the second input of the user, i.e. the row of the battlefield.
     * @return a list containing the selected and the not selected token.
     */
    public List<Token> computeTokens(int posX, int posY){

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
        if(numberOfPlayers==3){
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
     * @param playerAction action of the player
     * @throws IOException
     */
    public void notifyClient(PlayerAction playerAction) throws IOException {
        notify(playerAction);
    }


    /**
     * This method moves the mechanic of the game: it receives the
     * response from the server, and creates the respective action of the player.
     * @param serverResponse: response from the server containing al the necessary informations
     */
    @Override
    public void update(ServerResponse serverResponse) throws IOException {

        this.currentServerResponse = serverResponse;

        switch (serverResponse.getPack().getAction()) {

            // The first time a player connects it is asked for his name
            // This continue till the name is valid
            case WELCOME:{

                new WelcomeFrame(this);
                break;
            }

            case INVALID_NAME: {

                final JDialog dialog = new JDialog();
                dialog.setAlwaysOnTop(true);
                JOptionPane.showMessageDialog(dialog,"Another player has already selected this name, retry.","Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ImageIO.read(getClass().getResource(Pics.ERRORICON.getPath()))));
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

                //final JDialog dialog = new JDialog();
                //dialog.setAlwaysOnTop(true);
                //JOptionPane.showMessageDialog(dialog,serverResponse.getPack().getAction().toString(),"NUMBER RECEIVED", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ImageIO.read(getClass().getResource(Pics.INFORMATIONICON.getPath()))));
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
                //final JDialog dialog = new JDialog();
                //dialog.setAlwaysOnTop(true);
                //JOptionPane.showMessageDialog(dialog,serverResponse.getPack().getAction().toString(),"WAIT AND SAVE PLAYER FROM SERVER", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ImageIO.read(getClass().getResource(Pics.INFORMATIONICON.getPath()))));
                break;
            }


            // SINCE NOW ALL THE MESSAGES ARE BROADCAST-RECEIVED

            case SELECT_YOUR_GOD_CARD:{

                Pack pack = serverResponse.getPack();

                // If the player is not in turn he is just notified to wait
                if (!this.player.getTokenColor().equals(serverResponse.getTurn())){
                    //final JDialog dialog = new JDialog();
                    //dialog.setAlwaysOnTop(true);
                    //JOptionPane.showMessageDialog(dialog,pack.getMessageOpponents(),"NOT YOUR TURN! PLEASE WAIT!", JOptionPane.WARNING_MESSAGE, new ImageIcon(ImageIO.read(getClass().getResource(Pics.ERRORICON.getPath()))));
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
                        this.gameFrame.getInnerMainPanel().getMessageLabel().setIcon(new ImageIcon(ImageIO.read(getClass().getResource(Pics.NOT_YOUR_TURN.getPath()))));
                    }
                    //altrimenti crea la board così può vedere dove l'altro posiziona i token
                    else{
                        this.gameFrame = new GameFrame(serverResponse,this);
                        this.battlefieldGUI = getBattlefieldGUI();
                        contPlaceToken++;
                    }
                }
                else {
                    if(pack.getPlayer()!=null)
                        this.player = serverResponse.getPack().getPlayer();

                    if(contPlaceToken == 0){
                        //final JDialog dialog = new JDialog();
                        //dialog.setAlwaysOnTop(true);
                        //JOptionPane.showMessageDialog(dialog, pack.getAction().getName().toUpperCase(),"IT'S YOUR TURN! PLACE YOUR FIRST TOKEN!"+getPlayer().getUsername(), JOptionPane.WARNING_MESSAGE, new ImageIcon(ImageIO.read(getClass().getResource(Pics.INFORMATIONICON.getPath()))));
                        this.gameFrame = new GameFrame(serverResponse,this);
                        this.battlefieldGUI = getBattlefieldGUI();
                        this.gameFrame.getInnerMainPanel().getMessageLabel().setIcon(new ImageIcon(ImageIO.read(getClass().getResource(Pics.PLACE_YOUR_TOKEN.getPath()))));
                        contPlaceToken++;
                    }
                    else{
                        //final JDialog dialog = new JDialog();
                        //dialog.setAlwaysOnTop(true);
                        //JOptionPane.showMessageDialog(dialog,pack.getAction().getName().toUpperCase(),"AGAIN YOUR TURN! PLACE YOUR SECOND TOKEN! "+getPlayer().getUsername(), JOptionPane.WARNING_MESSAGE, new ImageIcon(ImageIO.read(getClass().getResource(Pics.INFORMATIONICON.getPath()))));
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
                    this.gameFrame.getInnerMainPanel().getMessageLabel().setIcon(new ImageIcon(ImageIO.read(getClass().getResource(Pics.NOT_YOUR_TURN.getPath()))));
                    //JOptionPane.showMessageDialog(new JFrame(),pack.getMessageOpponents(),"NOT YOUR TURN!! PLEASE WAIT!", JOptionPane.WARNING_MESSAGE, Pics.ERRORICON.getImageIcon());
                }
                else {
                    if(pack.getPlayer()!=null)
                        this.player = pack.getPlayer();
                    //final JDialog dialog = new JDialog();
                    //dialog.setAlwaysOnTop(true);
                    //JOptionPane.showMessageDialog(dialog, pack.getAction().getName().toUpperCase(), "YOUR TURN, "+getPlayer().getUsername(), JOptionPane.WARNING_MESSAGE, new ImageIcon(ImageIO.read(getClass().getResource(Pics.INFORMATIONICON.getPath()))));

                    this.gameFrame.getInnerMainPanel().getMessageLabel().setIcon(new ImageIcon(ImageIO.read(getClass().getResource(Pics.ASK_FOR_SELECT_TOKEN.getPath()))));

                }
                displayGui(getBattlefieldGUI(), serverResponse.getPack().getModelCopy(), null);
                break;
            }


            case ASK_FOR_PROMETHEUS_POWER:{

                Pack pack = serverResponse.getPack();

                if (!player.getTokenColor().equals(serverResponse.getTurn())){
                    displayGui(getBattlefieldGUI(), serverResponse.getPack().getModelCopy(), null);
                }
                else {
                    if(pack.getPlayer()!=null)
                        this.player = pack.getPlayer();
                    displayGui(getBattlefieldGUI(), serverResponse.getPack().getModelCopy(), null);
                    new AskPrometheusPowerWindow(this);
                }
                break;
            }


            case ASK_FOR_WHERE_TO_MOVE:{

                Pack pack = serverResponse.getPack();

                if (!player.getTokenColor().equals(serverResponse.getTurn())){
                    this.gameFrame.getInnerMainPanel().getMessageLabel().setIcon(new ImageIcon(ImageIO.read(getClass().getResource(Pics.NOT_YOUR_TURN.getPath()))));
                    displayGui(getBattlefieldGUI(), serverResponse.getPack().getModelCopy(), null);
                }
                else{
                    this.gameFrame.getInnerMainPanel().getMessageLabel().setIcon(new ImageIcon(ImageIO.read(getClass().getResource(Pics.ASK_FOR_WHERE_TO_MOVE.getPath()))));
                    //final JDialog dialog = new JDialog();
                    //dialog.setAlwaysOnTop(true);
                    //JOptionPane.showMessageDialog(dialog, pack.getAction().getName().toUpperCase(), "YOUR TURN, "+getPlayer().getUsername(), JOptionPane.WARNING_MESSAGE, new ImageIcon(ImageIO.read(getClass().getResource(Pics.INFORMATIONICON.getPath()))));
                    displayGui(getBattlefieldGUI(), serverResponse.getPack().getModelCopy(), pack.getValidMoves());
                }
                break;
            }


            case ASK_FOR_BUILD:{

                Pack pack = serverResponse.getPack();

                if (!player.getTokenColor().equals(serverResponse.getTurn())){
                    displayGui(getBattlefieldGUI(), serverResponse.getPack().getModelCopy(), null); //edit
                    this.gameFrame.getInnerMainPanel().getMessageLabel().setIcon(new ImageIcon(ImageIO.read(getClass().getResource(Pics.ANOTHERPLAYERISBUILDING.getPath()))));
                }
                else{
                    // Refresh the player so the token position is updated
                    if (pack.getPlayer() != null)
                        this.player = pack.getPlayer();

                    setValidBuilds(serverResponse.getPack().getValidBuilds());
                    this.power = false;
                    //final JDialog dialog = new JDialog();
                    //dialog.setAlwaysOnTop(true);
                    //JOptionPane.showMessageDialog(dialog, pack.getAction().getName().toUpperCase(), "YOUR TURN, "+getPlayer().getUsername(), JOptionPane.WARNING_MESSAGE, new ImageIcon(ImageIO.read(getClass().getResource(Pics.INFORMATIONICON.getPath()))));
                    this.gameFrame.getInnerMainPanel().getMessageLabel().setIcon(new ImageIcon(ImageIO.read(getClass().getResource(Pics.ASK_FOR_BUILD.getPath()))));
                    displayGui(getBattlefieldGUI(), serverResponse.getPack().getModelCopy(), pack.getValidBuilds());
                }
                break;
            }


            case GAME_OVER:{
                Pack pack = serverResponse.getPack();

                if(!serverResponse.getTurn().equals(player.getTokenColor())){
                    //final JDialog dialog = new JDialog();
                    //dialog.setAlwaysOnTop(true);
                    //JOptionPane.showMessageDialog(dialog,"" , "GAME OVER ", JOptionPane.WARNING_MESSAGE, new ImageIcon(ImageIO.read(getClass().getResource(Pics.GAMEOVERICON.getPath()))));
                    displayGui(getBattlefieldGUI(), serverResponse.getPack().getModelCopy(), null);
                    new GameOverDialog(pack,false);
                }
                else{
                    displayGui(getBattlefieldGUI(), serverResponse.getPack().getModelCopy(), null);
                    new GameOverDialog(pack,true);
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
    public void displayGui(CellButton[][] battlefieldGUI, ModelUtils modelCopy, List<Cell> validMoves) throws IOException {

        Battlefield newBattlefield = modelCopy.getBattlefield();
        List<Player> allPlayers = modelCopy.getAllPlayers();

        for (int y = 4; y > -1; y--) {

            for (int x = 0; x < 5; x++) {

                if (validMoves != null) {
                    if (validMoves.contains(newBattlefield.getCell(x, y))) {
                        battlefieldGUI[x][y].getCell().setHeight(newBattlefield.getCell(x, y).getHeight());
                        battlefieldGUI[x][y].changeImageIcon(selectIcon(player, newBattlefield.getCell(x, y),false, true));
                        battlefieldGUI[x][y].setRolloverIcon(selectRolloverIcon(newBattlefield.getCell(x,y),false));
                    }
                    else{
                        displayInnerGui(battlefieldGUI, newBattlefield, allPlayers, y, x);
                    }
                }
                else{
                    displayInnerGui(battlefieldGUI, newBattlefield, allPlayers, y, x);
                }

                //disattiva i bottoni se se non è il proprio turno
                if(!player.getTokenColor().equals(currentServerResponse.getTurn())){
                    battlefieldGUI[x][y].setEnabled(false);
                }
                else{
                    battlefieldGUI[x][y].setEnabled(true);
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
    public void displayInnerGui(CellButton[][] battlefieldGUI, Battlefield battlefield, List<Player> allPlayers, int y, int x) throws IOException {

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
    public ImageIcon selectRolloverIcon(Cell cell, boolean isDome) throws IOException {

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
     * @param cell: the cell in which i have to put the icon
     * @param iHaveToDisplayValidMoves auto explicative
     * @return ImageIcon of the cell to display
     */
    public ImageIcon selectIcon(Player player, Cell cell, boolean isDome, boolean iHaveToDisplayValidMoves) throws IOException {

        ImageIcon toReturn = new ImageIcon();

        //player not present
        if(!cell.getThereIsPlayer() ){
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
    public ImageIcon switchOnHeight(int height, Pics p1, Pics p2, Pics p3, Pics p4) throws IOException {

            switch (height) {
                case 0: {
                    return new ImageIcon(ImageIO.read(getClass().getResource(p1.getPath())));
                }
                case 1: {
                    return new ImageIcon(ImageIO.read(getClass().getResource(p2.getPath())));
                }
                case 2: {
                    return new ImageIcon(ImageIO.read(getClass().getResource(p3.getPath())));
                }
                case 3: {
                    return new ImageIcon(ImageIO.read(getClass().getResource(p4.getPath())));
                }
                default: { //error
                    return new ImageIcon(ImageIO.read(getClass().getResource(Pics.ERRORICON.getPath())));
                }
            }

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
     * Since Hestia can't make her second build on a perimeter space, we check for this condition.
     * A perimeter space is a cell that has x==0 || x==4 || y==0 || y==4.
     * @param targetCell cell we are asking for.
     * @return true if that cell is not on a perimeter zone of the battlefield.
     */
    public boolean notPerimeterCell(Cell targetCell){
        return ((targetCell.getPosX()!=4 && targetCell.getPosY()!=4) && (targetCell.getPosX()!=0 && targetCell.getPosY()!=0));
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
     * In case a player wants to use the power of Demeter or Hestia (the cases where you can build
     * twice) the buttonHandler call this method (twice) to save the two targetCell. At the end of
     * the second call the playerAction is sent.
     * @param targetCell the selected cell in which the player wants to build
     */
    public void buildGod(Pack pack, Cell targetCell) throws IOException{

        count --;
        switch (player.getMyGodCard()) {
            case DEMETER: {
                if (count == 1) {
                    SwingView.this.displayGui(getBattlefieldGUI(), pack.getModelCopy(), pack.getValidBuilds());
                    firstCell = targetCell;
                    break;
                }
                if (count == 0) {
                    //if the two selected cell are different (different coordinate)
                    if(targetCell.getPosX()!= firstCell.getPosX() || targetCell.getPosY() != firstCell.getPosY()) {
                        count = 2;

                        List<Cell> newValidBuilds = new ArrayList<>();
                        for (Cell c : pack.getValidBuilds()) {
                            if (!c.equals(targetCell))
                                newValidBuilds.add(c);
                        }
                        SwingView.this.displayGui(getBattlefieldGUI(), pack.getModelCopy(), newValidBuilds);

                        this.gameFrame.getInnerMainPanel().getMessageLabel().setIcon(new ImageIcon(ImageIO.read(getClass().getResource(Pics.SECOND_BUILD.getPath()))));

                        PlayerAction playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, player, null, null, savedToken, 0, firstCell, targetCell, true, null);
                        notifyClient(playerAction);
                        firstCell = null;
                    }
                    else{
                        count = 1;
                        JOptionPane.showMessageDialog(new JFrame(), "You can't build twice in the same cell", "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ImageIO.read(getClass().getResource(Pics.ERRORICON.getPath()))));
                    }
                    break;
                }
                break;
            }
            case HESTIA:{
                if(count == 1){
                    firstCell = targetCell;
                    break;
                }
                if(count == 0){

                    //the cell of the second buil can't be a perimeter cell.
                    if(notPerimeterCell(targetCell)) {
                        count = 2;
                        PlayerAction playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, player, null, null, savedToken, 0, firstCell, targetCell, true, null);
                        notifyClient(playerAction);
                        firstCell = null;
                    }
                    else{
                        count = 1;
                        JOptionPane.showMessageDialog(new JFrame(), "You can't select a perimeter cell", "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ImageIO.read(getClass().getResource(Pics.ERRORICON.getPath()))));
                    }
                    break;
                }
            }
        }
    }


    /**
     * Recalculates the new valid builds in case one of
     * these gods decided to use his power, and modifies
     * the next mvoe
     * @param selectedCell cell that has been selected to perform build generallty
     * @return the new vlid builds
     */
    public List<Cell> newValidBuilds(Cell selectedCell){

        List<Cell> valid = getCurrentServerResponse().getPack().getValidBuilds();
        List<Cell> targetCells = new ArrayList<>();
        Cell targetcell = null;

        switch (player.getMyGodCard()){
            case DEMETER:{
                for(Cell c: valid){
                    if(c.getPosY()==selectedCell.getPosY() && c.getPosX()==selectedCell.getPosX()){
                        targetcell=c;
                        break;
                    }
                }
                valid.remove(targetcell);
                getCurrentServerResponse().getPack().setValidBuilds(valid);
                break;
            }

            case HESTIA:{
                for(Cell c: valid){
                    if(notPerimeterCell(c)){
                        targetCells.add(c);
                    }
                }
                getCurrentServerResponse().getPack().setValidBuilds(targetCells);
                break;
            }

            case HEPHAESTUS:{
                for (Cell c: valid){
                    if(c.getPosY()==selectedCell.getPosY() && c.getPosX()==selectedCell.getPosX()){
                        if(c.getHeight()>=2){
                            targetCells.add(c);
                        }
                    }
                }
                getCurrentServerResponse().getPack().setValidBuilds(targetCells);
                break;
            }

            case ATLAS:{
                for(Cell c: valid){
                    if(c.getPosY()==selectedCell.getPosY() && c.getPosX()==selectedCell.getPosX()){
                        if(c.getHeight()==3){
                            targetCells.add(c);
                        }
                    }
                }
                getCurrentServerResponse().getPack().setValidBuilds(targetCells);
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + player.getMyGodCard());
        }

        setValidBuilds(getCurrentServerResponse().getPack().getValidBuilds());

        return getCurrentServerResponse().getPack().getValidBuilds();
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

    /**
     * Get an input from a player and return the corresponding cell of the battlefield.
     * @param posx x
     * @param posy y
     * @param battlefield the model's battlefield copy received in server response.
     * @return the cell of the battlefield with the coordinates written in inputs. Null if something goes wrong.
     */
    public Cell getCell(int posx, int posy, Battlefield battlefield){

        int selectX, selectY;
        selectX = posx;
        selectY = posy;

        try {
            return battlefield.getCell(selectX, selectY);
        } catch (Exception e) {
            return null;
        }
    }



}
