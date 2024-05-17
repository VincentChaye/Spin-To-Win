package roulette;


import java.util.ArrayList;
import java.util.List;
 

public class Player {
    public String name;
    private int credits;
    private List<Bet> bets;

    public Player(String name, int initialCredits) {
        this.name = name;
        this.credits = initialCredits;
        this.bets = new ArrayList<>();
    }

    public void placeBet(Bet bet) {
        if (bet.getAmount() <= credits) {
            bets.add(bet);
            credits -= bet.getAmount();
        } else {
            System.out.println("Pas assez de crédits pour placer ce pari.");
        }
    }

    public void updateCredits(int amount) {
        credits += amount;
    }

    public List<Bet> getBets() {
        return bets;
    }

    public int getCredits() {
        return credits;
    }
}

