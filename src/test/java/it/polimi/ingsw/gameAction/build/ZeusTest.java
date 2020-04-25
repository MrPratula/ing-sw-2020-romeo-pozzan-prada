/*


package it.polimi.ingsw.gameAction.build;

import it.polimi.ingsw.controller.CellHeightException;
import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.controller.ReachHeightLimitException;
import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Token;
import it.polimi.ingsw.model.TokenColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

//    public List<Cell> computeValidBuilds(Token selectedToken, Token otherToken, List<Token> enemyTokens, List<GodCard> enemyGodCards, Battlefield battlefield, List<Player> allPlayers) throws CellOutOfBattlefieldException
//    public PlayerAction(Action action, Player player, Player oppo1, Player oppo2, int tokenMain, int tokenOther, Cell first_cell, Cell second_cell , boolean doWantUsePower)

public class ZeusTest {

    Battlefield battlefield = null;
    Cell targetCell = null;
    int targetCellOldHeight;
    Token token = null;
    ZeusBuild zeusBuild = null;
    List<Cell> validBuilds = null;

    @BeforeEach
    public void init() throws CellOutOfBattlefieldException {
        List<Cell> validBuilds = new ArrayList<>();
        battlefield = new Battlefield();
        targetCell = battlefield.getCell(2,3);
        targetCell.setHeight(1);
        targetCellOldHeight = targetCell.getHeight();
        targetCell.setFree();
        targetCell.setThereIsPlayer();
        token = new Token(TokenColor.BLUE);
        token.setTokenPosition(targetCell);}

    @Test
    public void ZeusPerformBuild() throws CellHeightException, ReachHeightLimitException, CellOutOfBattlefieldException {

        assertEquals(targetCellOldHeight,1);

        validBuilds = zeusBuild.computeValidBuilds(token,null,null,null,battlefield,null);
        zeusBuild.performBuild(targetCell,null,battlefield);

        //targetCell.incrementHeight();
        //assertTrue(targetCell.getHeight()==2);

        assertEquals(targetCell.getHeight(),2);
        assertEquals(targetCell,token.getTokenPosition());
    }
}


*/