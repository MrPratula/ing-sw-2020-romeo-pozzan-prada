package it.polimi.ingsw.model.gameAction.win;

import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Token;


/**
 * CRONUS
 *
 * You also win when there are at least five Complete Towers on the board.
 * Note that if ATLAS build a dome on a level that is not 3, that tower is not a Complete Tower.
 */
public class ChronusWin implements WinBehavior{


    /**
     * This works a little different.
     * It is never called in the checkWin phase, but it is always called if CHRONUS exist
     * after each player has performed a build.
     * @param movedToken always null. It is not needed here.
     * @param battlefield the model's battlefield.
     * @return true if there are at leas 5 Complete Builds on the battlefield.
     */
    @Override
    public boolean checkWin(Token movedToken, Battlefield battlefield){

        int completeTowers = 0;

        for (int x=0; x<5; x++) {
            for (int y=0; y<5; y++) {
                if (battlefield.getCell(x,y).getHeight()==3 && battlefield.getCell(x,y).getIsDome())
                    completeTowers++;
            }
        }
        return completeTowers >= 5;
    }
}
