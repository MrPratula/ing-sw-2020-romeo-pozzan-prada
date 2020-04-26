package it.polimi.ingsw.gameAction;

import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.model.Battlefield;

public class Utility {

    /**
     * This just set up a battlefield with some various builds
     * for testing.
     * @return the battlefield.
     */
    public static Battlefield setUpForTest() throws CellOutOfBattlefieldException {

        Battlefield battlefield = new Battlefield();

        battlefield.getCell(2,1).setIsDome();
        battlefield.getCell(1,2).setHeight(1);
        battlefield.getCell(2,2).setHeight(1);
        battlefield.getCell(3,2).setHeight(3);
        battlefield.getCell(1,3).setHeight(2);
        battlefield.getCell(2,3).setHeight(3);

        battlefield.getCell(3,2).setIsDome();
        battlefield.getCell(2,1).setIsDome();

        return battlefield;
    }
}
