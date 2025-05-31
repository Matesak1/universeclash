package cz.spsejecna.universeclash.items;

import java.util.ArrayList;

public class ItemController {

    private final ArrayList<Item> items = new ArrayList<>();
    private final ItemRepository itemRepository = new ItemRepository();

    /**
     * Adds a new item to the controller's inventory.
     * The item is obtained as a copy from the `ItemRepository` based on its unique ID.
     * This ensures that each item in the inventory is a distinct instance.
     *
     * @param id The integer ID of the item to be added.
     */
    public void addNewItem(int id){
        items.add(itemRepository.obtainItem(id));
    }

    /**
     * Returns the current list of items held by the controller.
     *
     * @return An `ArrayList` of `Item` objects representing the inventory.
     */
    public ArrayList<Item> getItems() {
        return items;
    }
}
