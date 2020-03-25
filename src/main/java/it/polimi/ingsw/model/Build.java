package it.polimi.ingsw.model;

/**
 * 
 */
public class Build {

    private int height;
    private boolean isDome;

    /**
     * Default constructor
     */
    public Build(int height, boolean isDome) {

        this.height = height;
        this.isDome = false;
    }

    public int getHeight() {
        return height;
    }

    public void incrementHeight() {
        this.height++;
    }

    public boolean isDome() {
        return isDome;
    }

    public void setTerminated() {
        if (isDome) isDome = false;
        else isDome = true;
    }
}