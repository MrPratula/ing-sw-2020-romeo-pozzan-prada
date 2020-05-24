package it.polimi.ingsw.model;
import it.polimi.ingsw.client.View;
import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.controller.ReachHeightLimitException;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class BattlefieldLimitTest {

    Battlefield battlefield;
    Player p1, p2, p3;
    Token t1, t2, t3, t11, t22, t33;
    List<Player> players;
    Model model;
    View view;

    @Before
    public void setUp() throws Exception {

        battlefield = new Battlefield();
        model = new Model();
        model.setBattlefield(battlefield);
        view = new View();

        p1 = new Player("Alpha", TokenColor.RED);
        p2 = new Player("Beta", TokenColor.BLUE);
        p3 = new Player("Charlie", TokenColor.YELLOW);

        t1 = new Token(TokenColor.RED);
        t11 = new Token(TokenColor.RED);
        t2 = new Token(TokenColor.BLUE);
        /*t22 = new Token(TokenColor.BLUE);
        t3 = new Token(TokenColor.YELLOW);*/
        t33 = new Token(TokenColor.YELLOW);


        p1.setToken1(t1);
        p1.setToken2(t11);
        p2.setToken1(t2);
        p3.setToken2(t33);

        players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);

        for (Player p : players) {
            model.addPlayer(p);
        }

        view.printCLI(model.getCopy(), null);
        //output: empty battlefield, because tokens has no position

        t1.setTokenPosition(battlefield.getCell(1, 0));
        t11.setTokenPosition(battlefield.getCell(0, 1));
        t2.setTokenPosition(battlefield.getCell(3,2));   //the one that moves
        /*t22.setTokenPosition(battlefield.getCell(4,1));
        t3.setTokenPosition(battlefield.getCell(2,4));*/
        t33.setTokenPosition(battlefield.getCell(0,3));

        view.printCLI(model.getCopy(), null);
        //output: again empty battlefield, because tokens has a position, but their cell isn't set as Occupied

        battlefield.getCell(1, 0).setOccupied();
        battlefield.getCell(0, 1).setOccupied();
        battlefield.getCell(3,2).setOccupied();
        /*battlefield.getCell(4,1).setOccupied();
        battlefield.getCell(2,4).setOccupied();*/
        battlefield.getCell(0,3).setOccupied();

        //battlefield.getCell(1,4).setIsDome();

        view.printCLI(model.getCopy(), null);
        //output: colored tokens, because they has a position, AND their cell is set as Occupied

        battlefield.getCell(1, 0).setHeight(1);
        battlefield.getCell(2, 2).setHeight(2);
        battlefield.getCell(3, 3).setHeight(3);
        battlefield.getCell(4, 4).setHeight(3);
        battlefield.getCell(0, 4).setHeight(1);
        battlefield.getCell(4, 4).incrementHeight();
        //battlefield.getCell(4,4).incrementHeight();
        //battlefield.getCell(4,4).incrementHeight();
        battlefield.getCell(0, 1).setHeight(2);
        battlefield.getCell(3, 4).setHeight(1);
        battlefield.getCell(2, 1).setIsDome();
        battlefield.getCell(2, 0).setIsDome();

        view.printCLI(model.getCopy(), null);

        //here i see the height

        battlefield.getCell(1, 4).incrementHeight();
        battlefield.getCell(4, 0).incrementHeight();
        battlefield.getCell(2, 0).setIsDome();

        view.printCLI(model.getCopy(), null);
    }


    /**
     * Test that prints the CLI, i.e. the battlefield
     * with some different heights and tokens
     */
    @Test
    public void PrintCLITest() throws ReachHeightLimitException, CellOutOfBattlefieldException {
        view.printCLI(model.getCopy(), null);
    }

}