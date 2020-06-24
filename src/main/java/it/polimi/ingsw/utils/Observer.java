package it.polimi.ingsw.utils;

import java.io.IOException;

public interface Observer<Message> {

    void update(Message message) throws IOException;
}
