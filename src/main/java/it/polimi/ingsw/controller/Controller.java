package it.polimi.ingsw.controller;

;
import it.polimi.ingsw.model.*;
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

        if (model.isPlayerTurn(playerAction.getPlayer())){

            switch(playerAction.getAction()){


                case CHOSE_GOD_CARD: {
                    model.computeGodChoices(playerAction);
                    break;
                }

                case TOKEN_PLACED:{
                    model.placeToken(playerAction);
                    break;
                }

                case TOKEN_SELECTED: {

                    if (playerAction.getPlayer().getMyGodCard().equals(GodCard.PROMETHEUS)) {
                        model.askForPrometheus(playerAction);
                    } else {
                        model.validMoves(playerAction);
                    }
                break;
                }


                case PROMETHEUS_ANSWER:{

                    if (playerAction.getDoWantUsePower()){
                        model.prometheusFirstBuild();
                    }
                    else {
                        model.validMoves(playerAction);
                    }
                }


                case WHERE_TO_MOVE_SELECTED:{

                    List<Cell> validMoves = model.askValidMoves(playerAction);
                    Cell targetCell = playerAction.getFirstCell();
                    for (Cell c: validMoves) {
                        if (c.getPosX() == targetCell.getPosX() && c.getPosY() == targetCell.getPosY()){
                            model.performMove(playerAction);
                        }
                    }
                    break;
                }


                case WHERE_TO_BUILD_SELECTED:{
                    List<Cell> validBuilds = model.askForValidBuilds(playerAction);
                    Cell targetCell = playerAction.getFirstCell();

                    for (Cell c: validBuilds) {
                        if (c.getPosX() == targetCell.getPosX() && c.getPosY() == targetCell.getPosY()){
                            if(playerAction.getPlayer().getMyGodCard().equals(GodCard.DEMETER) && playerAction.getDoWantUsePower()){
                                if (model.differentCell(targetCell, playerAction.getSecondCell())){
                                    model.performBuild(playerAction);
                                }else {
                                    model.notifyWrongInput(playerAction);
                                }
                            }
                            if(playerAction.getPlayer().getMyGodCard().equals((GodCard.HESTIA))){
                                if(model.notperimetercell(playerAction.getSecondCell())){
                                    model.performBuild(playerAction);
                                }
                                else{
                                    model.notifyWrongInput(playerAction);
                                }
                            }
                            else {
                                model.performBuild(playerAction);
                            }
                        }
                        else{
                            model.notifyWrongInput(playerAction);
                        }
                    }
                    break;
                }
            }
        }
        else {
            // this should never happen
            System.out.println("BAAAAAAAAAAAD");
        }
    }
}
