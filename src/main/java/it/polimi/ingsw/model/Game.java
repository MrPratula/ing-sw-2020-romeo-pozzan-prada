package it.polimi.ingsw.model;
import java.util.*;

public class Game {

    private Battlefield battlefield;
    private List<GodCard> allGodCards;

    public Game (Battlefield battlefield) {
        this.battlefield = battlefield;
    }

    public Battlefield getBattlefield() {
        return battlefield;
    }

    public void setBattlefield(Battlefield battlefield) {
        this.battlefield = battlefield;
    }

    public List<GodCard> getAllGodCards() {
        return allGodCards;
    }

    public void initGame() {
        //  CONTINUE HERE
    }

    public void startGameRoutine(Battlefield battlefield) {
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







