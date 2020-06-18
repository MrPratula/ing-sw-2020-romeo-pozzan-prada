package it.polimi.ingsw.gameAction.win;

import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Token;


/**
 * This is the interface for the win behavior.
 * For more info about this interface read the Move's interface JavaDOC.
 */
public interface WinBehavior {

    public boolean checkWin(Token movedToken, Battlefield battlefield) throws CellOutOfBattlefieldException;

}
