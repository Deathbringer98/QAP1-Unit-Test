package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SuggestionEngineTest {

    private SuggestionEngine suggestionEngine;

    @BeforeEach
    public void setUp() {
        suggestionEngine = new SuggestionEngine();
    }

    @Test
    public void testLoadDictionaryData() {
        try {
            // Adjust the path to your words.txt file as needed
            suggestionEngine.loadDictionaryData(Paths.get("src/resources/words.txt"));
            Map<String, Integer> wordMap = suggestionEngine.getWordSuggestionDB();
            assertFalse(wordMap.isEmpty(), "The word map should not be empty after loading the dictionary data.");
        } catch (Exception e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }

    @Test
    public void testGenerateSuggestions() {
        SuggestionsDatabase mockDatabase = new SuggestionsDatabase();
        mockDatabase.setWordMap(Map.of("hello", 1, "help", 2, "hell", 1, "hero", 1));
        suggestionEngine.setWordSuggestionDB(mockDatabase);

        String suggestions = suggestionEngine.generateSuggestions("helo");
        assertTrue(suggestions.contains("hello"), "Suggestions should contain 'hello'");
        assertTrue(suggestions.contains("help"), "Suggestions should contain 'help'");
        assertTrue(suggestions.contains("hell"), "Suggestions should contain 'hell'");
    }

    @Test
    public void testGenerateSuggestionsNoMatches() {
        SuggestionsDatabase mockDatabase = new SuggestionsDatabase();
        mockDatabase.setWordMap(Map.of("world", 1, "java", 2, "code", 1, "test", 1));
        suggestionEngine.setWordSuggestionDB(mockDatabase);

        String suggestions = suggestionEngine.generateSuggestions("helo");
        assertEquals("", suggestions, "Suggestions should be empty for no matches");
    }
}
