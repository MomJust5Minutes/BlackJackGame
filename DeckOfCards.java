import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// Iniciates the deck that will be used throughout the game
public class DeckOfCards {
   private Card[] deck; // array of Card objects
   private int currentCard; // index of next Card to be dealt (0-51)
   private static final int NUMBER_OF_CARDS = 52; // constant # of Cards
   // random number generator
   private static final SecureRandom randomNumbers = new SecureRandom();
   // ArrayList of the card's suit (naipe)
   private static final List<String> suits = Arrays.asList("Hearts", "Diamonds", "Clubs", "Spades");

   // constructor fills deck of Cards
   public DeckOfCards() {
      deck = new Card[NUMBER_OF_CARDS]; // create array of Card objects
      currentCard = 0; // first Card dealt will be deck[0]

      // populate deck with Card objects and assign card values and suits
      for (int count = 0; count < deck.length; count++) {
         String face = getRandomCardFace(); // Atribui a face da carta com base no índice (13 índices)
         String suit = suits.get(count / 13); // Escolhe o naipe aleatoriamente
         int value = getValueForCard(face); // Chama o método para adquirir valor numérico da carta
         deck[count] = new Card(face, suit, value);
      }

      shuffle(); // Embaralha o baralho após criá-lo
   }

   private String getRandomCardFace() {
      // Gera um valor aleatório de 1 a 13 para representar a face da carta
      int randomValue = randomNumbers.nextInt(13) + 1; // +1 porque array começa em 0
      if (randomValue == 1) {
         return "Ace";
      } else if (randomValue == 11) {
         return "Jack";
      } else if (randomValue == 12) {
         return "Queen";
      } else if (randomValue == 13) {
         return "King";
      } else {
         return Integer.toString(randomValue);
      }
   }

   // shuffle deck of Cards with one-pass algorithm
   public void shuffle() {
      // next call to method dealCard should start at deck[0] again
      currentCard = 0;

      // Shuffle the deck using Collections.shuffle
      List<Card> cardList = Arrays.asList(deck);
      Collections.shuffle(cardList);
      cardList.toArray(deck);
   }

   // Método para obter o valor de uma carta com base na face
   private int getValueForCard(String face) {
      if (face.equals("Ace")) {
         return 1; // "Ás" vale 1 ponto
      } else if (face.equals("Jack") || face.equals("Queen") || face.equals("King")) {
         return 10; // "J", "Q" e "K" valem 10 pontos
      } else {
         return Integer.parseInt(face); // Retorna o valor das cartas em INT
      }
   }

   // deal one Card
   public Card dealCard() {
      // determine whether Cards remain to be dealt
      if (currentCard < deck.length)
         return deck[currentCard++]; // return current Card in array
      else
         return null; // return null to indicate that all Cards were dealt
   }

   // Esse método adicionará duas cartas à mão do jogador passado como argumento,
   // utilizando o método dealCard() da classe DeckOfCards.
   // O blackjack tem como inicio obrigatório DUAS cartas em mão
   public void dealInitialCards(Player player) {
      player.addCardToHand(dealCard());
      player.addCardToHand(dealCard());
   }

} // end class DeckOfCards

/*
 * shuffle, Fisher-Yates method:
 * for (int i = deck.length - 1; i > 0; i--) {
 * int j = randomNumbers.nextInt(i + 1);
 * Card temp = deck[i];
 * deck[i] = deck[j];
 * deck[j] = temp;
 * }
 * currentCard = 0; // Reset currentCard to start dealing from the beginning
 */