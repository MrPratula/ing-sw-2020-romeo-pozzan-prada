package it.polimi.ingsw.model.gameAction.win;


import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Token;

/**
 * ARTEMIS
 *
 * Since you can move 2 times you also win even if your initial height was 1
 */
public class ArtemisWin implements WinBehavior {

    @Override
    public boolean checkWin(Token movedToken, Battlefield battlefield) {
        int oldHeight = movedToken.getOldHeight();
        int newHeight = movedToken.getTokenPosition().getHeight();

        return ((oldHeight == 2 || oldHeight==1) && newHeight == 3);
    }
}
