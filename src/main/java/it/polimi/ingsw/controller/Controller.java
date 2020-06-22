package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.GodCard;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.utils.PlayerAction;
import it.polimi.ingsw.utils.Observer;
import java.io.IOException;
import java.util.*;


/**
 * The Controller contains the game logic.
 * It receives a PlayerMove from the Server.
 * The Controller modifies the Model via ad-hoc method selected by the playerAction.
 */
public class Controller implements Observer<PlayerAction> {

    private final Model model;
    private static boolean gameOver;

    public Controller (Model model) {
        this.model = model;
        gameOver = false;
    }

    public static void setGameOver(){
        gameOver = true;
    }

    /**
     * It gets a PlayerAction and parse it.
     * The switch selects the Action that a player intends to perform and calls
     * the appropriate method in the model.
     * The controller needs to check the correct format of the input and always pass valid arguments to the model.
     * @param playerAction the message from the observer that contains all the information.
     */
    @Override
    public void update(PlayerAction playerAction) throws CellOutOfBattlefieldException, CellHeightException, ReachHeightLimitException, WrongNumberPlayerException, ImpossibleTurnException, IOException {

        if (model.isPlayerTurn(playerAction.getPlayer())){

            if (gameOver)
                return;

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
                    break;
                }


                case WHERE_TO_MOVE_SELECTED:{

                    List<Cell> validMoves = model.askValidMoves(playerAction);
                    Cell targetCell = playerAction.getFirstCell();
                    boolean isItCorrect = false;
                    for (Cell c: validMoves) {
                        if (c.getPosX() == targetCell.getPosX() && c.getPosY() == targetCell.getPosY()){
                            model.performMove(playerAction);
                            isItCorrect = true;
                            break;
                        }
                    }

                    if (!isItCorrect)
                        model.notifyWrongInput();

                    // Not sure if this is the best place for this, but it works here. Don't touch plz
                    if (Model.isDidPrometheusUsePower())
                        Model.prometheusUsePower(false);

                    break;
                }


                case WHERE_TO_BUILD_SELECTED:{
                    List<Cell> validBuilds = model.askForValidBuilds(playerAction);

                    Cell targetCell = model.getBattlefield().getCell(playerAction.getFirstCell());
                    Cell secondCell = null;
                    if (playerAction.getSecondCell()!=null)
                        secondCell = model.getBattlefield().getCell(playerAction.getSecondCell());

                    boolean isTheCellCorrect = false;
                    for (Cell c: validBuilds) {
                        if (c.getPosX() == targetCell.getPosX() && c.getPosY() == targetCell.getPosY()) {
                            isTheCellCorrect = true;
                            break;
                        }
                    }

                    if (isTheCellCorrect) {

                        GodCard god = model.getPlayerInTurn().getMyGodCard();

                        switch (god){


                            case DEMETER:{
                                if (playerAction.getDoWantUsePower()) {
                                    if (model.differentCell(targetCell, secondCell)) {
                                        model.performBuild(playerAction);
                                    } else {
                                        model.notifyWrongInput();
                                    }
                                } else {
                                    model.performBuild(playerAction);
                                }
                                break;
                            }


                            case HESTIA:{
                                if (playerAction.getDoWantUsePower()) {
                                    if (model.notPerimeterCell(secondCell)) {
                                        model.performBuild(playerAction);
                                    } else {
                                        model.notifyWrongInput();
                                    }
                                    break;
                                }
                            }


                            default: {
                                model.performBuild(playerAction);
                            }
                        }
                    }
                    else
                        model.notifyWrongInput();
                break;
                }
            }
        }
        else {
            System.out.println("BAAAAAAAAAAAD");
            model.notifyWrongInput();
        }
    }
}
