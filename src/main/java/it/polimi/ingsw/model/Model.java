package it.polimi.ingsw.model;
import it.polimi.ingsw.exception.CellOutOfBattlefieldException;
import it.polimi.ingsw.exception.ImpossibleTurnException;
import it.polimi.ingsw.exception.WrongNumberPlayerException;
import it.polimi.ingsw.utils.Observable;

import java.util.*;


/**
 * A Game is a Battlefield with an Observable for notify the player
 * when the model (me) change it's status
 */
public class Model extends Observable<Model> implements Cloneable {

    private Battlefield battlefield;
    private TokenColor turn;
    private int numberOfPlayer;
    private Map<List<Cell>, Token> validMoves = new HashMap<>();
    private Map<List<Cell>, Token> validBuilds = new HashMap<>();
    private Map<Player, Choice> choices = new HashMap<>();
    private Map<Player, Outcome> outcomes = new HashMap<>();

    public Model(Battlefield battlefield) {
        this.battlefield = battlefield;
    }


    public Battlefield getBattlefieldCopy() {
        return battlefield.copy();
    }

    public Battlefield getBattlefield() {
        return this.battlefield;
    }

    public Choice getPlayerSelection(Player player, Token token) {
        return choices.get(player);
    }

    public Outcome getOutcome(Player player) {
        return outcomes.get(player);
    }

    public void setPlayerChoice(Player player, Choice playerChoice){
        if(choices.size() == 2){
            choices.clear();
            outcomes.clear();
        }
        if(!choices.containsKey(player)){
            if(choices.size() == 1){
                Player other = new LinkedList<Player>(choices.keySet()).get(0);
                outcomes.put(player, playerChoice.compareChoices(choices.get(other)));
                outcomes.put(other, choices.get(other).compareChoices(playerChoice));
            }
            choices.put(player, playerChoice);
        }
        if(choices.size() == 2){
            notify(this);
        }
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




    public void validMoves (Token token) throws CellOutOfBattlefieldException {

        List<Cell> result = new ArrayList<>();
        List<Player> players = battlefield.getPlayers();
        List<Token> allTokens = null;
        int provX, provY;

        for (Player player:players) {
            allTokens.add(player.getToken1());
            allTokens.add(player.getToken2());
        }
        for (int i=-1; i<2; i++){                                                   // ciclo di +-1 intorno alla posizione del token
            provX = token.getTokenPosition().getPosX()+i;                            // per poter ottenere le 8 caselle in cui
            for (int j=-1; j<2; j++){                                               // posso muovere
                provY = token.getTokenPosition().getPosY()+j;

                if ( (provX>=0 && provX <5) && (provY>=0 && provY<5) &&                     // la cella provv è dentro le dimensioni del battlefield
                        (battlefield.getCell(provX,provY).getHeight()-              // l'altezza della cella provv -
                                token.getTokenPosition().getHeight()<=1) &&            // l'altezza del token <= 1
                        (!battlefield.getCell(provX,provY).getIsDome())) {         // non deve essere una cupola

                    assert allTokens != null;   //suggerimento
                    for (Token t:allTokens) {
                        if (provX != t.getTokenPosition().getPosX() &&                 // il token non può andare dove c'è un altro token
                                provY != t.getTokenPosition().getPosX()) {             // compreso sè stesso, quindi non può stare fermo

                            result.add(battlefield.getCell(provX, provY));
                        }
                    }
                }
            }
        }
        validMoves.put(result,token);
        notify(this);
        //return result;
    }

    public String getCopy() {
    }



    public void validBuilds (Token token) throws CellOutOfBattlefieldException {

        List<Cell> result = new ArrayList<>();
        List<Player> players = battlefield.getPlayers();
        List<Token> allOtherTokens = null;
        int provX, provY;

        for (Player player:players) {
            allOtherTokens.add(player.getToken1());
            allOtherTokens.add(player.getToken2());
        }
        for (int i=-1; i<2; i++){                                                   // ciclo di +-1 intorno alla posizione del token
            provX = token.getTokenPosition().getPosX()+i;                            // per poter ottenere le 8 caselle in cui
            for (int j=-1; j<2; j++){                                               // posso muovere
                provY = token.getTokenPosition().getPosY()+j;

                if ( (provX>=0 && provX <5) && (provY>=0 && provY<5) &&                       // la cella provv è dentro le dimensioni del battlefield
                        (!battlefield.getCell(provX,provY).getIsDome()) ) {        // non deve essere una cupola

                    for (Token t:allOtherTokens) {
                        if (provX != t.getTokenPosition().getPosX() &&                 // il token non può andare dove c'è un altro token
                                provY != t.getTokenPosition().getPosX()) {             // compreso sè stesso, quindi non può stare fermo

                            result.add(battlefield.getCell(provX, provY));
                        }
                    }
                }
            }
        }
        validBuilds.put(result,token);
        notify(this);
        //return result;
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


}
