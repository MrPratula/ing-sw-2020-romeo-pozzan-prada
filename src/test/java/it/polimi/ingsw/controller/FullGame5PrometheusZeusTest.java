package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.PlayerAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class FullGame5PrometheusZeusTest {

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
     * Player 1 -> ZEUS
     * Player 2 -> PROMETHEUS
     */
    @Test
    void test5 () throws IOException {

        player1God = GodCard.ZEUS;
        player2God = GodCard.PROMETHEUS;

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

        targetCell = model.getBattlefield().getCell(1,2);
        playerAction = new PlayerAction(Action.TOKEN_PLACED, player1, null, null, 0, 0, targetCell, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), null);

        // Player 2 place token

        targetCell = model.getBattlefield().getCell(3,1);
        playerAction = new PlayerAction(Action.TOKEN_PLACED, player2, null, null, 0, 0, targetCell, null, false, null);
        controller.update(playerAction);

        targetCell = model.getBattlefield().getCell(3,2);
        playerAction = new PlayerAction(Action.TOKEN_PLACED, player2, null, null, 0, 0, targetCell, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), null);

        // Player 1 select token

        selectedToken = 1;
        playerAction = new PlayerAction(Action.TOKEN_SELECTED, player1, null, null, selectedToken, 0, null, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());

        // Player 1 select where to move

        savedToken = 1;
        selectedCell = model.getBattlefield().getCell(2,1);
        playerAction = new PlayerAction(Action.WHERE_TO_MOVE_SELECTED, player1, null, null, savedToken, 0, selectedCell, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());

        // Player 1 select where to build

        selectedCell = model.getBattlefield().getCell(2,1);
        playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, player1, null, null, savedToken, 0, selectedCell, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());

        // Player 2 select token

        selectedToken = 2;
        playerAction = new PlayerAction(Action.TOKEN_SELECTED, player2, null, null, selectedToken, 0, null, null, false, null);
        controller.update(playerAction);

        System.out.println("Ask if want to use power or not");

        // Player 2 answer YES

        playerAction = new PlayerAction(Action.PROMETHEUS_ANSWER, player2, null, null, 0, 0, null, null, true, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());

        // Player 2 select his first build

        savedToken = 2;
        selectedCell = model.getBattlefield().getCell(2,0);
        playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, player2, null, null, savedToken, 0, selectedCell, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());

        // Player 2 select where to move

        savedToken = 2;
        selectedCell = model.getBattlefield().getCell(3,0);
        playerAction = new PlayerAction(Action.WHERE_TO_MOVE_SELECTED, player2, null, null, savedToken, 0, selectedCell, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());

        // Player 2 select where to build

        selectedCell = model.getBattlefield().getCell(2,0);
        playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, player2, null, null, savedToken, 0, selectedCell, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());

        // Player 1 select token

        selectedToken = 1;
        playerAction = new PlayerAction(Action.TOKEN_SELECTED, player1, null, null, selectedToken, 0, null, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());

        // Player 1 select where to move

        savedToken = 1;
        selectedCell = model.getBattlefield().getCell(2,0);
        playerAction = new PlayerAction(Action.WHERE_TO_MOVE_SELECTED, player1, null, null, savedToken, 0, selectedCell, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());

        // Player 1 select where to build

        selectedCell = model.getBattlefield().getCell(2,0);
        playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, player1, null, null, savedToken, 0, selectedCell, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());

        // Player 2 select token

        selectedToken = 2;
        playerAction = new PlayerAction(Action.TOKEN_SELECTED, player2, null, null, selectedToken, 0, null, null, false, null);
        controller.update(playerAction);

        System.out.println("Ask if want to use power or not");

        // Player 2 answer NO

        playerAction = new PlayerAction(Action.PROMETHEUS_ANSWER, player2, null, null, 0, 0, null, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());

        // Player 2 select where to move

        savedToken = 2;
        selectedCell = model.getBattlefield().getCell(2,1);
        playerAction = new PlayerAction(Action.WHERE_TO_MOVE_SELECTED, player2, null, null, savedToken, 0, selectedCell, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());

        // Player 2 select where to build

        selectedCell = model.getBattlefield().getCell(3,0);
        playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, player2, null, null, savedToken, 0, selectedCell, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());

        model.clearBattlefield();
    }
}


