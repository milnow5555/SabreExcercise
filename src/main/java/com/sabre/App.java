package com.sabre;


import com.sabre.mapper.SetOfWordsToLetterMapper;

import java.util.*;

public class App
{
    public static void main( String[] args ) {

        System.out.println("Please input arbitrary string.. ");
        Scanner scan = new Scanner(System.in);

        Optional<Map<Character, Set<String>>> characterSetMapOptional = SetOfWordsToLetterMapper
                .adjustEveryWordToLettersItContains(scan.nextLine());

        characterSetMapOptional.ifPresent(App::displayResult);
    }

    private static void displayResult(Map<Character, Set<String>> characterSetMap) {
        characterSetMap.forEach((letter, words) -> {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(letter).append(":");
            words.forEach(word -> stringBuilder.append(word).append(", "));
            stringBuilder.deleteCharAt(stringBuilder.length()-2);
            System.out.println(stringBuilder.toString());
        });
    }
}
