package com.sabre.mapper;

import com.google.common.collect.Sets;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;


public class SetOfWordsToLetterMapperTest {

    @Test
    @DisplayName("Null argument test")
    public void nullArgumentTest(){
        Optional<Map<Character, Set<String>>> characterSetMap = SetOfWordsToLetterMapper.adjustEveryWordToLettersItContains(null);
        Assertions.assertTrue(characterSetMap.isEmpty());
    }
    @ParameterizedTest
    @ValueSource(strings = "Ala ma kota")
    @DisplayName("Any element in map test")
    public void anyElementInMapTest(String testString){
        Map<Character, Set<String>> characterSetMap = invokeMethodAndObtainValueFromOptional(testString);
        MatcherAssert.assertThat(characterSetMap.isEmpty(), is(false));
    }
    @ParameterizedTest
    @ValueSource(strings = {
            "Ala ma",
            "Ala ma kota, kot koduje w Javie Kota",
            "Moim zdaniem to nie ma tak, że dobrze albo że nie dobrze..",
            "Lorem ipsum dolor sit amet"
    })
    @DisplayName("Checking if map has all letters from given sentence preserved as keys")
    public void mapShouldContainAllGivenLettersTest(String testString){
        Set<Character> characterSet = getCharactersSetFromString(testString);
        Map<Character, Set<String>> characterSetMap = invokeMethodAndObtainValueFromOptional(testString);
        Assertions.assertTrue(Sets.symmetricDifference(characterSet,characterSetMap.keySet()).isEmpty());
    }

    private Set<Character> getCharactersSetFromString(String testString) {
        return testString
                .replaceAll("\\p{Punct}","")
                .toLowerCase()
                .replace(" ", "")
                .chars()
                .mapToObj(words->(char)words)
                .collect(Collectors.toSet());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Ala ma ! kota, kot ! ! ! ! %%%%% ^ & koduje w Javie Kota",
    })
    @DisplayName("Punctuation avoiding test")
    public void punctuationAvoidingTest (String testString){
        Map<Character, Set<String>> characterSetMap = invokeMethodAndObtainValueFromOptional(testString);
        Set<Character> keySet = characterSetMap.keySet()
                .stream()
                .filter(character -> containsCharacters(character, ",!&%."))
                .collect(Collectors.toSet());

        MatcherAssert.assertThat(keySet, is(empty()));
    }

    @ParameterizedTest
    @ValueSource(strings = "Chrząszczyżewoszyce, powiat Łękołody")
    @DisplayName("Polish words compliancy test")
    public void polishWordsCompliancyTest(String testString) {
        Map<Character, Set<String>> characterSetMap = invokeMethodAndObtainValueFromOptional(testString);
        Set<Character> keySet = characterSetMap.keySet()
                .stream()
                .filter(character -> containsCharacters(character, "żąłę"))
                .collect(Collectors.toSet());
        MatcherAssert.assertThat(keySet, is(not(empty())));

    }

    private Map<Character, Set<String>> invokeMethodAndObtainValueFromOptional(String testString){
        return SetOfWordsToLetterMapper.adjustEveryWordToLettersItContains(testString).get();
    }


    private boolean containsCharacters(Character element, String characters) {
        return Pattern.compile("[" + characters + "]").matcher(element.toString()).find();
    }

}