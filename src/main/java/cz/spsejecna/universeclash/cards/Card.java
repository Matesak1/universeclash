package cz.spsejecna.universeclash.cards;

public class Card {
    private final int id;
    private final String name;
    private final String description;

    /**
     * Constructs a `Card` object using the provided `CardBuilder`.
     * This constructor is private, enforcing the use of the `CardBuilder` for object creation.
     * This promotes a more readable and flexible way to instantiate `Card` objects.
     *
     * @param builder The `CardBuilder` instance containing the desired properties for the card.
     */
    public Card(CardBuilder builder){
        this.id  = builder.id;
        this.name = builder.name;
        this.description = builder.description;
    }

    /**
     * A static nested builder class for creating `Card` objects.
     * This builder allows for a step-by-step construction of a `Card` instance,
     * making the object creation process clearer and more flexible by setting properties individually.
     */
    public static class CardBuilder{
        public int id;
        public String name;
        public String description;

        /**
         * Sets the unique identifier for the `Card` being built.
         *
         * @param id The integer ID of the card.
         * @return The current `CardBuilder` instance for method chaining.
         */
        public CardBuilder setId(int id){
            this.id = id;
            return this;
        }

        /**
         * Sets the name for the `Card` being built.
         *
         * @param name The string name of the card.
         * @return The current `CardBuilder` instance for method chaining.
         */
        public CardBuilder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the description for the `Card` being built, detailing its effect.
         *
         * @param description The string description of the card's effect.
         * @return The current `CardBuilder` instance for method chaining.
         */
        public CardBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * Builds and returns a new `Card` object with the properties set in this builder.
         *
         * @return A new `Card` instance.
         */
        public Card build(){
            return new Card(this);
        }
    }

    /**
     * Returns the name of the card.
     *
     * @return A string representing the card's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the card, explaining its effect.
     *
     * @return A string providing details about the card's function.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the unique integer ID of the card.
     *
     * @return The card's ID.
     */
    public int getCardId() {
        return id;
    }

    /**
     * Creates and returns a new `Card` object that is a deep copy of the current instance.
     * This method is useful for creating independent duplicates of card items,
     * ensuring that modifications to the copy don't affect the original template.
     *
     * @return A new `Card` object with identical ID, name, and description.
     */
    public Card copy(){
        return new CardBuilder().setId(this.id).setName(this.name).setDescription(this.description).build();
    }

    /**
     * Returns a string representation of the `Card`, including its ID, name, and description.
     *
     * @return A formatted string showing the card's details.
     */
    @Override
    public String toString() {
        return "{id: "+id+"} "+ name +" ("+description+')';
    }
}