package cz.spsejecna.universeclash.cards;

import cz.spsejecna.universeclash.PathFinder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CardRepository {
    private final HashMap<Integer, Card> cards = new HashMap<>();

    /**
     * Constructs a `CardRepository` and automatically loads all card data
     * from the configured JSON file.
     */
    public CardRepository() {
        loadCards();
    }

    /**
     * Loads card data from a JSON file specified by `PathFinder.cardsFilePath`.
     * It parses the JSON array, and for each card object, extracts its ID, name, and description.
     * A new `Card` object is then built with these properties and stored in the `cards` HashMap,
     * using the card's ID as the key.
     * If there's an error during file reading or parsing, it prints an error message and exits the program.
     */
    private void loadCards(){
        JSONParser parser = new JSONParser();

        try {
            JSONArray a = (JSONArray) parser.parse(new FileReader(PathFinder.cardsFilePath));

            for (Object o : a) {
                JSONObject jsonCard = (JSONObject) o;
                int id = Math.toIntExact((long) jsonCard.get("id"));
                String name = (String) jsonCard.get("name");
                String description = (String) jsonCard.get("description");

                Card card = new Card.CardBuilder().setId(id).setName(name).setDescription(description).build();

                cards.put(id, card);
            }
        } catch (Exception e) {
            System.err.println("Failed to load cards. Shutting down.\n" + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Returns a new `HashMap` containing copies of all card templates
     * currently loaded in the repository. Each card object returned is a deep copy,
     * ensuring that modifications to these cards do not affect the original
     * template cards stored in the repository.
     *
     * @return A `HashMap` where keys are card IDs (Integer) and values are copied `Card` objects.
     */
    public HashMap<Integer, Card> getCards() {
        return (HashMap<Integer, Card>) cards.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().copy()));
    }

    /**
     * Retrieves a copy of a `Card` from the repository based on its ID.
     * This method ensures that a new instance of the card is returned,
     * rather than a reference to the original template card. This prevents
     * unintended modifications to the master card objects.
     *
     * @param cardid The integer ID of the card to retrieve.
     * @return A copied `Card` object if found, or `null` if a card with the given ID does not exist.
     */
    public Card obtainCard(Integer cardid){
        if (cards.containsKey(cardid)) {
            return cards.get(cardid).copy();
        } else {
            System.out.println("Searched for a card that doesn't exist.");
            return null;
        }
    }
}