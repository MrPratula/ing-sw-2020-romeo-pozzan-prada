package it.polimi.ingsw.model.gameAction.move;

import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.GodCard;
import it.polimi.ingsw.model.Token;
import java.util.ArrayList;
import java.util.List;


/**
 * MINOTAUR
 *
 * Your Worker move into an opponent Worker’s space, if their Worker can
 * be forced one space straight backwards to an unoccupied space at any level
 */
public class MinotaurMoves implements MoveBehavior{


    /**
     * It considers even the cells in which there is on opponent token, but
     * only in the case the cell behind him (in that direction) is unoccupied/not dome
     * @param selectedToken the token a player want to move,
     * @param otherToken the other player token.
     * @param enemyTokens a list of all enemy tokens.
     * @param enemyGodCards a list of all enemy god cards.
     * @param battlefield the model's battlefield.
     * @return a list of cell in which a player can build.
     * */
    @Override
    public List<Cell> computeValidMoves(Token selectedToken, Token otherToken, List<Token> enemyTokens, GodCard myGodCard, List<GodCard> enemyGodCards, Battlefield battlefield, List<Cell> moveToCheck) {

        List<Cell> allMoves = new ArrayList<>();
        int provX, provY;

        for (int i=-1; i<2; i++) {
            provX = selectedToken.getTokenPosition().getPosX() + i;
            for (int j = -1; j < 2; j++) {
                provY = selectedToken.getTokenPosition().getPosY() + j;
                if ((provX >= 0 && provX < 5) && (provY >= 0 && provY < 5) &&
                    (battlefield.getCell(provX, provY).getHeight() -
                     selectedToken.getTokenPosition().getHeight() <= 1) &&
                    (!battlefield.getCell(provX, provY).getIsDome())) {

                    // Compute if i can push a player
                    if(battlefield.getCell(provX,provY).getThereIsPlayer()){

                        // If the pushed cell is inside the battlefield
                        if (0<=provX+i && provX+i<5 && 0<=provY+j && provY+j<5) {
                            Cell nextOne = battlefield.getCell(provX + i, provY + j);

                            if(!(nextOne.getThereIsPlayer() || nextOne.getIsDome() || nextOne.getPosX()>4 || nextOne.getPosY()>4 || nextOne.getPosX()<0 || nextOne.getPosY()<0)){
                                allMoves.add(battlefield.getCell(provX, provY));
                            }
                        }
                    }
                    else{
                        allMoves.add(battlefield.getCell(provX, provY));
                    }
                }
            }
        }

        List<Cell> allMovesToReturn = new ArrayList<>(allMoves);

        // Remove both my tokens and not enemy tokens
        try{
            allMovesToReturn.remove(battlefield.getCell(selectedToken.getTokenPosition()));
        } catch (NullPointerException ignore){}
        try{
            allMovesToReturn.remove(battlefield.getCell(otherToken.getTokenPosition()));
        } catch (NullPointerException ignore){}

        return allMovesToReturn;
    }


    /**
     * Here is handled the push.
     * It set the selected token position to the target cell and, only if there is an
     * enemy token on the target cell, using the deltas of this move, it even moves that token
     * to the next position on that direction
     * @param targetCell the cell to be incremented.
     * @param battlefield the model's battlefield.
     *
     */
    @Override
    public void performMove(Token selectedToken, Token otherToken, List<Token> enemyTokens, Cell targetCell, List<GodCard> enemyGodCards, Battlefield battlefield){

        int deltaX, deltaY;
        Cell copy = selectedToken.getTokenPosition();
        if (targetCell.getThereIsPlayer()) {

            for(Token t: enemyTokens) {
                if (t.getTokenPosition().getPosX()==targetCell.getPosX() && t.getTokenPosition().getPosY()==targetCell.getPosY() ){
                    deltaX = targetCell.getPosX() - selectedToken.getTokenPosition().getPosX();
                    deltaY = targetCell.getPosY() - selectedToken.getTokenPosition().getPosY();
                    t.setOldHeight(battlefield.getCell(t.getTokenPosition()).getHeight());
                    t.setTokenPosition(battlefield.getCell(targetCell.getPosX()+deltaX,targetCell.getPosY()+deltaY));
                    battlefield.getCell(targetCell.getPosX()+deltaX,targetCell.getPosY()+deltaY).setOccupied();
                    battlefield.getCell(targetCell).setFree();
                    break;
                }
            }
        }
        selectedToken.setTokenPosition(targetCell);
        battlefield.getCell(targetCell).setOccupied();
        battlefield.getCell(copy.getPosX(),copy.getPosY()).setFree();
    }
}
