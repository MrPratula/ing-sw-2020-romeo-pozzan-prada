package it.polimi.ingsw.gameAction.win;

import it.polimi.ingsw.model.Token;


/**
 * A player win the game if he go from a level 2 build to a level 3 build.
 */
public class SimpleWin implements WinBehavior{


    /**
     * It check the old height and the new height.
     * @param movedToken the token to be considered.
     * @return true if the old height was 2 and the new height is 3. False else.
     */
    @Override
    public boolean checkWin(Token movedToken) {
        int oldHeight = movedToken.getOldHeight();
        int newHeight = movedToken.getTokenPosition().getHeight();
        return oldHeight == 2 && newHeight == 3;
    }
}
