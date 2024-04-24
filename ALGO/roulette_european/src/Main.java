import Bet.Bet;
import Game.Game;
import Player.Player;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Player player1 = new Player("Elio", 1000);
        game.addPlayer(player1);

        player1.placeBet(new Bet(100, "noir"));
        game.playRound();
    }
}
