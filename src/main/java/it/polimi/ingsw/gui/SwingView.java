package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.*;
import it.polimi.ingsw.utils.Action;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class SwingView extends View {

    private JFrame mainFrame;        //1
    private JPanel mainPanel;        //2
    private JButton playButton;      //3.1
    private JLabel logoImage;

    private Player player;
    private int savedToken;

    public JFrame getMainFrame() {
        return mainFrame;
    }

    /**
     * Constructor of the client view with Swing GUI
     */
    public SwingView(/*Player p*/){

        //this.player = p;
        mainFrame = new JFrame("Santorini");
        mainFrame.setResizable(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);

        logoImage = new JLabel(new ImageIcon(new File("./src/main/images/utils/Santorini.png").getAbsolutePath()));

        playButton = new JButton(new ImageIcon(new File("./src/main/images/utils/buttonPlay.png").getAbsolutePath()));
        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NickNameWindow(SwingView.this);
                mainFrame.dispose(); //close mainFrame.
            }
        });

        mainPanel.add(logoImage, BorderLayout.CENTER);
        mainPanel.add(playButton, BorderLayout.SOUTH);
        mainFrame.add(mainPanel, BorderLayout.CENTER);

        mainFrame.pack();
        mainFrame.setVisible(true);
    }


    protected Player getPlayer(){
        return player;
    }


    /**
     * When needed, it returns the token that has just been selected from the user,
     * and even the not selected one (it can be useful).
     * @param serverResponse: the response passed through the observers from the server.
     * @param posX: the first input of the user, i.e. the column of the battlefield.
     * @param posY: the second input of the user, i.e. the row of the battlefield.
     * @return a list containing the selected and the not selected token.
     */
    public List<Token> computeTokens(ServerResponse serverResponse, int posX, int posY){

        Token selectedToken, otherToken;
        if (getPlayer().getToken1().getTokenPosition().getPosX() == posX &&
                getPlayer().getToken1().getTokenPosition().getPosY() == posY) {
            selectedToken = getPlayer().getToken1();
            otherToken = getPlayer().getToken2();
        }  else
        if (getPlayer().getToken2().getTokenPosition().getPosX() == posX &&
                getPlayer().getToken2().getTokenPosition().getPosY() == posY) {
            selectedToken = getPlayer().getToken2();
            otherToken = getPlayer().getToken1();
        } else {
            selectedToken = null;
            otherToken = null;
        }

        List<Token> tokens = new ArrayList<>();
        tokens.add(selectedToken);
        tokens.add(otherToken);
        return tokens;
    }

    /**
     * When needed, it returns the opponent players(1 or 2) of the player associated to this view.
     * @param serverResponse: the response passed throught the observers from the server.
     * @return a list containing the opponent players.
     */
    public List<Player> computeOpponentPlayers (ServerResponse serverResponse){

        int numberOfPlayers = serverResponse.getPack().getModelCopy().getAllPlayers().size();
        List<Player> players = serverResponse.getPack().getModelCopy().getAllPlayers();
        Player opp1 = null, opp2 = null;
        if(numberOfPlayers==2){
            if(players.get(0).equals(getPlayer()))  opp1 = players.get(1);
            else opp1 = players.get(0);
        }
        if(numberOfPlayers==3){                             ///si potrebbe fare meglio
            for(int i=0; i<3; i++){
                if(players.get(i).equals(getPlayer())){
                    opp1 = players.get(1);
                    opp2 = players.get(2);
                }
            }
        }

        List<Player> opponents = new ArrayList<>();
        opponents.add(opp1);
        opponents.add(opp2);
        return opponents;
    }


    public void notifyClient(PlayerAction playerAction) throws CellOutOfBattlefieldException, ReachHeightLimitException, CellHeightException, IOException, ImpossibleTurnException, WrongNumberPlayerException {
        notify(playerAction);
    }


    /**
     * This method moves the mechanic of the game: it receives the
     * response from the server, and creates the respective action of the player.
     * @param serverResponse: response from the server containing al the necessary informations
     */
    @Override
    public void update(ServerResponse serverResponse) throws ImpossibleTurnException, IOException, CellHeightException, WrongNumberPlayerException, ReachHeightLimitException, CellOutOfBattlefieldException {

        switch (serverResponse.getPack().getAction()) {


            // The first time a player connects it is asked for his name
            // This continue till the name is valid
            case WELCOME:{

                NickNameWindow s = new NickNameWindow(this);
                break;
            }
            case INVALID_NAME: {

                //probably dovrei prenderlo dalla classe
                JOptionPane.showMessageDialog(new JFrame(),"Your name is invalid","Error", JOptionPane.ERROR_MESSAGE);  //posso anche mettere un'immagine error
                NickNameWindow s = new NickNameWindow(this);
                PlayerAction playerAction = new PlayerAction(Action.MY_NAME, null, null, null,0, 0, null, null, false, s.getNicknameTextField().getText());
                notifyClient(playerAction);
                break;
            }

            // Only the first player is asked for how many player he wants to play with
            // This value is stored in token main field of PlayerAction
            case HOW_MANY_PLAYERS:
            case WRONG_NUMBER_OF_PLAYER: {

                NumberOfPlayersWindow n = new NumberOfPlayersWindow(this);
                PlayerAction playerAction = new PlayerAction(Action.NUMBER_OF_PLAYERS, null, null, null, (Integer)n.getNumberOfPlayersBox().getSelectedItem(), 0, null, null, false, null);
                notifyClient(playerAction);
                break;
            }

            // Just tell the client the server is not popped
            // When the first player answer how much players there will be, waiting for the players to connect
            case WAIT_OTHER_PLAYERS_TO_CONNECT:
            case NUMBER_RECEIVED: {
                System.out.println(serverResponse.getPack().getAction().toString());
                break;
            }

            //The first choice is send by the server and contains player data
            case SELECT_YOUR_GOD_CARD_FROM_SERVER: {

                this.player = serverResponse.getPack().getPlayer();
                List<GodCard> godInGame = serverResponse.getPack().getGodCards();

                ChooseGodCardWindow c = new ChooseGodCardWindow(this.mainFrame,godInGame);
//c.getButtonGroup().getSelection().get;
                //PlayerAction playerAction = new PlayerAction(Action.CHOSE_GOD_CARD, this.player, null, null, 0, 0, null, null, false, c..toUpperCase());
                //notifyClient(playerAction);
                player = serverResponse.getPack().getPlayer();
                break;
            }

            // The second player receive the god choice message, the 2nd and 3rd receive this with player data
            case WAIT_AND_SAVE_PLAYER_FROM_SERVER:{
                player = serverResponse.getPack().getPlayer();
                //TODO System.out.println(serverResponse.getPack().getAction().toString());
                break;
            }


            // TILL NOW ALL THE MESSAGES ARE BROADCAST-RECEIVED

            case SELECT_YOUR_GOD_CARD:{

                PlayerAction playerAction;

                Pack pack = serverResponse.getPack();

                // If the player is not in turn he is just notified to wait
                if (!player.getTokenColor().equals(serverResponse.getTurn())){
                    System.out.println(pack.getMessageOpponents());
                }
                // else he has to pick his god card
                else {
                    List<GodCard> godInGame = serverResponse.getPack().getGodCards();
                    boolean needToLoop = true;
                    String choice;

                    while (needToLoop) {
                        System.out.println(serverResponse.getPack().getMessageInTurn());
                        Scanner scanner = new Scanner(System.in);

                        // Check if the input is a valid string
                        try {
                            choice = scanner.nextLine();
                        } catch (InputMismatchException exception) {
                            choice = "error";
                        }

                        // Check if the string is a valid god name
                        for (GodCard god: godInGame) {
                            if (choice.toUpperCase().equals(god.name().toUpperCase())){
                                System.out.println("Ohhh good choice!");
                                playerAction = new PlayerAction(Action.CHOSE_GOD_CARD, this.player, null, null, 0, 0, null, null, false, choice.toUpperCase());
                                notifyClient(playerAction);
                                needToLoop = false;
                            }
                        }
                    }

                }
                break;
            }

            // A player has to place his token, other wait
            case PLACE_YOUR_TOKEN:{

                PlayerAction playerAction;

                Pack pack = serverResponse.getPack();
                printCLI(pack.getModelCopy(), null);

                if (!player.getTokenColor().equals(serverResponse.getTurn())){
                    System.out.println(pack.getMessageOpponents());
                }
                else {
                    boolean needToLoop = true;
                    Scanner scanner = new Scanner(System.in);
                    String message;
                    String[] messageParsed;
                    Cell targetCell = null;

                    while (needToLoop) {

                        System.out.println(pack.getAction().toString());
                        printCLI(pack.getModelCopy(), null);

                        try {
                            message = scanner.nextLine();
                            messageParsed = message.split(",");

                            targetCell = pack.getModelCopy().getBattlefield().getCell(Integer.parseInt(messageParsed[0]), Integer.parseInt(messageParsed[1]));

                        } catch (Exception exception) {
                            targetCell = null;
                        }

                        if (targetCell!= null) {
                            needToLoop = false;
                        }
                    }
                    playerAction = new PlayerAction(Action.TOKEN_PLACED, this.player, null, null, 0, 0, targetCell, null, false, null);
                    notifyClient(playerAction);
                }
                break;
            }

            case PLAYER_LOST:
            case TOKEN_NOT_MOVABLE:
            case ASK_FOR_SELECT_TOKEN: {

                PlayerAction playerAction = null;
                Pack pack = serverResponse.getPack();

                // Update the player
                this.player = pack.getPlayer();

                if (!player.getTokenColor().equals(serverResponse.getTurn())){
                    printCLI(pack.getModelCopy(), null);
                    System.out.println(pack.getMessageOpponents());
                }
                else {

                    if (pack.getMessageInTurn() != null){
                        System.out.println(pack.getMessageInTurn());
                    }

                    boolean needToLoop = true;

                    while (needToLoop){
                        printCLI(pack.getModelCopy(), null);
                        System.out.print(serverResponse.getPack().getAction().getInfo());

                        try{
                            String[] inputs = getUserInput();

                            int selectedToken = getToken(inputs, player);

                            if (selectedToken != 0){
                                playerAction = new PlayerAction(Action.TOKEN_SELECTED, getPlayer(), null, null, selectedToken, 0, null, null, false, null);
                                savedToken = selectedToken;
                                needToLoop = false;
                            }
                        } catch (Exception e){
                            System.out.println("Your input wasn't correct!");
                        }
                    }
                    notifyClient(playerAction);
                }
                break;
            }


            case ASK_FOR_PROMETHEUS_POWER:{

                Pack pack = serverResponse.getPack();
                printCLI(pack.getModelCopy(), null);

                if (!player.getTokenColor().equals(serverResponse.getTurn())){
                    System.out.println(pack.getMessageOpponents());
                }
                else {

                    boolean needToLoop = true;
                    PlayerAction playerAction = null;
                    System.out.print(serverResponse.getPack().getAction().getInfo());

                    while (needToLoop){
                        try {

                            Scanner scanner = new Scanner(System.in);
                            String answer = scanner.nextLine().toUpperCase();

                            if (answer.equals("YES")) {
                                playerAction = new PlayerAction(Action.PROMETHEUS_ANSWER, player, null, null, 0, 0, null, null, true, null);
                                needToLoop = false;
                            }
                            else if (answer.equals("NO")) {
                                playerAction = new PlayerAction(Action.PROMETHEUS_ANSWER, player, null, null, 0, 0, null, null, false, null);
                                needToLoop = false;
                            }
                            else {
                                System.out.println("Please write yes or no...");
                            }

                        }catch (Exception e){
                            System.out.println("Please write yes or no...");
                            needToLoop = true;
                        }
                    }
                    notifyClient(playerAction);
                }
                break;
            }


            case GAME_OVER:{
                Pack pack = serverResponse.getPack();
                printCLI(pack.getModelCopy(), null);
                System.out.print(serverResponse.getPack().getAction().getInfo());
                System.out.println(pack.getMessageInTurn());
                break;
            }


            case ASK_FOR_WHERE_TO_MOVE:{

                Pack pack = serverResponse.getPack();
                printCLI(pack.getModelCopy(), pack.getValidMoves());

                if (!player.getTokenColor().equals(serverResponse.getTurn())){
                    System.out.println(pack.getMessageOpponents());
                }
                else{

                    boolean needToLoop = true;
                    PlayerAction playerAction = null;

                    while (needToLoop){

                        printCLI(pack.getModelCopy(), pack.getValidMoves());
                        System.out.println(pack.getAction().toString());

                        try{
                            String[] inputs = getUserInput();

                            Cell selectedCell = getCell(inputs, pack.getModelCopy().getBattlefield());

                            if (selectedCell != null){
                                playerAction = new PlayerAction(Action.WHERE_TO_MOVE_SELECTED, getPlayer(), null, null, savedToken, 0, selectedCell, null, false, null);
                                needToLoop = false;
                            }
                        } catch (Exception e){
                            System.out.println("Your input wasn't correct!");
                        }
                    }
                    notify(playerAction);
                }
                break;
            }


            case ASK_FOR_BUILD:{

                Pack pack = serverResponse.getPack();
                printCLI(pack.getModelCopy(), pack.getValidBuilds());

                if (!player.getTokenColor().equals(serverResponse.getTurn())){
                    System.out.println(pack.getMessageOpponents());
                }
                else{

                    boolean needToLoop = true;
                    PlayerAction playerAction = null;

                    // If i have demeter i have to pick 2 cell for build
                    if (player.getMyGodCard().equals(GodCard.DEMETER)){

                        Cell selectedCell = null;
                        Cell otherCell = null;

                        printCLI(pack.getModelCopy(), pack.getValidBuilds());
                        System.out.println(pack.getAction().toString());

                        while (needToLoop) {

                            try {
                                String[] inputs = getUserInput();

                                if (inputs==null && selectedCell!=null){
                                    playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, getPlayer(), null, null, savedToken, 0, selectedCell, null, false, null);
                                    savedToken = 0;
                                    needToLoop = false;
                                    break;
                                }

                                else if (otherCell==null && selectedCell!=null)
                                    otherCell=getCell(inputs, pack.getModelCopy().getBattlefield());

                                else if (selectedCell==null)
                                    selectedCell = getCell(inputs, pack.getModelCopy().getBattlefield());

                                if (selectedCell != null && otherCell!=null) {
                                    playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, getPlayer(), null, null, savedToken, 0, selectedCell, otherCell, true, null);
                                    savedToken = 0;
                                    needToLoop = false;
                                }
                                else {
                                    printCLI(pack.getModelCopy(), pack.getValidBuilds());
                                    System.out.println("Select where do you want to place your second build... (x,y)\nType 'no' if you don't want to build a second time!");
                                }
                            } catch (Exception e) {
                                printCLI(pack.getModelCopy(), pack.getValidBuilds());
                                System.out.println("Your input wasn't correct!");
                            }
                        }
                    }
                    // If not demeter only one build
                    else {
                        while (needToLoop) {

                            printCLI(pack.getModelCopy(), pack.getValidBuilds());
                            System.out.println(pack.getAction().toString());

                            try {
                                String[] inputs = getUserInput();

                                Cell selectedCell = getCell(inputs, pack.getModelCopy().getBattlefield());

                                if (selectedCell != null) {
                                    playerAction = new PlayerAction(Action.WHERE_TO_BUILD_SELECTED, getPlayer(), null, null, savedToken, 0, selectedCell, null, false, null);
                                    savedToken = 0;
                                    needToLoop = false;
                                }
                            } catch (Exception e) {
                                System.out.println("Your input wasn't correct!");
                            }
                        }
                    }
                    notify(playerAction);
                }
                break;
            }
        }
    }









    public int getToken(String[] inputs, Player player){

        int selectX, selectY;
        selectX = Integer.parseInt(inputs[0]);
        selectY = Integer.parseInt(inputs[1]);

        if (player.getToken1().getTokenPosition().getPosX() == selectX && player.getToken1().getTokenPosition().getPosY() == selectY){
            return player.getToken1().getId();
        }
        else if (player.getToken2().getTokenPosition().getPosX() == selectX && player.getToken2().getTokenPosition().getPosY() == selectY){
            return player.getToken2().getId();
        }
        else return 0;
    }



    public Cell getCell(String[] inputs, Battlefield battlefield){

        int selectX, selectY;
        selectX = Integer.parseInt(inputs[0]);
        selectY = Integer.parseInt(inputs[1]);

        try {
            return battlefield.getCell(selectX, selectY);
        } catch (CellOutOfBattlefieldException e) {
            return null;
        }
    }


}
