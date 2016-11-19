public class War {
	public static final int NUMBER_CARDS_DECK = 52;
	private static final int NUMBER_CARDS_TIEBREAKER = 3;

	public static void main(String[] args){
		Card[] entireDeck = createDeck();
		Card[] shuffledDeck = shuffleDeck(entireDeck);

		//we will create an array of size NUMBER_CARDS_DECK for each player
		//anytime that a value in the array is null, it will mean that
		//there is not actually a real card there. We will have to 
		//be smart about inserting and deleting cards appropriately.
		Card[] playerOne = new Card[NUMBER_CARDS_DECK];
		Card[] playerTwo = new Card[NUMBER_CARDS_DECK];

		//The following loop "distributes" the cards to the arrays
		//playerOne and playerTwo . It does this by putting even
		//indexed Cards into playerOne and odd ones into playerTwo
		//Here is where knowledge of references vs primitive types
		//is useful as we actually move all this to a method that changed
		//an array. We will not do this for now.
		for (int i= 0; i < NUMBER_CARDS_DECK; i++) {
			if (i % 2==0) {
				playerOne[i / 2] = shuffledDeck[i];
			}
			else
			{
				playerTwo[i / 2] = shuffledDeck[i];
			}
		}

		while (hasCards(playerOne) && hasCards(playerTwo))
		{
			//Add some print statements to print information:
			System.out.println("Player one has " + countCards(playerOne) + " card and player two has " + countCards(playerTwo) + " cards.");

			//one turn of the game
			playRound(playerOne, playerTwo);

			//add some spacing to separate rounds
			System.out.println("\n");
		}

		if (hasCards(playerOne)) {
			System.out.println("Player one won!");
		}
		else {
			System.out.println("Player two won!");
		}
	}

	//This method takes nothing as input and returns a Card[]
	//The Card array will contain 52 Card Objects, each of which
	//stores a different value and suit. All 52 combinations of suit/value
	//will be present in one of these Objects in the array in different spots.
	private static Card[] createDeck() {
		//Create a Card[] . Note that to begin with, by default,
		//when we create an array of Object, all values are null
		//What this means, is after create a new Card[NUMBER_CARDS_DECK]
		//we have create an ARRAY of Card, but not the Cards themselves.
		//The array COULD hold Cards, but for now, it holds "null" Cards
		//The significance of this, is that we have to CREATE a Card for
		//each spot and then put it into the array.
		//There are a few ways to do this that are equivalent.
		Card[] entireDeck = new Card[NUMBER_CARDS_DECK];
		int z =0;
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 4; j++) {
				entireDeck[z] = new Card();
				entireDeck[z].setSuit(j);
				entireDeck[z].setNumber(i + 1);
				z++;
			}
		}

		/*equivalent code (which we did in the afternoon) is the following loop instead 
		for (int i =0; i < NUMBER_CARDS_DECK; i++) {
			entireDeck[i] = new Card();
			entireDeck[i].setSuit(i / 13);
			entireDeck[i].setNumber(i % 13 +1);
		}
		*/

		return entireDeck;
	}

	//This method will randomly swap cards in the array until
	//a certain number of loops. You are not responsible for knowing
	//how the random number generation works. 
	//(It is just inserted for completeness.)
	//You should understand the idea of returning a new array with swapped
	//values though
	private static Card[] shuffleDeck(Card[] deck) {
		final int NUMBER_SWAPS = 100000;
		//create an Object that will be used to generate "random" numbers
		java.util.Random generator = new java.util.Random();
		
		//The idea is that we will pick 2 random numbers between 0 and 52
		//We will then SWAP the cards that are at those spots of the array
		//deck. If we do this enough times, the cards will be shuffled.

		for (int i = 0; i < NUMBER_SWAPS; i++) {
			//Generate 2 random ints between 0 and 52 (counting 0 but not 52)
			//See http://www.cs.geneseo.edu/~baldwin/reference/random.html
			//The beauty of Object oriented programming
			//is you actually don't care HOW generator computes the nextInt()
			//All you need to know is that it returns a random int
			int index1 = generator.nextInt(NUMBER_CARDS_DECK);
			int index2 = generator.nextInt(NUMBER_CARDS_DECK);

			//note that it's possible we'll swap a Card with itself. This
			//could be removed by an if statement, but it's harmless so we'll
			//leave it in.

			//To swap 2 variables, you normally create a 3rd temporary variable
			//A good trick is that the right side of the equals on one line
			//should be the left side of the equals on the next line
			Card temporaryCard = deck[index1];
			//the right side of the previous line becomes the left side of this line
			deck[index1] = deck[index2];
			//the right side of the previous line becomes the left side of this line
			deck[index2] = temporaryCard;
		}		

		return deck;
	}

	//This method plays one round of war
	private static void playRound(Card[] deck1, Card[] deck2) {
		//create an array of Card to store the cards in the pile
		//these cards will be added to each array.
		Card[] pile = new Card[NUMBER_CARDS_DECK];

		Card playerOneCard = deck1[0];
		Card playerTwoCard = deck2[0];		
		
		//remove the first card from playerOneCard
		//This method shifts everything over, so that the value at index 0 is a 
		//different card.
		removeTopCard(deck1);
		addCardToBottom(pile, playerOneCard);
		removeTopCard(deck2);
		addCardToBottom(pile, playerTwoCard);

		int comparison = compareCards(playerOneCard, playerTwoCard);
		printRoundResults(playerOneCard, playerTwoCard);

		while (comparison == 0) {
			//deal out 3 more cards because you do that in war normally, then evaluate again on the 4th.
			//note that we need to make sure the piles are not empty.

			for (int j = 0; j < NUMBER_CARDS_TIEBREAKER; j++) {
				//We must assure that both decks have cards before removing from the top
				//This sort of check will be easier to do when we add better Object oriented design
				//as it can be "contained" or "encapsulated" into another class-- the same way
				//that we kept all the logic of suits being 0-3 inside the Card class. This allowed us
				//to prove that no card could ever have suit < 0 or > 3. Similarly, with a type we
				//define called CardPile , we will be able to assure various other things
				//In this case, if a deck has nothing in it, we'll return. This means we'll leave the method
				//and in the main method, the central while loop will be left.
				if (!hasCards(deck1) || !hasCards(deck2)) {
					return;
				}

				addCardToBottom(pile, deck1[0]);
				addCardToBottom(pile, deck2[0]);
				removeTopCard(deck1);
				removeTopCard(deck2);
			}
			
			if (!hasCards(deck1) || !hasCards(deck2)) {
				return;
			}

			//now compare the top cards again:
			playerOneCard = deck1[0];
			playerTwoCard = deck2[0];		
		
			//remove the first card from playerOneCard
			removeTopCard(deck1);
			addCardToBottom(pile, playerOneCard);
			removeTopCard(deck2);
			addCardToBottom(pile, playerTwoCard);

			comparison = compareCards(playerOneCard, playerTwoCard);
			printRoundResults(playerOneCard, playerTwoCard);
		}

		//now add all cards in the pile to the winner of that round's hand
		if (comparison > 0) {
			while (hasCards(pile)) {
				addCardToBottom(deck1, pile[0]);
				removeTopCard(pile);
			}
		}
		else if (comparison < 0) {
			while (hasCards(pile)) {
				addCardToBottom(deck2, pile[0]);
				removeTopCard(pile);
			}
		}
	}

	//This method prints the output of a round given two Cards
	private static void printRoundResults(Card playerOneCard, Card playerTwoCard) {
		System.out.println("Player one plays " + playerOneCard.getNumber());
		System.out.println("Player two plays " + playerTwoCard.getNumber());

		int comparison = compareCards(playerOneCard, playerTwoCard);

		if (comparison == 0) {
			System.out.println("WAR!");
		}
		else if (comparison > 0) {
			System.out.println("Player one wins that round!");
		}
		else {
			System.out.println("Player two wins that round!");
		}
	}

	//This method removes a card from an array by taking the top card off and shifting
	//every other value in the array.
	private static void removeTopCard(Card[] deck)
	{
		//note we only go up to length -1 to avoid an array out of bounds error
		//Because deck is a reference type, we can modify it inside this method
		for (int i=0; i < deck.length - 1; i++) {
			deck[i] = deck[i + 1];
		}
	}

	//This method takes as inptu a Card[] and a Card.
	//It adds the Card to the first non-null spot of Card[]
	private static void addCardToBottom(Card[] deck, Card newCard)
	{
		for (int i = 0; i < deck.length; i++) {
			if (deck[i] == null) {
				deck[i] = newCard;
				return; //leave the method immediately!
			}
		}
	}

	//This method takes as input 2 Card. It assumes neither Card is null
	//If the value of card1 == the value of card2, it returns 0;
	//If the value of card1 > the value of card2, it returns 1;
	//If the value of card1 < the value of card2, it returns -1;
	//Note that by "value" we mean the value of the card in the game of War,
	//meaning aces are > king
	private static int compareCards(Card card1, Card card2) {
		int playerOneNumber = card1.getNumber();
		int playerTwoNumber = card2.getNumber();

		if (playerOneNumber == playerTwoNumber) {
			return 0;
		}	

		//check for aces. Note that at this point in the code we know for sure
		//that playerOneNumber != playerTwoNumber. Otherwise, we'd have returned
		//at the previous statement. This means if playerOneNumber == 1, then he won.
		if (playerOneNumber == 1) {
			return 1;
		}

		if (playerTwoNumber == 1) {
			return 1;
		}

		if (playerTwoNumber > playerOneNumber) {
			return -1;
		}
		else {
			return 1;
		}
	}

	private static boolean hasCards(Card[] deck) {
		for (int i=0; i < deck.length; i++) {
			if (deck[i] != null) {
				return true;
			}
		}

		return false;
	}

	private static int countCards(Card[] deck) {
		int total = 0;
		for (int i=0; i < deck.length; i++) {
			if (deck[i] != null) {
				total++;
			}
		}

		return total;
	}
}