package testing.mockito;

public class Dictionary {
    private final DictionaryRepository repository;

    public enum DictionaryStatus {
        ADDED,
        UPDATED,
        INVALID,
        NOT_FOUND;
    }

    public Dictionary (DictionaryRepository repository) {
        this.repository = repository;
    }

    public DictionaryStatus addOrUpdateWord(String word, String definition) {
        if (word.equals("")) {
            return DictionaryStatus.INVALID;
        }

        if ((word.matches(".*[0-9].*"))) {
            return DictionaryStatus.INVALID;
        }

        if (repository.getDefinition(word) == null) {
            repository.add(word, definition);
            return DictionaryStatus.ADDED;
        } else {
            repository.update(word, definition);
            return DictionaryStatus.UPDATED;
        }
    }

    public String getDefinition(String word) {
        if (word.equals("")) {
            return "Invalid value";
        }

        if ((word.matches(".*[0-9].*"))) {
            return "Invalid character(s)";
        }

        if (repository.getDefinition(word) == null) {
            return "Not found";
        } else {
            return "The definition is:";
        }
    }
}
