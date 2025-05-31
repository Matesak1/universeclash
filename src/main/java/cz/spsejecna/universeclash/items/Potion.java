package cz.spsejecna.universeclash.items;


public class Potion extends Item{
    /**
     * Constructs a Potion object using the provided PotionBuilder.
     * This constructor is private to enforce the use of the PotionBuilder for object creation.
     *
     * @param builder The PotionBuilder instance containing the desired properties for the Potion.
     */
    public Potion(PotionBuilder builder) {
        super(builder.id, builder.name, builder.description);
    }
    /**
     * A static nested builder class for creating Potion objects.
     * It allows for a more readable and flexible way to construct Potion instances
     * by setting properties individually before calling the build method.
     */
    public static class PotionBuilder{
        public int id;
        public String name;
        public String description;
        /**
         * Sets the ID for the Potion being built.
         *
         * @param id The integer ID of the potion.
         * @return The current PotionBuilder instance for method chaining.
         */
        public PotionBuilder setId(int id){
            this.id = id;
            return this;
        }
        /**
         * Sets the name for the Potion being built.
         *
         * @param name The string name of the potion.
         * @return The current PotionBuilder instance for method chaining.
         */
        public PotionBuilder setName(String name) {
            this.name = name;
            return this;
        }
        /**
         * Sets the description for the Potion being built.
         *
         * @param description The string description of the potion's effect.
         * @return The current PotionBuilder instance for method chaining.
         */
        public PotionBuilder setDescription(String description) {
            this.description = description;
            return this;
        }
        /**
         * Builds and returns a new Potion object with the properties set in this builder.
         *
         * @return A new Potion instance.
         */
        public Potion build(){
            return new Potion(this);
        }

    }
    /**
     * Creates and returns a new Potion object that is a deep copy of the current instance.
     * This is useful for creating new instances with the same properties without modifying the original.
     *
     * @return A new Potion object with identical ID, name, and description.
     */
    public Item copy(){
        return new Potion.PotionBuilder().setId(this.id).setName(this.name).setDescription(this.description).build();
    }
}
