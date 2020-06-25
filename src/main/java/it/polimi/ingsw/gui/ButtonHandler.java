package it.polimi.ingsw.gui;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.GodCard;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.PlayerAction;
import it.polimi.ingsw.utils.ServerResponse;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;


/**
 * Class that handles all the mouse click and interprets
 * them by putting the correct image on the right spot
 */
public class ButtonHandler implements ActionListener {

    final private CellButton cellButton;
    private ServerResponse currentServerResponse;
    private final SwingView swingView;


    public ButtonHandler(CellButton cellButton, SwingView swingView) {
        this.cellButton = cellButton;
        this.swingView = swingView;
    }


    /**
     * Depending on the event, it makes the choice and send the
     * right Playeraction the the server
     * @param clickedButtonEvent clicked button
     */
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
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        final JDialog dialog = new JDialog();
                        dialog.setAlwaysOnTop(true);
                        JOptionPane.showMessageDialog(dialog, "You can't place your token here! Already occupied!", "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ImageIO.read(getClass().getResource(Pics.ERRORICON.getPath()))));
                    }
                } catch (Exception e) {
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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    final JDialog dialog = new JDialog();
                    dialog.setAlwaysOnTop(true);
                    try {
                        JOptionPane.showMessageDialog(dialog, "You have to select one of your tokens!", "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ImageIO.read(getClass().getResource(Pics.ERRORICON.getPath()))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }


            case ASK_FOR_WHERE_TO_MOVE:{
                Cell targetCell = swingView.getCell(cellButton.getCell().getPosX(), cellButton.getCell().getPosY(), currentServerResponse.getPack().getModelCopy().getBattlefield());

                //cell != null and it has to be one of the valid move
                if(targetCell != null && swingView.cellIsInValidCells(targetCell,currentServerResponse.getPack().getValidMoves())){
                    PlayerAction playerAction = new PlayerAction(Action.WHERE_TO_MOVE_SELECTED, swingView.getPlayer(), null, null, swingView.getSavedToken(), 0, targetCell, null, false, null);
                    try {
                        swingView.notifyClient(playerAction);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    final JDialog dialog = new JDialog();
                    dialog.setAlwaysOnTop(true);
                    try {
                        JOptionPane.showMessageDialog(dialog, "You can't place your token here! That cell is not a valid move!", "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ImageIO.read(getClass().getResource(Pics.ERRORICON.getPath()))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }


            case ASK_FOR_BUILD: {

                Cell targetCell;
                try {
                    targetCell = currentServerResponse.getPack().getModelCopy().getBattlefield().getCell(cellButton.getCell().getPosX(),cellButton.getCell().getPosY());
                    if(targetCell != null && swingView.cellIsInValidCells(targetCell,swingView.getCurrentValidBuilds())){
                        try {
                            if(!swingView.wantToUsePower()) {
                                if(swingView.getPlayer().getMyGodCard() == GodCard.HEPHAESTUS || swingView.getPlayer().getMyGodCard() == GodCard.ATLAS)
                                {
                                    new AskToUseTheGodsPower(swingView, currentServerResponse, targetCell);
                                }
                                else if (swingView.getPlayer().getMyGodCard() == GodCard.DEMETER || swingView.getPlayer().getMyGodCard() == GodCard.HESTIA) {
                                    swingView.setFirstCell(targetCell);
                                    List<Cell> validBuilds = swingView.newValidBuilds(targetCell);
                                    if (validBuilds != null) {
                                        new AskToUseTheGodsPower(swingView, currentServerResponse, targetCell);
                                    } else {
                                        PlayerAction playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, swingView.getPlayer(), null, null, swingView.getSavedToken(), 0, targetCell, null, false, null);
                                        swingView.notifyClient(playerAction);
                                    }
                                }
                                //In case the god's player isn't one of them (upper if).
                                else {
                                    PlayerAction playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, swingView.getPlayer(), null, null, swingView.getSavedToken(), 0, targetCell, null, false, null);
                                    swingView.notifyClient(playerAction);
                                }
                            }
                            else{
                                swingView.buildGod(currentServerResponse.getPack(),targetCell);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        final JDialog dialog = new JDialog();
                        dialog.setAlwaysOnTop(true);
                        JOptionPane.showMessageDialog(dialog, "You can't build here! That cell is not a valid build!", "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ImageIO.read(getClass().getResource(Pics.ERRORICON.getPath()))));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }



    /**
     * It sets che correct image of the height level,
     * before calling the real method that update the model.
     */
    public void incrementHeight() throws IOException { //fixme deleteme

        if(cellButton.getCell().getIsDome()){
            final JDialog dialog = new JDialog();
            dialog.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(dialog,"You can't build over a dome!","Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ImageIO.read(getClass().getResource(Pics.ERRORICON.getPath()))));  //posso anche mettere un'immagine error
        }

        else {
            //if i just selected to increment a cell occupied
            if (cellButton.getCell().getThereIsPlayer()) {
            } else{
                if (cellButton.getCell().getHeight() == 0) {
                    cellButton.setIcon(Pics.LEVEL1.getImageIcon());
                    cellButton.getCell().incrementHeight();
                    cellButton.setRolloverIcon(Pics.LEVEL1TEXT.getImageIcon());
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


