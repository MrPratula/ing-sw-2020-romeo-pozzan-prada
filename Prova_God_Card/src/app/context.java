package app;

public class context {
    private carta card;

    public void setcard(carta prova) {
        card = prova;
    }

    public void start(){
        card.cartascelta();
    }
}
