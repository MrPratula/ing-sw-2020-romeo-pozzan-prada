package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.utils.PlayerAction;
import it.polimi.ingsw.utils.Observer;

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
     * @throws CellOutOfBattlefieldException if something goes wrong.
     * @throws CellHeightException if something goes wrong.
     * @throws ReachHeightLimitException if something goes wrong.
     * @throws WrongNumberPlayerException if something goes wrong.
     * @throws ImpossibleTurnException if something goes wrong.
     */
    @Override
    public void update(PlayerAction playerAction) throws CellOutOfBattlefieldException, CellHeightException, ReachHeightLimitException, WrongNumberPlayerException, ImpossibleTurnException {

        if( model.isPlayerTurn(playerAction.getPlayer()) ){

            switch(playerAction.getAction()){

                case SELECT_TOKEN: {
                    model.validMoves(playerAction);
                    break;
                }

                case MOVE_TOKEN:{
                    List<Cell> validMoves = model.validMoves(playerAction);
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
