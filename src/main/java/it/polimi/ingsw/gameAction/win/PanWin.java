package it.polimi.ingsw.gameAction.win;

import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Token;

/**
 * PAN
 *
 * You also win if your Worker moves down two or more levels.
 */
public class PanWin implements WinBehavior {


    /**
     * Pan also win if the difference between old height and new height is 2 or more
     * @param movedToken the token Pan's owner just moved.
     * @return true if it satisfy the win condition.
     */
    @Override
    public boolean checkWin(Token movedToken, Battlefield battlefield) {

        int oldHeight = movedToken.getOldHeight();
        int newHeight = movedToken.getTokenPosition().getHeight();

        return (oldHeight == 2 && newHeight == 3)||(oldHeight-newHeight>1);

    }

}
