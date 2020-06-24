package it.polimi.ingsw.controller;


import it.polimi.ingsw.gameAction.Utility;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.PlayerAction;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

/**
 * This test will manage to test all the possible action that can be received by a controller
 * and handle them.
 * Each action is tested with the most significant god card, not all of them.
 * The single god card is tested in it's own section.
 */
public class ActionTest {

    Controller controller;
    Model model;
    Player player1, player2;

    GodCard player1God;
    GodCard player2God;

    List<Player> allPlayers;
    List<GodCard> allGodCards;


    /**
     * It is created a whole game space server-side only.
     * The functioning is checked inspecting the message that the model send to the remote view.
     */
    @BeforeEach
    void setUp() {

        player1 = new Player("Mario", TokenColor.RED);
        player2 = new Player("Luigi", TokenColor.BLUE);

        player1God = GodCard.APOLLO;
        player2God = GodCard.PAN;

        model = new Model();
        controller = new Controller(model);

        model.addPlayer(player1);
        model.addPlayer(player2);

        model.addGod(player1God);
        model.addGod(player2God);

        allPlayers = model.getAllPlayers();
        allGodCards = model.getGodCards(allPlayers);
    }


    /**
     * The players should write some god names and then the corresponding god card
     * is assigned to that player till there are no more players without a god card
     */
    @Test
    void CHOSE_GOD_CARD_test() throws IOException {

        String choice = "apollo";
        model.setTurn(TokenColor.RED);

        // First time the deck has 2 cards and one is removed
        PlayerAction playerAction1 = new PlayerAction(Action.CHOSE_GOD_CARD, player1, null, null, 0, 0, null, null, false, choice.toUpperCase());
        controller.update(playerAction1);
        model.updateTurn();
        controller.update(playerAction1);

        Assert.assertFalse(allGodCards.contains(GodCard.APOLLO));
        Assert.assertEquals(player1.getMyGodCard(), GodCard.APOLLO);

        choice = "pan";
        PlayerAction playerAction2 = new PlayerAction(Action.CHOSE_GOD_CARD, player2, null, null, 0, 0, null, null, false, choice.toUpperCase());
        controller.update(playerAction2);

        allGodCards = model.getGodCards(allPlayers);

        Assert.assertTrue(allGodCards.contains(GodCard.PAN));
        Assert.assertTrue(allGodCards.contains(GodCard.APOLLO));
        Assert.assertEquals(player2.getMyGodCard(), GodCard.PAN);
    }


    /**
     * Place a token in 1,1 and 2,2 during set up phase,
     * than player2 do the same in 3,3 and 4,4.
     */
    @Test
    void TOKEN_PLACED_test() throws IOException {

        model.setTurn(TokenColor.RED);

        // Player1 choose 1,1
        Cell targetCell = model.getBattlefield().getCell(1,1);
        PlayerAction playerAction = new PlayerAction(Action.TOKEN_PLACED, player1, null, null, 0, 0, targetCell, null, false, null);
        controller.update(playerAction);

        Assert.assertTrue(model.getBattlefield().getCell(1,1).getThereIsPlayer());
        Assert.assertEquals(player1.getToken1().getTokenPosition().getPosX(), 1);
        Assert.assertEquals(player1.getToken1().getTokenPosition().getPosY(), 1);

        // Player1 choose 2,2
        targetCell = model.getBattlefield().getCell(2,2);
        playerAction = new PlayerAction(Action.TOKEN_PLACED, player1, null, null, 0, 0, targetCell, null, false, null);
        controller.update(playerAction);

        Assert.assertTrue(model.getBattlefield().getCell(2,2).getThereIsPlayer());
        Assert.assertEquals(player1.getToken2().getTokenPosition().getPosX(), 2);
        Assert.assertEquals(player1.getToken2().getTokenPosition().getPosY(), 2);

        // Player2 choose 3,3
        targetCell = model.getBattlefield().getCell(3,3);
        playerAction = new PlayerAction(Action.TOKEN_PLACED, player2, null, null, 0, 0, targetCell, null, false, null);
        controller.update(playerAction);

        Assert.assertTrue(model.getBattlefield().getCell(3,3).getThereIsPlayer());
        Assert.assertEquals(player2.getToken1().getTokenPosition().getPosX(), 3);
        Assert.assertEquals(player2.getToken1().getTokenPosition().getPosY(), 3);

        // Player2 choose 4,4
        targetCell = model.getBattlefield().getCell(4,4);
        playerAction = new PlayerAction(Action.TOKEN_PLACED, player2, null, null, 0, 0, targetCell, null, false, null);
        controller.update(playerAction);

        Assert.assertTrue(model.getBattlefield().getCell(4,4).getThereIsPlayer());
        Assert.assertEquals(player2.getToken2().getTokenPosition().getPosX(), 4);
        Assert.assertEquals(player2.getToken2().getTokenPosition().getPosY(), 4);

        // Uncomment the following lines to see a cli printed of the generated battlefield
        /*
        View view = new View();
        ModelUtils modelCopy = model.getCopy();
        view.printCLI(modelCopy, null);
         */
    }


    /**
     * Test the select token phase.
     * There are no asset because nothing change server side, but it is tested
     * the functioning.
     * The correctness of the methods are tested in the gameAction section
     */
    @Test
    void TOKEN_SELECTED_test() throws IOException {

        model.setTurn(TokenColor.RED);
        model.setBattlefield(Utility.setUpForTest2());
        player1.setMyGodCard(player1God);
        player2.setMyGodCard(player2God);

        player1.getToken1().setTokenPosition(model.getBattlefield().getCell(1,2));
        player1.getToken2().setTokenPosition(model.getBattlefield().getCell(1,3));
        player2.getToken1().setTokenPosition(model.getBattlefield().getCell(2,4));
        player2.getToken2().setTokenPosition(model.getBattlefield().getCell(3,3));

        // Player1 want to move token in 1,1
        int selectedToken = 1;
        PlayerAction playerAction = new PlayerAction(Action.TOKEN_SELECTED, player1, null, null, selectedToken, 0, null, null, false, null);
        controller.update(playerAction);

        // If player1 has prometheus the response is different
        player1.setMyGodCard(GodCard.PROMETHEUS);
        playerAction = new PlayerAction(Action.TOKEN_SELECTED, player1, null, null, selectedToken, 0, null, null, false, null);
        controller.update(playerAction);
        Assert.assertEquals(1, model.getPrometheusToken().getTokenPosition().getPosX());
        Assert.assertEquals(2, model.getPrometheusToken().getTokenPosition().getPosY());
    }


    /**
     * When a player has prometheus, after he select a token
     * he is asked for the will to use his power or not.
     * It is tested when it is YES or NO.
     */
    @Test
    void PROMETHEUS_ANSWER_test() throws IOException{

        model.setTurn(TokenColor.RED);
        model.setBattlefield(Utility.setUpForTest2());

        player1.getToken1().setTokenPosition(model.getBattlefield().getCell(1,2));
        model.getBattlefield().getCell(1,2).setOccupied();
        player1.getToken2().setTokenPosition(model.getBattlefield().getCell(1,3));
        model.getBattlefield().getCell(1,3).setOccupied();
        player2.getToken1().setTokenPosition(model.getBattlefield().getCell(2,4));
        model.getBattlefield().getCell(2,4).setOccupied();
        player2.getToken2().setTokenPosition(model.getBattlefield().getCell(3,3));
        model.getBattlefield().getCell(3,3).setOccupied();

        player1.setMyGodCard(GodCard.PROMETHEUS);
        model.setPrometheusToken(player1.getToken1());

        // The case when prometheus answer is YES
        PlayerAction playerAction = new PlayerAction(Action.PROMETHEUS_ANSWER, player1, null, null, 0, 0, null, null, true, null);
        controller.update(playerAction);

        // The case when prometheus answer is NO
        playerAction = new PlayerAction(Action.PROMETHEUS_ANSWER, player1, null, null, 0, 0, null, null, false, null);
        controller.update(playerAction);
    }


    @Test
    void WHERE_TO_MOVE_SELECTED_test() throws IOException {

        model.setTurn(TokenColor.RED);
        model.setBattlefield(Utility.setUpForTest2());

        player1.getToken1().setTokenPosition(model.getBattlefield().getCell(1,2));
        model.getBattlefield().getCell(1,2).setOccupied();
        player1.getToken2().setTokenPosition(model.getBattlefield().getCell(1,3));
        model.getBattlefield().getCell(1,3).setOccupied();
        player2.getToken1().setTokenPosition(model.getBattlefield().getCell(2,4));
        model.getBattlefield().getCell(2,4).setOccupied();
        player2.getToken2().setTokenPosition(model.getBattlefield().getCell(3,3));
        model.getBattlefield().getCell(3,3).setOccupied();

        player1.setMyGodCard(player1God);
        player2.setMyGodCard(player2God);

        int savedToken = 1;
        Cell selectedCell = model.getBattlefield().getCell(0,1);

        PlayerAction playerAction = new PlayerAction(Action.WHERE_TO_MOVE_SELECTED, player1, null, null, savedToken, 0, selectedCell, null, false, null);
        controller.update(playerAction);

        Assert.assertEquals(0, player1.getToken1().getTokenPosition().getPosX());
        Assert.assertEquals(1, player1.getToken1().getTokenPosition().getPosY());
        Assert.assertFalse(model.getBattlefield().getCell(1,2).getThereIsPlayer());
        Assert.assertTrue(model.getBattlefield().getCell(0,1).getThereIsPlayer());
    }

    @Test
    void WHERE_TO_BUILD_SELECTED_test() throws IOException {

        model.setTurn(TokenColor.RED);
        model.setBattlefield(Utility.setUpForTest2());

        player1.getToken1().setTokenPosition(model.getBattlefield().getCell(0,1));
        model.getBattlefield().getCell(1,2).setOccupied();
        player1.getToken2().setTokenPosition(model.getBattlefield().getCell(1,3));
        model.getBattlefield().getCell(1,3).setOccupied();
        player2.getToken1().setTokenPosition(model.getBattlefield().getCell(2,4));
        model.getBattlefield().getCell(2,4).setOccupied();
        player2.getToken2().setTokenPosition(model.getBattlefield().getCell(3,3));
        model.getBattlefield().getCell(3,3).setOccupied();

        player1.setMyGodCard(player1God);
        player2.setMyGodCard(player2God);

        int savedToken = 1;
        Cell selectedCell = model.getBattlefield().getCell(1,1);

        PlayerAction playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, player1, null, null, savedToken, 0, selectedCell, null, false, null);
        controller.update(playerAction);

        Assert.assertEquals(model.getTurn(), TokenColor.BLUE);
        Assert.assertEquals(1, model.getBattlefield().getCell(1,1).getHeight());
    }


    @Test
    void WHERE_TO_BUILD_SELECTED_hestiaTest() throws IOException {

        model.setTurn(TokenColor.RED);
        model.setBattlefield(Utility.setUpForTest2());

        player1.getToken1().setTokenPosition(model.getBattlefield().getCell(0,1));
        model.getBattlefield().getCell(1,2).setOccupied();
        player1.getToken2().setTokenPosition(model.getBattlefield().getCell(1,3));
        model.getBattlefield().getCell(1,3).setOccupied();
        player2.getToken1().setTokenPosition(model.getBattlefield().getCell(2,4));
        model.getBattlefield().getCell(2,4).setOccupied();
        player2.getToken2().setTokenPosition(model.getBattlefield().getCell(3,3));
        model.getBattlefield().getCell(3,3).setOccupied();

        player1.setMyGodCard(GodCard.HESTIA);
        player2.setMyGodCard(player2God);

        int savedToken = 1;
        Cell secondCell = model.getBattlefield().getCell(1,1);
        Cell selectedCell = model.getBattlefield().getCell(0,0);

        PlayerAction playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, player1, null, null, savedToken, 0, selectedCell, secondCell, true, null);
        controller.update(playerAction);

        Assert.assertEquals(model.getTurn(), TokenColor.BLUE);
        Assert.assertEquals(1, model.getBattlefield().getCell(1,1).getHeight());
        Assert.assertEquals(1, model.getBattlefield().getCell(0,0).getHeight());
    }
}
