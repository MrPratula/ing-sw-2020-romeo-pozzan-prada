package it.polimi.ingsw.model;

/**
 * A TokenColor object has the purpose to identify which player has which tokens.
 * Each token and each player has a proper TokenColor and if it is the same
 * it means that that player has the control of that token.
 */
public enum TokenColor {

    RED("\033[041m"),
    BLUE("\033[044m"),
    GREEN("\033[042m");

    static final String RESET = "\033[049m";

    private String escape;

    TokenColor(String escape) {
        this.escape = escape;
    }

    public String getEscape(){
        return escape;
    }

    @Override
    public String toString(){
        return escape;
    }

}


/*
    Color.RESET;
    System.out.println(redMessage);
*/