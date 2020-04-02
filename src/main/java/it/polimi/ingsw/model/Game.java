package it.polimi.ingsw.model;
import java.util.*;

public class Game {

    private Battlefield battlefield;
    private List<String> allGodCards;

    public Game (Battlefield battlefield) {
        this.battlefield = battlefield;
    }

    public Battlefield getBattlefield() {
        return battlefield;
    }

    public void setBattlefield(Battlefield battlefield) {
        this.battlefield = battlefield;
    }

    public List<String> getAllGodCards() {
        return allGodCards;
    }

    public void setAllGodCards(List<String> allGodCards) {
        this.allGodCards = allGodCards;
    }

    public void initGame(Battlefield battlefield) {

        createGodCards ();
        for(Player p: battlefield.getPlayers()) {
            assignGodCard(p);
        }

        System.out.println("Sta per iniziare una partita tra:");
        for (Player p: battlefield.getPlayers()) {
            p.print();
            System.out.println("");
        }

        for (Player player: battlefield.getPlayers()) {
            System.out.println(player.getNickname()+" in quale posizione vuoi mettere token1? x y");
            Scanner s = new Scanner(System.in);
            int valueX = s.nextInt();
            int valueY = s.nextInt();
            player.getToken1().setTokenPosition(battlefield.getCell(valueX, valueY));

            System.out.println(player.getNickname()+" in quale posizione vuoi mettere token2? x y");
            valueX = s.nextInt();
            valueY = s.nextInt();
            player.getToken1().setTokenPosition(battlefield.getCell(valueX, valueY));
            s.close();
        }
    }

    public void assignGodCard (Player player) {
        Random rand = new Random();
        int index = rand.nextInt(allGodCards.size());
        String god = allGodCards.get(index);
        player.setGod(god);
        allGodCards.remove(god);
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

    public void createGodCards () {
        List<String> allGodCardsName = new ArrayList<String>();
        allGodCardsName.add("Apollo");
        allGodCardsName.add("Artemis");
        allGodCardsName.add("Athena");
        allGodCardsName.add("Atlas");
        allGodCardsName.add("Demeter");
        allGodCardsName.add("Hephaestus");
        allGodCardsName.add("Minotaur");
        allGodCardsName.add("Pan");
        allGodCardsName.add("Prometheus");
        setAllGodCards(allGodCardsName);
    }
}







