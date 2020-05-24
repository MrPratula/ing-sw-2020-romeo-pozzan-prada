package it.polimi.ingsw.gui;

import it.polimi.ingsw.controller.CellHeightException;
import it.polimi.ingsw.controller.ReachHeightLimitException;
import it.polimi.ingsw.model.ModelUtils;
import it.polimi.ingsw.model.TokenColor;
import it.polimi.ingsw.utils.PlayerAction;
import it.polimi.ingsw.utils.ServerResponse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ButtonHandler implements ActionListener {

    private ModelUtils modelUtils;
    final private CellButton cellButton;
    private CellButton prevButton;
    private ImageIcon[] pics = new ImageIcon[8];

    public ButtonHandler(CellButton cellButton, ImageIcon[] pics, ModelUtils modelUtils) {
        this.cellButton = cellButton;
       // this.prevButton = prevButton;
        this.modelUtils = modelUtils;
        this.pics = pics;
    }

    /*     GETTER     */
    public CellButton getCellButton() {
        return cellButton;
    }

    public CellButton getPrevButton() {
        return prevButton;
    }

    @Override
    public void actionPerformed(ActionEvent clickedButtonEvent) {


        //  solo per compilare
        ServerResponse s = new ServerResponse(null,null);
        PlayerAction playerAction = new PlayerAction(null,null,null,null,0,0,null,null,false,"");

        //switch(clickedButtonEvent.getSource()):{}
        switch(playerAction.getAction()) {
            case ASK_FOR_BUILD: {
                try {
                    prevButton = (CellButton) clickedButtonEvent.getSource(); //dubbio
                    incrementHeight();
                } catch (CellHeightException | ReachHeightLimitException exception) {
                    exception.printStackTrace();
                }
            }
            case ASK_FOR_WHERE_TO_MOVE:{
                takeCareOfStartingPosition(prevButton);
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
                if(height==0) prevButton.setIcon(pics[5]);
                if(height==1) prevButton.setIcon(pics[6]);
                if(height==2) prevButton.setIcon(pics[7]);
                if(height==2) prevButton.setIcon(pics[8]);
                break;
            }
            case BLUE:{
                if(height==0) prevButton.setIcon(pics[9]);
                if(height==1) prevButton.setIcon(pics[10]);
                if(height==2) prevButton.setIcon(pics[11]);
                if(height==3) prevButton.setIcon(pics[12]);
                break;
            }
            case YELLOW:{
                if(height==0) prevButton.setIcon(pics[13]);
                if(height==1) prevButton.setIcon(pics[14]);
                if(height==2) prevButton.setIcon(pics[15]);
                if(height==3) prevButton.setIcon(pics[16]);
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
            JOptionPane.showMessageDialog(new JFrame(),"You can't build over a dome!","Error", JOptionPane.ERROR_MESSAGE);  //posso anche mettere un'immagine error
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
                    cellButton.setIcon(pics[1]);
                    cellButton.getCell().incrementHeight();
                    cellButton.setRolloverIcon(pics[18]);
                    //button.setPressedIcon(pics[1]);
                    //button.setDisabledIcon(pics[1]);
                }
                else if (cellButton.getCell().getHeight() == 1) {
                    cellButton.setIcon(pics[2]);
                    cellButton.setRolloverIcon(pics[19]);
                    cellButton.getCell().incrementHeight();
                }
                else if (cellButton.getCell().getHeight() == 2) {
                    cellButton.setIcon(pics[3]);
                    cellButton.setRolloverIcon(pics[20]);
                    cellButton.getCell().incrementHeight();
                }
                else if (cellButton.getCell().getHeight() == 3) {
                    cellButton.setIcon(pics[4]);
                    cellButton.setRolloverIcon(pics[21]);
                    cellButton.getCell().incrementHeight();
                }
            }
        }
    }


}


