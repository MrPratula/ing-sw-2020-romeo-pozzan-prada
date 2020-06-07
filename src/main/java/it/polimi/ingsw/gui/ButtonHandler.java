package it.polimi.ingsw.gui;

import it.polimi.ingsw.cli.Cell;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.cli.TokenColor;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.PlayerAction;
import it.polimi.ingsw.utils.ServerResponse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class ButtonHandler implements ActionListener {

    final private CellButton cellButton;
    private final ServerResponse serverResponse;
    private final SwingView view;
    private final JFrame mainframe;
    private final int posx;
    private final int posy;


    public ButtonHandler(CellButton cellButton,ServerResponse serverResponse, SwingView swingView, JFrame jframe, int posx, int posy) {
        this.serverResponse = serverResponse;
        this.cellButton = cellButton;
        this.view = swingView;
        this.mainframe = jframe;
        this.posx = posx;
        this.posy = posy;
    }


    @Override
    public void actionPerformed(ActionEvent clickedButtonEvent) {


        switch(serverResponse.getPack().getAction()) {

            case PLACE_YOUR_TOKEN:{
                try {
                    Cell targetCell = serverResponse.getPack().getModelCopy().getBattlefield().getCell(posx,posy);
                    if(view.isFree(targetCell,serverResponse.getPack().getModelCopy())){
                        PlayerAction playerAction = new PlayerAction(Action.TOKEN_PLACED, view.getPlayer(), null, null, 0, 0, cellButton.getCell(), null, false, null);
                        try {
                            view.notifyClient(playerAction);
                            mainframe.dispose();
                        } catch (CellOutOfBattlefieldException | ReachHeightLimitException | CellHeightException | IOException | ImpossibleTurnException | WrongNumberPlayerException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(new JFrame(), "You can't place your token here! Already occcupied!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (CellOutOfBattlefieldException e) {
                    e.printStackTrace();
                }
                break;
            }


            case ASK_FOR_SELECT_TOKEN:{
                int selectedToken = view.getToken(posx,posy,view.getPlayer());
                if(selectedToken != 0){
                    view.SavedToken(selectedToken);
                    PlayerAction playerAction = new PlayerAction(Action.TOKEN_SELECTED, view.getPlayer(), null, null, selectedToken, 0, null, null, false, null);
                    try {
                        view.notifyClient(playerAction);
                        mainframe.dispose();
                    } catch (CellOutOfBattlefieldException | ReachHeightLimitException | CellHeightException | IOException | ImpossibleTurnException | WrongNumberPlayerException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(new JFrame(), "You have to select a your token!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            }


            case ASK_FOR_BUILD: {
                try {
                    //prevButton = (CellButton) clickedButtonEvent.getSource(); //dubbio
                    incrementHeight();
                } catch (CellHeightException | ReachHeightLimitException exception) {
                    exception.printStackTrace();
                }
            }


            case ASK_FOR_WHERE_TO_MOVE:{
                //takeCareOfStartingPosition(prevButton);
                moveToken(); //System.out.println(clickedButtonEvent.getActionCommand());
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
     * It removes the token on the starting position
     * @param prevButton: button of the starting position
     */
    private void takeCareOfStartingPosition(CellButton prevButton) {

        switch (prevButton.getCell().getHeight()) {
            case 0: {
                checkTokenColor(0, prevButton/*tc*/);
                break;
            }
            case 1: {
                checkTokenColor(1, prevButton/*tc*/);
                break;
            }
            case 2: {
                checkTokenColor(2, prevButton/*tc*/);
                break;
            }
            case 3: {
                checkTokenColor(3, prevButton/*tc*/);
                break;
            }
            default: {
                System.out.println("\nERRORE SICURO\n");
                break;
            }
        }
    }



    /**
     * It moves the image of the token on the selected
     * position, modifing even the old one
     */
    private void moveToken() {

        //continuo

    }


    /**
     * It sets che correct image of the height level,
     * before calling the real method that update the model.
     * @throws CellHeightException
     * @throws ReachHeightLimitException
     */
    public void incrementHeight() throws CellHeightException, ReachHeightLimitException {

        if(cellButton.getCell().getIsDome()){
            JOptionPane.showMessageDialog(new JFrame(),"You can't build over a dome!","Error", JOptionPane.ERROR_MESSAGE,Pics.ERRORICON.getImageIcon());  //posso anche mettere un'immagine error
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


