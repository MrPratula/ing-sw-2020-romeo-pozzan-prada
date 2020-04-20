package it.polimi.ingsw.gameAction.win;

import it.polimi.ingsw.model.Token;

public class SimpleWin implements WinBehavior{

    @Override
    public boolean checkWin(Token movedToken) {
        int oldHeight = movedToken.getOldHeight();
        int newHeight = movedToken.getTokenPosition().getHeight();

        return oldHeight == 2 && newHeight == 3;
    }
}
