package cz.spsejecna.universeclash.items;

public class Grenade extends Item{
    /**
     * Constructs a Grenade object using the provided {@link GrenadeBuilder}.
     * This constructor is intentionally private to enforce the use of the builder pattern,
     * promoting more structured and readable object creation.
     *
     * @param builder The {@link GrenadeBuilder} instance containing the desired properties for the Grenade.
     */
    public Grenade(Grenade.GrenadeBuilder builder) {
        super(builder.id, builder.name, builder.description);
    }

    /**
     * A static nested builder class for creating {@link Grenade} objects.
     * This builder allows for a step-by-step construction of a Grenade instance,
     * making the object creation process clearer and more flexible.
     */
    public static class GrenadeBuilder{
        public int id;
        public String name;
        public String description;

        /**
         * Sets the unique identifier for the Grenade being built.
         *
         * @param id The integer ID of the grenade.
         * @return The current `GrenadeBuilder` instance for method chaining.
         */
        public Grenade.GrenadeBuilder setId(int id){
            this.id = id;
            return this;
        }

        /**
         * Sets the name for the Grenade being built.
         *
         * @param name The string name of the grenade.
         * @return The current `GrenadeBuilder` instance for method chaining.
         */
        public Grenade.GrenadeBuilder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the description for the Grenade being built, explaining its effect.
         *
         * @param description The string description of the grenade's effect.
         * @return The current `GrenadeBuilder` instance for method chaining.
         */
        public Grenade.GrenadeBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * Builds and returns a new {@link Grenade} object with the properties
         * set in this builder.
         *
         * @return A new `Grenade` instance.
         */
        public Grenade build(){
            return new Grenade(this);
        }

    }

    /**
     * Creates and returns a new {@link Grenade} object that is a deep copy of the current instance.
     * This method is useful for creating independent duplicates of Grenade items,
     * ensuring that modifications to the copy don't affect the original.
     *
     * @return A new `Grenade` object with identical ID, name, and description.
     */
    public Item copy(){
        return new Grenade.GrenadeBuilder().setId(this.id).setName(this.name).setDescription(this.description).build();
    }
}