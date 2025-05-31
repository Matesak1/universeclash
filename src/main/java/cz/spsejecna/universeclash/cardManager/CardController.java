package cz.spsejecna.universeclash.cardManager;

import cz.spsejecna.universeclash.cards.CardRepository;
import cz.spsejecna.universeclash.cards.Card;


import java.util.ArrayList;
import java.util.Objects;

public class CardController {
    private final ArrayList<Card> cards = new ArrayList<>();
    private final CardRepository cardRepository = new CardRepository();

    /**
     * Adds a new card to the player's collection.
     * The card is obtained as a copy from the `CardRepository` based on its unique ID.
     * This ensures that each card in the collection is a distinct instance.
     *
     * @param id The integer ID of the card to be added.
     */
    public void addCard(int id){
        cards.add(cardRepository.obtainCard(id).copy());
    }

    /**
     * Returns the current list of cards held by the controller.
     *
     * @return An `ArrayList` of `Card` objects representing the player's card collection.
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Counts the number of "Regeneration" cards in the player's collection.
     *
     * @return The total count of "Regeneration" cards.
     */
    public int numberOfRegenerationCards(){
        int number = 0;
        for (Card c : cards){
            if(Objects.equals(c.getName(), "Regeneration")){
                number++;
            }
        }
        return number;
    }

    /**
     * Counts the number of "Restoration" cards in the player's collection.
     *
     * @return The total count of "Restoration" cards.
     */
    public int numberOfRestorationCards(){
        int number = 0;
        for (Card c : cards){
            if(Objects.equals(c.getName(), "Restoration")){
                number++;
            }
        }
        return number;
    }

    /**
     * Counts the number of "Gift" cards in the player's collection and multiplies the count by 3.
     *
     * @return The calculated value based on the number of "Gift" cards.
     */
    public int numberOfGiftCards(){
        int number = 0;
        for (Card c : cards){
            if(Objects.equals(c.getName(), "Gift")){
                number++;
            }
        }
        number = number * 3; // "Gift" cards provide 3 times their count
        return number;
    }

    /**
     * Counts the number of "Last" cards in the player's collection and multiplies the count by 5.
     *
     * @return The calculated value based on the number of "Last" cards.
     */
    public int numberOfLastCards(){
        int number = 0;
        for (Card c :cards){
            if(Objects.equals(c.getName(), "Last")) {
                number++;
            }
        }
        number = number * 5; // "Last" cards provide 5 times their count
        return number;
    }

    /**
     * Counts the number of "Anchor" cards in the player's collection.
     *
     * @return The total count of "Anchor" cards.
     */
    public int numberOfAnchorCards(){
        int number = 0;
        for (Card c : cards){
            if(Objects.equals(c.getName(), "Anchor")){
                number++;
            }
        }
        return number;
    }

    /**
     * Counts the number of "Snowballing" cards in the player's collection.
     *
     * @return The total count of "Snowballing" cards.
     */
    public int numberOfSnowballingCards(){
        int number = 0;
        for (Card c : cards){
            if(Objects.equals(c.getName(), "Snowballing")){
                number++;
            }
        }
        return number;
    }
}