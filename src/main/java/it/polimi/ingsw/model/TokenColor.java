package it.polimi.ingsw.model;

/**
 * A TokenColor object has the purpose to identify which player has which tokens.
 * Each token and each player has a proper TokenColor and if it is the same
 * it means that that player has the control of that token.
 */
public enum TokenColor {

    RED("\u001B[31m"),
    BLUE("\u001B[34m"),
    YELLOW("\u001B[33m");

    static final String RESET = "\u001B[0m";

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
    Così viene stampato HELLo in rosso e WORLD in blu e da lì in poi resettati a nero

    String redMessage =
    Color.ANSI_RED + "HELLO " +
    Color.ANSI_BLUE + "WORLD" +
    Color.RESET;
    System.out.println(redMessage);
*/