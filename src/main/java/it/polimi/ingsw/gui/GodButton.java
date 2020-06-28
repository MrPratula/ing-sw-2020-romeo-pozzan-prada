package it.polimi.ingsw.gui;

import it.polimi.ingsw.model.GodCard;
import javax.swing.*;

/**
 * Button to select your GodCard
 */
public class GodButton extends JButton {

    /**
     * GodCard associated to the button
     */
    private GodCard godCard;

    public GodButton(GodCard godCard) {
        this.godCard = godCard;
    }

    //GETTER
    public GodCard getGodCard() {
        return godCard;
    }

    //SETTER
    public void setGodCard(GodCard godCard) {
        this.godCard = godCard;
    }
}
