package cz.spsejecna.universeclash.partyManager;
import cz.spsejecna.universeclash.characters.Character;
import cz.spsejecna.universeclash.characters.CharacterRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class PartyPicker {
    private final Random rand = new Random();
    private final Scanner sc = new Scanner(System.in);
    private final CharacterRepository characterRepository = new CharacterRepository();
    /**
     * Generates a selection of characters for either the player's team or an enemy team.
     * If generating for the player's team, it presents three unique character options
     * (not already in the current party) and prompts the user to select one by ID.
     * If generating for an enemy team, it randomly selects one unique character
     * (not already in the enemy party).
     *
     * @param party The current list of characters in the team (either player's or enemy's) to avoid duplicates.
     * @param yourteam A boolean indicating whether the selection is for the player's team (true) or an enemy team (false).
     * @return The character ID of the chosen or randomly selected character.
     * For the player's team, it ensures the chosen ID is from the presented options.
     * For the enemy team, it returns a random unique character ID.
     */
    public int generatePicks(ArrayList<Character> party, boolean yourteam){
        if (yourteam) {
            HashMap<Integer, Character> remainingCharacters = characterRepository.getCharacters();
            ArrayList<Character> options = new ArrayList<>();

            for (Character ch : party) {
                remainingCharacters.remove(ch.getCharacterID());
            }

            for (int i = 0; i < 3; i++) {
                Object[] remainingCharactersArray = remainingCharacters.values().toArray();
                Character randomCharacter = (Character) remainingCharactersArray[rand.nextInt(remainingCharactersArray.length)];
                options.add(randomCharacter);
                System.out.println(randomCharacter.fullinfo());
                remainingCharacters.remove(randomCharacter.getCharacterID());
            }
            System.out.print("Type the id of the character you want to add to your team.\n>id: ");
            while (true) {
                try {
                    int chosen = sc.nextInt();
                    if (options.get(0).getCharacterID() == chosen ||
                            options.get(1).getCharacterID() == chosen ||
                            options.get(2).getCharacterID() == chosen) {
                        return chosen;
                    } else {
                        System.out.println("Character with that id isn't currently available");
                    }
                } catch (Exception e) {
                    System.err.println("ID has to be an integer.");
                }
            }
        }else {
            HashMap<Integer, Character> remainingCharacters = characterRepository.getCharacters();

            for (Character ch : party) {
                remainingCharacters.remove(ch.getCharacterID());
            }

            Object[] remainingCharactersArray = remainingCharacters.values().toArray();
            Character randomCharacter = (Character) remainingCharactersArray[rand.nextInt(remainingCharactersArray.length)];
            remainingCharacters.remove(randomCharacter.getCharacterID());
            return randomCharacter.getCharacterID();

        }

    }
}
