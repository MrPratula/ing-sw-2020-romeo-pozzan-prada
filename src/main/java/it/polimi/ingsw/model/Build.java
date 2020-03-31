package it.polimi.ingsw.model;

/**
 * 
 */
public class Build {

    private int height;
    private boolean isDome;

    //Default constructor
    public Build(){
        this.height = 0;
        this.isDome = false;
    }

    //Sarebbe un SetBuild in pratica, magari inutile, ma utile in caso dovessi passargli una altezza diversa
    public Build(int height, boolean isDome) {
        this.height = height;
        this.isDome = isDome;
    }

    public int getHeight() {
        return height;
    }

    public boolean getIsDome() {
        return isDome;
    }

    public void incrementHeight() {
        this.height++;
        if(this.height == 4) this.isDome = true;
    }

    public void setTerminated() {
        this.isDome = false;
    }

}