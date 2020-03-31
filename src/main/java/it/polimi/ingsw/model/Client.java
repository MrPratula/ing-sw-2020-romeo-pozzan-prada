package it.polimi.ingsw.model;
import java.util.*;

public class Client {

    public List<Player> recruitPlayers() {

        List<String> playersName = new ArrayList<>();
        String player1Name;
        String player2Name;
        String player3Name;

        while (true) {

            System.out.println("Benvenuto su Santorini, come ti chiami?");
            Scanner s = new Scanner(System.in);
            player1Name = s.next();
            System.out.println("ciao " + player1Name + ". come si chiama il giocatore2?");
            player2Name = s.next();
            System.out.println("ciao " + player2Name + ".\n scrivi start per giocare in 2 contro " + player1Name + " o inserisci il nome del giocatore 3");
            String choice = s.next();

            if (choice.equals("start")) {
                System.out.println("avviata partita 2 giocatori: " + player1Name + " vs " + player2Name + "!\nChe vinca il migliore!");
                playersName.add(player1Name);
                playersName.add(player2Name);
                return init2Players(playersName);

            } else {
                player3Name = choice;
                System.out.println("vuoi avviare un match ffa tra " + player1Name + ", " + player2Name + " e " + player3Name + "? [yes][no]");
                String finalChoice = s.next();
                if (finalChoice.equals("yes")) {
                    System.out.println("avviata partita 3 giocatori " + player1Name + " contro " + player2Name + " contro " + player3Name + "!\nChe vinca il migliore!");
                    playersName.add(player1Name);
                    playersName.add(player2Name);
                    playersName.add(player3Name);
                    return init3Players(playersName);
                }
            }
        }
    }

    public List<Player> init2Players (List<String> playersName) {

        List<Player> players = new ArrayList<>();
        Player player1 = new Player();
        Player player2 = new Player();

        player1.setNickname(playersName.get(0));
        player2.setNickname(playersName.get(1));

        player1.setTokenColor(0);
        player2.setTokenColor(1);

        player1.setToken1(new Token(player1.getTokenColor()));
        player1.setToken2(new Token(player1.getTokenColor()));
        player2.setToken1(new Token(player2.getTokenColor()));
        player2.setToken2(new Token(player2.getTokenColor()));

        players.add(player1);
        players.add(player2);

        return players;
    }

    public List<Player> init3Players (List<String> playersName) {

        List<Player> players = new ArrayList<>();
        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player();

        player1.setNickname(playersName.get(0));
        player2.setNickname(playersName.get(1));
        player3.setNickname(playersName.get(2));

        player1.setTokenColor(0);
        player2.setTokenColor(1);
        player3.setTokenColor(2);

        player1.setToken1(new Token(player1.getTokenColor()));
        player1.setToken2(new Token(player1.getTokenColor()));
        player2.setToken1(new Token(player2.getTokenColor()));
        player2.setToken2(new Token(player2.getTokenColor()));
        player3.setToken1(new Token(player3.getTokenColor()));
        player3.setToken2(new Token(player3.getTokenColor()));

        players.add(player1);
        players.add(player2);
        players.add(player3);

        return players;
    }

    public void startNewGame (List<Player> players) {

        Battlefield battlefield = new Battlefield();
        battlefield.setPlayers(players);

        Game game = new Game(battlefield);

        game.initGame();
        game.startGameRoutine();

        // CONTINUE HERE!
    }
}
