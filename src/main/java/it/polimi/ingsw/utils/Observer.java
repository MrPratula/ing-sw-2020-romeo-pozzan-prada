package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.*;

import java.io.IOException;

public interface Observer<Message> {

    void update(Message message) throws CellOutOfBattlefieldException, CellHeightException, ReachHeightLimitException, WrongNumberPlayerException, ImpossibleTurnException, IOException;

}
