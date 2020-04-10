package it.polimi.ingsw.model;
import it.polimi.ingsw.exception.ImpossibleTurnException;
import it.polimi.ingsw.exception.WrongNumberPlayerException;
import it.polimi.ingsw.utils.Observable;


/**
 * A Game is a Battlefield with an Observable for notify the player
 * when the model (me) change it's status
 */
public class Game extends Observable {

    private Battlefield battlefield;
    private TokenColor turn;
    private int numberOfPlayer;


    public Game(Battlefield battlefield) {
        this.battlefield = battlefield;
    }


    public Battlefield getBattlefieldCopy() {
        return battlefield.copy();
    }

    public Battlefield getBattlefield() {
        return this.battlefield;
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
                    this.turn = TokenColor.GREEN;
                }
                else {
                    throw new WrongNumberPlayerException(
                            String.format("There are %d players and it is not allowed!", numberOfPlayer));
                }
                break;
            }

            case GREEN: {
                this.turn = TokenColor.RED;
                break;
            }
            default: {
                throw new ImpossibleTurnException(
                        String.format("The color %s is not a valid turn", turn));
            }
        }
    }
}
