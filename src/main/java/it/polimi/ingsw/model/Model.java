package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.PlayerAction;
import it.polimi.ingsw.utils.ServerResponse;

import java.util.*;


/**
 * The model contains a battlefield and the info to let the game run properly.
 * It has also all the methods to change itself and to check if a move is valid or not.
 */
public class Model extends Observable<ServerResponse> implements Cloneable {

    private Battlefield battlefield;
    private TokenColor turn;
    private int numberOfPlayer;

    public Model(Battlefield battlefield) {
        this.battlefield = battlefield;
    }

    /*   GETTER   */
    public Battlefield getBattlefieldCopy() {
        return battlefield.getCopy();
    }

    public Battlefield getBattlefield() {
        return this.battlefield;
    }


    /**
     * compare the current turn with a player's color.
     * if they match then it is that player's turn
     *
     * @param player the player to ask for color
     * @return true if it is it's turn, false otherwise.
     */
    public boolean isPlayerTurn(Player player) {////////////dubbia
        return turn == player.getTokenColor();
    }


    /**
     * It returns a partial copy of the model.
     * It contains the battlefield ready to be printed
     *
     * @return modelCopy
     */
    public Model getCopy() {
        Battlefield battlefieldCopy = new Battlefield();
        battlefieldCopy = battlefield.getCopy();
        Model modelCopy = new Model(battlefieldCopy);
        return modelCopy;
    }


    /**
     * It calculates the valid moves that a player can make.
     * A token CAN move one Cell around himself.
     * A token can NOT move out of the battlefield,
     * where there is another token (himself too),
     * on a build height more than 1 of it's own,
     * where there is a dome.
     *
     * @param playerAction the message from the observer that contain all the information.
     * @return a list of Cell in which that token can move
     * @throws CellOutOfBattlefieldException if something goes wrong
     */
    public void validMoves(PlayerAction playerAction) throws CellOutOfBattlefieldException, WrongNumberPlayerException, ImpossibleTurnException {

        Player player = playerAction.getPlayer();

        Player oppo1 = playerAction.getOppo1();

        List<Token> allTokens = new ArrayList<Token>();
        List<Player> allPlayers = new ArrayList<Player>();

        allPlayers.add(player);
        allPlayers.add(oppo1);

        if (numberOfPlayer == 3) {
            Player oppo2 = playerAction.getOppo2();
            allPlayers.add(oppo2);
        }

        for (Player p : allPlayers) {
            allTokens.add(p.getToken1());
            allTokens.add(p.getToken2());
        }

        List<Cell> validMoves = new ArrayList<>();

        validMoves = computeValidMoves(playerAction.getTokenMain(), allTokens);
        ServerResponse serverResponse;

        if (validMoves == null) {
            serverResponse = checkLose(playerAction, allTokens);
        } else {
            serverResponse = new ServerResponse(Action.ASK_FOR_MOVE, this.getCopy(), validMoves, null, null);
        }
        notify(serverResponse);
    }

    /**
     * This method is called when i check the valid moves of a player.
     * If the return of that method is null I check if that player has lost the game or
     * if he can move the other token.
     * If he lost it check how many player there are.
     * If 2 the other one wins the game,
     * if 3 this player is removed from the game.
     * @param playerAction the message from the observer that contain all the information.
     * @param allTokens all the token son the battlefield.
     * @return the correct ServerResponse to let the game routine run properly.
     * It could be a TOKEN_NOT_MOVABLE, GAME_OVER or PLAYER_LOST.
     * @throws CellOutOfBattlefieldException if something goes wrong.
     */
    public ServerResponse checkLose(PlayerAction playerAction, List<Token> allTokens) throws CellOutOfBattlefieldException, WrongNumberPlayerException, ImpossibleTurnException {

        if (playerAction.getTokenOther() == null) {                              // se il secondo token non esiste
            if (numberOfPlayer == 2) {                                             // se ci sono 2 player
                return gameOver(playerAction.getOppo1().getUsername());
            }
            if (numberOfPlayer == 3) {                                             // se ci sono 3 player
                return playerLost(playerAction.getPlayer());
            }
        }
        else {                                                                  // se il secondo token esiste
            List<Cell> validMoves2 = new ArrayList<>();
            validMoves2 = computeValidMoves(playerAction.getTokenOther(), allTokens);

            if (validMoves2 == null) {                                         // se non lo posso muovere
                if (numberOfPlayer == 2) {                                     // se ci sono 2 player
                    return gameOver(playerAction.getOppo1().getUsername());
                }
            if (numberOfPlayer == 3) {                                         // se ci sono 3 player
                    return playerLost(playerAction.getPlayer());
                }
            }
        }
        return new ServerResponse(Action.TOKEN_NOT_MOVABLE, this.getCopy(), null, null, null);
    }


    /**
     * It return the moves a token can perform
     * @param movableToken the token i want move
     * @param allTokens all the tokens on the battlefield
     * @return a list of Cell in which I can move the token
     * @throws CellOutOfBattlefieldException if something goes wrong.
     */
    public List<Cell> computeValidMoves(Token movableToken, List<Token> allTokens) throws CellOutOfBattlefieldException {

        List<Cell> validMoves = new ArrayList<>();

        int provX, provY;

        for (int i=-1; i<2; i++){                                                   // ciclo di +-1 intorno alla posizione del token
            provX = movableToken.getTokenPosition().getPosX()+i;                            // per poter ottenere le 8 caselle in cui
            for (int j=-1; j<2; j++){                                               // posso muovere
                provY = movableToken.getTokenPosition().getPosY()+j;

                if ( (provX>=0 && provX <5) && (provY>=0 && provY<5) &&                     // la cella provv è dentro le dimensioni del battlefield
                        (battlefield.getCell(provX,provY).getHeight()-              // l'altezza della cella provv -
                                movableToken.getTokenPosition().getHeight()<=1) &&            // l'altezza del token <= 1
                        (!battlefield.getCell(provX,provY).getIsDome())) {         // non deve essere una cupola

                    for (Token t:allTokens) {
                        if (provX != t.getTokenPosition().getPosX() &&                 // il token non può andare dove c'è un altro token
                                provY != t.getTokenPosition().getPosX()) {             // compreso sè stesso, quindi non può stare fermo

                            validMoves.add(battlefield.getCell(provX, provY));
                        }
                    }
                }
            }
        }
        return validMoves;
    }

    /**
     * Here is where the move is effectively done.
     * The check for the legal move is made by the controller.
     * After a player has moved his token, that token id checked for the win condition.
     * If true a message is sent to the client,
     * if not the game continue normally.
     * @param playerAction the message from the observer that contain all the information.
     * @throws CellOutOfBattlefieldException if something goes wrong.
     */
    public void performMove (PlayerAction playerAction) throws CellOutOfBattlefieldException {

        Cell targetCell = playerAction.getCell();
        Token movableToken = playerAction.getTokenMain();

        movableToken.setTokenPosition(targetCell);

        if (this.checkWin(playerAction.getTokenMain())) {

            String winner = playerAction.getPlayer().getUsername();
            gameOver(winner);
        }
        else {
            List<Cell> validBuilds = validBuilds(playerAction);
            ServerResponse serverResponse = new ServerResponse(Action.ASK_FOR_BUILD, this.getCopy(), null, validBuilds, null);
            notify(serverResponse);
        }
    }


    /**
     * It check for the legal Cell in which a player can build.
     * A player CAN build around the token he moved.
     * A token can NOT build out of the battlefield,
     * where there is a token (himself too),
     * where there is a dome.
     * @param playerAction the message from the observer that contain all the information.
     * @return a list of Cell in which the player can build around the token he moved.
     * @throws CellOutOfBattlefieldException if something goes wrong.
     */
    public List<Cell> validBuilds (PlayerAction playerAction) throws CellOutOfBattlefieldException {

        int provX, provY;

        Player player = playerAction.getPlayer();

        Player oppo1 = playerAction.getOppo1();

        Token canBuildToken = playerAction.getTokenMain();

        List<Token> allTokens = new ArrayList<Token>();
        List<Player> allPlayers = new ArrayList<Player>();
        List<Cell> buildableCells = new ArrayList<>();

        allPlayers.add(player);
        allPlayers.add(oppo1);

        if (numberOfPlayer == 3) {
            Player oppo2 = playerAction.getOppo2();
            allPlayers.add(oppo2);
        }

        for (Player p: allPlayers) {
            allTokens.add(p.getToken1());
            allTokens.add(p.getToken2());
        }

        for (int i=-1; i<2; i++){                                                   // ciclo di +-1 intorno alla posizione del token
            provX = canBuildToken.getTokenPosition().getPosX()+i;                            // per poter ottenere le 8 caselle in cui
            for (int j=-1; j<2; j++){                                               // posso costruire
                provY = canBuildToken.getTokenPosition().getPosY()+j;

                if ( (provX>=0 && provX <5) && (provY>=0 && provY<5) &&                       // la cella provv è dentro le dimensioni del battlefield
                        (!battlefield.getCell(provX,provY).getIsDome()) ) {        // non deve essere una cupola

                    for (Token t:allTokens) {
                        if (provX != t.getTokenPosition().getPosX() &&                 // il token non può costruire dove c'è un altro token
                                provY != t.getTokenPosition().getPosX()) {             // compreso sè stesso, quindi non può costruire sotto i piedi

                            buildableCells.add(battlefield.getCell(provX, provY));
                        }
                    }
                }
            }
        }
        return buildableCells;
    }


    /**
     * It call the incrementHeight on the cell that a player has chosen to build.
     * After a build has been made, the turn is updated.
     * @param playerAction the message from the observer that contain all the information.
     * @throws CellHeightException if something goes wrong.
     * @throws ReachHeightLimitException if something goes wrong.
     * @throws WrongNumberPlayerException if something goes wrong.
     * @throws ImpossibleTurnException if something goes wrong.
     */
    public void performBuild (PlayerAction playerAction) throws CellHeightException, ReachHeightLimitException, WrongNumberPlayerException, ImpossibleTurnException {

        Cell targetCell = playerAction.getCell();
        battlefield.getCell(targetCell).incrementHeight();

        this.updateTurn();

        String askForSelect = String.format("Player %s select the token you want to move (x,y)",this.turn.toString());

        ServerResponse serverResponse = new ServerResponse(Action.START_NEW_TURN, this.getCopy(), null, null, askForSelect);
    }


    /**
     * A player win the game if he goes up from level 2 to level 3
     * @param token the token to check if he verify the win condition
     * @return true if the token's owner win, false else.
     */
    public boolean checkWin (Token token) {

        int oldHeight = token.getOldHeight();
        int newHeight = token.getTokenPosition().getHeight();

        return oldHeight == 2 && newHeight == 3;
    }

    /**
     * This method create a GAME OVER message because someone has won the game.
     * @param winner the player who win.
     * @return the correct ServerResponse.
     * @throws CellOutOfBattlefieldException if something goes wrong.
     */
    public ServerResponse gameOver (String winner) throws CellOutOfBattlefieldException {
        String victoryMessage = String.format("%s HAS WIN!", winner.toUpperCase());
        return new ServerResponse (Action.GAME_OVER, this.getCopy(), null, null, victoryMessage);
    }

    /**
     * This method remove a player from the game because there are 3 player and one lost.
     * @param looser the player who lost.
     * @return the correct ServerResponse.
     */
    public ServerResponse playerLost (Player looser) throws WrongNumberPlayerException, ImpossibleTurnException {
        numberOfPlayer = 2;
        String lostMessage = String.format("%s HAS LOST! Better luck next time!", looser.getUsername().toUpperCase());
        removeFromTheGame(looser);
        return new ServerResponse (Action.PLAYER_LOST, this.getCopy(), null, null, lostMessage);
    }


    /**
     * It removes a player from the game and set free the cells where he has his tokens.
     * @param player the player who has to be removed.
     */
    public void removeFromTheGame (Player player) throws WrongNumberPlayerException, ImpossibleTurnException {

        player.getToken1().getTokenPosition().setFree();
        player.getToken2().getTokenPosition().setFree();

        // probabilmente dovrei anche deallocare cose e liberarne altre, ma confido nel garbage collector <3

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
                if (numberOfPlayer == 2) {
                    this.turn = TokenColor.RED;
                } else if (numberOfPlayer == 3) {
                    this.turn = TokenColor.YELLOW;
                }
                else {
                    throw new WrongNumberPlayerException(
                            String.format("There are %d players and it is not allowed!", numberOfPlayer));
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
     * It is called by the controller if a player make a request when it is not his turn.
     * It creates a ServerResponse with a message that tells him whose player is turn.
     * @throws CellOutOfBattlefieldException if something goes wrong.
     */
    public void notifyNotYourTurn() throws CellOutOfBattlefieldException {

        String whoIsTurn = String.format("Now is player %s turn!", this.turn.toString());

        ServerResponse serverResponse = new ServerResponse(Action.NOT_YOUR_TURN, null, null, null, whoIsTurn);

        notify(serverResponse);
    }
}
