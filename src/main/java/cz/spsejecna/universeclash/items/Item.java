package cz.spsejecna.universeclash.items;

public abstract class Item {

    protected final int id;
    protected final String name;
    protected final String description;

    /**
     * Constructs a new Item with a given ID, name, and description.
     * This is the base constructor for all item types in the game.
     *
     * @param id The unique integer identifier for the item.
     * @param name The name of the item.
     * @param description A brief description of the item's purpose or effect.
     */
    public Item(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * Returns the name of the item.
     *
     * @return A string representing the item's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the item.
     *
     * @return A string providing details about the item.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the unique integer ID of the item.
     *
     * @return The item's ID.
     */
    public int getItemId() {
        return id;
    }

    /**
     * Creates and returns a new {@link Potion} object that is a copy of this item's properties.
     * This method is designed to be used when you need to specifically create a Potion type
     * from a generic Item reference, ensuring proper object instantiation via its builder.
     *
     * @return A new {@link Potion} instance with identical ID, name, and description.
     */
    public Item copyPotion(){
        return new Potion.PotionBuilder().setId(this.id).setName(this.name).setDescription(this.description).build();
    }

    /**
     * Creates and returns a new {@link Debuffer} object that is a copy of this item's properties.
     * This method is designed to be used when you need to specifically create a Debuffer type
     * from a generic Item reference, ensuring proper object instantiation via its builder.
     *
     * @return A new {@link Debuffer} instance with identical ID, name, and description.
     */
    public Item copyDebuffer(){
        return new Debuffer.DebufferBuilder().setId(this.id).setName(this.name).setDescription(this.description).build();
    }

    /**
     * Creates and returns a new {@link Grenade} object that is a copy of this item's properties.
     * This method is designed to be used when you need to specifically create a Grenade type
     * from a generic Item reference, ensuring proper object instantiation via its builder.
     *
     * @return A new {@link Grenade} instance with identical ID, name, and description.
     */
    public Item copyGrenade(){
        return new Grenade.GrenadeBuilder().setId(this.id).setName(this.name).setDescription(this.description).build();
    }

    /**
     * Creates and returns a new {@link Misc} object that is a copy of this item's properties.
     * This method is designed to be used when you need to specifically create a Misc type
     * from a generic Item reference, ensuring proper object instantiation via its builder.
     *
     * @return A new {@link Misc} instance with identical ID, name, and description.
     */
    public Item copyMisc(){
        return new Misc.MiscBuilder().setId(this.id).setName(this.name).setDescription(this.description).build();
    }

    /**
     * Returns a string representation of the Item, including its ID, name, and description.
     *
     * @return A formatted string showing the item's details.
     */
    @Override
    public String toString() {
        return "\n{id: "+id+"} "+ name +" ("+description+')';
    }
}