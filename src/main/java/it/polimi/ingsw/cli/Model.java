package it.polimi.ingsw.cli;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.gameAction.build.*;
import it.polimi.ingsw.gameAction.move.*;
import it.polimi.ingsw.gameAction.win.PanWin;
import it.polimi.ingsw.gameAction.win.SimpleWin;
import it.polimi.ingsw.gameAction.win.WinContext;
import it.polimi.ingsw.gameAction.move.ApolloMoves;
import it.polimi.ingsw.gameAction.move.ArtemisMoves;
import it.polimi.ingsw.gameAction.move.MoveContext;
import it.polimi.ingsw.gameAction.move.SimpleMoves;
import it.polimi.ingsw.gameAction.win.*;
import it.polimi.ingsw.utils.*;
import it.polimi.ingsw.utils.Observable;

import java.io.IOException;
import java.util.*;


/**
 * The model contains a battlefield and the info to let the game run properly.
 * It has also all the methods to change itself and to check if a move is valid or not.
 */
public class Model extends Observable<ServerResponse> implements Cloneable {

    private static boolean didAthenaMovedUp;
    private static boolean didPrometheusUsePower;
    private Battlefield battlefield;
    private TokenColor turn;
    private final List<Player> allPlayers = new ArrayList<>();
    private final List<GodCard> allGodCards = new ArrayList<>();
    private boolean firstTime = true;
    private Token prometheusToken;
    private ServerResponse lastSentServerResponse;


    public Model() {
        this.battlefield = new Battlefield();
    }

    /*    GETTER     */

    public void setBattlefield(Battlefield battlefield){
        this.battlefield = battlefield;
    }

    public List<Player> getAllPlayers() {
        return allPlayers;
    }

    public Battlefield getBattlefield() {
        return this.battlefield;
    }

    public TokenColor getTurn() {
        return turn;
    }

    public void setTurn(TokenColor turn) {
        this.turn = turn;
    }

    public void addGod(GodCard god) {
        allGodCards.add(god);
    }

    public static void athenaMovedUp(Boolean trueOrFalse) {
        didAthenaMovedUp = trueOrFalse;
    }

    // Only needed for test in AthenaMovesTest.
    public static boolean isDidAthenaMovedUp() {
        return didAthenaMovedUp;
    }

    // Only needed for test in TOKEN_SELECTED_test
    public Token getPrometheusToken(){
        return this.prometheusToken;
    }

    // Only needed for test in PROMETHEUS_ANSWER_test
    public void setPrometheusToken(Token token){
        this.prometheusToken = token;
    }

    public static boolean isDidPrometheusUsePower() {
        return didPrometheusUsePower;
    }

    public static void prometheusUsePower (Boolean trueOrFalse) {
        didPrometheusUsePower = trueOrFalse;
    }

    //Test use only
    private List<Cell> validCells;

    public List<Cell> getValidCells(){
        List<Cell> returnCell = validCells;
        validCells = null;
        return returnCell;
    }


    /**
     * It adds a player to the list of all players
     * @param player: Player that has to be added
     */
    public void addPlayer(Player player) {
        allPlayers.add(player);
    }


    /**
     * compare the current turn with a player's color.
     * if they match then it is that player's turn
     * @param player the player to ask for color
     * @return true if it is it's turn, false otherwise.
     */
    public boolean isPlayerTurn(Player player) {
        return turn == player.getTokenColor();
    }


    /**
     * @return the player-in-turn. Null if something goes wrong.
     */
    public Player getPlayerInTurn() {
        for (Player p: allPlayers){
            if (p.getTokenColor().equals(getTurn())){
                return p;
            }
        }
        return null;
    }


    /**
     * @param playerActive a player in the game.
     * @return all the opponent of that player.
     */
    public List<Player> getOpponents(Player playerActive) {

        List<Player> opponents = new ArrayList<>();
        for (Player p: allPlayers) {
            if (!p.getTokenColor().equals(playerActive.getTokenColor())) {
                opponents.add(p);
            }
        }
        return opponents;
    }


    /**
     * @return the next player who has to play. Null if something goes wrong.
     */
    public Player getNextPlayer() {

        int playerIndex;

        switch (getTurn()){

            case RED:{
                playerIndex=1;
                break;
            }
            case BLUE:{
                if (allPlayers.size()==2)
                    playerIndex=0;
                else
                    playerIndex=2;
                break;
            }
            case YELLOW:{
                playerIndex=0;
                break;
            }
            default:{
                return null;
            }
        }
        return allPlayers.get(playerIndex);
    }


    /**
     * It returns a partial copy of the model.
     * It contains the battlefield ready to be printed, the turn and the players
     *
     * @return modelCopy
     */
    public ModelUtils getCopy() {
        Battlefield battlefieldCopy = battlefield.getCopy();
        ModelUtils modelCopy = new ModelUtils(battlefieldCopy);
        modelCopy.setAllPlayers((this.getAllPlayers()));
        modelCopy.setTurn(this.getTurn());
        return modelCopy;
    }


    /**
     * It parse the player action to get the chose god from a player,
     * then that got is set to that player and removed from the allGodCards list.
     * After this if the list is empty it is started the routine for let players place their tokens
     * and the allGodCards list is re-created.
     * If not then it is asked to the next player what god he wants.
     * @param playerAction the action to parse in order to get the name of the chosen god.
     */
    public void computeGodChoices(PlayerAction playerAction) throws WrongNumberPlayerException, ImpossibleTurnException, CellOutOfBattlefieldException, ReachHeightLimitException, CellHeightException, IOException {

        if (firstTime){
            firstTime=false;

            String[] godNames = playerAction.getArgs().split(",");

            List<GodCard> godsDeck = new ArrayList<>(Arrays.asList(GodCard.values()).subList(0, 14));

            for (GodCard god: godsDeck){
                for (String name: godNames) {
                    if (god.name().toUpperCase().equals(name)){
                        allGodCards.add(god);
                    }
                }
            }

            updateTurn();

            StringBuilder text = new StringBuilder("There are the following Gods available:");
            for (GodCard god : allGodCards) {
                text.append("\n").append(god.name().toUpperCase());
                text.append("\n").append(god.toString());
            }

            Pack pack = new Pack(Action.SELECT_YOUR_GOD_CARD);
            pack.setGodCards(allGodCards);
            pack.setMessageInTurn(text.toString());
            pack.setMessageOpponents("Another player is picking his GodCard, wait please...");

            ServerResponse serverResponse = new ServerResponse(turn, pack);
            lastSentServerResponse = serverResponse;
            notify(serverResponse);

        }
        else {

            Player player = getPlayerInTurn();
            GodCard myGod = null;

            for (GodCard god : allGodCards) {
                if (playerAction.getArgs().toUpperCase().equals(god.name().toUpperCase())) {
                    myGod = god;
                    break;
                }
            }

            // Check if the choice is correct. If so continue else send back the same request
            if (player != null && myGod != null) {
                player.setMyGodCard(myGod);
                allGodCards.remove(myGod);
                updateTurn();

                /*
                 * If the last player make his choice, than the allGodCards list is re-build
                 * and the next player is asked to place his tokens on the battlefield
                 * and the other player are notified to wait
                 */
                if (allGodCards.size()==1) {

                    GodCard lastGod = allGodCards.get(0);
                    allGodCards.remove(lastGod);
                    getPlayerInTurn().setMyGodCard(lastGod);

                    // Write the text to notify all players who has which god card
                    StringBuilder text = new StringBuilder("Everyone has picked his God:");
                    for (Player p : allPlayers) {
                        allGodCards.add(p.getMyGodCard());
                        text.append("\n").append(p.getUsername().toUpperCase()).append(" ---> ").append(p.getMyGodCard().name().toUpperCase());
                        text.append("\n").append(p.getMyGodCard().toString());
                    }

                    Pack pack = new Pack(Action.PLACE_YOUR_TOKEN);
                    pack.setPlayer(getPlayerInTurn());
                    pack.setGodCards(getGodCards(allPlayers));
                    pack.setModelCopy(getCopy());
                    pack.setMessageInTurn(text.toString());
                    pack.setMessageOpponents("Another player is placing his tokens on the battlefield. Be patient please...");

                    ServerResponse serverResponse = new ServerResponse(getTurn(), pack);
                    lastSentServerResponse = serverResponse;
                    notify(serverResponse);
                }

                /*
                 * If there are other players who need to pick a god card
                 * the god card list is updated and sent to the next player,
                 * other players are notified to wait
                 */
                else {
                    StringBuilder text = new StringBuilder("There are the following Gods available:");
                    for (GodCard god : allGodCards) {
                        text.append("\n").append(god.name().toUpperCase());
                        text.append("\n").append(god.toString());
                    }

                    Pack pack = new Pack(Action.SELECT_YOUR_GOD_CARD);
                    pack.setGodCards(allGodCards);
                    pack.setMessageInTurn(text.toString());
                    pack.setMessageOpponents("Another player is picking his GodCard, wait please...");

                    ServerResponse serverResponse = new ServerResponse(turn, pack);
                    lastSentServerResponse = serverResponse;
                    notify(serverResponse);
                }
            }
            // Error in the message, no update and same request to the same person
            else {
                StringBuilder text = new StringBuilder("There are the following Gods available:");
                for (GodCard god : allGodCards) {
                    text.append("\n").append(god.name().toUpperCase());
                    text.append("\n").append(god.toString());
                }

                Pack pack = new Pack(Action.SELECT_YOUR_GOD_CARD);
                pack.setGodCards(allGodCards);
                pack.setMessageInTurn(text.toString());
                pack.setMessageOpponents("Another player is picking his GodCard, wait please...");

                ServerResponse serverResponse = new ServerResponse(turn, pack);
                lastSentServerResponse = serverResponse;
                notify(serverResponse);
            }
        }
    }


    /**
     * Check if the message is correct. If not is requested the same thing to the same player.
     * If it is, then if the same player has another token to place it is requested the same thing to that player,
     * else it check if all the player have placed their tokens. If so then the game can start,
     * else it requested the same thing to the next player.
     * @param playerAction package message with all the info.
     */
    public void placeToken(PlayerAction playerAction) throws WrongNumberPlayerException, ImpossibleTurnException, CellOutOfBattlefieldException, ReachHeightLimitException, CellHeightException, IOException {

        Cell targetCell = playerAction.getFirstCell();
        ServerResponse serverResponse;

        // If the cell is correct
        if (targetCell!=null && battlefield.getCell(targetCell)!=null){

            // If the first token has a position and the second not, it is assigned
            if (getPlayerInTurn().getToken1().getTokenPosition()!=null && getPlayerInTurn().getToken2().getTokenPosition()==null) {
                battlefield.getCell(targetCell).setOccupied();
                getPlayerInTurn().getToken2().setTokenPosition(battlefield.getCell(targetCell));
            }

            // If the first token has no position, it is assigned
            if (getPlayerInTurn().getToken1().getTokenPosition()==null) {
                battlefield.getCell(targetCell).setOccupied();
                getPlayerInTurn().getToken1().setTokenPosition(battlefield.getCell(targetCell));
            }

            if (getPlayerInTurn().getToken1().getTokenPosition()!=null && getPlayerInTurn().getToken2().getTokenPosition()!=null)
                updateTurn();

            boolean gameCanStart = false;

            for (Player p: allPlayers){

                // If someone need to place his token, he has to do it before the game start
                if (p.getToken1().getTokenPosition()==null || p.getToken2().getTokenPosition()==null){
                    gameCanStart = false;
                    break;
                }
                // If all the tokens of all players have a position the game can start
                else
                    gameCanStart=true;
            }

            Pack pack;
            if (gameCanStart){

                pack = new Pack(Action.ASK_FOR_SELECT_TOKEN);
                pack.setModelCopy(getCopy());
                pack.setPlayer(getPlayerInTurn());
                pack.setGodCards(getGodCards(allPlayers));
                pack.setMessageOpponents("Another player is choosing which token to move, wait please...");

            } else {

                pack = new Pack(Action.PLACE_YOUR_TOKEN);
                pack.setPlayer(getPlayerInTurn());
                pack.setModelCopy(getCopy());
                pack.setGodCards(getGodCards(allPlayers));
                pack.setMessageOpponents("Another player is placing his tokens onto the battlefield, wait please...");

            }
            serverResponse = new ServerResponse(getTurn(), pack);
        }
        // If the cell is not correct, nothing change and the player receive the same request
        else {
            Pack pack = new Pack(Action.PLACE_YOUR_TOKEN);
            pack.setPlayer(getPlayerInTurn());
            pack.setModelCopy(getCopy());
            pack.setGodCards(getGodCards(allPlayers));
            pack.setMessageOpponents("Another player is placing his tokens onto the battlefield, wait please...");

            serverResponse = new ServerResponse(getTurn(), pack);
        }
        lastSentServerResponse = serverResponse;
        notify(serverResponse);
    }


    /**
     * It calculates the valid moves that a player can make.
     * A token CAN move one Cell around himself.
     * A token can NOT move out of the battlefield,
     * where there is another token (himself too),
     * on a build height more than 1 of it's own,
     * where there is a dome.
     *
     * This take all the values needed for call the properly method based on the god card and
     * and call the methods passing it all the parameters.
     *
     * @param playerAction the message from the observer that contain all the information.
     */
    public void validMoves(PlayerAction playerAction) throws CellOutOfBattlefieldException, WrongNumberPlayerException, ImpossibleTurnException, CellHeightException, IOException, ReachHeightLimitException {

        Token selectedToken, otherToken = null;
        int selectedTokenId;

        if (playerAction.getAction().equals(Action.PROMETHEUS_ANSWER)){

            selectedToken = prometheusToken;

            try {
                if (getPlayerInTurn().getToken1().getId() == prometheusToken.getId()) {
                    otherToken = getPlayerInTurn().getToken2();
                }
                if (getPlayerInTurn().getToken2().getId() == prometheusToken.getId()) {
                    otherToken = getPlayerInTurn().getToken1();
                }
            } catch (NullPointerException e){
                otherToken = null;
            }
        }
        else{

            selectedTokenId = playerAction.getTokenMain();

            try {
                if (getPlayerInTurn().getToken1().getId() == selectedTokenId) {
                    otherToken = getPlayerInTurn().getToken2();
                }
                if (getPlayerInTurn().getToken2().getId() == selectedTokenId) {
                    otherToken = getPlayerInTurn().getToken1();
                }
            } catch (NullPointerException e){
                otherToken = null;
            }

            selectedToken = parseToken(selectedTokenId);
        }

        List<Player> opponents = getOpponents(getPlayerInTurn());
        List<Token> enemyTokens = getTokens(opponents);

        GodCard myGodCard = getPlayerInTurn().getMyGodCard();
        List<GodCard> enemyGodCards = getGodCards(opponents);

        List<Cell> validMoves;
        validMoves = computeValidMoves(selectedToken, otherToken, enemyTokens, myGodCard, enemyGodCards, getBattlefield());

        ServerResponse serverResponse;

        if (validMoves.isEmpty()) {
            serverResponse = checkLoseForMove(otherToken, enemyTokens, myGodCard, enemyGodCards);
        } else {

            Pack pack = new Pack(Action.ASK_FOR_WHERE_TO_MOVE);
            pack.setModelCopy(getCopy());
            pack.setValidMoves(validMoves);
            pack.setGodCards(getGodCards(allPlayers));
            pack.setMessageOpponents(getPlayerInTurn().getUsername()+" is selecting where to move his token...");

            serverResponse = new ServerResponse(getTurn(), pack);
        }

        validCells = validMoves;
        lastSentServerResponse = serverResponse;
        notify(serverResponse);
    }


    /**
     * When the controller receive a TOKEN_SELECTED if the owner has Prometheus,
     * I ask him if he want to use his power or not.
     * The token he want to move is saved for his next answer.
     * @param playerAction the packet with all the info
     */
    public void askForPrometheus(PlayerAction playerAction) throws ImpossibleTurnException, IOException, CellHeightException, WrongNumberPlayerException, ReachHeightLimitException, CellOutOfBattlefieldException {

        // Set default value
        didPrometheusUsePower = false;

        List<Player> opponents = getOpponents(getPlayerInTurn());
        List<Token> enemyTokens = getTokens(opponents);

        GodCard myGodCard = getPlayerInTurn().getMyGodCard();
        List<GodCard> enemyGodCards = getGodCards(opponents);

        Token selectedToken, otherToken = null;
        int selectedTokenId;

        selectedTokenId = playerAction.getTokenMain();
        selectedToken = parseToken(selectedTokenId);

        try {
            if (getPlayerInTurn().getToken1().getId() == selectedTokenId) {
                otherToken = getPlayerInTurn().getToken2();
            }
            if (getPlayerInTurn().getToken2().getId() == selectedTokenId) {
                otherToken = getPlayerInTurn().getToken1();
            }
        } catch (NullPointerException e){
            otherToken = null;
        }

        List<Cell> validMoves;
        validMoves = computeValidMoves(selectedToken, otherToken, enemyTokens, myGodCard, enemyGodCards, getBattlefield());

        ServerResponse serverResponse;

        if (validMoves == null) {
            serverResponse = checkLoseForMove(otherToken, enemyTokens, myGodCard, enemyGodCards);
        } else {

            Pack pack = new Pack(Action.ASK_FOR_PROMETHEUS_POWER);
            pack.setPlayer(getPlayerInTurn());
            pack.setModelCopy(getCopy());
            pack.setGodCards(getGodCards(allPlayers));
            pack.setMessageOpponents("Another player is choosing if use Prometheus power or not...");

            prometheusToken = parseToken(playerAction.getTokenMain());

            serverResponse = new ServerResponse(getTurn(), pack);
        }

        lastSentServerResponse = serverResponse;
        notify(serverResponse);
    }


    public void prometheusFirstBuild() throws CellOutOfBattlefieldException, ReachHeightLimitException, CellHeightException, IOException, ImpossibleTurnException, WrongNumberPlayerException {

        didPrometheusUsePower = true;

        Pack pack = new Pack(Action.ASK_FOR_BUILD);
        pack.setPlayer(getPlayerInTurn());
        pack.setGodCards(getGodCards(allPlayers));
        pack.setMessageOpponents(getPlayerInTurn().getUsername()+" has used his power!\nHe is placing his first build!");
        pack.setModelCopy(getCopy());

        Token token2 = getOtherToken(prometheusToken.getId());

        List<Token> enemyTokens = getTokens(getOpponents(getPlayerInTurn()));
        GodCard myGodCard = getPlayerInTurn().getMyGodCard();
        List<GodCard> enemyGodCards = getGodCards(getOpponents(getPlayerInTurn()));

        List<Cell> validBuilds = validBuilds(prometheusToken, token2, enemyTokens, myGodCard, enemyGodCards, getBattlefield());

        pack.setValidBuilds(validBuilds);

        ServerResponse serverResponse = new ServerResponse(getTurn(), pack);

        validCells = validBuilds;
        lastSentServerResponse = serverResponse;
        notify(serverResponse);
    }


    /**
     * @param players a list of players.
     * @return all the tokens of those players.
     */
    public List<Token> getTokens(List<Player> players) {
        List<Token> tokens = new ArrayList<>();
        for (Player p: players) {
            try {
                tokens.add(p.getToken1());
                tokens.add(p.getToken2());
            } catch (NullPointerException ignored){}
        }
        return tokens;
    }


    /**
     * @param players a list of players.
     * @return a list of all those players god cards.
     */
    public List<GodCard> getGodCards(List<Player> players) {
        List<GodCard> godCards = new ArrayList<>();
        for (Player p: players) {
            try {
                godCards.add(p.getMyGodCard());
            } catch (NullPointerException ignored){}
        }
        return godCards;
    }


    /**
     * @param tokenId a unique identifier of a token.
     * @return the token who is associated with that token id. Null if there is no token with that id
     */
    public Token parseToken(int tokenId) {
        for (Player player: allPlayers) {
            if (tokenId == player.getToken1().getId())
                return player.getToken1();
            else if(tokenId == player.getToken2().getId())
                return player.getToken2();
        }
        return null;
    }


    /**
     * Here there is the ad-hoc call for the computeValidMoves method.
     * It is based on the player-in-turn god card.
     * If that god card do not modify the move than it calls the default move.
     * More JavaDOC inside the ad-hoc method.
     */
    public List<Cell> computeValidMoves(Token selectedToken, Token otherToken, List<Token> enemyTokens, GodCard myGodCard, List<GodCard> enemyGodCards, Battlefield battlefield) throws CellOutOfBattlefieldException {

        List<Cell> validMoves;

        switch (myGodCard) {
            case APOLLO: {
                MoveContext thisMove = new MoveContext(( new ApolloMoves()));
                validMoves = thisMove.executeValidMoves(selectedToken, otherToken, enemyTokens, myGodCard, enemyGodCards, battlefield, null);
                break;
            }
            case ARTEMIS: {
                MoveContext thisMove = new MoveContext(( new ArtemisMoves()));
                validMoves = thisMove.executeValidMoves(selectedToken, otherToken, enemyTokens, myGodCard, enemyGodCards, battlefield, null);
                break;
            }
            case MINOTAUR: {
                MoveContext thisMove = new MoveContext(( new MinotaurMoves()));
                validMoves = thisMove.executeValidMoves(selectedToken, otherToken, enemyTokens, myGodCard, enemyGodCards, battlefield, null);
                break;
            }
            case PROMETHEUS:{
                MoveContext thisMove = new MoveContext(( new PrometheusMove()));
                validMoves = thisMove.executeValidMoves(selectedToken, otherToken, enemyTokens, myGodCard, enemyGodCards, battlefield, null);
                break;
            }
            default: {
                MoveContext thisMove = new MoveContext(new SimpleMoves());
                validMoves = thisMove.executeValidMoves(selectedToken, otherToken, enemyTokens, myGodCard, enemyGodCards, battlefield, null);
            }
        }

        if (enemyGodCards.contains(GodCard.ATHENA) && didAthenaMovedUp) {
            MoveContext thisMove = new MoveContext(new AthenaMoves());
            validMoves = thisMove.executeValidMoves(selectedToken, null, null, null, null, null, validMoves);
        }

        return validMoves;
    }


    /**
     * This is called by the controller just to check that the real player has given an appropriate input in the
     * move phase.
     * It works exactly like the computeValidMoves but there is no need to check that there are no valid moves.
     */
    public List<Cell> askValidMoves (PlayerAction playerAction) throws CellOutOfBattlefieldException {

        Player playerActive = playerAction.getPlayer();

        List<Player> opponents = getOpponents(playerActive);
        List<Token> enemyTokens = getTokens(opponents);

        int selectedTokenId = playerAction.getTokenMain();
        int otherTokenId = playerAction.getTokenOther();

        Token selectedToken = parseToken(selectedTokenId);
        Token otherToken = parseToken(otherTokenId);

        GodCard myGodCard = playerActive.getMyGodCard();
        List<GodCard> enemyGodCards = getGodCards(opponents);

        return computeValidMoves(selectedToken, otherToken, enemyTokens, myGodCard, enemyGodCards, getBattlefield());
    }


    /**
     * This method is called when i check the valid moves of a player.
     * If the return of that method is null I check if that player has lost the game or
     * if he can move the other token.
     * If he lost it check how many player there are.
     * If 2 the other one wins the game,
     * if 3 this player is removed from the game.
     * @return the correct ServerResponse to let the game routine run properly.
     * It could be a TOKEN_NOT_MOVABLE, GAME_OVER or PLAYER_LOST.
     * @throws CellOutOfBattlefieldException if something goes wrong.
     */
    public ServerResponse checkLoseForMove(Token otherToken, List<Token> enemyTokens, GodCard myGodCard, List<GodCard> enemyGodCards) throws CellOutOfBattlefieldException, WrongNumberPlayerException, ImpossibleTurnException {

        // If there is no 2nd token
        if (otherToken == null) {
            // If there are 2 players
            if (allPlayers.size()==2) {
                return gameOver(getNextPlayer().getUsername());
            }
            // If there are 3 players
            if (allPlayers.size() == 3) {
                return playerLost(getPlayerInTurn());
            }
        }
        // If there is 2nd token
        else {
            List<Cell> validMoves2;
            validMoves2 = computeValidMoves(otherToken, null, enemyTokens, myGodCard, enemyGodCards, battlefield);

            // If i can not move it
            if (validMoves2.isEmpty()) {
                if (allPlayers.size() == 2) {
                    // If there are 2 players
                    return gameOver(getNextPlayer().getUsername());
                }
            // If there are 3 players
            if (allPlayers.size() == 3) {
                    return playerLost(getPlayerInTurn());
                }
            }
        }

        Pack pack = new Pack(Action.TOKEN_NOT_MOVABLE);

        pack.setModelCopy(getCopy());
        pack.setMessageOpponents(getPlayerInTurn().getUsername()+" can not move the token he selected...");
        pack.setGodCards(getGodCards(allPlayers));
        pack.setPlayer(getPlayerInTurn());

        return new ServerResponse(getTurn(), pack);
    }


    /**
     * Here is where the move is parsed for ad-hoc method.
     * The check for the legal move is made by the controller.
     * After a player has moved his token, that token id checked for the win condition.
     * If true a message is sent to the client,
     * if not the game continue normally.
     * @param playerAction the message from the observer that contain all the information.
     */
    public void performMove (PlayerAction playerAction) throws CellOutOfBattlefieldException, ReachHeightLimitException, CellHeightException, IOException, ImpossibleTurnException, WrongNumberPlayerException {

        Player playerActive = getPlayerInTurn();
        GodCard myGodCard = playerActive.getMyGodCard();
        Cell targetCell = battlefield.getCell(playerAction.getFirstCell());

        int selectedTokenId = playerAction.getTokenMain();
        Token selectedToken = parseToken(selectedTokenId);

        Token otherToken = getOtherToken(selectedTokenId);

        List<Player> opponents = getOpponents(playerActive);
        List<Token> enemyTokens = getTokens(opponents);

        List<GodCard> enemyGodCards = getGodCards(opponents);

        switch (myGodCard) {
            case APOLLO:{
                MoveContext thisMove = new MoveContext(new ApolloMoves());
                thisMove.executeMove(selectedToken, otherToken, enemyTokens, targetCell, enemyGodCards, battlefield);
                break;
            }
            case ATHENA:{
                MoveContext thisMove = new MoveContext(new AthenaMoves());
                thisMove.executeMove(selectedToken, otherToken, enemyTokens, targetCell, enemyGodCards, battlefield);
                break;
            }
            case MINOTAUR:{
                MoveContext thisMove = new MoveContext(new MinotaurMoves());
                thisMove.executeMove(selectedToken, otherToken, enemyTokens, targetCell, enemyGodCards, battlefield);
                break;
            }
            default:{
                MoveContext thisMove = new MoveContext(new SimpleMoves());
                thisMove.executeMove(selectedToken, otherToken, enemyTokens, targetCell, enemyGodCards, battlefield);
            }
        }

        // Check for a win move
        if (checkWin(selectedToken, myGodCard, enemyGodCards)) {
            String winner = playerAction.getPlayer().getUsername();
            notify (gameOver(winner));
            return;
        }

        // If nobody has won then i aks for where to build
        List<Cell> validBuilds = validBuilds(selectedToken, otherToken, enemyTokens, myGodCard, enemyGodCards, battlefield);

        if (validBuilds.isEmpty()) {
            if (allPlayers.size() == 3) {
                playerLost(playerAction.getPlayer());
            }
            else if(allPlayers.size() == 2) {
                updateTurn();
                String winner = getPlayerInTurn().getUsername();
                notify(gameOver(winner));
            }
        }
        else {
            Pack pack = new Pack(Action.ASK_FOR_BUILD);
            pack.setPlayer(getPlayerInTurn());
            pack.setModelCopy(getCopy());
            pack.setGodCards(getGodCards(allPlayers));
            pack.setValidBuilds(validBuilds);
            pack.setMessageOpponents(getPlayerInTurn().getUsername()+" is choosing where to build...");

            ServerResponse serverResponse = new ServerResponse(getTurn(), pack);
            validCells = validBuilds;
            lastSentServerResponse = serverResponse;
            notify(serverResponse);
        }
    }


    /**
     * @param firstTokenID unique integer of a token
     * @return the other token of the player who own the token with the ID in the parameter
     */
    public Token getOtherToken(int firstTokenID){

        Player red = null;
        Player blue = null;
        Player yellow = null;

        for (Player p: allPlayers){
            if (p.getTokenColor().equals(TokenColor.RED)){
                red=p;
            }
            if (p.getTokenColor().equals(TokenColor.BLUE)){
                blue=p;
            }
            if (p.getTokenColor().equals(TokenColor.YELLOW)){
                yellow=p;
            }
        }

        switch (firstTokenID){

            case 1:{
                if (red!=null)
                    return red.getToken2();
            }
            case 11:{
                if (red!=null)
                    return red.getToken1();
            }
            case 2:{
                if (blue!=null)
                    return blue.getToken2();
            }
            case 22:{
                if (blue!=null)
                    return blue.getToken1();
            }
            case 3:{
                if (yellow!=null)
                    return yellow.getToken2();
            }
            case 33:{
                if (yellow!=null)
                    return yellow.getToken1();
            }
            default: return null;
        }
    }


    /**
     * It parses the input for get the correct method based on the god card.
     * A player CAN build around the token he moved.
     * A token can NOT build out of the battlefield,
     * where there is a token (himself too),
     * where there is a dome.
     * @return a List of Cell in which the token just moved can build.
     */
    public List<Cell> validBuilds (Token selectedToken, Token otherToken, List<Token> enemyTokens, GodCard myGodCard, List<GodCard> enemyGodCards, Battlefield battlefield) throws CellOutOfBattlefieldException {

        List<Cell> validBuilds;

        switch (myGodCard) {
            case DEMETER:
            case HESTIA: {
                BuildContext thisBuild = new BuildContext(( new SimpleBuild()));
                validBuilds = thisBuild.executeValidBuilds(selectedToken, otherToken, enemyTokens, enemyGodCards, battlefield, null);
                break;
            }
            case HEPHAESTUS:{
                BuildContext thisBuild = new BuildContext(( new HephaestusBuild()));
                validBuilds = thisBuild.executeValidBuilds(selectedToken, otherToken, enemyTokens, enemyGodCards, battlefield, null);
                break;
            }
            case ZEUS:{
                BuildContext thisBuild = new BuildContext(new ZeusBuild());
                validBuilds = thisBuild.executeValidBuilds(selectedToken, otherToken, enemyTokens, enemyGodCards, battlefield, null);
                break;
            }
            default:{
                BuildContext thisBuild = new BuildContext(new SimpleBuild());
                validBuilds = thisBuild.executeValidBuilds(selectedToken, otherToken, enemyTokens, enemyGodCards, battlefield, null);
            }
        }

        if (enemyGodCards.contains(GodCard.LIMUS)){
            BuildContext thisBuild = new BuildContext(new LimusBuild());
            List<Cell> toRemove = thisBuild.executeValidBuilds(selectedToken, otherToken, enemyTokens, enemyGodCards, battlefield, allPlayers);

            List<Cell> validBuildsToReturn = new ArrayList<>(validBuilds);

            for(Cell returnCell: validBuilds){
                for(Cell removeCell: toRemove){
                    if (returnCell.equals(removeCell)){
                        validBuildsToReturn.remove(returnCell);
                    }
                }
            }
            return validBuildsToReturn;
        }
        return validBuilds;
    }


    /**
     * This is called by the controller just to check if the real player has given a correct value.
     * It works exactly like the validBuilds method.
     * @param playerAction the message from the observer that contain all the information.
     * @return a list of Cell in which a player can build.
     */
    public List<Cell> askForValidBuilds (PlayerAction playerAction) throws CellOutOfBattlefieldException {

        Player playerActive = playerAction.getPlayer();

        List<Player> opponents = getOpponents(playerActive);
        List<Token> enemyTokens = getTokens(opponents);

        int selectedTokenId = playerAction.getTokenMain();
        int otherTokenId = playerAction.getTokenOther();

        Token selectedToken = parseToken(selectedTokenId);
        Token otherToken = parseToken(otherTokenId);

        GodCard myGodCard = playerActive.getMyGodCard();
        List<GodCard> enemyGodCards = getGodCards(opponents);

        return validBuilds(selectedToken, otherToken, enemyTokens, myGodCard, enemyGodCards, getBattlefield());
    }


    /**
     * It call the incrementHeight on the cell that a player has chosen to build.
     * After a build has been made, the turn is updated.
     * @param playerAction the message from the observer that contain all the information.
     */
    public void performBuild (PlayerAction playerAction) throws WrongNumberPlayerException, ImpossibleTurnException, CellHeightException, ReachHeightLimitException, CellOutOfBattlefieldException, IOException {

        Player playerActive = getPlayerInTurn();
        GodCard myGodCard = playerActive.getMyGodCard();
        Cell targetCell = battlefield.getCell(playerAction.getFirstCell());
        Cell secondCell;
        try{
            secondCell = battlefield.getCell(playerAction.getSecondCell());
        } catch (Exception e){
            secondCell = null;
        }

        boolean wantToUsePower = playerAction.getDoWantUsePower();

        switch (myGodCard) {
            case ATLAS: {
                BuildContext thisBuild;
                if (wantToUsePower){
                    thisBuild = new BuildContext(new AtlasBuild());
                }
                else{
                    thisBuild = new BuildContext(new SimpleBuild());
                }
                thisBuild.executePerformBuild(targetCell, null, getBattlefield());
                break;
            }
            case DEMETER:{
                BuildContext thisBuild;
                thisBuild = new BuildContext(new DemeterBuild());
                thisBuild.executePerformBuild(targetCell, secondCell, getBattlefield());
                break;
            }
            case HEPHAESTUS:{
                BuildContext thisBuild;
                if(wantToUsePower){
                    thisBuild = new BuildContext(new HephaestusBuild());
                }
                else{
                    thisBuild = new BuildContext(new SimpleBuild());
                }
                thisBuild.executePerformBuild(targetCell, null, getBattlefield());
                break;
            }
            case HESTIA:{
                BuildContext thisBuild;
                thisBuild = new BuildContext(new HestiaBuild());
                thisBuild.executePerformBuild(targetCell,secondCell,getBattlefield());
                break;
            }
            default:{
                BuildContext thisBuild = new BuildContext(new SimpleBuild());
                thisBuild.executePerformBuild(targetCell, null, battlefield);
            }
        }

        // After the build need to check if chronus win
        List<GodCard> allGodCards = getGodCards(allPlayers);

        if (allGodCards.contains(GodCard.CHRONUS)){
            WinContext thisWin = new WinContext(new ChronusWin());
            if (thisWin.executeCheckWin(null, battlefield)) {
                String winner = null;
                for (Player p: allPlayers){
                    if (p.getMyGodCard().equals(GodCard.CHRONUS)){
                        winner = p.getUsername();
                    }
                }
                assert winner != null;
                notify(gameOver(winner));
                return;
            }
        }

        ServerResponse serverResponse;

        // If it was Prometheus first build need to make him move and build again
        if (getPlayerInTurn().getMyGodCard().equals(GodCard.PROMETHEUS) && didPrometheusUsePower){
            validMoves(playerAction);
        }
        else{
            updateTurn();

            String inTurnMessage = String.format("%s is now your turn!",getPlayerInTurn().getUsername().toUpperCase());
            String opponentMessage = String.format("Is now %s turn!",getPlayerInTurn().getUsername().toUpperCase());
            Pack pack = new Pack(Action.ASK_FOR_SELECT_TOKEN);
            pack.setGodCards(getGodCards(allPlayers));
            pack.setMessageOpponents(opponentMessage);
            pack.setMessageInTurn(inTurnMessage);
            pack.setModelCopy(getCopy());
            pack.setPlayer(getPlayerInTurn());

            serverResponse = new ServerResponse(getTurn(), pack);
            lastSentServerResponse = serverResponse;
            notify(serverResponse);
        }
    }


    /**
     * A player win the game if he goes up from level 2 to level 3
     * @param movedToken the token to check if he verify the win condition
     * @return true if the token's owner win, false else.
     */
    public boolean checkWin (Token movedToken, GodCard myGodCard, List<GodCard> enemyGodCards) throws CellOutOfBattlefieldException {

        boolean didIWin;

        switch (myGodCard) {
            case PAN: {
                WinContext thisWin = new WinContext(new PanWin());
                didIWin = thisWin.executeCheckWin(movedToken, null);
                break;
            }
            default: {
                WinContext thisWin = new WinContext(new SimpleWin());
                didIWin = thisWin.executeCheckWin(movedToken, null);
            }
        }
        if (enemyGodCards.contains(GodCard.HERA)){
            if (didIWin){
                WinContext thisWin = new WinContext(new HeraWin());
                didIWin = thisWin.executeCheckWin(movedToken, null);
            }
        }
        return didIWin;
    }


    /**
     * This method creates a GAME OVER message because someone has won the game.
     * @param winner the player who win.
     * @return the correct ServerResponse.
     */
    public ServerResponse gameOver (String winner) {

        Pack pack = new Pack(Action.GAME_OVER);
        String message = winner.toUpperCase()+" won the game!";
        pack.setMessageInTurn(message);
        pack.setModelCopy(getCopy());

        return new ServerResponse (getTurn(), pack);
    }


    /**
     * This method removes a player from the game because there are 3 player and one lost.
     * @param looser is the player who lost.
     * @return the correct ServerResponse.
     */
    public ServerResponse playerLost (Player looser) throws WrongNumberPlayerException, ImpossibleTurnException {

        Pack pack = new Pack(Action.PLAYER_LOST);
        String message = looser.getUsername().toUpperCase()+" lost the game!";
        pack.setMessageInTurn(message);
        pack.setMessageOpponents(message);
        pack.setModelCopy(getCopy());

        removeFromTheGame(looser);
        pack.setPlayer(getPlayerInTurn());

        return new ServerResponse (getTurn(), pack);
    }


    /**
     * It removes a player from the game and set free the cells where he has his tokens.
     * @param player the player who has to be removed.
     */
    public void removeFromTheGame (Player player) throws WrongNumberPlayerException, ImpossibleTurnException {

        player.getToken1().getTokenPosition().setFree();
        player.getToken2().getTokenPosition().setFree();
        allPlayers.remove(player);

        updateTurn();
    }


    /**
     * It updates the current turn:
     * if it is red than it update to blue,
     * if it is blue it check for number of players. If 2 than it is red again,
     * if it is 3 it change to yellow.
     * if it is yellow it update to red.
     * @throws ImpossibleTurnException if the turn is not red, blue or yellow.
     * @throws WrongNumberPlayerException if the numbers of player is not 2 nor 3;
     */
    public void updateTurn() throws ImpossibleTurnException, WrongNumberPlayerException {

        switch (turn) {

            case RED: {
                this.turn = TokenColor.BLUE;
                break;
            }

            case BLUE: {
                if (allPlayers.size() == 2) {
                    this.turn = TokenColor.RED;
                } else if (allPlayers.size() == 3) {
                    this.turn = TokenColor.YELLOW;
                }
                else {
                    throw new WrongNumberPlayerException(
                            String.format("There are %d players and it is not allowed!", allPlayers.size()));
                }
                break;
            }

            case YELLOW: {
                this.turn = TokenColor.RED;
                break;
            }
            default: {
                throw new ImpossibleTurnException(
                        String.format("The color %s is not a valid turn", turn));
            }
        }
    }


    /**
     * It is called by the controller to notify the player-in-turn that he has insert a wrong or
     * a non expected input.
     */
    public void notifyWrongInput() throws ImpossibleTurnException, IOException, CellHeightException, WrongNumberPlayerException, ReachHeightLimitException, CellOutOfBattlefieldException {

        System.out.println("WrongInput caught");

        Pack oldPack = lastSentServerResponse.getPack();
        Pack pack = new Pack(oldPack.getAction());

        pack.setPlayer(oldPack.getPlayer());
        pack.setNumberOfPlayers(oldPack.getNumberOfPlayers());
        pack.setModelCopy(getCopy());
        pack.setValidBuilds(oldPack.getValidBuilds());
        pack.setValidMoves(oldPack.getValidBuilds());
        pack.setGodCards(oldPack.getGodCards());

        String oldMessageOpponents = oldPack.getMessageOpponents();
        String oldMessageInTurn = oldPack.getMessageInTurn();

        if (oldMessageOpponents != null){
            if (oldMessageInTurn.contains("An error in your input was caught")){
                oldMessageInTurn = "An error in your input was caught.\n"+oldMessageInTurn;
            }
        }

        if (oldMessageOpponents != null){
            if (oldMessageInTurn.contains("An error in your opponent's input was caught")){
                oldMessageInTurn = "An error in your opponent's input was caught.\n"+oldMessageInTurn;
            }
        }

        pack.setMessageInTurn(oldMessageInTurn);
        pack.setMessageOpponents(oldMessageOpponents);

        ServerResponse serverResponse = new ServerResponse(turn, pack);
        lastSentServerResponse = serverResponse;
        notify(serverResponse);
    }

    /**
     * Check if the two cells have different coordinates.
     */
    public boolean differentCell(Cell firstCell, Cell secondCell){
        if (firstCell==null || secondCell == null)
            return true;
        return !(firstCell.getPosX()==secondCell.getPosX() && firstCell.getPosY()==secondCell.getPosY());
    }

    /**
     * Check if the targetCell is a perimeter cell.
     */
    public boolean notPerimeterCell(Cell targetCell){
        if(targetCell == null)
            return false;
        return ((targetCell.getPosX()!=4 && targetCell.getPosY()!=4) && (targetCell.getPosX()!=0 && targetCell.getPosY()!=0));
    }
}
