package cz.spsejecna.universeclash.items;

import cz.spsejecna.universeclash.PathFinder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ItemRepository {
    private final HashMap<Integer, Item> items = new HashMap<>();

    /**
     * Constructs an `ItemRepository` and automatically loads all item data
     * from the configured JSON file.
     */
    public ItemRepository() {
        loadItems();
    }

    /**
     * Loads item data from a JSON file specified by `PathFinder.itemsFilePath`.
     * It parses the JSON array, and for each item object, extracts its ID, type, name, and description.
     * Based on the 'type' field, it instantiates the appropriate `Item` subclass (Potion, Debuffer, Grenade, or Misc)
     * using its respective builder and stores it in the `items` HashMap.
     * If there's an error during file reading or parsing, it prints an error message and exits the program.
     */
    private void loadItems(){
        JSONParser parser = new JSONParser();

        try {
            JSONArray a = (JSONArray) parser.parse(new FileReader(PathFinder.itemsFilePath));
            for (Object o : a) {
                JSONObject jsonItem = (JSONObject) o;
                int id = Math.toIntExact((long) jsonItem.get("id"));
                String itemType = (String) jsonItem.get("type");
                String name = (String) jsonItem.get("name");
                String description = (String) jsonItem.get("description");
                switch (itemType) {
                    case "POTION":
                        items.put(id, new Potion.PotionBuilder().setId(id).setName(name).setDescription(description).build());
                        break;
                    case "DEBUFFER":
                        items.put(id, new Debuffer.DebufferBuilder().setId(id).setName(name).setDescription(description).build());
                        break;
                    case "GRENADE":
                        items.put(id, new Grenade.GrenadeBuilder().setId(id).setName(name).setDescription(description).build());
                        break;
                    case "MISC":
                        items.put(id, new Misc.MiscBuilder().setId(id).setName(name).setDescription(description).build());
                        break;
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to load items. Shutting down.\n" + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Returns a new HashMap containing all the items currently loaded in the repository.
     * This method creates a copy of the internal `items` HashMap to prevent direct modification
     * of the repository's data structure from outside.
     *
     * @return A `HashMap` where keys are item IDs (Integer) and values are `Item` objects.
     */
    public HashMap<Integer, Item> getItems() {
        return (HashMap<Integer, Item>) items.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Retrieves a copy of an `Item` from the repository based on its ID.
     * It uses specific `copy` methods for each item type (Potion, Debuffer, Grenade, Misc)
     * to ensure that a new instance of the item is returned, rather than a reference
     * to the original item in the repository. This prevents unintended modifications
     * to the master item objects.
     *
     * @param itemid The integer ID of the item to retrieve.
     * @return A copied `Item` object if found, or `null` if an item with the given ID does not exist.
     */
    public Item obtainItem(int itemid){
        if (items.containsKey(itemid)) {
            // Using switch expression for concise item copying based on ID ranges
            return switch (itemid){
                case 0, 1 -> items.get(itemid).copyPotion();
                case 2, 3 -> items.get(itemid).copyDebuffer();
                case 4, 5 -> items.get(itemid).copyGrenade();
                case 6, 7 -> items.get(itemid).copyMisc();
                default -> {
                    System.out.println("Searched for an item with an unknown ID type: " + itemid);
                    yield null;
                }
            };
        } else {
            System.out.println("Searched for an item that doesn't exist with ID: " + itemid);
            return null;
        }
    }
}