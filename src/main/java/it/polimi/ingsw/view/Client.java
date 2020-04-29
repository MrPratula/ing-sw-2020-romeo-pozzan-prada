package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TokenColor;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.ServerResponse;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Client extends Observable<ServerResponse> {


    private int port;
    private String ip;
    private InputStream inputStream;


    public Client(String ip, int port) throws IOException {

        this.ip = ip;
        this.port = port;



    }


    /**
     * It receive a ServerResponse and notify the view.
     * This happens while there is an active remote view. (?)
     */
    public void run() throws IOException {

        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        Scanner socketIn = new Scanner(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);
        String socketLine;

        try{
            socketLine = socketIn.nextLine();
            System.out.println(socketLine);
            String name = socketIn.nextLine();
            socketOut.println(name);
            socketOut.flush();
        }catch (NoSuchElementException ignore) {}

        /*
        while (true) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                ServerResponse serverResponse = (ServerResponse)objectInputStream.readObject();
                notify(serverResponse);
            } catch(NoSuchElementException | ClassNotFoundException | CellOutOfBattlefieldException | ImpossibleTurnException | ReachHeightLimitException | CellHeightException | WrongNumberPlayerException e){
                System.out.println("Connection closed from the server side");
            } finally {
                inputStream.close();
                socket.close();
            }

        }

         */
    }
}