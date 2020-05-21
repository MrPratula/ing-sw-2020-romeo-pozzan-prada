package it.polimi.ingsw.gui;

import it.polimi.ingsw.model.Cell;

import javax.swing.*;
import java.awt.*;

public class CellButton extends JButton {

    Cell cell;

    public Cell getCell(){
        return cell;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        // super.paintComponent(g);
        // ^^ opzionale se vuoi visualizzare il bordo del bottone

        //Image img1 =  l'immagine che sta sotto
                //Image img2 = l'immagine che sta sopra

            // renderizza le due immagini, in ordine dal dietro verso il davanti
            g.drawImage(img1,
                    0, 0,    // <<- x, y in pixel della posizione dell'immagine
                    10, 10,  // <<- larghezza, altezza in pixel dell'immagine
                            // (si possono cambiare per ridimensionare l'immagine) null);
        g.drawImage(img2, 5, 5, 15, 15, null);
    }

}
