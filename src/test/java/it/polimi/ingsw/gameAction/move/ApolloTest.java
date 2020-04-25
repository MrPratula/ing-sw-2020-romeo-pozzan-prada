package it.polimi.ingsw.gameAction.move;


import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Token;
import it.polimi.ingsw.model.TokenColor;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * APOLLO
 *
 * Your Worker may move into an opponent Worker’s space by forcing their Worker to the space yours just vacated.
 */
public class ApolloTest {

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

        List<Cell> validMoves = new ArrayList<>();
        MoveContext thisMove = new MoveContext(new ApolloMoves());
        validMoves = thisMove.executeValidMoves(selectedToken, null, enemyTokens, null, null, battlefield, null);

        Assert.assertTrue(validMoves.contains(battlefield.getCell(2,3)));
        Assert.assertTrue(selectedToken.getTokenPosition().getThereIsPlayer());
        Assert.assertTrue(enemyTokens.get(0).getTokenPosition().getThereIsPlayer());
    }






}
