package it.polimi.ingsw;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
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

        // inizia la routine di gioco
        view.runSetUpToken(battlefield);

        while (/*esistono giocatori*/){
            view.makeMove();
            view.checkWin();
            view.makeBuild();
        }


        view.runGameRoutine(battlefield);


    }
}

/*

public class App
{
    public static void main( String[] args )
    {
        Model model = new Model();
        ModelView modelView = new ModelView();
        View view = new View();
        Controller controller = new Controller(model, view);

        view.addObserver(controller);
        model.addObserver(modelView);
        modelView.addObserver(view);

        view.run();

    }
}
*/