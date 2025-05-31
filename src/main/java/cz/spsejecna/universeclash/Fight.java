package cz.spsejecna.universeclash;
import cz.spsejecna.universeclash.characters.Character;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Fight {
    private final Scanner sc = new Scanner(System.in);
    private final Random rand = new Random();

    /**
     * Prompts the player to choose an attack type (basic attack or ability) and returns the chosen option.
     * Displays the character's name and available abilities.
     * If an invalid input is provided, it defaults to a basic attack (0).
     * Handles non-integer input by printing an error and exiting the program.
     *
     * @param playingAs The character the player is currently controlling.
     * @return An integer representing the chosen attack type: 0 for basic attack, 1 for ability 1, 2 for ability 2.
     */
    public int play(Character playingAs){
        System.out.println("Playing as: "+playingAs.getName()+
                "\n type 0 for a simple 1 dmg attack, type 1 or 2 to use corresponding abilities.\n"
                +playingAs.getAbility1()+playingAs.getAbility2());
        try {
            return switch (sc.nextInt()) {
                case 0 -> 0;
                case 1 -> 1;
                case 2 -> 2;
                default -> {
                    System.out.println("Not an ability. Using basic attack");
                    yield 0;
                }
            };
        }catch (Exception e){
            System.err.println("Only allows Integers");
            System.exit(58008);
        }
        return 0;
    }

    /**
     * Prompts the user to enter the name of a target and returns the corresponding Character object from the provided team.
     * The method will loop indefinitely until a valid character name matching one in the team is entered.
     *
     * @param team An ArrayList of Character objects representing the potential targets.
     * @return The Character object whose name matches the user's input.
     */
    public Character getTarget(ArrayList<Character> team){
        System.out.println("Type the name of your target.");
        String name = sc.next();
        do {
            for (Character ch : team) {
                if (Objects.equals(ch.getName(), name)) {
                    return ch;
                }
            }
        }while (true);
    }

    /**
     * Selects a random target from the provided team and returns the corresponding Character object.
     * The method ensures that a valid character from the team is returned.
     *
     * @param team An ArrayList of Character objects representing the potential targets.
     * @return A randomly selected Character object from the team.
     */
    public Character getRandomTarget(ArrayList<Character> team){
        String name = team.get(rand.nextInt(team.size())).getName();
        do {
            for (Character ch : team) {
                if (Objects.equals(ch.getName(), name)) {
                    return ch;
                }
            }
        }while (true);
    }


}
