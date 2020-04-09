package it.polimi.ingsw.server;

import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.view.RemoteView;

public interface ClientConnection {

    void closeConnection();

    void addObserver(RemoteView.MessageReceiver observer);

    void asyncSend(Object message);

}
