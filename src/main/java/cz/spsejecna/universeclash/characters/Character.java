package cz.spsejecna.universeclash.characters;

import cz.spsejecna.universeclash.abilities.Ability;
import cz.spsejecna.universeclash.effects.Effect;
import cz.spsejecna.universeclash.effects.EffectRepository;
import cz.spsejecna.universeclash.effects.EffectTypeEnum;

import java.util.ArrayList;

public class Character {
    private final EffectRepository effectRepository = new EffectRepository();
    private final int characterID;
    private final String name;
    private int maxHP;
    private int currentHP;
    private int maxSP;
    private int currentSP;
    private final ArrayList<Effect> effects = new ArrayList<>();
    private boolean alive = true;
    private final Ability ability1;
    private final Ability ability2;

    /**
     * Constructs a `Character` object using the provided `CharacterBuilder`.
     * This private constructor ensures that `Character` instances are created
     * exclusively through the builder pattern, promoting a more readable
     * and flexible construction process.
     *
     * @param builder The `CharacterBuilder` instance containing the desired properties for the character.
     */
    private Character(CharacterBuilder builder) {
        this.characterID = builder.characterID;
        this.name = builder.name;
        this.maxHP = builder.maxHP;
        this.currentHP = maxHP; // Current HP initializes to max HP
        this.maxSP = builder.maxSP;
        this.currentSP = maxSP; // Current SP initializes to max SP
        this.ability1 = builder.ability1;
        this.ability2 = builder.ability2;
    }

    /**
     * A static nested builder class for constructing `Character` objects.
     * This builder provides a fluent API to set various properties of a character
     * before it's built, enhancing readability and maintainability of character creation.
     */
    public static class CharacterBuilder{
        public int characterID;
        public String name;
        public int maxHP;
        public int maxSP;
        public Ability ability1;
        public Ability ability2;

        /**
         * Sets the unique identifier for the character.
         *
         * @param id The integer ID of the character.
         * @return The current `CharacterBuilder` instance for method chaining.
         */
        public CharacterBuilder setID(int id){
            this.characterID = id;
            return this;
        }

        /**
         * Sets the name of the character.
         *
         * @param name The string name of the character.
         * @return The current `CharacterBuilder` instance for method chaining.
         */
        public CharacterBuilder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the maximum health points for the character.
         *
         * @param maxHP The maximum HP value.
         * @return The current `CharacterBuilder` instance for method chaining.
         */
        public CharacterBuilder setMaxHP(int maxHP) {
            this.maxHP = maxHP;
            return this;
        }

        /**
         * Sets the maximum special points for the character.
         *
         * @param maxSP The maximum SP value.
         * @return The current `CharacterBuilder` instance for method chaining.
         */
        public CharacterBuilder setMaxSP(int maxSP) {
            this.maxSP = maxSP;
            return this;
        }

        /**
         * Sets the first ability of the character with its name and description.
         * It creates a new `Ability` object using its own builder.
         *
         * @param name The name of the first ability.
         * @param description The description of the first ability.
         * @return The current `CharacterBuilder` instance for method chaining.
         */
        public CharacterBuilder setAbility1(String name, String description) {
            this.ability1 = new Ability.AbilityBuilder().setAbilityName(name).setAbilityDescription(description).build();
            return this;
        }

        /**
         * Sets the second ability of the character with its name and description.
         * It creates a new `Ability` object using its own builder.
         *
         * @param name The name of the second ability.
         * @param description The description of the second ability.
         * @return The current `CharacterBuilder` instance for method chaining.
         */
        public CharacterBuilder setAbility2(String name, String description) {
            this.ability2 = new Ability.AbilityBuilder().setAbilityName(name).setAbilityDescription(description).build();
            return this;
        }

        /**
         * Builds and returns a new `Character` object with the properties
         * set in this builder.
         *
         * @return A new `Character` instance.
         */
        public Character build(){
            return new Character(this);
        }
    }

    /**
     * Returns a concise string representation of the character, including their name,
     * current and maximum HP, current and maximum SP, and active effects.
     *
     * @return A formatted string displaying key character stats.
     */
    @Override
    public String toString() {
        return '[' + name + "] "+currentHP+'/'+maxHP+"HP  "+currentSP+'/'+maxSP+"SP  ("+effects+")\n";
    }

    /**
     * Returns a detailed string representation of the character, including their ID,
     * name, maximum HP, maximum SP, and descriptions of both abilities.
     *
     * @return A comprehensive formatted string of the character's details.
     */
    public String fullinfo(){
        return "\n[["+name+"]] {id:"+characterID+"}  HP: "+maxHP+"  SP: "+maxSP+ability1+ability2;
    }

    /**
     * Returns the name of the character.
     *
     * @return The character's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the maximum health points of the character.
     *
     * @return The character's maximum HP.
     */
    public int getMaxHP() {
        return maxHP;
    }

    /**
     * Returns the current health points of the character.
     *
     * @return The character's current HP.
     */
    public int getCurrentHP() {
        return currentHP;
    }

    /**
     * Returns the maximum special points of the character.
     *
     * @return The character's maximum SP.
     */
    public int getMaxSP() {
        return maxSP;
    }

    /**
     * Returns the current special points of the character.
     *
     * @return The character's current SP.
     */
    public int getCurrentSP() {
        return currentSP;
    }

    /**
     * Returns the list of active effects on the character.
     *
     * @return An `ArrayList` of `Effect` objects currently affecting the character.
     */
    public ArrayList<Effect> getEffects() {
        return effects;
    }

    /**
     * Adds a new effect to the character's active effects list.
     * The effect is obtained from the `EffectRepository` and its duration is increased
     * by one (plus the specified duration) before being added.
     *
     * @param effectName The name of the effect to add.
     * @param duration The base duration of the effect to be added.
     */
    public void addEffect(String effectName, int duration){
        // Obtain a copy of the effect and increase its duration before adding
        effects.add(effectRepository.obtainEffect(effectName).copy().increaseDuration(duration+1));
    }

    /**
     * Returns the number of effects currently active on the character.
     *
     * @return The size of the effects list.
     */
    public int getEffectsSize(){
        return effects.size();
    }

    /**
     * Removes all debuff effects from the character's active effects list.
     * Effects are identified as debuffs based on their `EffectTypeEnum`.
     */
    public void removeDebuffs(){
        effects.removeIf(ef -> ef.getType() == EffectTypeEnum.DEBUFF);
    }

    /**
     * Checks if the character is currently alive.
     * A character is considered alive if their current HP is 1 or more.
     *
     * @return `true` if the character is alive, `false` otherwise.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Returns the unique integer ID of the character.
     *
     * @return The character's ID.
     */
    public int getCharacterID(){
        return characterID;
    }

    /**
     * Returns the first ability of the character.
     *
     * @return The `Ability` object representing the character's first ability.
     */
    public Ability getAbility1() {
        return ability1;
    }

    /**
     * Returns the second ability of the character.
     *
     * @return The `Ability` object representing the character's second ability.
     */
    public Ability getAbility2() {
        return ability2;
    }

    /**
     * Changes the character's current HP by a specified amount.
     * HP cannot exceed `maxHP` and cannot drop below 0.
     * If HP drops to 0 or less, the character is marked as not alive.
     *
     * @param changeHP The integer amount to change HP by (positive for healing, negative for damage).
     */
    public void changeHP(int changeHP){
        currentHP += changeHP;
        if (currentHP > maxHP){
            currentHP = maxHP; // Cap HP at maxHP
        } else if(currentHP < 1){
            alive = false; // Character dies if HP drops below 1
            currentHP = 0; // Set current HP to 0 upon death
        }
    }

    /**
     * Changes the character's current SP by a specified amount.
     * SP cannot exceed `maxSP` and cannot drop below 0.
     * If SP drops below 0, an error is printed and the program exits.
     *
     * @param changeSP The integer amount to change SP by (positive for gaining, negative for spending).
     */
    public void changeSP(int changeSP) {
        currentSP += changeSP;
        if(currentSP > maxSP){
            currentSP = maxSP; // Cap SP at maxSP
        } else if(currentSP < 0){
            System.err.println("SP can't go into negatives"); // SP should not be negative
            System.exit(505); // Exit with an error code
        }
    }

    /**
     * Sets the character's maximum HP to a new value.
     *
     * @param maxHP The new maximum HP value.
     */
    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    /**
     * Changes the character's maximum HP by a specified amount.
     * If `maxHP` drops to 0 or less, the character is marked as not alive
     * and a message is printed.
     *
     * @param change The integer amount to change maximum HP by.
     */
    public void changeMaxHP(int change){
        maxHP += change;
        if(maxHP <= 0){
            alive = false; // Character dies if max HP drops to 0 or less
            System.out.println("That was stupid..."); // Debugging message
        }
    }

    /**
     * Changes the character's maximum SP by a specified amount.
     * `maxSP` cannot drop below 0.
     *
     * @param change The integer amount to change maximum SP by.
     */
    public void changeMaxSP(int change){
        maxSP += change;
        if(maxSP < 0){
            maxSP = 0; // Ensure max SP does not go below 0
        }
    }

    /**
     * Restores the character's current HP and current SP to their maximum values.
     * This is useful for resetting a character's stats, for example, between battles.
     */
    public void maxOut(){
        currentHP = maxHP;
        currentSP = maxSP;
    }

    /**
     * Creates and returns a new `Character` object that is a deep copy of the current instance.
     * This method is useful for creating independent duplicates of characters,
     * ensuring that modifications to the copy don't affect the original template character.
     *
     * @return A new `Character` object with identical ID, name, max HP, max SP, and abilities.
     */
    public Character copy(){
        return new Character.CharacterBuilder().setID(characterID).setName(name)
                .setMaxHP(maxHP).setMaxSP(maxSP)
                .setAbility1(ability1.getAbilityName(), ability1.getAbilityDescription())
                .setAbility2(ability2.getAbilityName(), ability2.getAbilityDescription())
                .build();
    }
}