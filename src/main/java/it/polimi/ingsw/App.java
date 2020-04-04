package it.polimi.ingsw;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {

        Model model = new Model();
        ModelView modelView = new ModelView();
        View view = new View();
        Controller controller = new Controller(model, view);

        view.addObserver(controller);
        model.addObserver(modelView);
        modelView.addObserver(view);


        List<Player> players = new ArrayList<Player>();

        Client client = new Client();
        players = client.recruitPlayers();

        Battlefield battlefield = client.startNewGame(players);

        // faccio il setup
        view.runSetUpToken(battlefield);

        // inizia la routine di gioco
        while (/*esistono giocatori*/){

            view.runGameRoutine(battlefield);


        }




    }
}
