package cz.spsejecna.universeclash.cardManager;

import cz.spsejecna.universeclash.cards.Card;
import cz.spsejecna.universeclash.cards.CardRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class CardPicker {

    private final Random rand = new Random();
    private final Scanner sc = new Scanner(System.in);
    private final CardRepository cardRepository = new CardRepository();

    /**
     * Generates three random and unique card options for the player to choose from.
     * It displays the details of each card and prompts the user to select one by its ID.
     * The method ensures that the chosen ID corresponds to one of the presented options
     * and handles invalid input.
     *
     * @return The integer ID of the card chosen by the player.
     */
    public int generatePicks(){
        // Get a fresh copy of all available cards to pick from
        HashMap<Integer, Card> remainingCards = cardRepository.getCards();
        ArrayList<Card> options = new ArrayList<>();

        // Select 3 random and unique cards
        for (int i = 0; i < 3; i++) {
            // Convert HashMap values to an array to pick a random element
            Object[] remainingCardsArray = remainingCards.values().toArray();
            // Pick a random card from the available ones
            Card randomCard = (Card) remainingCardsArray[rand.nextInt(remainingCardsArray.length)];
            options.add(randomCard);
            System.out.println(randomCard.toString()); // Display the card details to the player
            remainingCards.remove(randomCard.getCardId()); // Remove the selected card to ensure uniqueness
        }

        // Prompt the player for their choice and validate input
        System.out.print("Type the id of the card you want.\n>id: ");
        while (true) {
            try{
                int chosen = sc.nextInt();
                // Check if the chosen ID matches any of the presented options
                if (options.get(0).getCardId() == chosen ||
                        options.get(1).getCardId() == chosen ||
                        options.get(2).getCardId() == chosen) {
                    return chosen;
                } else {
                    System.out.println("Card with that id isn't currently available");
                }
            } catch (Exception e){
                System.err.println("ID has to be an integer."); // Handle non-integer input
                sc.nextLine(); // Consume the invalid input to prevent infinite loop
            }
        }
    }
}