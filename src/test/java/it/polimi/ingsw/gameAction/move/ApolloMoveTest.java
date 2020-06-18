package it.polimi.ingsw.gameAction.move;


import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Token;
import it.polimi.ingsw.model.TokenColor;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * APOLLO
 *
 * Your Worker may move into an opponent Worker’s space by forcing their Worker to the space yours just vacated.
 */
public class ApolloMoveTest {

    Battlefield battlefield;
    Token selectedToken;
    List<Token> enemyTokens;


    /**
     * Create to-move token and enemy token.
     * Do not place them on the battlefield yet.
     */
    @BeforeEach void setUp() {
        battlefield = new Battlefield();
        selectedToken = new Token(TokenColor.RED);
        enemyTokens = new ArrayList<>();
        enemyTokens.add(new Token(TokenColor.BLUE));
    }


    /**
     * In the valid moves must be present even the opponent's token position.
     */
    @Test void apolloValidMoves() throws CellOutOfBattlefieldException {

        selectedToken.setTokenPosition(battlefield.getCell(2,2));
        battlefield.getCell(2,2).setOccupied();
        enemyTokens.get(0).setTokenPosition(battlefield.getCell(2,3));
        battlefield.getCell(2,3).setOccupied();

        List<Cell> validMoves;
        MoveContext thisMove = new MoveContext(new ApolloMoves());
        validMoves = thisMove.executeValidMoves(selectedToken, null, enemyTokens, null, null, battlefield, null);

        Assert.assertTrue(validMoves.contains(battlefield.getCell(2,3)));
        Assert.assertTrue(selectedToken.getTokenPosition().getThereIsPlayer());
        Assert.assertTrue(enemyTokens.get(0).getTokenPosition().getThereIsPlayer());
    }


    /**
     * Test a token swap with an enemy token on a different height level.
     */
    @Test void apolloPerformMove() throws CellOutOfBattlefieldException {

        selectedToken.setTokenPosition(battlefield.getCell(1,1));
        battlefield.getCell(1,1).setOccupied();

        enemyTokens.get(0).setTokenPosition(battlefield.getCell(2,2));
        battlefield.getCell(2,2).setOccupied();
        battlefield.getCell(2,2).setHeight(1);

        Cell targetCell = battlefield.getCell(2,2);

        MoveContext thisMove = new MoveContext(new ApolloMoves());
        thisMove.executeMove(selectedToken, null, enemyTokens, targetCell, null, battlefield);

        Assert.assertTrue(selectedToken.getTokenPosition().equals(battlefield.getCell(2, 2)));
        Assert.assertTrue(enemyTokens.get(0).getTokenPosition().equals(battlefield.getCell(1, 1)));
        Assert.assertTrue(battlefield.getCell(1,1).getThereIsPlayer());
        Assert.assertTrue(battlefield.getCell(2,2).getThereIsPlayer());
        Assert.assertEquals(0,selectedToken.getOldHeight());
        Assert.assertEquals(1,enemyTokens.get(0).getOldHeight());
    }
}
