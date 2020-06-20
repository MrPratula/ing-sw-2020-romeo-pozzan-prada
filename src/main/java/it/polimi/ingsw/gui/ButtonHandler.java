package it.polimi.ingsw.gui;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.TokenColor;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.Pack;
import it.polimi.ingsw.utils.PlayerAction;
import it.polimi.ingsw.utils.ServerResponse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class ButtonHandler implements ActionListener {

    final private CellButton cellButton;
    private ServerResponse currentServerResponse; //todo fixme
    private final SwingView swingView;
    private Boolean power;


    public ButtonHandler(CellButton cellButton, SwingView swingView) {
        this.cellButton = cellButton;
        this.swingView = swingView;
    }

    @Override
    public void actionPerformed(ActionEvent clickedButtonEvent) {

        currentServerResponse = swingView.getCurrentServerResponse();

        switch(currentServerResponse.getPack().getAction()) {

            case PLACE_YOUR_TOKEN:{
                try {
                    Cell targetCell = currentServerResponse.getPack().getModelCopy().getBattlefield().getCell(cellButton.getCell().getPosX(),cellButton.getCell().getPosY());
                    if(swingView.isFree(targetCell,currentServerResponse.getPack().getModelCopy())){
                        PlayerAction playerAction = new PlayerAction(Action.TOKEN_PLACED, swingView.getPlayer(), null, null, 0, 0, cellButton.getCell(), null, false, null);
                        try {
                            swingView.notifyClient(playerAction);
                            //mainframe.dispose();// non dovrei disposarlo
                        } catch (CellOutOfBattlefieldException | ReachHeightLimitException | CellHeightException | IOException | ImpossibleTurnException | WrongNumberPlayerException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(new JFrame(), "You can't place your token here! Already occupied!", "Error", JOptionPane.ERROR_MESSAGE, Pics.ERRORICON.getImageIcon());
                    }
                } catch (CellOutOfBattlefieldException e) {
                    e.printStackTrace();
                }
                break;
            }


            case ASK_FOR_SELECT_TOKEN:{
                int selectedToken = swingView.getToken(cellButton.getCell().getPosX(),cellButton.getCell().getPosY(), swingView.getPlayer());
                if(selectedToken != 0){
                    swingView.savedToken(selectedToken);
                    PlayerAction playerAction = new PlayerAction(Action.TOKEN_SELECTED, swingView.getPlayer(), null, null, selectedToken, 0, null, null, false, null);
                    try {
                        swingView.notifyClient(playerAction);
                        //mainframe.dispose();
                    } catch (CellOutOfBattlefieldException | ReachHeightLimitException | CellHeightException | IOException | ImpossibleTurnException | WrongNumberPlayerException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(new JFrame(), "You have to select one of your tokens!", "Error", JOptionPane.ERROR_MESSAGE, Pics.ERRORICON.getImageIcon());
                }
                break;
            }

            case ASK_FOR_WHERE_TO_MOVE:{
                Cell targetCell = swingView.getCell(cellButton.getCell().getPosX(), cellButton.getCell().getPosY(), currentServerResponse.getPack().getModelCopy().getBattlefield());



                if(targetCell != null){
                    PlayerAction playerAction = new PlayerAction(Action.WHERE_TO_MOVE_SELECTED, swingView.getPlayer(), null, null, swingView.getSavedToken(), 0, targetCell, null, false, null);
                    try {
                        swingView.notifyClient(playerAction);
                    } catch (CellOutOfBattlefieldException | ReachHeightLimitException | CellHeightException | IOException | ImpossibleTurnException | WrongNumberPlayerException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(new JFrame(), "You can't place your token here! Already occupied!", "Error", JOptionPane.ERROR_MESSAGE, Pics.ERRORICON.getImageIcon());
                }
                break;
            }

            case ASK_FOR_BUILD: {

                Pack pack = currentServerResponse.getPack();

                Cell targetCell = null;
                power = swingView.wantToUsePower();
                try {
                    targetCell = currentServerResponse.getPack().getModelCopy().getBattlefield().getCell(cellButton.getCell().getPosX(),cellButton.getCell().getPosY());

                    //Simple Build
                    if(!power) {
                        if (targetCell != null) {
                            PlayerAction playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, swingView.getPlayer(), null, null, swingView.getSavedToken(), 0, targetCell, null, false, null);
                            try {
                                swingView.notifyClient(playerAction);
                            } catch (CellOutOfBattlefieldException | ReachHeightLimitException | CellHeightException | IOException | ImpossibleTurnException | WrongNumberPlayerException e) {
                                e.printStackTrace();
                            }
                        } else {
                            JOptionPane.showMessageDialog(new JFrame(), "You can't place your token here! Already occupied!", "Error", JOptionPane.ERROR_MESSAGE, Pics.ERRORICON.getImageIcon());
                        }
                        break;
                    }
                    else {
                        switch (swingView.getPlayer().getMyGodCard()){
                            case DEMETER:{
                                swingView.buildGod(pack,targetCell);
                                break;
                            }
                            case HESTIA:{
                                if(swingView.notPerimeterCell(targetCell)) {
                                    swingView.buildGod(pack, targetCell);
                                    break;
                                }
                            }
                            case ATLAS:
                            case HEPHAESTUS:{
                                PlayerAction playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, swingView.getPlayer(), null, null, swingView.getSavedToken(), 0, targetCell, null, true, null);
                                try {
                                    swingView.notifyClient(playerAction);
                                } catch (CellOutOfBattlefieldException | ReachHeightLimitException | CellHeightException | IOException | ImpossibleTurnException | WrongNumberPlayerException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                } catch (CellOutOfBattlefieldException | IOException | WrongNumberPlayerException | CellHeightException | ImpossibleTurnException | ReachHeightLimitException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * It calculates which image to display, based on the params:
     * @param height
     * @param prevButton
     * @return
     */
    private void checkTokenColor(int height, CellButton prevButton/*, TokenColor tc*/) {

        TokenColor tc = TokenColor.RED; //per far compilare
        //ImageIcon toReturn;

        switch (tc){
            case RED:{
                if(height==0) prevButton.setIcon(Pics.LEVEL0TOKENRED.getImageIcon());
                if(height==1) prevButton.setIcon(Pics.LEVEL1TOKENRED.getImageIcon());
                if(height==2) prevButton.setIcon(Pics.LEVEL2TOKENRED.getImageIcon());
                if(height==3) prevButton.setIcon(Pics.LEVEL3TOKENRED.getImageIcon());
                break;
            }
            case BLUE:{
                if(height==0) prevButton.setIcon(Pics.LEVEL0TOKENBLUE.getImageIcon());
                if(height==1) prevButton.setIcon(Pics.LEVEL1TOKENBLUE.getImageIcon());
                if(height==2) prevButton.setIcon(Pics.LEVEL2TOKENBLUE.getImageIcon());
                if(height==3) prevButton.setIcon(Pics.LEVEL3TOKENBLUE.getImageIcon());
                break;
            }
            case YELLOW:{
                if(height==0) prevButton.setIcon(Pics.LEVEL0TOKENYELLOW.getImageIcon());
                if(height==1) prevButton.setIcon(Pics.LEVEL1TOKENYELLOW.getImageIcon());
                if(height==2) prevButton.setIcon(Pics.LEVEL2TOKENYELLOW.getImageIcon());
                if(height==3) prevButton.setIcon(Pics.LEVEL3TOKENYELLOW.getImageIcon());
                break;
            }
            default: break;
        }
    }



    /**
     * It sets che correct image of the height level,
     * before calling the real method that update the model.
     * @throws CellHeightException
     * @throws ReachHeightLimitException
     */
    public void incrementHeight() throws CellHeightException, ReachHeightLimitException {

        if(cellButton.getCell().getIsDome()){
            JOptionPane.showMessageDialog(new JFrame(),"You can't build over a dome!","Error", JOptionPane.ERROR_MESSAGE, Pics.ERRORICON.getImageIcon());  //posso anche mettere un'immagine error
        }

        else {
            //if i just selected to increment a cell occupied
            if (cellButton.getCell().getThereIsPlayer()) {
                // se il player corrent è ZEUS ed è il suo turno
//                    if(modelUtils.getMyGodCard().toString().equals("ZEUS") && modelUtils.getTurn().equals(p.getTokenColor()))  //prendo immagine con suo token piu alta e faccio
                // se non è zeus
                //    else  JOptionPane.showMessageDialog(new JFrame(),"You can't build here!","Error", JOptionPane.ERROR_MESSAGE);
            } else{
                if (cellButton.getCell().getHeight() == 0) {
                    cellButton.setIcon(Pics.LEVEL1.getImageIcon());
                    cellButton.getCell().incrementHeight();
                    cellButton.setRolloverIcon(Pics.LEVEL1TEXT.getImageIcon());
                    //button.setPressedIcon(pics[1]);
                    //button.setDisabledIcon(pics[1]);
                }
                else if (cellButton.getCell().getHeight() == 1) {
                    cellButton.setIcon(Pics.LEVEL2.getImageIcon());
                    cellButton.setRolloverIcon(Pics.LEVEL2TEXT.getImageIcon());
                    cellButton.getCell().incrementHeight();
                }
                else if (cellButton.getCell().getHeight() == 2) {
                    cellButton.setIcon(Pics.LEVEL3.getImageIcon());
                    cellButton.setRolloverIcon(Pics.LEVEL3TEXT.getImageIcon());
                    cellButton.getCell().incrementHeight();
                }
                else if (cellButton.getCell().getHeight() == 3) {
                    cellButton.setIcon(Pics.LEVEL3DOME.getImageIcon());
                    cellButton.setRolloverIcon(Pics.LEVEL3DOMETEXT.getImageIcon());
                    cellButton.getCell().incrementHeight();
                }
            }
        }
    }


}


