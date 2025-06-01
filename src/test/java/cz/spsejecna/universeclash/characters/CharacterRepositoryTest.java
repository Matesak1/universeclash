package cz.spsejecna.universeclash.characters;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class CharacterRepositoryTest {
    @Test
    public void loadCharacterTest(){
        CharacterRepository characterRepository = new CharacterRepository();
        assertEquals(characterRepository.getCharacters().size(), 13);

        Character ch = characterRepository.obtainCharacter(1);
        assertEquals(ch.getName(), "Leafy");


        Character cha = characterRepository.obtainCharacter(25);
        assertNull(cha);
    }
}
