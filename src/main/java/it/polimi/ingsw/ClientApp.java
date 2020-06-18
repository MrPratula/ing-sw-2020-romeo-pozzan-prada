package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.model.TokenColor;

import java.io.IOException;

public class ClientApp {

    public static void main(String[] args) throws CellOutOfBattlefieldException {

        Client client = new Client("127.0.0.1", 12345);

        /* //todo FIXARE ALIGNMENT LABEL
        Pack pack = new Pack(Action.CHOOSE_GOD_CARD_TO_PLAY);
        pack.setMessageInTurn("fineeeeeeeeee");
        new GameOverDialog(pack);
*/

        //new GodSelectedWindow("minotaur,prometheus");


        //JOptionPane.showMessageDialog(new JFrame(),new Pack(Action.SELECT_YOUR_GOD_CARD).getAction().getName().toUpperCase(),"AGAIN YOUR TURN, ", JOptionPane.WARNING_MESSAGE, Pics.INFORMATIONICON.getImageIcon());

        /*      //  PROVA FINESTRA SCELTA SINGOLO GOD
        List<GodCard> allGodCards = new ArrayList<>();
        allGodCards.add(GodCard.CHRONUS);
        allGodCards.add(GodCard.LIMUS);
        allGodCards.add(GodCard.PROMETHEUS);
        Pack pack = new Pack(Action.SELECT_YOUR_GOD_CARD);
        pack.setGodCards(allGodCards);
        ServerResponse serverResponse = new ServerResponse(TokenColor.RED, pack);
        new ChooseGodCardWindow(new SwingView(),serverResponse);
        */

        /*   //  PROVA FINESTRA SCELTA dei GOD
        List<GodCard> godsDeck = new ArrayList<>(Arrays.asList(GodCard.values()).subList(0, 14));
        Pack pack = new Pack(Action.CHOOSE_GOD_CARD_TO_PLAY);
        pack.setGodCards(godsDeck);
        pack.setNumberOfPlayers(3);
        ServerResponse serverResponse = new ServerResponse(TokenColor.RED, pack);
        new ChooseGodCardToPlayWindow(new SwingView(),serverResponse);
        */


        /*   //PROVA GAMEFRAME CON GODS
        List<GodCard> godsInGame = new ArrayList<>();
        godsInGame.add(GodCard.ZEUS);
        //godsInGame.add(GodCard.LIMUS);
        godsInGame.add(GodCard.ATHENA);
        Pack pa = new Pack(Action.CHOOSE_GOD_CARD_TO_PLAY);
        pa.setGodCards(godsInGame);
        new GameFrame(new ServerResponse(TokenColor.RED, pa),new SwingView());
*/


        //new NickNameWindow(new SwingView());

        //new NumberOfPlayersWindow(new SwingView());


        //new WelcomeFrame(new SwingView());

        //new AskPrometheusPowerFrame(new SwingView());


            //PROVA test CON GODS
        /*
        List<GodCard> godsInGame = new ArrayList<>();
        godsInGame.add(GodCard.CHRONUS);
        godsInGame.add(GodCard.ATHENA);
        godsInGame.add(GodCard.MINOTAUR);
        new test(godsInGame,new ServerResponse(TokenColor.RED, new Pack(Action.ASK_FOR_BUILD)));
        */

        //new AskToUseTheGodsPower(new SwingView(),new ServerResponse(TokenColor.RED, new Pack(Action.CHOOSE_GOD_CARD_TO_PLAY)));

        try{
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }

    }
}
