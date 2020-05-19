package it.polimi.ingsw.gui;

import it.polimi.ingsw.controller.CellHeightException;
import it.polimi.ingsw.controller.ReachHeightLimitException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ButtonHandler implements ActionListener {

    CellButton button;
    // Array with all the pics
    ImageIcon[] pics = new ImageIcon[8];

    public ButtonHandler(CellButton button) {
        this.button= button;
        setPicture();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            incrementHeight();
        } catch (CellHeightException cellHeightException) {
            cellHeightException.printStackTrace();
        } catch (ReachHeightLimitException reachHeightLimitException) {
            reachHeightLimitException.printStackTrace();
        }
    }

    public void incrementHeight() throws CellHeightException, ReachHeightLimitException {
        if(button.cell.getHeight()==0){
            button.setIcon(pics[1]);
            button.cell.incrementHeight();
        }
        else if(button.cell.getHeight()==1){
            button.setIcon(pics[2]);
            button.cell.incrementHeight();
        }
        else if(button.cell.getHeight()==2){
            button.setIcon(pics[3]);
            button.cell.incrementHeight();
        }
        else if(button.cell.getHeight()==3){
            button.setIcon(pics[4]);
            button.cell.incrementHeight();
        }
    }

    private void setPicture(){
        pics[0] = new ImageIcon("C:\\Users\\ricca\\Desktop\\gui\\level0.png");         //level 0
        pics[1] = new ImageIcon("C:\\Users\\ricca\\Desktop\\gui\\level1.png");         //level 1
        pics[2] = new ImageIcon("C:\\Users\\ricca\\Desktop\\gui\\level2.png");         //level 2
        pics[3] = new ImageIcon("C:\\Users\\ricca\\Desktop\\gui\\level3.png");         //level 3
        pics[4] = new ImageIcon("C:\\Users\\ricca\\Desktop\\gui\\dome.png");           //dome
        pics[5] = new ImageIcon("C:\\Users\\ricca\\Desktop\\gui\\tokenRed.png");       //token red
        pics[6] = new ImageIcon("C:\\Users\\ricca\\Desktop\\gui\\tokenBlue.png");      //token blue
        pics[7] = new ImageIcon("C:\\Users\\ricca\\Desktop\\gui\\tokenYellow.png");    //token yellow
    }
}


