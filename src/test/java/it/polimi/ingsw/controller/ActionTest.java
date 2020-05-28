package it.polimi.ingsw.controller;


import it.polimi.ingsw.cli.*;
import it.polimi.ingsw.gameAction.Utility;
import it.polimi.ingsw.gameAction.build.BuildContext;
import it.polimi.ingsw.gameAction.build.SimpleBuild;
import it.polimi.ingsw.server.RemoteView;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.PlayerAction;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
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


    @Test
    void CHOSE_GOD_CARD_test() throws WrongNumberPlayerException, IOException, CellHeightException, ImpossibleTurnException, ReachHeightLimitException, CellOutOfBattlefieldException {

        String choice = "apollo";
        model.setTurn(TokenColor.RED);

        // First time the deck has 2 cards and one is removed
        PlayerAction playerAction1 = new PlayerAction(Action.CHOSE_GOD_CARD, player1, null, null, 0, 0, null, null, false, choice.toUpperCase());
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


    @Test
    void TOKEN_PLACED_test() {
        
    }











}
