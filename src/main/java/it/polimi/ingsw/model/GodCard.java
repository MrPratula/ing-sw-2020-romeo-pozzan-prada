package it.polimi.ingsw.model;


public class GodCard implements MoveBehaviour, WinConditionBehaviour, BuildBehaviour {


    public GodCard() {
    }


    private BuildBehaviour bb;


    private MoveBehaviour mb;

    private WinConditionBehaviour wcb;


    private Player owner;


    public void move() {
        // TODO implement here
    }


    public void build() {
        // TODO implement here
    }


    public void checkWin() {
        // TODO implement here
    }

}