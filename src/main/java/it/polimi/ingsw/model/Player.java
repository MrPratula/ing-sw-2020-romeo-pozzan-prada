package it.polimi.ingsw.model;
import java.util.List;
import java.util.Scanner;


public class Player {

    private String nickname;
    private TokenColor tokenColor;
    private Token token1, token2;
    private String god;

    public Player() {
        this.nickname = null;
        this.tokenColor = null;
        this.token1 = null;
        this.token2 = null;
        this.god = null;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setTokenColor (int color) {
        TokenColor c;
        switch(color) {
            case 0:
                c = TokenColor.Red;
            case 1:
                c = TokenColor.Blu;
            case 2:
                c = TokenColor.Yellow;
            default:
                c= TokenColor.Red;
        }
        this.tokenColor = c;
        System.out.println(c);
        token1.setTokenColor(c);
        token2.setTokenColor(c);
    }

    public TokenColor getTokenColor() {
        return tokenColor;
    }

    public Token getToken1() { return token1; }

    public Token getToken2() {
        return token2;
    }

    public void setToken1(Token token1) {
        this.token1 = token1;
    }

    public void setToken2(Token token2) {
        this.token2 = token2;
    }

    public void setGod(String god) {
        this.god = god;
    }

    public String getGod() {
        return god;
    }

    public Token chooseToken(){
        System.out.println("Scegli se usare Token [1][2]");
        while(true) {
            Scanner s = new Scanner(System.in);
            String choice = s.next();
            if( choice.equals("1") ) return getToken1();
            if( choice.equals("2")) return getToken2();
            else System.out.println("Errore: Scrivere solamente 1 oppure 2");
        }
    }

    public Cell chooseCell(List<Cell> validMoves){
        int cont = 0;
        for( Cell cell : validMoves ){
            System.out.println("Cella numero " + cont + ": [" + cell.getPosX() + "][" + cell.getPosY() + "]");
            cont++;
        }
        while (true) {
            System.out.println("Scegli la cella (numero) in cui muovere il token: ");
            Scanner s = new Scanner(System.in);
            int index = s.nextInt();
            if (validMoves.get(index) != null) {
                s.close();
                return validMoves.get(index);
            }
        }
    }

    public Cell chooseBuild(List<Cell> validBuilds){
        int cont = 0;
        for( Cell cell : validBuilds ){
            System.out.println("Cella numero " + cont + ": [" + cell.getPosX() + "][" + cell.getPosY() + "]");
            cont++;
        }
        while (true) {
            System.out.println("Scegli la cella (numero) in cui costruire: ");
            Scanner s = new Scanner(System.in);
            int index = s.nextInt();
            if (validBuilds.get(index) != null) {
                s.close();
                return validBuilds.get(index);
            }
        }
    }

    public void move(Token token, Battlefield battlefield) {

        token.setOldHeight(token.getTokenPosition().getBuild().getHeight());
        List<Cell> validCells = token.validMoves(battlefield);
        Cell chosenCell = this.chooseCell(validCells);
        token.setTokenPosition(chosenCell);
    }

    public boolean checkWin(Token token) {
        if( token.getTokenPosition().getBuild().getHeight()==3 && token.getOldHeight()<=2 ) return true;
        else return false;
    }

    public void build(Token token, Battlefield battlefield) {

        Cell chosenBuild = chooseBuild(token.validBuilds(battlefield));
        chosenBuild.getBuild().incrementHeight();
    }

    public void print () {
        String out = "nome = "+nickname+"\ntokenColor = "+tokenColor+"\nGodCard = "+god;
        System.out.println(out);
    }





}