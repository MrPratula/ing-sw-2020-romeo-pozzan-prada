package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.io.PrintStream;
import java.util.*;

public abstract class View extends Observable implements Observer {

    private Scanner scanner;
    private PrintStream outputStream;

    public void view () {
        this.scanner = new Scanner(System.in);
        outputStream = new PrintStream(System.out);
    }

    public void runSetUpToken(Battlefield battlefield) {
        while (true) {

            for (Player player: battlefield.getPlayers()) {
                Cell choose;
                while (true) {
                    System.out.println(player.getNickname()+" in quale posizione vuoi mettere token1?");
                    choose = player.askForCell(battlefield);
                    if (choose.isFree()) break;
                    else System.out.println("quella casella è occupata, scegline un'altra!");
                }
                player.getToken1().setTokenPosition(choose);

                while (true) {
                    System.out.println(player.getNickname()+" in quale posizione vuoi mettere token2?");
                    choose = player.askForCell(battlefield);
                    if (choose.isFree()) break;
                    else System.out.println("quella casella è occupata, scegline un'altra!");
                }
                player.getToken2().setTokenPosition(choose);
            }
        }
    }

    public void runGameRoutine (Battlefield battlefield) {

        Token token;
        while( battlefield.getPlayers().size()>=2 ){
            for(Player p : battlefield.getPlayers()){
                token = p.chooseToken();
                p.move(token, battlefield);
                if( p.checkWin(token) ){
                    System.out.println("Il Player" + p + "ha vinto!");
                    break;
                }
                p.build(token, battlefield);
            }
        }
    }


}

