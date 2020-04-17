package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.ServerResponse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;


public class Client extends Observable<ServerResponse> {

    public Client() {
    }


    /**
     * Run a server.
     * It waits till he receive a ServerResponse via socket from the remoteView.
     * When a ServerResponce is received, it is notified to the view.
     */
    public void run() throws IOException, ClassNotFoundException, CellOutOfBattlefieldException {

        ServerSocket ss = new ServerSocket(12345);
        System.out.println("ServerSocket awaiting connections...");
        // blocking call, this will wait until a connection is attempted on this port.
        Socket socket = ss.accept();

        // get the input stream from the connected socket
        InputStream inputStream = socket.getInputStream();
        // create a DataInputStream so we can read data from it.
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        ServerResponse serverResponse;

        try{
            while (true) {
                serverResponse = (ServerResponse) objectInputStream.readObject();
                notify(serverResponse);
            }
        } catch(NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        } catch (WrongNumberPlayerException e) {
            e.printStackTrace();
        } catch (CellHeightException e) {
            e.printStackTrace();
        } catch (ImpossibleTurnException e) {
            e.printStackTrace();
        } catch (ReachHeightLimitException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Closing sockets.");
            ss.close();
            socket.close();
        }
    }
}