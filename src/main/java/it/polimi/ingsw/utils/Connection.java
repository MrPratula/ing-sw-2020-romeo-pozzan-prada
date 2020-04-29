package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Connection extends Observable<String> implements Runnable{

    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private Server server;
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


    @Override
    public void run() {
        try{
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
            send("Welcome! What's your name?");
            String name = in.nextLine();

            send("How many players do you want to play with?");
            int numberOfPlayers = in.nextInt();

            server.lobby(this, name, numberOfPlayers);

            while(isActive()){
                String read = in.nextLine();
                notify(read);
            }
        } catch(IOException | CellOutOfBattlefieldException | ImpossibleTurnException | ReachHeightLimitException | CellHeightException | WrongNumberPlayerException e){
            System.err.println(e.getMessage());
        } finally {
            close();
        }
    }


    private void close(){
        closeConnection();
        System.out.println("Deregistering client...");
        //momentaneo server.deregisterConnection(this);
        System.out.println("Done!");
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



}
