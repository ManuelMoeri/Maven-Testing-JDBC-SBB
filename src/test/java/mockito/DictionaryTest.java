package mockito;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import testing.mockito.Dictionary;
import testing.mockito.DictionaryRepository;

@RunWith(MockitoJUnitRunner.class)
public class DictionaryTest {
    @Mock
    DictionaryRepository dictionaryRepository;
    @InjectMocks
    Dictionary dictionary;

    @Test
    public void wordNotInDatabase_addOrUpdateWord_addWordToDatabase() {
        assertEquals(Dictionary.DictionaryStatus.ADDED, dictionary.addOrUpdateWord("Flugzeug", "Ein wundertolles, gottleiches, überaus erhabenes flugzeug"));
        Mockito.verify(dictionaryRepository, Mockito.times(1)).add("Flugzeug", "Ein wundertolles, gottleiches, überaus erhabenes flugzeug");
        Mockito.verify(dictionaryRepository, Mockito.never()).update("Flugzeug", "Ein wundertolles, gottleiches, überaus erhabenes flugzeug");
        Mockito.verify(dictionaryRepository, Mockito.times(1)).getDefinition("Flugzeug");
    }

    @Test
    public void wordInDataBase_addOrUpdateWord_updateWord() {
        Mockito.when(dictionaryRepository.getDefinition("Flugzeug")).thenReturn("Neue Definition");
        assertEquals(Dictionary.DictionaryStatus.UPDATED, dictionary.addOrUpdateWord("Flugzeug", "Neue Definition"));
        Mockito.verify(dictionaryRepository, Mockito.never()).add("Flugzeug", "Neue Definition");
        Mockito.verify(dictionaryRepository, Mockito.times(1)).update("Flugzeug", "Neue Definition");
        Mockito.verify(dictionaryRepository, Mockito.times(1)).getDefinition("Flugzeug");
    }

    @Test
    public void wordIsEmpty_addOrUpdateWord_invalidValue() {
        assertEquals(Dictionary.DictionaryStatus.INVALID, dictionary.addOrUpdateWord("", dictionary.getDefinition("")));
        Mockito.verify(dictionaryRepository, Mockito.never()).add("", "");
        Mockito.verify(dictionaryRepository, Mockito.never()).update("", "");
        Mockito.verify(dictionaryRepository, Mockito.never()).getDefinition("");
    }

    @Test
    public void wordWithNumbers_addOrUpdateWord_invalidCharacters() {
        assertEquals(Dictionary.DictionaryStatus.INVALID, dictionary.addOrUpdateWord("Boeing777", "idk"));
        Mockito.verify(dictionaryRepository, Mockito.never()).add("Boeing777", "idk");
        Mockito.verify(dictionaryRepository, Mockito.never()).update("Boeing777", "idk");
        Mockito.verify(dictionaryRepository, Mockito.never()).getDefinition("Boeing777");
    }

    @Test
    public void searchDefinition_getDefinition_returnDefinition() {
        Mockito.when(dictionary.getDefinition("Definition")).thenReturn("The definition is:");
        assertEquals("The definition is:", dictionary.getDefinition("Definition"));
        Mockito.verify(dictionaryRepository, Mockito.never()).add("Definition", "");
        Mockito.verify(dictionaryRepository, Mockito.never()).update("Definition", "");
        Mockito.verify(dictionaryRepository, Mockito.times(1)).getDefinition("Definition");
    }

    @Test
    public void searchDefinition_getDefinition_wordNotFound() {
        assertEquals("Not found", dictionary.getDefinition("Greetings"));
        Mockito.verify(dictionaryRepository, Mockito.never()).add("Greetings", "");
        Mockito.verify(dictionaryRepository, Mockito.never()).update("Greetings", "");
        Mockito.verify(dictionaryRepository, Mockito.times(1)).getDefinition("Greetings");
    }

    @Test
    public void searchDefinition_getDefinition_wordIsEmpty() {
        assertEquals("Invalid value", dictionary.getDefinition(""));
        Mockito.verify(dictionaryRepository, Mockito.never()).add("", "");
        Mockito.verify(dictionaryRepository, Mockito.never()).update("", "");
        Mockito.verify(dictionaryRepository, Mockito.never()).getDefinition("");
    }

    @Test
    public void searchDefinition_getDefinition_wordHasNumbers() {
        assertEquals("Invalid character(s)", dictionary.getDefinition("G'day m8"));
        Mockito.verify(dictionaryRepository, Mockito.never()).add("G'day m8", "");
        Mockito.verify(dictionaryRepository, Mockito.never()).update("G'day m8", "");
        Mockito.verify(dictionaryRepository, Mockito.never()).getDefinition("G'day m8");
    }
}