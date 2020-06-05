package it.polimi.ingsw;

import it.polimi.ingsw.cli.GodCard;
import it.polimi.ingsw.cli.TokenColor;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.gui.*;
import it.polimi.ingsw.utils.Action;
import it.polimi.ingsw.utils.Pack;
import it.polimi.ingsw.utils.ServerResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientApp {

    public static void main(String[] args) {

        Client client = new Client("127.0.0.1", 12345);


        //todo: non cancellare


              //  PROVA FINESTRA SCELTA SINGOLO GOD
        List<GodCard> allGodCards = new ArrayList<>();
        allGodCards.add(GodCard.CHRONUS);
        allGodCards.add(GodCard.LIMUS);
        allGodCards.add(GodCard.PROMETHEUS);
        Pack pack = new Pack(Action.SELECT_YOUR_GOD_CARD);
        pack.setGodCards(allGodCards);
        ServerResponse serverResponse = new ServerResponse(TokenColor.RED, pack);
        new ChooseGodCardWindow(new SwingView(),serverResponse);
        

        /*   //  PROVA FINESTRA SCELTA dei GOD
        List<GodCard> godsDeck = new ArrayList<>(Arrays.asList(GodCard.values()).subList(0, 14));
        Pack pack = new Pack(Action.CHOOSE_GOD_CARD_TO_PLAY);
        pack.setGodCards(godsDeck);
        pack.setNumberOfPlayers(3);
        ServerResponse serverResponse = new ServerResponse(TokenColor.RED, pack);
        new ChooseGodCardToPlayWindow(new SwingView(),serverResponse);
        */


        /*    //PROVA GAMEFRAME CON GODS
        List<GodCard> godsInGame = new ArrayList<>();
        godsInGame.add(GodCard.CHRONUS);
        godsInGame.add(GodCard.ATHENA);
        godsInGame.add(GodCard.MINOTAUR);
        new GameFrame(godsInGame,new ServerResponse(TokenColor.RED, new Pack(Action.CHOOSE_GOD_CARD_TO_PLAY)));
        */


        //new NickNameWindow(new SwingView());

        //new NumberOfPlayersWindow(new SwingView());

        //new WelcomeFrame(new SwingView());

            //PROVA test CON GODS
        /*List<GodCard> godsInGame = new ArrayList<>();
        godsInGame.add(GodCard.CHRONUS);
        godsInGame.add(GodCard.ATHENA);
        godsInGame.add(GodCard.MINOTAUR);
        new test(godsInGame,new ServerResponse(TokenColor.RED, new Pack(Action.ASK_FOR_BUILD)));
        */

        try{
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }

    }
}
