package it.polimi.ingsw.controller;

import it.polimi.ingsw.cli.*;
import it.polimi.ingsw.client.View;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.PlayerAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class FullGame1ApolloArtemisTest {

    Model model;
    Controller controller;

    Player player1, player2;

    GodCard player1God, player2God;

    View view;

    @BeforeEach
    void setUp(){

        model = new Model();
        controller = new Controller(model);

        player1 = new Player("Mario", TokenColor.RED);
        player2 = new Player("Luigi", TokenColor.BLUE);

        model.addPlayer(player1);
        model.addPlayer(player2);

        view = new View();
    }


    /**
     * Player 1 -> APOLLO
     * Player 2 -> ARTEMIS
     */
    @Test
    void test1 () throws CellOutOfBattlefieldException, ImpossibleTurnException, ReachHeightLimitException, CellHeightException, WrongNumberPlayerException, IOException {

        player1God = GodCard.APOLLO;
        player2God = GodCard.ARTEMIS;

        player1.setMyGodCard(player1God);
        player2.setMyGodCard(player2God);

        model.addGod(player1God);
        model.addGod(player2God);

        model.setTurn(TokenColor.RED);

        PlayerAction playerAction;
        Cell targetCell;
        Cell selectedCell;
        int selectedToken;
        int savedToken;

        // Player 1 place token

        targetCell = model.getBattlefield().getCell(1,1);
        playerAction = new PlayerAction(Action.TOKEN_PLACED, player1, null, null, 0, 0, targetCell, null, false, null);
        controller.update(playerAction);

        targetCell = model.getBattlefield().getCell(3,3);
        playerAction = new PlayerAction(Action.TOKEN_PLACED, player1, null, null, 0, 0, targetCell, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), null);

        // Player 2 place token

        targetCell = model.getBattlefield().getCell(3,2);
        playerAction = new PlayerAction(Action.TOKEN_PLACED, player2, null, null, 0, 0, targetCell, null, false, null);
        controller.update(playerAction);

        targetCell = model.getBattlefield().getCell(1,3);
        playerAction = new PlayerAction(Action.TOKEN_PLACED, player2, null, null, 0, 0, targetCell, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), null);

        // Player 1 select token

        selectedToken = 11;
        playerAction = new PlayerAction(Action.TOKEN_SELECTED, player1, null, null, selectedToken, 0, null, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());

        // Player 1 select where to move

        savedToken = 11;
        selectedCell = model.getBattlefield().getCell(3,2);
        playerAction = new PlayerAction(Action.WHERE_TO_MOVE_SELECTED, player1, null, null, savedToken, 0, selectedCell, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());

        // Player 1 select where to build

        selectedCell = model.getBattlefield().getCell(2,2);
        playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, player1, null, null, savedToken, 0, selectedCell, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());

        // Player 2 select token

        selectedToken = 2;
        playerAction = new PlayerAction(Action.TOKEN_SELECTED, player2, null, null, selectedToken, 0, null, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());

        // Player 2 select where to move

        savedToken = 2;
        selectedCell = model.getBattlefield().getCell(3,1);
        playerAction = new PlayerAction(Action.WHERE_TO_MOVE_SELECTED, player2, null, null, savedToken, 0, selectedCell, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());

        // Player 2 select where to build

        selectedCell = model.getBattlefield().getCell(2,1);
        playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, player2, null, null, savedToken, 0, selectedCell, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());

        // Speed up the game

        System.out.println("Speed up the game...");
        model.getBattlefield().getCell(2,3).setHeight(3);
        model.getBattlefield().getCell(2,2).setHeight(2);
        model.getBattlefield().getCell(2,1).setHeight(1);
        model.setTurn(TokenColor.BLUE);

        player2.getToken2().setTokenPosition(model.getBattlefield().getCell(2,1));
        model.getBattlefield().getCell(2,1).setOccupied();
        model.getBattlefield().getCell(1,3).setFree();

        view.printCLI(model.getCopy(), model.getValidCells());

        // Player 2 select token

        selectedToken = 22;
        playerAction = new PlayerAction(Action.TOKEN_SELECTED, player2, null, null, selectedToken, 0, null, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());

        // Player 2 select where to move and win the game

        savedToken = 22;
        selectedCell = model.getBattlefield().getCell(2,3);
        playerAction = new PlayerAction(Action.WHERE_TO_MOVE_SELECTED, player2, null, null, savedToken, 0, selectedCell, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());
    }
}
