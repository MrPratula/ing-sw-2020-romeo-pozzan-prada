package it.polimi.ingsw.view;

import it.polimi.ingsw.utils.Observable;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Connection extends Observable<String> implements Runnable {

    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private Server server;
    private String name;
    private boolean active = true;

    public Connection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
    }

    private synchronized boolean isActive(){
        return active;
    }

    public void send(String message){
        out.println(message);
        out.flush();
    }

    public void asyncSend(final String message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }

    public synchronized void closeConnection(){
        send("Connection closed from the server side");
        try{
            socket.close();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        active = false;
    }

    private void close(){
        closeConnection();
        System.out.println("Deregistering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    @Override
    public void run() {
        try{
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
            send("Welcome! What's your name?"); //ATTENDE RISPOSTA VIA RETE SOCKET SERIALIZ (in)

            name = in.nextLine(); //RICEVE DAL CLIENT VIA SOCKET RISPOSTA
            server.lobby(this, name); //AGGIUNGE STO NUOVO TIZIO ALLA LOBBY
            while(isActive()){ ///////////////////////////////////////////////////////////////dopo che il server ha creato tutto, dopo aver raggiunto il num di player necessari,
                String read = in.nextLine();  //per questo è observable di string,               legge la scelta
                notify(read);
            }
        } catch(IOException e){
            System.err.println(e.getMessage());
        } finally {
            close();
        }
    }
}
