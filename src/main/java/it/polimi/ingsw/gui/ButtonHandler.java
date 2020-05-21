package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.SetUpDialog;
import it.polimi.ingsw.controller.CellHeightException;
import it.polimi.ingsw.controller.ReachHeightLimitException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ButtonHandler implements ActionListener {

    CellButton button, prevButton;
    ImageIcon[] pics = new ImageIcon[8];

    public ButtonHandler(CellButton button/*, CellButton prevButton*/, ImageIcon[] pics) {
        this.button=button;
       // this.prevButton = prevButton;
        this.pics = pics;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //moveToken(); System.out.println(e.getActionCommand());
        try{
            incrementHeight();
        } catch (CellHeightException | ReachHeightLimitException exception) {
        exception.printStackTrace();
    }



    }

    //TODO
    private void moveToken() {
        button.setIcon(pics[5]);
        //todo: previous pic to
    }

    public void incrementHeight() throws CellHeightException, ReachHeightLimitException {
       if(button.getCell().getIsDome()){
           JOptionPane.showMessageDialog(new JFrame(),"You can't build over a dome!","Error", JOptionPane.ERROR_MESSAGE);  //posso anche mettere un'immagine error
       }

       else {
           if (button.getCell().getHeight() == 0) {
               button.setIcon(pics[1]);
               //button.setRolloverIcon(pics[1]);
               //button.setPressedIcon(pics[1]);
               //button.setDisabledIcon(pics[1]);
               button.getCell().incrementHeight();
           } else if (button.getCell().getHeight() == 1) {
               button.setIcon(pics[2]);
               //button.setRolloverIcon(pics[2]);
               //button.setPressedIcon(pics[2]);
               //button.setDisabledIcon(pics[2]);
               button.getCell().incrementHeight();
           } else if (button.getCell().getHeight() == 2) {
               button.setIcon(pics[3]);
               //button.setRolloverIcon(pics[3]);
               //button.setPressedIcon(pics[3]);
               //button.setDisabledIcon(pics[3]);
               button.getCell().incrementHeight();
           } else if (button.getCell().getHeight() == 3) {
               button.setIcon(pics[4]);
               //button.setRolloverIcon(pics[4]);
               //button.setPressedIcon(pics[4]);
               //button.setDisabledIcon(pics[4]);
               button.getCell().incrementHeight();
           }
       }
    }


}


