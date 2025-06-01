package cz.spsejecna.universeclash.items;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class ItemRepositoryTest {
    @Test
    public void loadItemTest(){
        ItemRepository itemRepository = new ItemRepository();
        assertEquals(itemRepository.getItems().size(), 8);

        Item item = itemRepository.obtainItem(0);
        assertEquals(item.getName(), "HP potion");

        Item i = itemRepository.obtainItem(15);
        assertNull(i);
    }
}
