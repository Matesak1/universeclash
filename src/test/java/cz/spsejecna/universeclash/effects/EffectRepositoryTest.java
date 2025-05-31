package cz.spsejecna.universeclash.effects;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class EffectRepositoryTest {

    @Test
    public void loadEffectsTest(){
        EffectRepository effectRepository = new EffectRepository();
        assertEquals(effectRepository.getEffects().size(), 11);

        Effect effect = effectRepository.obtainEffect("Fire");
        assertEquals(effect.getName(), "Fire");
        assertEquals(effect.getType(), EffectTypeEnum.DEBUFF);

        Effect e = effectRepository.obtainEffect("WrongEffectName");
        assertNull(e);
    }

}
