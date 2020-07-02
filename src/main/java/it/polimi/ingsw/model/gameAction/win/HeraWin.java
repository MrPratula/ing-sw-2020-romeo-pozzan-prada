package it.polimi.ingsw.model.gameAction.win;

import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Token;

/**
 * HERA
 *
 * An opponent can not win by moving on to a perimeter space.
 */
public class HeraWin implements WinBehavior{


    /**
     * This works a little different from the Simple win.
     * It is called at the end of each kind of check win only if HERA exist.
     * If it exists then if the player has won, I check for the HERA's condition and it modify that value.
     * @param movedToken the token that has moved.
     * @param battlefield always null here.
     * @return false if the moved token is on a corner of the battlefield, true else.
     */
    @Override
    public boolean checkWin(Token movedToken, Battlefield battlefield) {

        int movedX = movedToken.getTokenPosition().getPosX();
        int movedY = movedToken.getTokenPosition().getPosY();

        return movedX != 0 && movedY != 0 && movedX != 4 && movedY != 4;
    }
}
