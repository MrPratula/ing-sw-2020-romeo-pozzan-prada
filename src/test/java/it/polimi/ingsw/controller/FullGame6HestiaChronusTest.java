package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.PlayerAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class FullGame6HestiaChronusTest {

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
     * Player 1 -> HESTIA
     * Player 2 -> CHRONUS
     */
    @Test
    void test6 () throws CellOutOfBattlefieldException, ImpossibleTurnException, ReachHeightLimitException, CellHeightException, WrongNumberPlayerException, IOException {

        player1God = GodCard.HESTIA;
        player2God = GodCard.CHRONUS;

        player1.setMyGodCard(player1God);
        player2.setMyGodCard(player2God);

        model.addGod(player1God);
        model.addGod(player2God);

        model.setTurn(TokenColor.RED);

        PlayerAction playerAction;
        Cell targetCell;
        Cell selectedCell;
        Cell otherCell;
        int selectedToken;
        int savedToken;

        // Player 1 place token

        targetCell = model.getBattlefield().getCell(2,1);
        playerAction = new PlayerAction(Action.TOKEN_PLACED, player1, null, null, 0, 0, targetCell, null, false, null);
        controller.update(playerAction);

        targetCell = model.getBattlefield().getCell(4,4);
        playerAction = new PlayerAction(Action.TOKEN_PLACED, player1, null, null, 0, 0, targetCell, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), null);

        // Player 2 place token

        targetCell = model.getBattlefield().getCell(1,1);
        playerAction = new PlayerAction(Action.TOKEN_PLACED, player2, null, null, 0, 0, targetCell, null, false, null);
        controller.update(playerAction);

        targetCell = model.getBattlefield().getCell(3,4);
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
        selectedCell = model.getBattlefield().getCell(3,1);
        playerAction = new PlayerAction(Action.WHERE_TO_MOVE_SELECTED, player1, null, null, savedToken, 0, selectedCell, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());

        // Player 1 select where to build 2 times

        selectedCell = model.getBattlefield().getCell(2,0);
        otherCell = model.getBattlefield().getCell(2,1);
        playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, player1, null, null, savedToken, 0, selectedCell, otherCell, true, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());

        // Player 2 select token

        selectedToken = 2;
        playerAction = new PlayerAction(Action.TOKEN_SELECTED, player2, null, null, selectedToken, 0, null, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());

        // Player 2 select where to move

        savedToken = 2;
        selectedCell = model.getBattlefield().getCell(2,0);
        playerAction = new PlayerAction(Action.WHERE_TO_MOVE_SELECTED, player2, null, null, savedToken, 0, selectedCell, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());

        // Player 2 select where to build

        selectedCell = model.getBattlefield().getCell(2,1);
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
        selectedCell = model.getBattlefield().getCell(3,2);
        playerAction = new PlayerAction(Action.WHERE_TO_MOVE_SELECTED, player1, null, null, savedToken, 0, selectedCell, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());

        // Player 1 select where to build 2 times

        selectedCell = model.getBattlefield().getCell(2,1);
        otherCell = model.getBattlefield().getCell(2,1);
        playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, player1, null, null, savedToken, 0, selectedCell, otherCell, true, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());

        // Player 2 select token

        selectedToken = 2;
        playerAction = new PlayerAction(Action.TOKEN_SELECTED, player2, null, null, selectedToken, 0, null, null, false, null);
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

        // Speed up the game to test chronus power and hestia dont use her

        model.getBattlefield().getCell(1,1).setHeight(3);
        model.getBattlefield().getCell(1,1).setIsDome();
        model.getBattlefield().getCell(1,2).setHeight(3);
        model.getBattlefield().getCell(1,2).setIsDome();
        model.getBattlefield().getCell(2,2).setHeight(3);
        model.getBattlefield().getCell(2,2).setIsDome();
        model.getBattlefield().getCell(4,2).setHeight(3);

        // Player 1 select token

        selectedToken = 1;
        playerAction = new PlayerAction(Action.TOKEN_SELECTED, player1, null, null, selectedToken, 0, null, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());

        // Player 1 select where to move

        savedToken = 1;
        selectedCell = model.getBattlefield().getCell(3,1);
        playerAction = new PlayerAction(Action.WHERE_TO_MOVE_SELECTED, player1, null, null, savedToken, 0, selectedCell, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());

        // Player 1 select where to build and chronus win

        selectedCell = model.getBattlefield().getCell(4,2);
        otherCell = null;
        playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, player1, null, null, savedToken, 0, selectedCell, null, false, null);
        controller.update(playerAction);

        view.printCLI(model.getCopy(), model.getValidCells());
    }
}
