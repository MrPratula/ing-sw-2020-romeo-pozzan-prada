package it.polimi.ingsw.gui;

import it.polimi.ingsw.cli.GodCard;
import javax.swing.*;

public class GodButton extends JButton {

    private GodCard godCard;

    public GodButton(GodCard godCard) {
        this.godCard = godCard;
    }

    public GodCard getGodCard() {
        return godCard;
    }

    public void setGodCard(GodCard godCard) {
        this.godCard = godCard;
    }

}
