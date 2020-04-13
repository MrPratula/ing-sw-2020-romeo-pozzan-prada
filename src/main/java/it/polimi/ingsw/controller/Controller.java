package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.CellOutOfBattlefieldException;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerAction;
import it.polimi.ingsw.model.Token;
import it.polimi.ingsw.utils.Observer;

import java.util.*;


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

                case SELECT_TOKEN: {

                    Player player = playerAction.getPlayer();

                    Player oppo1 = playerAction.getOppo1();
                    Player oppo2 = playerAction.getOppo2();

                    Token movableToken = playerAction.getToken();

                    List<Token> allTokens = new ArrayList<Token>();
                    List<Player> allPlayers = new ArrayList<Player>();

                    allPlayers.add(player);
                    allPlayers.add(oppo1);
                    try{
                        allPlayers.add(oppo2);
                    } catch (NullPointerException e) {
                        System.out.println("There is no player2");
                    }

                    for (Player p: allPlayers) {
                        allTokens.add(p.getToken1());
                        allTokens.add(p.getToken2());
                    }

                    model.validMoves(movableToken, allTokens);
                    break;
                }

                case MOVE_TOKEN:{
                    break;

                }

                case BUILD:{
                    break;
                }
                case SELECT_CELL:{
                    break;
                }

            }
        }
        else {
            /**
             * mandare messaggio di "non è il tuo turno"
             */
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
