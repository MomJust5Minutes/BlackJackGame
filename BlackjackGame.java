import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Classe com os metodos de análise dos jogos
public class BlackjackGame {
    private DeckOfCards deck; // Inicializa o deck criado na classe DeckOfCards
    private List<Player> players; // Inicializa o ArrayList criado em players
    private Player dealer; // Cria um player especial (dealer)
    private Scanner scanner; // Para entrada do usuário

    public BlackjackGame() {
        deck = new DeckOfCards(); // Chama novo objeto Deck
        players = new ArrayList<>(); // Chama nova ArrayList de players
        dealer = new Player("Dealer"); // Cria um objeto dealer
        scanner = new Scanner(System.in); // Incializa o scan que será a ação a ser tomada
    }

    // Método para adicionar player ao jogo
    public void addPlayer(Player player) {
        players.add(player);
    }

    // Método para entregar a mão inicial
    public void dealInitialHands() {
        for (Player player : players) {
            player.clearHand(); // Limpa a mão para caso existam cartas anteriores (se for necessário um "Jogue
                                // novamente")
            player.addCardToHand(deck.dealCard()); // Adiciona uma carta (class DeckOfCards) à mao do jogador (class
                                                   // Player)
            player.addCardToHand(deck.dealCard()); // Adiciona uma segunda carta à mao do jogador
        }
        dealer.clearHand(); // Limpa a mão do dealer
        dealer.addCardToHand(deck.dealCard()); // Adiciona uma carta (class DeckOfCards) à mao do dealer (class Player)
        dealer.addCardToHand(deck.dealCard()); // Adiciona uma segunda carta à mao do dealer
    }

    // Método geral para jogar um Round
    public void playRound() {
        for (Player player : players) { // Para cada iteração player receber players
            while (true) { // Menu simples
                System.out.println(player.getName() + ", do you want to (1) Hit, (2) Stand, or (3) Show deck? ");
                int choice = scanner.nextInt(); // Scan da decisão
                if (choice == 1) {
                    playerHit(player); // Chama método de sacar (Hit)
                    if (player.calculateHandValue() >= 21) {
                        break; // Se o jogador atingir 21 ou mais, ele não pode mais sacar.
                    }
                } else if (choice == 2) {
                    break; // O jogador optou por "stand".
                } else if (choice == 3) {
                    player.showDeck(); // Não é necessário passar o jogador como argumento aqui
                }
            }
        }

        // Verifica se todos os jogadores estouraram
        boolean allPlayersBusted = true;
        for (Player player : players) {
            if (player.calculateHandValue() <= 21) {
                allPlayersBusted = false;
                break;
            }
        }

        // Se todos os jogadores estouraram, encerre a rodada sem a lógica do dealer
        if (allPlayersBusted) {
            return;
        }

        // Lógica do dealer (ele saca até chegar a 17 ou mais, como não há apostas a
        // rodada simplesmente encerra)
        while (dealer.calculateHandValue() < 17) {
            playerHit(dealer);
        }

        // Quando o dealer para de sacar, a rodada está completa.
    }

    // Método para jogador que decide jogar mais (Hit)
    private void playerHit(Player player) {
        player.addCardToHand(deck.dealCard()); // Adiciona uma carta à mão do jogador
        System.out.println(player.getName() + " received a card.");
    }

    // gets
    public Player getDealer() {
        return dealer;
    }

    public List<Player> getPlayers() { // Esta é a lógica de um get para ArrayList
        return players;
    }
}