package it.polimi.ingsw.model;

import java.io.Serializable;

public enum GodCard implements Serializable {

    APOLLO("Your Worker may move into an opponent Worker’s space by forcing their Worker to the space yours just vacated"),
    ARTEMIS("Your Worker may move one additional time, but not back to its initial space"),
    ATHENA("If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn"),
    ATLAS("Your Worker may build a dome at any level including the ground"),
    DEMETER("Your Worker may build one additional time, but not on the same space."),
    HEPHAESTUS("Your Worker may build one additional block (not dome) on top of your first block"),
    MINOTAUR("Your Worker move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied space at any level"),
    PAN("You also win if your Worker moves down two or more levels"),
    PROMETHEUS("If your Worker does not move up, it may build both before and after moving"),
    ZEUS("Your Worker may build a block under itself."),
    CHRONUS("You also win when there are at least five Complete Towers on the board"),
    HESTIA("Your worker may build one additional time. The additional build can not be on a perimeter space"),
    HERA("An opponent can not win by moving on to a perimeter space"),
    LIMUS("Opponents Workers can not build on spaces neighboring your workers, unless building a dome to create a Complete Tower");


    private String info;

    GodCard(String info) {
        this.info = info;
    }

    public String getInfo(){
        return info;
    }

    @Override
    public String toString(){
        return info;
    }






}
