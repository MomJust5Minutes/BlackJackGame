import java.util.ArrayList;
import java.util.List;

// Main > BlackJackGame > Player > DeckOfCards > Card
public class Main {
    public static void main(String[] args) {
        BlackjackGame game = new BlackjackGame(); // Inicializa um novo jogo, que retornará o winner

        Player player1 = new Player("Player 1"); // Novo player
        Player player2 = new Player("Player 2"); // Novo player
        Player dealer = game.getDealer(); // get dealer (player especial)

        // Adiciona player à Array List
        game.addPlayer(player1);
        game.addPlayer(player2);

        game.dealInitialHands(); // class DeckOfCards
        game.playRound(); // Jogo

        // Exiba a mão dos jogadores usando o método getPlayers() no fim do jogo
        List<Player> players = game.getPlayers(); // Pega os jogadores do objeto jogo
        for (Player player : players) {
            System.out.println(player.getName() + "'s Hand:");
            for (Card card : player.getHand()) {
                System.out.println(card); // Print de cada carta na mão do jogador dentro do jogo
            }
        }

        // Determine o vencedor e imprima o resultado
        Player winner = determineWinner(players, dealer); // Use o tipo List<Player> corretamente
        if (winner == null) {
            System.out.println("It's a tie!");
        } else {
            System.out.println(winner.getName() + " wins!");
        }
    }

    // Método para determinar o vencedor (ou empate) do jogo
    private static Player determineWinner(List<Player> players, Player dealer) {
        Player winner = null;
        int dealerValue = dealer.calculateHandValue();

        for (Player player : players) {
            int playerValue = player.calculateHandValue();

            if (playerValue <= 21) {
                // O jogador ainda está na rodada e tem uma mão válida
                if (dealerValue > 21 || playerValue > dealerValue) {
                    // O jogador ganhou ou o dealer estourou
                    winner = player;
                } else if (playerValue == dealerValue) {
                    // Empate
                    winner = null;
                }
            }
        }

        return winner; // Retorna o ganhador ou empate
    }
}
