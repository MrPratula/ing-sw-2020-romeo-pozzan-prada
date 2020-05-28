package it.polimi.ingsw.gui;

import it.polimi.ingsw.cli.*;
import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.controller.ReachHeightLimitException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BattlefieldGuiTest {

    Battlefield battlefield;
    Player p1,p2,p3;
    Token t1,t2,t3,t11,t22,t33;
    List<Player> players;
    Model model;
    SwingView swingView;
    //CellButton[][] battlefieldGUI;
    GameFrame g;

    @Before
    public void setUp() throws Exception {

        g = new GameFrame();

        battlefield = new Battlefield();
        model = new Model();
        model.setBattlefield(battlefield);
        swingView = new SwingView();
        swingView.setBattlefieldGUI(g.getBattlefieldGUI());

        p1 = new Player("Alpha", TokenColor.RED);
        p2 = new Player("Beta",TokenColor.BLUE);
        p3 = new Player("Charlie",TokenColor.YELLOW);

        t1 = new Token(TokenColor.RED);
        t11 = new Token(TokenColor.RED);
        t2 = new Token(TokenColor.BLUE);
        t22 = new Token(TokenColor.BLUE);
        t3 = new Token(TokenColor.YELLOW);
        t33 = new Token(TokenColor.YELLOW);

        t1.setTokenPosition(battlefield.getCell(1,0));
        t11.setTokenPosition(battlefield.getCell(0,1));
        t2.setTokenPosition(battlefield.getCell(3,2));   //the one that moves
        t22.setTokenPosition(battlefield.getCell(4,1));
        t3.setTokenPosition(battlefield.getCell(2,4));
        t33.setTokenPosition(battlefield.getCell(0,3));

        battlefield.getCell(1,0).setOccupied();
        battlefield.getCell(0,1).setOccupied();
        battlefield.getCell(3,2).setOccupied();
        battlefield.getCell(4,1).setOccupied();
        battlefield.getCell(2,4).setOccupied();
        battlefield.getCell(0,3).setOccupied();

        //battlefield.getCell(1,4).setIsDome();

        battlefield.getCell(1,0).setHeight(1);
        battlefield.getCell(2,2).setHeight(2);
        battlefield.getCell(3,3).setHeight(3);
        battlefield.getCell(4,4).setHeight(3);
        battlefield.getCell(0,4).setHeight(1);
        battlefield.getCell(4,4).incrementHeight();
        battlefield.getCell(0,1).setHeight(2);
        battlefield.getCell(3,4).setHeight(1);
        battlefield.getCell(2,1).setIsDome();
        battlefield.getCell(2,0).setIsDome();

        p1.setToken1(t1);
        p1.setToken2(t11);
        p2.setToken1(t2);
        p2.setToken2(t22);
        p3.setToken1(t3);
        p3.setToken2(t33);

        players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);

        for(Player p: players){
            model.addPlayer(p);
        }
    }


    /**
     * Test that prints the GUI, i.e. the battlefield
     * with some different heights and tokens
     */
    @Test
    public void displayGuiTest() throws ReachHeightLimitException, CellOutOfBattlefieldException {
        swingView.displayGui(model.getCopy(), null);
        g.setVisible(true);
    }


    /**
     * Test that prints the GUI, this time with the
     * valid moves (ORANGE CELLS) for the selected token
     */
    @Test
    public void displayGuiTestWithValidMoves(/* List<Cell> validMoves */) throws CellOutOfBattlefieldException, ReachHeightLimitException {

        List<Cell> validMoves = new ArrayList<>(); //saranno passate come parametro
        validMoves.add(battlefield.getCell(3,1));
        validMoves.add(battlefield.getCell(2,1));
        validMoves.add(battlefield.getCell(2,3));
        validMoves.add(battlefield.getCell(4,3));
        validMoves.add(battlefield.getCell(4,2));

        swingView.displayGui(model.getCopy(), validMoves);
        g.setVisible(true);

        // we don't care passing the selected token,
        // we just have to print the valid moves around him
    }



}
