package ascii_art;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Algorithms {

    /**
     * find the duplicate number in n+1 -len array filled with 1-n numbers.
     * @param numList
     * @return
     */
    public static int findDuplicate(int[] numList){
        int lenArray = numList.length;
        int first = numList[lenArray-1];
        int second = numList[numList[lenArray-1]-1];
        while (first != second){
            first = numList[first-1];
            second = numList[numList[second-1]-1];
        }
        first = lenArray;
        while (first != second){
            first = numList[first-1];
            second = numList[second-1];
        }
        return first;
    }

    /**
     * find how many unique morse words in the array.
     * @param words array words.
     * @return the number of the unique morse words.
     */
    public static int uniqueMorseRepresentations(String[] words){
        Set<String> morseWordsSet = new HashSet<>();
        Map<Character,String> charToMorse = new HashMap<>();
        charToMorse.put('a', ".-");charToMorse.put('b', "-...");charToMorse.put('c',  "-.-");
        charToMorse.put('d',  "-..");charToMorse.put('e',    "."); charToMorse.put('f', "..-.");
        charToMorse.put('g',  "--.");charToMorse.put('h', "....");charToMorse.put('i',   "..");
        charToMorse.put('j', ".---");charToMorse.put('k',   "-.");charToMorse.put('l', ".-..");
        charToMorse.put('m',   "--");charToMorse.put('n',   "-.");charToMorse.put('o',  "---");
        charToMorse.put('p', ".--.");charToMorse.put('q', "--.-");charToMorse.put('r', ".-.");
        charToMorse.put('s',  "...");charToMorse.put('t',   "-");charToMorse.put('u',  "..-");
        charToMorse.put('v', "...-");charToMorse.put('w',  ".--");charToMorse.put('x', "-..-");
        charToMorse.put('y', "-.--");charToMorse.put('z', "--..");
        for(String word: words){
            String morseWord = "";
            for (int i = 0; i < word.length(); i++) {
                morseWord = morseWord.concat(charToMorse.get(word.charAt(i)));
            }
            morseWordsSet.add(morseWord);
        }
        return morseWordsSet.size();
    }
}
