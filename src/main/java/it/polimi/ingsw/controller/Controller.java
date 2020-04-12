package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.CellOutOfBattlefieldException;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.PlayerAction;
import it.polimi.ingsw.utils.Observer;


/**
 * The Controller is the game logic.
 * It Observe the Player till they notify with a message.
 * The Controller modify the Model via ad-hoc method selected by the message in the update.
 * Other than the update message, each update should give Controller objects he need to perform
 * what is specified in the message.
 */
public class Controller implements Observer<PlayerAction> {

    private Model model;

    public Controller (Model model) {
        this.model = model;
    }


    /**
     * The update method uses the message parameter to choose the correct method to run.
     * @param playerAction is used to specify the method to run via the switch.
     */
    @Override
    public void update(PlayerAction playerAction) throws CellOutOfBattlefieldException {

        if( model.isPlayerTurn(playerAction.getPlayer()) ){

            switch(playerAction.getAction()){
                case SELECT_TOKEN:
                   model.validMoves(playerAction.getToken());
                case MOVE:

                case BUILD:

            }
        }
    }



    /*
            public void move(Token token, Battlefield battlefield) {

                token.setOldHeight(token.getTokenPosition().getHeight());
                List<Cell> validCells = token.validMoves(battlefield);
                Cell chosenCell = this.chooseCell(validCells);
                token.setTokenPosition(chosenCell);
                choosenCell.setThereIsPlayer();          //added
            }
            */




}
