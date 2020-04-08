package it.polimi.ingsw.utils;

public interface Observer<Message> {

    void update(Message message);


}
