package it.polimi.ingsw;

import it.polimi.ingsw.cli.GodCard;
import it.polimi.ingsw.cli.TokenColor;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.gui.ChooseGodCardWindow;
import it.polimi.ingsw.gui.GameFrame;
import it.polimi.ingsw.gui.SwingView;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.Pack;
import it.polimi.ingsw.utils.ServerResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientApp {

    public static void main(String[] args) {

        Client client = new Client("127.0.0.1", 12345);


        //  PROVA FINESTRA SCELTA SINGOLO GOD

        List<GodCard> allGodCards = new ArrayList<>();
        allGodCards.add(GodCard.CHRONUS);
        allGodCards.add(GodCard.LIMUS);
        allGodCards.add(GodCard.PROMETHEUS);
        Pack pack = new Pack(Action.SELECT_YOUR_GOD_CARD);
        pack.setGodCards(allGodCards);
        pack.setMessageOpponents("Another player is picking his GodCard, wait please...");

        ServerResponse serverResponse = new ServerResponse(TokenColor.RED, pack);

        new ChooseGodCardWindow(new SwingView(),serverResponse);



        /*    PROVA GAMEFRAME CON GODS

        List<GodCard> godsInGame = new ArrayList<>();
        godsInGame.add(GodCard.CHRONUS);
        godsInGame.add(GodCard.ATHENA);
        godsInGame.add(GodCard.MINOTAUR);

        new GameFrame(godsInGame);
        */


        //new test();


        try{
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }

    }
}
