package it.polimi.ingsw;

import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {

     //   Model model = new Model();
     //   ModelView modelView = new ModelView();
       // View view = new View();
       // Controller controller = new Controller(model, view);

     //   view.addObserver(controller);
     //   model.addObserver(modelView);
     //   modelView.addObserver(view);


        List<Player> players = new ArrayList<Player>();

        Client client = new Client();
        players = client.recruitPlayers();


        Battlefield battlefield = client.startNewGame(players);

        // inizia la routine di gioco
       // view.runSetUpToken(battlefield);

     //   while (true/*esistono giocatori*/){
       //     view.makeMove();
        //    view.checkWin();
         //   view.makeBuild();
        }


    //    view.runGameRoutine(battlefield);


    }

