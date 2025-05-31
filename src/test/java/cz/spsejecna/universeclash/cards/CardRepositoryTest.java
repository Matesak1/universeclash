package cz.spsejecna.universeclash.cards;

import cz.spsejecna.universeclash.effects.Effect;
import cz.spsejecna.universeclash.effects.EffectRepository;
import cz.spsejecna.universeclash.effects.EffectTypeEnum;
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
