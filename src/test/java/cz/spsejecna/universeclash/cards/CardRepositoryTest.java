package cz.spsejecna.universeclash.cards;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class CardRepositoryTest {
    @Test
    public void loadCardsTest(){
        CardRepository cardRepository = new CardRepository();
        assertEquals(cardRepository.getCards().size(), 10);

        Card card = cardRepository.obtainCard(6);
        assertEquals(card.getName(), "Anchor");

        Card c = cardRepository.obtainCard(15);
        assertNull(c);
    }
}
