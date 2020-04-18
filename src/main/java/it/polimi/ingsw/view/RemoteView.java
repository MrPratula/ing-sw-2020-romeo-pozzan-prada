package it.polimi.ingsw.view;

import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.ServerResponse;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;


/**
 * The remote view is unique and has the purpose to receive a
 * Server response object via an update, then send this object
 * via socket to the client.
 * This one will take care what to do with it.
 */
public class RemoteView implements Observer<ServerResponse> {

    ObjectOutputStream objectOutputStream;

    /**
     * When i create a remote view it is connected via socket to a client.
     * It will save the object output stream in order to send object via socket.
     * @throws IOException if something goes wrong.
     */
    public RemoteView() throws IOException {
        this.objectOutputStream = run();
    }


    /**
     * The run method is called in the constructor to set up a Socket connection and
     * return the way to send object.
     * @return the object output stream to send object via socket.
     * @throws IOException if something goes wrong.
     */
    public ObjectOutputStream run() throws IOException {

        Socket socket = new Socket("localhost", 12345);
        System.out.println("Connected!");

        // get the output stream from the socket.
        OutputStream outputStream = socket.getOutputStream();
        // create an object output stream from the output stream so we can send an object through it
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        return objectOutputStream;
    }


    /**
     * This is the update of the observer ServerResponse
     * The remote view observe the model and when it changes it send here the ServerResponse.
     * Here the object is received and sent to the client via object output stream.
     * @param serverResponse the ServerResponse received by the model.
     * @throws IOException if something goes wrong.
     */
    @Override
    public void update(ServerResponse serverResponse) throws IOException {
        objectOutputStream.writeObject(serverResponse);
    }
}
