package it.polimi.ingsw.utils;


import java.io.Serializable;

/**
 * This enum is an element that each PlayerAction and ServerResponse must have.
 * It specify what kind of action has ben made or what kind or information the server needs in
 * order to let the game routine run properly.
 */
public enum Action implements Serializable {


    /*************CLI**************/

    /*    FROM CLIENT TO SERVER     */

    NUMBER_OF_PLAYERS("The first player communicated how many players there are"),
    MY_NAME("Inside this message there is my name"),
    TOKEN_SET_UP("A player selected the set-up position for the token"),
    TOKEN_PLACED("A token has been placed"),

    PROMETHEUS_POWER("Prometheus wants to use his God power!"),
    SELECT_TOKEN("A player has selected a token"),
    MOVE("A player moved a token"),
    BUILD("A player made a build. Switch turn"),
    CHOSE_GOD_CARD ("A player has chose his god card"),


    /*    FROM SERVER TO CLIENT     */

    NOT_YOUR_TURN("Wait your turn to perform your action!"),

    START_NEW_TURN("Your turn is ended!"),
    ASK_FOR_PROMETHEUS_POWER("Do you want to use your GodPower? [yes][no]"),
    ASK_FOR_MOVE("Where do you want to move your token? (x,y)"),
    ASK_FOR_BUILD("Where do you want to build? (x,y)"),

    GAME_OVER("GAME OVER"),
    PLAYER_LOST("You lost the game, your tokens will be wiped!"),

    SET_UP("Select the position you want to place your first token (x,y)"),
    WELCOME("Welcome to Santorini! What is your name?"),
    HOW_MANY_PLAYERS("Do you want to make a 2 players game or a 3 players game? [2][3]"),

    NUMBER_RECEIVED("Waiting for the players to begin..."),
    WAIT_PLEASE("Please wait..."),
    WAIT_OTHER_PLAYER_MOVE("Someone else is performing their move. Please wait your turn..."),
    SELECT_YOUR_GOD_CARD("Which one of these GodCards do you want to use in this game?"),
    PLACE_YOUR_TOKEN("Please choose a position for your token... (x,y)"),

    PRINT_MESSAGE("Write here some text to be printed..."),

    // ERROR MESSAGES
    INVALID_NAME("This name is not available, please try with another one..."),
    WRONG_NUMBER_OF_PLAYER("Please insert 2 players or 3 players..."),
    WRONG_INPUT("Please insert a valid choice!"),
    TOKEN_NOT_MOVABLE("You can not move this token, please select a movable one! (x,y)"),
    CONNECTION_CLOSE("Connection closed from SERVER side!"),


    /*************GUI**************/

    /*    FROM CLIENT TO SERVER     */

    INITIALISED("User typed his username and number_of_players");



    private String info;

    Action(String info){ this.info = info;}

    public void print (Action message) {
        System.out.println(message);
    }

    public String getInfo(){
        return info;
    }

    @Override
    public String toString(){
        return info;
    }

    public String getName(){
        return this.name();
    }
}