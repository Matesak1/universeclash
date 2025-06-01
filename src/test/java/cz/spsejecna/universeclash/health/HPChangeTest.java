package cz.spsejecna.universeclash.health;

import cz.spsejecna.universeclash.characters.Character;
import cz.spsejecna.universeclash.characters.CharacterRepository;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class HPChangeTest {

    @Test
    public void loadCharacterTest(){
        CharacterRepository characterRepository = new CharacterRepository();

        Character ch = characterRepository.obtainCharacter(1);
        assertEquals(ch.getCurrentHP(), 10);
        ch.changeHP(-3);
        assertEquals(ch.getCurrentHP(), 7);



    }


}
