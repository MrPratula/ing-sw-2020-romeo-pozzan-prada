package it.polimi.ingsw.controller;

;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Token;
import it.polimi.ingsw.utils.PlayerAction;
import it.polimi.ingsw.utils.Observer;

import java.io.IOException;
import java.util.*;


/**
 * The Controller is the game logic.
 * It receive a PlayerMove from the Server.
 * The Controller modify the Model via ad-hoc method selected by the playerAction.
 */
public class Controller implements Observer<PlayerAction> {

    private Model model;

    public Controller (Model model) {
        this.model = model;
    }


    /**
     * It gets a PlayerAction and parse it.
     * The switch select the Action a player intend to perform and call the appropriate
     * method in the model.
     * The controller need to check the correct format of the input and always pass valid arguments to the model.
     * @param playerAction the message from the observer that contain all the information.
     */
    @Override
    public void update(PlayerAction playerAction) throws CellOutOfBattlefieldException, CellHeightException, ReachHeightLimitException, WrongNumberPlayerException, ImpossibleTurnException, IOException {

        if( model.isPlayerTurn(playerAction.getPlayer()) ){

            switch(playerAction.getAction()){

                case SELECT_TOKEN: {
                    model.validMoves(playerAction);
                    break;
                }

                case MOVE:{

                    Player player = playerAction.getPlayer();
                    Player oppo1 = playerAction.getOppo1();
                    List<Token> allTokens = new ArrayList<Token>();
                    List<Player> allPlayers = new ArrayList<Player>();

                    allPlayers.add(player);
                    allPlayers.add(oppo1);

                    if (playerAction.getOppo2()!=null) {
                        Player oppo2 = playerAction.getOppo2();
                        allPlayers.add(oppo2);
                    }

                    for (Player p: allPlayers) {
                        allTokens.add(p.getToken1());
                        allTokens.add(p.getToken2());
                    }

                    List<Cell> validMoves = model.computeValidMoves(playerAction.getTokenMain(), allTokens);
                    Cell targetCell = playerAction.getCell();

                    for (Cell c: validMoves) {
                        if (c.getPosX() == targetCell.getPosX() && c.getPosY() == targetCell.getPosY()){
                            model.performMove(playerAction);
                        }
                    }
                    break;
                }

                case BUILD:{
                    List<Cell> validBuilds = model.validBuilds(playerAction);
                    Cell targetCell = playerAction.getCell();

                    for (Cell c: validBuilds) {
                        if (c.getPosX() == targetCell.getPosX() && c.getPosY() == targetCell.getPosY()){
                            model.performBuild(playerAction);
                        }
                    }
                    break;
                }
            }
        }
        else {
           model.notifyNotYourTurn();
        }
    }
}
