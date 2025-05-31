package cz.spsejecna.universeclash.abilities;

public class Ability {

    private String abilityName;
    private String abilityDescription;

    /**
     * Constructs an `Ability` object using the provided `AbilityBuilder`.
     * This private constructor ensures that `Ability` instances are created
     * exclusively through the builder pattern, promoting a more readable
     * and flexible construction process.
     *
     * @param builder The `AbilityBuilder` instance containing the desired properties for the ability.
     */
    private Ability(AbilityBuilder builder) {
        this.abilityName = builder.abilityName;
        this.abilityDescription = builder.abilityDescription;
    }

    /**
     * Returns the name of the ability.
     *
     * @return A string representing the ability's name.
     */
    public String getAbilityName() {
        return abilityName;
    }

    /**
     * Returns the description of the ability.
     *
     * @return A string explaining what the ability does.
     */
    public String getAbilityDescription() {
        return abilityDescription;
    }

    /**
     * A static nested builder class for constructing `Ability` objects.
     * This builder provides a fluent API to set the name and description
     * of an ability before it's built, enhancing readability and maintainability.
     */
    public static class AbilityBuilder{
        public String abilityName;
        public String abilityDescription;

        /**
         * Sets the name of the ability being built.
         *
         * @param abilityName The name of the ability.
         * @return The current `AbilityBuilder` instance for method chaining.
         */
        public AbilityBuilder setAbilityName(String abilityName) {
            this.abilityName = abilityName;
            return this;
        }

        /**
         * Sets the description of the ability being built.
         *
         * @param abilityDescription A detailed description of the ability's effect.
         * @return The current `AbilityBuilder` instance for method chaining.
         */
        public AbilityBuilder setAbilityDescription(String abilityDescription) {
            this.abilityDescription = abilityDescription;
            return this;
        }

        /**
         * Builds and returns a new `Ability` object with the properties
         * set in this builder.
         *
         * @return A new `Ability` instance.
         */
        public Ability build(){
            return new Ability(this);
        }
    }

    /**
     * Returns a string representation of the `Ability`, including its name and description.
     * The format is `(abilityName) abilityDescription`.
     *
     * @return A formatted string showing the ability's name and description.
     */
    @Override
    public String toString() {
        return "\n(" + abilityName + ") " + abilityDescription;
    }
}