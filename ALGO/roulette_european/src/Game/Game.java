package Game;

import java.util.ArrayList;
import java.util.List;
import Bet.Bet;
import Player.Player;
import Roulette.Roulette;
import Color.Color;

public class Game {
    private Roulette roulette;
    private List<Player> players;

    public Game() {
        this.roulette = new Roulette();
        this.players = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void playRound(int numeroTombé) {
        for (Player player : players) {
            for (Bet bet : player.getBets()) {
                System.out.println(Color.CYAN + player.name + " joue " + Color.YELLOW + bet.getAmount() + Color.CYAN + " sur le " + Color.PURPLE + bet.getTarget() + Color.RESET);
            }
        }
        int winningNumber = numeroTombé;
        if (winningNumber == 0) {
            System.out.println(Color.CYAN + "Numéro tombé: " + Color.GREEN + winningNumber + Color.RESET);
        } else if (Roulette.RED_NUMBERS.contains(winningNumber)) {
                System.out.println(Color.CYAN + "Numéro tombé: " + Color.RED + winningNumber + Color.RESET);
        } else {
            System.out.println(Color.CYAN + "Numéro tombé: " + Color.RESET + winningNumber);
        }
        for (Player player : players) {
            int winnings = 0;
            for (Bet bet : player.getBets()) {
                winnings += calculateWinnings(bet, winningNumber);
            }
            player.updateCredits(winnings);
            System.out.println(Color.CYAN + "Crédits: " + Color.YELLOW + player.getCredits() + Color.RESET);
            player.getBets().clear();
        }
    }

    private int calculateWinnings(Bet bet, int winningNumber) {
        if (Roulette.NUMBERS.contains(bet.getTarget())) {
            if (Integer.parseInt(bet.getTarget()) == winningNumber) {
                return 36 * bet.getAmount();
            }
        } else if (winningNumber == 0 && Roulette.SIMPLES.contains(bet.getTarget())) {
            return bet.getAmount() / 2;

        } else if (Roulette.COLORS.contains(bet.getTarget())) {
            if (bet.getTarget().equals("red") && Roulette.RED_NUMBERS.contains(winningNumber)) {
                return 2 * bet.getAmount();
            } else if (bet.getTarget().equals("black") && Roulette.BLACK_NUMBERS.contains(winningNumber)) {
                return 2 * bet.getAmount();
            }
        } else if (Roulette.PARITIES.contains(bet.getTarget())) {
            if (bet.getTarget().equals("even") && winningNumber % 2 == 0) {
                return 2 * bet.getAmount();
            } else if (bet.getTarget().equals("odd") && winningNumber % 2 == 1) {
                return 2 * bet.getAmount();
            }
        } else if (Roulette.RANGES.contains(bet.getTarget())) {
            if (bet.getTarget().equals("low") && winningNumber >= 1 && winningNumber <= 18) {
                return 2 * bet.getAmount();
            } else if (bet.getTarget().equals("high") && winningNumber >= 19) {
                return 2 * bet.getAmount();
            }
        } else if (Roulette.TIERS.contains(bet.getTarget())) {
            if (bet.getTarget().equals("tier1") && winningNumber >= 1 && winningNumber <= 12) {
                return 3 * bet.getAmount();
            } else if (bet.getTarget().equals("tiers2") && winningNumber >= 13 && winningNumber <= 24) {
                return 3 * bet.getAmount();
            } else if (bet.getTarget().equals("tiers3") && winningNumber >= 25 && winningNumber <= 36) {
                return 3 * bet.getAmount();
            }
        } else if (Roulette.LIGNES.contains(bet.getTarget())) {
            if (bet.getTarget().equals("ligne1") && winningNumber % 3 == 1) {
                return 3 * bet.getAmount();
            } else if (bet.getTarget().equals("ligne2") && winningNumber % 3 == 2) {
                return 3 * bet.getAmount();
            } else if (bet.getTarget().equals("ligne3") && winningNumber % 3 == 0) {
                return 3 * bet.getAmount();
            }
        }
        return 0;
    }
}

