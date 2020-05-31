package it.polimi.ingsw.gameAction;

import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.cli.Battlefield;

public class Utility {

    /**
     * This just set up a battlefield with some various builds
     * for testing.
     * The battlefield created like this is the one you can see in the
     * battlefieldTest.jpg
     * The token you can see in the image are not placed in the battlefield yet.
     * @return the battlefield.
     */
    public static Battlefield setUpForTest1() throws CellOutOfBattlefieldException {

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

    public static Battlefield setUpForTest2() throws CellOutOfBattlefieldException {

        Battlefield battlefield = new Battlefield();

        battlefield.getCell(2,1).setIsDome();
        battlefield.getCell(1,2).setHeight(1);
        battlefield.getCell(2,2).setHeight(1);
        battlefield.getCell(3,2).setHeight(3);
        battlefield.getCell(1,3).setHeight(2);
        battlefield.getCell(2,3).setHeight(3);
        battlefield.getCell(3,3).setHeight(2);
        battlefield.getCell(4,3).setHeight(1);
        battlefield.getCell(4,4).setHeight(1);
        battlefield.getCell(3,4).setHeight(2);
        battlefield.getCell(2,4).setHeight(3);

        battlefield.getCell(3,2).setIsDome();
        battlefield.getCell(2,1).setIsDome();

        return battlefield;
    }
}
