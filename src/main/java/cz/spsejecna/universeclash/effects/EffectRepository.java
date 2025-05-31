package cz.spsejecna.universeclash.effects;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import cz.spsejecna.universeclash.PathFinder;

public class EffectRepository {

    private final HashMap<String, Effect> effects = new HashMap<>();

    /**
     * Constructs an `EffectRepository` and automatically loads all effect data
     * from the configured JSON file.
     */
    public EffectRepository() {
        loadEffects();
    }

    /**
     * Loads effect data from a JSON file specified by `PathFinder.effectsFilePath`.
     * It parses the JSON array and for each effect object, extracts its ID, name, and type.
     * A new `Effect` object is then built with these properties and stored in the `effects` HashMap,
     * using the effect's name as the key. The duration is initially set to -1, implying it's a template.
     * If there's an error during file reading or parsing, it prints an error message and exits the program.
     */
    private void loadEffects(){
        JSONParser parser = new JSONParser();

        try {
            JSONArray a = (JSONArray) parser.parse(new FileReader(PathFinder.effectsFilePath));

            for (Object o : a) {
                JSONObject jsonEffect = (JSONObject) o;
                int id = Math.toIntExact((long) jsonEffect.get("id"));
                String name = (String) jsonEffect.get("name");
                int duration = -1; // Duration is set to -1 as these are template effects
                EffectTypeEnum type = EffectTypeEnum.valueOf((String) jsonEffect.get("type"));

                Effect effect = new Effect.EffectBuilder().setId(id).setName(name).setDuration(duration).setType(type).build();

                effects.put(name, effect);
            }
        } catch (Exception e) {
            System.err.println("Failed to load effects. Shutting down.\n" + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Returns a new HashMap containing copies of all effects currently loaded in the repository.
     * Each effect object returned is a deep copy, ensuring that modifications to these effects
     * do not affect the original template effects stored in the repository.
     *
     * @return A `HashMap` where keys are effect names (String) and values are copied `Effect` objects.
     */
    public HashMap<String, Effect> getEffects() {
        return (HashMap<String, Effect>) effects.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().copy()));
    }

    /**
     * Retrieves a copy of an `Effect` from the repository based on its name.
     * This method ensures that a new instance of the effect is returned,
     * rather than a reference to the original template effect. This prevents
     * unintended modifications to the master effect objects.
     *
     * @param effectname The string name of the effect to retrieve.
     * @return A copied `Effect` object if found, or `null` if an effect with the given name does not exist.
     */
    public Effect obtainEffect(String effectname){
        if (effects.containsKey(effectname)) {
            return effects.get(effectname).copy();
        } else {
            System.out.println("Searched for an effect that doesn't exist: " + effectname);
            return null;
        }
    }
}