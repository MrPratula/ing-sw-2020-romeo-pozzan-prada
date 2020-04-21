


//                              IMPORTANTE IL COMMENTO CHE SEGUE


/*  Per com'è fatto il programma ora, il giocatore inserire solo una cella in cui vuole costruire
     non due, per cui per sfruttare il potere di demeter basterà chiedere al giocatore di inserire
     una seconda volta un'altra cella in cui costruire. Nel fare ciò si deve controllare che le due
     celle non siano uguali.
     Una alternativa, che possiamo vedere insieme (ora non la implemento per evitare problemi con
     git se state lavorando anche voi) può essere quella di, sapendo la god card del giocatore,
     chiedergli le due celle in cui vuole costruire ( e metterle dentro una lista per esempio ). In
     tal caso bisognerà rivedere i vari metodi.
     Ovviamente per i giocatori che non hanno in nessun caso la faccoltà di costruire due volte
     basterà continuare con il metodo finora implementato.
  */

    // In base alla decisione che verrà presa, questa classe sarà più o meno utile.




package it.polimi.ingsw.gameAction.build;

import it.polimi.ingsw.controller.CellHeightException;
import it.polimi.ingsw.controller.CellOutOfBattlefieldException;
import it.polimi.ingsw.controller.ReachHeightLimitException;
import it.polimi.ingsw.model.Battlefield;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.GodCard;
import it.polimi.ingsw.model.Token;

import java.util.ArrayList;
import java.util.List;

public class DemeterBuild implements BuildBehavior {

    /*  Demeter può costruire una seconda volta ma non nello stesso posto. Ho quindi bisogno della precedente
        cella per costruire la lista di celle in cui il giocatore può costruire.
     */

    @Override
    public List<Cell> computeValidBuilds(Token selectedToken, Token otherToken, List<Token> enemyTokens, List<GodCard> enemyGodCards, Battlefield battlefield) throws CellOutOfBattlefieldException {
        return null;
    }

    @Override
    public void performBuild(Cell targetCell, Battlefield battlefield) throws CellHeightException, ReachHeightLimitException {

    }
}
