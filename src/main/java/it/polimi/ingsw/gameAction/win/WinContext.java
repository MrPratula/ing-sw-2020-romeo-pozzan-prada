package it.polimi.ingsw.gameAction.win;

import it.polimi.ingsw.model.Token;

public class WinContext {

    private WinBehavior winStrategy;

    public WinContext(WinBehavior winStrategy) {
        this.winStrategy = winStrategy;
    }

    public boolean executeCheckWin (Token movedToken) {
        return winStrategy.checkWin(movedToken);
    }

}
