package it.polimi.ingsw.model;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class BattlefieldTest {

    Battlefield battlefield;
    Player p1,p2,p3;
    Token t1,t2,t3,t11,t22,t33;
    List<Player> players;

    @Before
    public void setUp() throws Exception {
        battlefield = new Battlefield();
        p1 = new Player("a",TokenColor.RED,battlefield);
        p2 = new Player("b",TokenColor.BLUE,battlefield);
        p3 = new Player("c",TokenColor.YELLOW,battlefield);
        t1 = new Token(TokenColor.RED);
        t11 = new Token(TokenColor.RED);
        t2 = new Token(TokenColor.BLUE);
        t22 = new Token(TokenColor.BLUE);
        t3 = new Token(TokenColor.YELLOW);
        t33 = new Token(TokenColor.YELLOW);
        t1.setTokenPosition(battlefield.getCell(1,1));
        battlefield.getCell(1,1).setThereIsPlayer();
        t11.setTokenPosition(battlefield.getCell(0,1));
        battlefield.getCell(0,1).setThereIsPlayer();
        t2.setTokenPosition(battlefield.getCell(3,2));
        battlefield.getCell(3,2).setThereIsPlayer();
        t22.setTokenPosition(battlefield.getCell(4,2));
        battlefield.getCell(4,2).setThereIsPlayer();
        t3.setTokenPosition(battlefield.getCell(0,4));
        battlefield.getCell(0,4).setThereIsPlayer();
        t33.setTokenPosition(battlefield.getCell(0,3));
        battlefield.getCell(0,3).setThereIsPlayer();
        p1.setToken1(t1);
        p1.setToken2(t11);
        p2.setToken1(t2);
        p2.setToken2(t22);
        p3.setToken1(t3);
        p3.setToken2(t33);
        players = new ArrayList<Player>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        battlefield.setPlayers(players);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void PrintCLITest(){
        battlefield.printCLI();
    }

}