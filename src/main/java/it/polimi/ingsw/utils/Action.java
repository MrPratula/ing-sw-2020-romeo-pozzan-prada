package it.polimi.ingsw.utils;


/**
 * This enum is an element that each PlayerAction and ServerResponse must have.
 * It specify what kind of action has ben made or what kind or information the server needs in
 * order to let the game routine run properly.
 */
public enum Action {

    /*    FROM CLIENT TO SERVER     */

    SELECT_TOKEN("A player has selected a token"),                      //1
    MOVE("A player has moved a token"),                           //2
    BUILD("A player has made a build. Switch turn"),                    //3


    /*    FROM SERVER TO CLIENT     */

    NOT_YOUR_TURN("Wait your turn to perform your action!"),

    START_NEW_TURN("Your turn is ended!"),  //maybe inutile, o almeno non qui
    ASK_FOR_SELECTION(" Which token do you want to move? (x,y)"),       //1
    ASK_FOR_MOVE("Where do you want to move your token? (x,y)"),        //2
    ASK_FOR_BUILD("Where do you want to build? (x,y)"),                 //3

    GAME_OVER("GAME OVER"),
    TOKEN_NOT_MOVABLE("You can not move this token, please select a movable one!"),
    PLAYER_LOST("You have lost the game, your token will be wiped!");

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
}