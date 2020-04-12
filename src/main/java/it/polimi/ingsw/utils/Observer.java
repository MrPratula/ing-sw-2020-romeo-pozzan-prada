package it.polimi.ingsw.utils;

import it.polimi.ingsw.exception.CellOutOfBattlefieldException;

public interface Observer<Message> {

    void update(Message message) throws CellOutOfBattlefieldException;


}
