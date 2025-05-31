package cz.spsejecna.universeclash.items;

public class Misc extends Item {
    /**
     * Constructs a Misc item using the provided {@link MiscBuilder}.
     * This constructor is private to ensure that `Misc` objects are created
     * exclusively through its builder for consistency and readability.
     *
     * @param builder The `MiscBuilder` instance containing the properties for the `Misc` item.
     */
    public Misc(Misc.MiscBuilder builder) {
        super(builder.id, builder.name, builder.description);
    }
    /**
     * A static nested builder class for constructing {@link Misc} objects.
     * This pattern allows for a flexible and clear way to set the properties
     * of a `Misc` item step-by-step.
     */
    public static class MiscBuilder{
        public int id;
        public String name;
        public String description;
        /**
         * Sets the unique identifier for the `Misc` item.
         * @param id The integer ID for the item.
         * @return The current `MiscBuilder` instance, allowing for method chaining.
         */
        public Misc.MiscBuilder setId(int id){
            this.id = id;
            return this;
        }
        /**
         * Sets the name of the `Misc` item.
         * @param name The string name of the item.
         * @return The current `MiscBuilder` instance, allowing for method chaining.
         */
        public Misc.MiscBuilder setName(String name) {
            this.name = name;
            return this;
        }
        /**
         * Sets the description of the `Misc` item, detailing its purpose or effect.
         * @param description The string description of the item.
         * @return The current `MiscBuilder` instance, allowing for method chaining.
         */
        public Misc.MiscBuilder setDescription(String description) {
            this.description = description;
            return this;
        }
        /**
         * Builds and returns a new {@link Misc} object with the properties
         * configured in this builder.
         * @return A new `Misc` instance.
         */
        public Misc build(){
            return new Misc(this);
        }

    }
    /**
     * Creates and returns a new {@link Misc} object that is an exact copy
     * of the current instance. This is useful for scenarios where you need
     * a duplicate object without modifying the original.
     * @return A new `Misc` object with the same ID, name, and description.
     */
    public Item copy(){
        return new Misc.MiscBuilder().setId(this.id).setName(this.name).setDescription(this.description).build();
    }
}
