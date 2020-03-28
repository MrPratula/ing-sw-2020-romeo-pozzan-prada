package it.polimi.ingsw.model;
import java.util.*;

public class Client {

    public void recruitPlayers() {

        String player1;
        String player2;
        String player3 = null;

        while (true) {

            System.out.println("Benvenuto a Santorini, come ti chiami?");
            Scanner s = new Scanner(System.in);
            player1 = s.next();
            System.out.println("ciao " + player1 + ". Come si chiama il giocatore2?");
            player2 = s.next();
            System.out.println("ciao " + player2 + ".\nstart per giocare in 2 contro " + player1 + " o inserisci il nome del giocatore 3");
            String choice = s.next();

            if (choice.equals("start")) {
                System.out.println("avviata partita 2 giocatori " + player1 + " contro " + player2 + "!\nche vinca il migliore!");
                return;

            } else {
                player3 = choice;
                System.out.println("vuoi avviare un match ffa tra " + player1 + " " + player2 + " e " + player3 + "? [yes][no]");
                String finalChoice = s.next();
                if (finalChoice.equals("yes")) {
                    System.out.println("avviata partita 3 giocatori " + player1 + " contro " + player2 + " contro " + player3 + "!\nche vinca il migliore!");
                    return;
                }
            }
        }
    }
}
