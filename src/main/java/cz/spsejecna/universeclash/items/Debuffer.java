package cz.spsejecna.universeclash.items;

public class Debuffer extends Item{
    /**
     * Constructs a **Debuffer** object using the provided `DebufferBuilder`.
     * This constructor is private to enforce the use of the builder pattern,
     * which helps in creating objects in a more structured and readable way.
     *
     * @param builder The `DebufferBuilder` instance containing the properties for the Debuffer.
     */
    public Debuffer(Debuffer.DebufferBuilder builder) {
        super(builder.id, builder.name, builder.description);
    }

    /**
     * A static nested builder class for creating **Debuffer** objects.
     * This builder allows for a step-by-step construction of a Debuffer instance,
     * providing flexibility and clarity in setting its properties.
     */
    public static class DebufferBuilder{
        public int id;
        public String name;
        public String description;

        /**
         * Sets the **ID** for the Debuffer being built.
         *
         * @param id The integer ID of the debuffer.
         * @return The current `DebufferBuilder` instance for method chaining.
         */
        public Debuffer.DebufferBuilder setId(int id){
            this.id = id;
            return this;
        }

        /**
         * Sets the **name** for the Debuffer being built.
         *
         * @param name The string name of the debuffer.
         * @return The current `DebufferBuilder` instance for method chaining.
         */
        public Debuffer.DebufferBuilder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the **description** for the Debuffer being built, detailing its effect.
         *
         * @param description The string description of the debuffer's effect.
         * @return The current `DebufferBuilder` instance for method chaining.
         */
        public Debuffer.DebufferBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * Builds and returns a new **Debuffer** object with the properties
         * set in this builder.
         *
         * @return A new `Debuffer` instance.
         */
        public Debuffer build(){
            return new Debuffer(this);
        }

    }

    /**
     * Creates and returns a new **Debuffer** object that is a deep copy of the current instance.
     * This method is useful for creating independent duplicates of Debuffer items,
     * ensuring that modifications to the copy don't affect the original.
     *
     * @return A new `Debuffer` object with identical ID, name, and description.
     */
    public Item copy(){
        return new Debuffer.DebufferBuilder().setId(this.id).setName(this.name).setDescription(this.description).build();
    }
}