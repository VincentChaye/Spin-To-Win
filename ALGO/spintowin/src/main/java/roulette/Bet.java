package roulette;


public class Bet {
    private int amount;       // Type de pari (number, color, even, odd, high, low, column, dozen)
    private String target;    // Cible du pari, peut être un numéro, une couleur, etc.

    public Bet(int amount, String target) {
        this.amount = amount;
        this.target = target;
    }

    public int getAmount() {
        return amount;
    }

    public String getTarget() {
        return target;
    }
}