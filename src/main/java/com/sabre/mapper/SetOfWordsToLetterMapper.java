package com.sabre.mapper;

import java.util.*;

public class SetOfWordsToLetterMapper {
    public static Optional<Map<Character, Set<String>>> adjustEveryWordToLettersItContains(String testString) {
        return Optional
                .ofNullable(testString)
                .map(testStringFromOptional -> testStringFromOptional.replaceAll("\\p{Punct}", ""))
                .map(testStringFromOptional -> {
                    Map<Character, Set<String>> lettersToWordsMap = new TreeMap<>();
                    var words = new HashSet<>(Arrays.asList(testStringFromOptional.split(" ")));
                    words.stream()
                            .map(String::toLowerCase)
                            .forEach(word ->
                                    word.chars()
                                            .mapToObj(integer -> (char) integer)
                                            .forEach(givenChar -> {
                                                Set<String> wordsThatContainsGivenLetter = lettersToWordsMap.getOrDefault(givenChar, new TreeSet<>());
                                                wordsThatContainsGivenLetter.add(word);
                                                lettersToWordsMap.put(givenChar, wordsThatContainsGivenLetter);
                                            }));
                    return lettersToWordsMap;
                });
    }
}
