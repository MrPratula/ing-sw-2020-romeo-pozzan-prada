package it.polimi.ingsw.gameAction.move;

import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.cli.Battlefield;
import it.polimi.ingsw.cli.Cell;
import it.polimi.ingsw.cli.GodCard;
import it.polimi.ingsw.cli.Token;
import java.util.ArrayList;
import java.util.List;


/**
 * MINOTAUR: Your Worker move into an opponent Worker’s space, if their Worker can
 * be forced one space straight backwards to an unoccupied space at any level
 */
public class MinotaurMoves implements MoveBehavior{


    /**
     * It considers even the cells in which there is on opponent token, but
     * only in the case the cell behind him (in that direction) is unoccupied
     */
    @Override
    public List<Cell> computeValidMoves(Token selectedToken, Token otherToken, List<Token> enemyTokens, GodCard myGodCard, List<GodCard> enemyGodCards, Battlefield battlefield, List<Cell> moveToCheck) throws CellOutOfBattlefieldException {

        List<Cell> allMoves = new ArrayList<Cell>();
        int provX, provY;

        for (int i=-1; i<2; i++) {                                                                      // ciclo di +-1 intorno alla posizione del token
            provX = selectedToken.getTokenPosition().getPosX() + i;                                     // per poter ottenere le 8 caselle in cui
            for (int j = -1; j < 2; j++) {                                                              // posso muovere
                provY = selectedToken.getTokenPosition().getPosY() + j;
                if ((provX >= 0 && provX < 5) && (provY >= 0 && provY < 5) &&                          // la cella provv è dentro le dimensioni del battlefield
                    (battlefield.getCell(provX, provY).getHeight() -                                   // l'altezza della cella provv -
                     selectedToken.getTokenPosition().getHeight() <= 1) &&                             // l'altezza del token <= 1
                    (!battlefield.getCell(provX, provY).getIsDome())) {                                // non deve essere una cupola


                    //MINOTAUR CHANGES HERE
                    if(battlefield.getCell(provX,provY).getThereIsPlayer() /*&& !(otherToken.getTokenPosition().getPosX()==provX && otherToken.getTokenPosition().getPosY()==provY)*/ ){           //se c'è un giocatore in questa cella E NON é IL MIO othertoken

                        Cell nextOne = battlefield.getCell(provX+i,provY+j);                                                                                               //cella in cui pusherò il token nemico, ricalcolata nella performMove
                        if(nextOne.getThereIsPlayer() || nextOne.getIsDome() || nextOne.getPosX()>4 || nextOne.getPosY()>4 || nextOne.getPosX()<0 || nextOne.getPosY()<0){            //se c'è un giocatore nella prossima, //or è dome, //or è fuori dalla battlefield
                            break; //insicuro sull'uscita dal loop                                                                      //non posso pushare, non aggiungo alle valid moevs
                        }
                        else{                                                                           //invece se posso pushare
                            allMoves.add(battlefield.getCell(provX, provY));                            //AGGIUNGO ALLA VALID MOVES LA CELLA DEL TOKEN NEMICO
                        }
                    }
                    else{
                        allMoves.add(battlefield.getCell(provX, provY));
                    }

                }
            }
        }

        List<Cell> allMovesToReturn = new ArrayList<>(allMoves);

        for (Cell validCell: allMoves) {
            try{
                allMovesToReturn.remove(battlefield.getCell(selectedToken.getTokenPosition()));     // rimuovo le posizioni dei miei token
            } catch (NullPointerException ignore){}
            try{
                allMovesToReturn.remove(battlefield.getCell(otherToken.getTokenPosition()));
            } catch (NullPointerException ignore){}
        }

        return allMovesToReturn;
    }



    /**
     * Here is handled the push.
     * It set the selected token position to the target cell and, only if there is an
     * enemy token on the target cell, using the deltas of this move, it even moves that token
     * to the next position on that direction
     * @param () the same as the simple perform move.
     */
    @Override
    public void performMove(Token selectedToken, Token otherToken, List<Token> enemyTokens, Cell targetCell, List<GodCard> enemyGodCards, Battlefield battlefield) throws CellOutOfBattlefieldException {

        int deltaX = 0, deltaY = 0;

        if (targetCell.getThereIsPlayer()) {

            for(Token t: enemyTokens) {
                if (t.getTokenPosition().getPosX()==targetCell.getPosX() && t.getTokenPosition().getPosY()==targetCell.getPosY() ){
                    deltaX = targetCell.getPosX() - selectedToken.getTokenPosition().getPosX();
                    deltaY = targetCell.getPosY() - selectedToken.getTokenPosition().getPosY();
                    t.setTokenPosition(battlefield.getCell(targetCell.getPosX()+deltaX,targetCell.getPosY()+deltaY));
                    battlefield.getCell(t.getTokenPosition()).setOccupied();
                    targetCell.setFree();
                    break;
                }
            }
        }

        selectedToken.setTokenPosition(targetCell);
        targetCell.setOccupied();
        battlefield.getCell(targetCell.getPosX()-deltaX,targetCell.getPosY()-deltaY).setFree();
    }



}
