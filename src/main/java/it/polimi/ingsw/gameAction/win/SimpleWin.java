package it.polimi.ingsw.gameAction.win;

import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Token;


/**
 * A player win the game if he go from a level 2 build to a level 3 build
 */
public class SimpleWin implements WinBehavior{


    /**
     * It check the old height and the new height
     * @param movedToken the token to be considered
     * @return true if i move up from 1 or 2 to 3
     */
    @Override
    public boolean checkWin(Token movedToken, Battlefield battlefield) {
        int oldHeight = movedToken.getOldHeight();
        int newHeight = movedToken.getTokenPosition().getHeight();
        return oldHeight == 2 && newHeight == 3;
    }
}
