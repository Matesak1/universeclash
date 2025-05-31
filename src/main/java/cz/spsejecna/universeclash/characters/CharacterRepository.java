package cz.spsejecna.universeclash.characters;

import cz.spsejecna.universeclash.PathFinder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CharacterRepository {

    private final HashMap<Integer, Character> characters = new HashMap<>();

    /**
     * Constructs a `CharacterRepository` and immediately loads all character data
     * from the specified JSON file.
     */
    public CharacterRepository() {
        loadCharacters();
    }

    /**
     * Loads character data from a JSON file located at `PathFinder.charactersFilePath`.
     * It parses the JSON array, and for each character object, extracts its ID, name,
     * maximum HP, maximum SP, and details for two abilities (name and description).
     * A `Character` object is then built using this data and stored in the `characters` HashMap,
     * with the character's ID as the key.
     * If any error occurs during file reading or JSON parsing, an error message is printed,
     * and the program exits with a status code of 1.
     */
    private void loadCharacters(){
        JSONParser parser = new JSONParser();

        try {
            JSONArray a = (JSONArray) parser.parse(new FileReader(PathFinder.charactersFilePath));

            for (Object o : a) {
                JSONObject jsonCharacter = (JSONObject) o;
                int id = Math.toIntExact((long) jsonCharacter.get("id"));
                String name = (String) jsonCharacter.get("name");
                int maxHP = Math.toIntExact((long) jsonCharacter.get("HP"));
                int maxSP = Math.toIntExact((long) jsonCharacter.get("SP"));

                JSONObject jsonAbility1 = (JSONObject) jsonCharacter.get("ability1");
                String ability1Name = (String) jsonAbility1.get("name");
                String ability1Description = (String) jsonAbility1.get("description");

                JSONObject jsonAbility2 = (JSONObject) jsonCharacter.get("ability2");
                String ability2Name = (String) jsonAbility2.get("name");
                String ability2Description = (String) jsonAbility2.get("description");

                // Build the Character object using its builder pattern
                Character character = new Character.CharacterBuilder().setID(id).setName(name)
                        .setMaxHP(maxHP).setMaxSP(maxSP)
                        .setAbility1(ability1Name, ability1Description)
                        .setAbility2(ability2Name, ability2Description)
                        .build();

                characters.put(id, character);
            }
        } catch (Exception e) {
            System.err.println("Failed to load characters. Shutting down.\n" + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Returns a new `HashMap` containing copies of all character templates
     * currently loaded in the repository. Each character object returned is a deep copy,
     * ensuring that external modifications to these characters do not affect the original
     * template characters stored in the repository.
     *
     * @return A `HashMap` where keys are character IDs (Integer) and values are copied `Character` objects.
     */
    public HashMap<Integer, Character> getCharacters() {
        return (HashMap<Integer, Character>) characters.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().copy()));
    }

    /**
     * Retrieves a copy of a `Character` object from the repository based on its ID.
     * This method ensures that a new instance of the character is returned, rather than
     * a direct reference to the template object. This prevents unintended modifications
     * to the master character objects.
     *
     * @param charid The integer ID of the character to obtain.
     * @return A copied `Character` object if found, or `null` if a character with the given ID does not exist.
     */
    public Character obtainCharacter(int charid) {
        if (characters.containsKey(charid)) {
            return characters.get(charid).copy();
        } else {
            System.out.println("Searched for a character that doesn't exist.");
            return null;
        }
    }
}