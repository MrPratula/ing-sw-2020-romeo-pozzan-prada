package it.polimi.ingsw.model;

/**
 * 
 */
public class Player {

    private String nickname;
    private TokenColor tokenColor;
    private Token token1;
    private Token token2;
    private GodCard god;


    /**
     * Default constructor
     */
    public Player() {
        this.nickname = null;
        this.tokenColor = null;
        this.token1 = null;
        this.token2=null;
        this.god = null;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setTokenColor (int color) {
        switch(color) {
            case 1:
                this.tokenColor = TokenColor.Red;
            case 2:
                this.tokenColor = TokenColor.Blu;
            case 3:
                this.tokenColor = TokenColor.Yellow;
        }
    }

    public TokenColor getTokenColor() {
        return tokenColor;
    }

    public Token getToken1() {
        return token1;
    }

    public Token getToken2() {
        return token2;
    }

    public void setToken1(Token token1) {
        this.token1 = token1;
    }

    public void setToken2(Token token2) {
        this.token2 = token2;
    }

    public void setGod(GodCard god) {
        this.god = god;
    }

    public GodCard getGod() {
        return god;
    }
}