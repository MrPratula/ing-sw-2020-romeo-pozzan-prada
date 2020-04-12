package it.polimi.ingsw.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {

    private String ip;
    private int port;

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        Scanner socketIn = new Scanner(socket.getInputStream());            //serializz
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());  //serializz
        Scanner stdin = new Scanner(System.in);
        String socketLine;
        try{

            socketLine = socketIn.nextLine();  //RICEVE TRAMITE connection.send   "WHATS UR NAME", e poi "MAKE UR MOVE)
            System.out.println(socketLine);  //IL CLIENT LO STAMPA A TERMINALE



            while (true){  //ASPETTA CHE IL CLIENT SCRIVA
                String inputLine = stdin.nextLine();   //LEGGE *QUANTO* SCRITTO DAL CLIENT SU TERMINALE (IN STO CASO  IL NOME)

                socketOut.println(inputLine);          //MANDA AL SOCKET IL SUO NOME
                socketOut.flush();                     //FLUSH
                //ATTESA RICEZIONE DAL SOCKET SU SOCKET IN  DI NUOVE ROBA E riinizia loop
                socketLine = socketIn.nextLine();      //RICEVE DAL SOCKET QUALCOSA
                System.out.println(socketLine);        // E LO STAMPA IL CLIENT A TERMINALE
            }



        } catch(NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }

}
