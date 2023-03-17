package testing.mockito;

public interface DictionaryRepository {

    void add(String key, String value);

    void update(String key, String value);

    String getDefinition(String word);
}