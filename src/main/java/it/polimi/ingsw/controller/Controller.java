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

        if( model.isPlayerTurn(playerAction.getPlayer()) ){

            switch(playerAction.getAction()){

                case PROMETHEUS_POWER:{
                    ///////////////////////////////
                            //simple build - prometheus move, simple build
                    List<Cell> validBuilds = model.askForValidBuilds(playerAction);
                    Cell targetCell = playerAction.getFirstCell();

                    for (Cell c: validBuilds) {
                        if (c.getPosX() == targetCell.getPosX() && c.getPosY() == targetCell.getPosY()) {
                            model.performBuild(playerAction);
                        }
                        else{
                            model.notifyWrongInput(playerAction);
                        }
                    }

                }

                case SELECT_TOKEN: {
                    model.validMoves(playerAction);
                    break;
                }

                case MOVE:{

                    List<Cell> validMoves = model.askValidMoves(playerAction);
                    Cell targetCell = playerAction.getFirstCell();

                    for (Cell c: validMoves) {
                        if (c.getPosX() == targetCell.getPosX() && c.getPosY() == targetCell.getPosY()){
                            model.performMove(playerAction);
                        }
                        else {
                            model.notifyWrongInput(playerAction);
                        }
                    }
                    break;
                }

                case BUILD:{
                    List<Cell> validBuilds = model.askForValidBuilds(playerAction);
                    Cell targetCell = playerAction.getFirstCell();


                    if (model.differentCell(targetCell, playerAction.getSecondCell()) &&  (playerAction.getPlayer().getMyGodCard().equals(GodCard.DEMETER))){
                        model.performBuild(playerAction);
                    }else{
                        model.notifyWrongInput(playerAction);
                    }

                    for (Cell c: validBuilds) {
                        if (c.getPosX() == targetCell.getPosX() && c.getPosY() == targetCell.getPosY()){
                            /* Se la godcard è demeter e vuole usare il potere, deve controllare che le due celle
                                abbiamo almeno una posizione (x o y) diversa. */
                            if (playerAction.getPlayer().getMyGodCard() == GodCard.DEMETER && playerAction.getDoWantUsePower()) {
                                Cell second_cell = playerAction.getSecondCell();
                                if ((targetCell.getPosX() != second_cell.getPosX()) || (targetCell.getPosY() != second_cell.getPosY())) {
                                    model.performBuild(playerAction);
                                } else {
                                    model.notifyWrongInput(playerAction);
                                }
                            }
                            /* Se la godcard è Hestia e vuole usare il potere, la seconda cella in cui voglio costruire non deve
                                essere una cella perimetrale. */
                            else if((playerAction.getPlayer().getMyGodCard() == GodCard.HESTIA) && playerAction.getDoWantUsePower()){
                                Cell second_cell = playerAction.getSecondCell();
                                if((second_cell.getPosY() != 4) && (second_cell.getPosX() != 4)){
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
           model.notifyNotYourTurn();
        }
    }
}
