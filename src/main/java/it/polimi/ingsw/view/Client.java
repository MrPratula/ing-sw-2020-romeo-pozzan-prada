package it.polimi.ingsw.view;

import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.ServerResponse;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Client extends Observable<ServerResponse> {

    private final int port;
    private final String ip;
    private InputStream inputStream;
    private View view;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }


    /**
     * It receives a ServerResponse and notifies the view.
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
            // here the user writes the string
            String name = stdin.nextLine();         //LEGGE *QUANTO* SCRITTO DAL CLIENT SU TERMINALE (IN STO CASO  IL NOME)
            socketOut.println(name);                //MANDA AL SOCKET IL SUO NOME
            socketOut.flush();                      //FLUSH

            socketLine = socketIn.nextLine();
            System.out.println(socketLine);
            // here the user writes the int
            int numberOfPlayers = stdin.nextInt();   //LEGGE *QUANTO* SCRITTO DAL CLIENT SU TERMINALE (IN STO CASO  IL NUMERO DI GIOCATORI)
            socketOut.println(numberOfPlayers);      //MANDA AL SOCKET L'INT
            socketOut.flush();                       //FLUSH


            while(true){
                view.run();
            }
            /*
                //ATTESA RICEZIONE DAL SOCKET SU SOCKETIN  DI NUOVE ROBA E riinizia loop
                socketLine = socketIn.nextLine();      //RICEVE DAL SOCKET QUALCOSA
                System.out.println(socketLine);        // E LO STAMPA IL CLIENT A TERMINALE
            */

            //////////////////////////view.run();

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

        } catch(NoSuchElementException e) {
            e.getMessage();
          }
    }


}