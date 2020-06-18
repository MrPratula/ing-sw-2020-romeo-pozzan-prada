package it.polimi.ingsw.gameAction.move;

import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.model.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * PROMETHEUS: If your Worker does not move up, it may build both before and after moving.
 */
public class PrometheusTest {

    Cell startingCell = null, targetCell = null, otherTokenCell = null;
    Token myToken = null, otherToken = null;
    Battlefield battlefield = null;
    List<Cell> validMoves = null;
    List<Token> enemyTokens = null;


    @BeforeEach
    public void init() throws CellOutOfBattlefieldException {

        battlefield = new Battlefield();
        validMoves = new ArrayList<>();
        enemyTokens = new ArrayList<>();

        startingCell = battlefield.getCell(2,2);
        myToken = new Token(TokenColor.BLUE);   //my token
        myToken.setTokenPosition(startingCell);
        startingCell.setOccupied();
        startingCell.setHeight(1);
    }


    /**
     * The overrided computeValidMoves of Prometheus doesn't
     * allow the token to move up, because in this case he
     * previously choose to build before (and after)
     */
    @Test
    public void PrometheusValidMovesNotUp() throws CellOutOfBattlefieldException {

        targetCell = battlefield.getCell(3,2);
        targetCell.setHeight(3);
        targetCell.setFree();

        Model.prometheusUsePower(true);

        MoveContext thisMove = new MoveContext(new PrometheusMove());
        validMoves = thisMove.executeValidMoves(myToken, null, null, GodCard.PROMETHEUS, null, battlefield, null);

        Assert.assertFalse(validMoves.contains(targetCell));
    }



}
