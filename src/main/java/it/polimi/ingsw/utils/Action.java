package it.polimi.ingsw.utils;


import java.io.Serializable;


/**
 * This enum is an element that each PlayerAction and Pack must have.
 * It specify what kind of action has ben made or what kind or information the server needs
 * in order to let the game routine run properly.
 */
public enum Action implements Serializable {

    /*************CLI**************/

    /*    FROM CLIENT TO SERVER     */

    MY_NAME("Inside this message there is my name"),
    NUMBER_OF_PLAYERS("The first player communicated how many players there are"),
    CHOSE_GOD_CARD ("A player has chose his god card"),
    TOKEN_PLACED("A token has been placed"),
    TOKEN_SELECTED("A player selected a token"),
    PROMETHEUS_ANSWER("Prometheus wants to use his God power!"),
    WHERE_TO_MOVE_SELECTED("A player choose where to move a token"),
    WHERE_TO_BUILD_SELECTED(""),

    /*    FROM SERVER TO CLIENT     */

    WELCOME("Welcome to Santorini!"),
    INVALID_NAME("This name is not available!"),

    HOW_MANY_PLAYERS("Do you want to make a 2 players game or a 3 players game? [2][3]"),
    WRONG_NUMBER_OF_PLAYER("Please insert 2 players or 3 players..."),

    NUMBER_RECEIVED("Waiting for the players to begin..."),
    WAIT_OTHER_PLAYERS_TO_CONNECT("Please wait other players to connect..."),

    CHOOSE_GOD_CARD_TO_PLAY("Choose which god cards you want to use in this game.\nType their name one by one..."),
    WAIT_AND_SAVE_PLAYER_FROM_SERVER("Wait your turn to select your God Card!"),

    /*     Broadcast     */

    SELECT_YOUR_GOD_CARD("Which one of these GodCards do you want to use in this game?"),
    PLACE_YOUR_TOKEN("Please choose a position for your token... (x,y)"),
    ASK_FOR_SELECT_TOKEN("Which token do you want to move? (x,y)"),
    ASK_FOR_PROMETHEUS_POWER("Do you want to use your GodPower? [yes][no]\nIf your Worker does not move up, it may build both before and after moving."),
    TOKEN_NOT_MOVABLE("You can not move this token, please select a movable one! (x,y)"),
    GAME_OVER("GAME OVER"),
    PLAYER_LOST(""),
    ASK_FOR_WHERE_TO_MOVE("Where do you want to move your token? (x,y)\nOnly the green cell are accepted"),
    WRONG_INPUT("Please insert a valid choice!"),
    ASK_FOR_BUILD("Where do you want to build? (x,y)"),

    // ERROR MESSAGES
    CONNECTION_CLOSE("Connection closed from SERVER side!");

    private final String info;

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