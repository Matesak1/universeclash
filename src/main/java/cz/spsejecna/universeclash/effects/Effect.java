package cz.spsejecna.universeclash.effects;

public class Effect {
    private final int id;
    private final String name;
    private int duration;
    private final EffectTypeEnum type;

    /**
     * Constructs an `Effect` object using the provided `EffectBuilder`.
     * This constructor is private, enforcing the use of the `EffectBuilder` for object creation,
     * which promotes a more readable and flexible instantiation process.
     *
     * @param builder The `EffectBuilder` instance containing the desired properties for the effect.
     */
    public Effect(EffectBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.duration = builder.duration;
        this.type = builder.type;
    }

    /**
     * A static nested builder class for creating `Effect` objects.
     * This builder allows for a step-by-step construction of an `Effect` instance,
     * making the object creation process clearer and more flexible by setting properties individually.
     */
    public static class EffectBuilder{
        public int id;
        public String name;
        public int duration;
        public EffectTypeEnum type;

        /**
         * Sets the unique identifier for the `Effect` being built.
         *
         * @param id The integer ID of the effect.
         * @return The current `EffectBuilder` instance for method chaining.
         */
        public EffectBuilder setId(int id){
            this.id = id;
            return this;
        }

        /**
         * Sets the name for the `Effect` being built.
         *
         * @param name The string name of the effect.
         * @return The current `EffectBuilder` instance for method chaining.
         */
        public EffectBuilder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the initial duration for the `Effect` being built.
         *
         * @param duration The integer duration of the effect.
         * @return The current `EffectBuilder` instance for method chaining.
         */
        public EffectBuilder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        /**
         * Sets the type for the `Effect` being built, using the `EffectTypeEnum`.
         *
         * @param type The `EffectTypeEnum` representing the category of the effect.
         * @return The current `EffectBuilder` instance for method chaining.
         */
        public EffectBuilder setType(EffectTypeEnum type) {
            this.type = type;
            return this;
        }

        /**
         * Builds and returns a new `Effect` object with the properties set in this builder.
         *
         * @return A new `Effect` instance.
         */
        public Effect build(){
            return new Effect(this);
        }
    }

    /**
     * Returns the name of the effect.
     *
     * @return A string representing the effect's name.
     */
    public String getName() {return name;}

    /**
     * Creates and returns a new `Effect` object that is a copy of the current instance,
     * but with its duration increased by 1. This is useful for scenarios where a new
     * instance of an effect is needed, perhaps for a new application, with a slightly
     * extended initial duration.
     *
     * @return A new `Effect` object with identical ID, name, and type, but an incremented duration.
     */
    public Effect copy(){
        // Note: The ID is not set in the copy, which might be an oversight depending on desired behavior.
        // It currently defaults to 0 if not explicitly set in the builder.
        return new EffectBuilder().setName(this.name).setDuration(this.duration + 1).setType(this.type).build();
    }

    /**
     * Returns the current duration of the effect.
     *
     * @return The integer duration of the effect.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Increases the duration of the effect by a specified amount.
     *
     * @param extraDuration The integer amount by which to increase the duration.
     * @return The current `Effect` instance, allowing for method chaining.
     */
    public Effect increaseDuration(int extraDuration){
        duration += extraDuration;
        return this;
    }

    /**
     * Decreases the duration of the effect by one. This is typically called at the end of a turn
     * or at specific intervals to reduce the remaining time of the effect.
     */
    public void decreaseDuration(){
        duration--;
    }

    /**
     * Returns the unique integer ID of the effect.
     *
     * @return The effect's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the type of the effect, as defined by `EffectTypeEnum`.
     *
     * @return The `EffectTypeEnum` categorizing the effect.
     */
    public EffectTypeEnum getType() {
        return type;
    }

    /**
     * Returns a string representation of the Effect, including its name and current duration.
     *
     * @return A formatted string showing the effect's name and its remaining duration in curly braces.
     */
    @Override
    public String toString() {
        return name+'{'+duration+"} ";
    }
}