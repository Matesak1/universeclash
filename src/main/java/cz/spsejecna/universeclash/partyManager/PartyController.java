package cz.spsejecna.universeclash.partyManager;
import cz.spsejecna.universeclash.characters.Character;
import cz.spsejecna.universeclash.characters.CharacterRepository;
import java.util.ArrayList;

public class PartyController {

    private final ArrayList<Character> party = new ArrayList<>();
    private final ArrayList<Character> enemyParty = new ArrayList<>();
    private final CharacterRepository characterRepository = new CharacterRepository();

    /**
     * Adds a new character to the player's party. The character is obtained from the
     * character repository using its ID and a copy is added to ensure independent instances.
     * @param id The integer ID of the character to add.
     */
    public void addNewMember(int id){
        party.add(characterRepository.obtainCharacter(id).copy());
    }
    /**
     * Adds a new character to the enemy party. The character is obtained from the
     * character repository using its ID and a copy is added.
     * @param id The integer ID of the character to add as an enemy.
     */
    public void addNewEnemy(int id){
        enemyParty.add(characterRepository.obtainCharacter(id).copy());
    }
    /**
     * Returns the current list of characters in the player's party.
     * @return An `ArrayList` of `Character` objects representing the player's team.
     */
    public ArrayList<Character> getParty() {
        return party;
    }
    /**
     * Returns the current list of characters in the enemy party.
     * @return An `ArrayList` of `Character` objects representing the enemy team.
     */
    public ArrayList<Character> getEnemyParty() {
        return enemyParty;
    }
    /**
     * Clears all characters from the enemy party, effectively resetting it.
     */
    public void resetEnemies(){enemyParty.clear();}
    /**
     * Prints the current state of both the player's team and the enemy team to the console,
     * showing each character's details.
     */
    public void printOutTheFight(){
        System.out.println("Your team:\n"+party);
        System.out.println("\n\nEnemy team:\n"+enemyParty);
    }
    /**
     * Checks if there is at least one character alive in the player's party.
     * This method assumes `removeCorpses()` has been called recently to ensure accuracy.
     * @return `true` if the player's party is not empty (i.e., at least one character is alive),
     * `false` otherwise.
     */
    public boolean isAnyoneAlive(){
        return !party.isEmpty();
    }
    /**
     * Checks if any of the enemy characters have died since the last check.
     * This is typically used to activate effects like "Snowballing" which trigger when an enemy dies.
     * Corpses are removed before the check.
     * @return `true` if the size of the enemy party decreased after removing dead characters,
     * `false` otherwise.
     */
    public boolean activateSnowballing(){
        int saved = enemyParty.size();
        this.removeCorpses();
        return saved > enemyParty.size();
    }
    /**
     * Determines if the player has won the current battle.
     * This is true if all enemy characters have been defeated.
     * Corpses are removed before the check to ensure only living enemies are counted.
     * @return `true` if the enemy party is empty, `false` otherwise.
     */
    public boolean wonBattle(){
        this.removeCorpses();
        return enemyParty.isEmpty();
    }
    /**
     * Removes all dead characters (those with `isAlive()` returning `false`)
     * from both the player's party and the enemy party.
     */
    public void removeCorpses(){
        party.removeIf(ch -> !ch.isAlive());
        enemyParty.removeIf(ch -> !ch.isAlive());
    }
}
