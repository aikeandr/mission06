package edu.isu.cs.cs3308.structures.impl;

import edu.isu.cs.cs3308.SpellChecker;
import edu.isu.cs.cs3308.structures.Set;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A spell checking class which uses a HashSet<T> for the dictionary.
 *
 * @author Andrew Aikens
 */
public class SetSpellChecker implements SpellChecker {
    private Set<String> dictionary = new HashSet<>();

    /**
     * Constructor for the spell checker.
     *
     * @param path - the file path for the dictionary to be used.
     */
    public SetSpellChecker(String path){
        Scanner s = null;
        try{
            s = new Scanner(new BufferedReader(new FileReader(path)));
            while(s.hasNext()){
                dictionary.add(s.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally{
            if(s!=null)
                s.close();
        }
    }

    /**
     * Checks if the given string has any misspelled words and gives suggestions for misspelled words.
     *
     * @param s The string to check the spelling of
     * @return A list of misspelled words with up to five suggestions for correct spelling
     * or a list containing only "No misspelling".
     */
    @Override
    public List<String> check(String s) {
        List<String> answer = new ArrayList<>();
        List<String> suggestions;
        int maxSuggestions;
        int numberMisspelled = 0;                //keeps track of how many words are misspelled
        String[] stringList = s.split("[\\.,\\s!;?:\"]+");
        for (String string : stringList) {
            if(!dictionary.contains(string)) {
                numberMisspelled++;
                suggestions = new ArrayList<>();
                maxSuggestions = 5;                      //limits the number of suggestions
                if (answer.isEmpty())
                    answer.add("Misspelled Words:\n");
                answer.add(numberMisspelled + ". " + string + ": [");
                suggestions = suggest(string);
                if (suggestions.isEmpty())
                    answer.add("No suggestions available.");
                else{
                    if (maxSuggestions > suggestions.size())
                        maxSuggestions = suggestions.size();
                    for(String suggestion : suggestions){
                        if (maxSuggestions > 0) {
                            if (suggestions.size() == 1 || maxSuggestions < 2)
                                answer.add(suggestion);
                            else if (suggestions.size() - 1 != suggestions.indexOf(suggestion))
                                answer.add(suggestion + ", ");
                            maxSuggestions--;
                        }
                    }
                }
                answer.add("]\n");
            }
        }
        if (answer.isEmpty())
            answer.add("No misspellings!");
        return answer;
    }

    /**
     * Finds possible suggestions based on common spelling mistakes. Swapping adjacent characters,
     * inserting a single character, deleting a single character, and replacing a character with another.
     *
     * @param misspelled the given word to find suggestion for
     * @return List<String> of any suggestions found
     */
    private List<String> suggest(String misspelled){
        List<String> suggestions = new ArrayList<>();
        suggestions.addAll(adjacent(misspelled));
        suggestions.addAll(insertChar(misspelled));
        suggestions.addAll(deleteChar(misspelled));
        suggestions.addAll(replaceChar(misspelled));
        return suggestions;
    }

    /**
     * Swaps two adjacent characters in the string and checks if the new string
     * can be found in the dictionary.
     *
     * @param s - the misspelled word
     * @return List<String> of any suggestions found
     */
    private List<String> adjacent(String s){
        List<String> suggestions = new ArrayList<>();
        StringBuilder string;
        for (int i = 0; i < s.length() - 1;i++){
            string = new StringBuilder();
            string.append(s.substring(0,i));
            string.append(s.charAt(i+1));
            string.append(s.charAt(i));
            string.append(s.substring(i+2));
            if(dictionary.contains(string.toString()))
                if(!suggestions.contains(string.toString()))        //Don't add a suggestion if it is the list already
                    suggestions.add(string.toString());
        }
        return suggestions;
    }

    /**
     * Inserts a character at an index and checks to see if the new string
     * can be found in the dictionary.
     *
     * @param s - the misspelled word
     * @return List<String> of any suggestions found
     */
    private List<String> insertChar(String s){
        List<String> suggestions = new ArrayList<>();
        StringBuilder string;
        String character;
        for (int i = 0; i < s.length();i++){
            for(int upperCase = 65; upperCase < 91; upperCase++){
                string = new StringBuilder();
                character = Character.toString((char)upperCase);
                string.append(s.substring(0,i));
                string.append(character);
                string.append(s.substring(i));
                if(dictionary.contains(string.toString()))
                    if(!suggestions.contains(string.toString()))        //Don't add a suggestion if it is the list already
                        suggestions.add(string.toString());
            }
            for(int lowerCase = 97; lowerCase < 123; lowerCase++){
                string = new StringBuilder();
                character = Character.toString((char)lowerCase);
                string.append(s.substring(0,i));
                string.append(character);
                string.append(s.substring(i));
                if(dictionary.contains(string.toString()))
                    if(!suggestions.contains(string.toString()))        //Don't add a suggestion if it is the list already
                        suggestions.add(string.toString());
            }
        }
        return suggestions;
    }

    /**
     * Deletes a single character and checks if the new string is in the dictionary.
     *
     * @param s - the misspelled word
     * @return List<String> of any suggestions found
     */
    private List<String> deleteChar(String s){
        List<String> suggestions = new ArrayList<>();
        StringBuilder string;
        for (int i = 0; i < s.length();i++){
            string = new StringBuilder();
            string.append(s.substring(0,i));
            string.append(s.substring(i+1));
            if(dictionary.contains(string.toString())) {
                if(!suggestions.contains(string.toString()))        //Don't add a suggestion if it is the list already
                    suggestions.add(string.toString());
            }
        }
        return suggestions;
    }

    /**
     * Replaces a single character and checks if the new string is in the dictionary.
     *
     * @param s - the misspelled word
     * @return List<String> of any suggestions found
     */
    private List<String> replaceChar(String s){
        List<String> suggestions = new ArrayList<>();
        StringBuilder string;
        String character;
        for (int i = 0; i < s.length();i++){
            for(int upperCase = 65; upperCase < 91; upperCase++){
                string = new StringBuilder();
                character = Character.toString((char)upperCase);
                string.append(s.substring(0,i));
                string.append(character);
                string.append(s.substring(i+1));
                if(dictionary.contains(string.toString()))
                    if(!suggestions.contains(string.toString()))        //Don't add a suggestion if it is the list already
                        suggestions.add(string.toString());
            }
            for(int lowerCase = 97; lowerCase < 123; lowerCase++){
                string = new StringBuilder();
                character = Character.toString((char)lowerCase);
                string.append(s.substring(0,i));
                string.append(character);
                string.append(s.substring(i+1));
                if(dictionary.contains(string.toString()))
                    if(!suggestions.contains(string.toString()))        //Don't add a suggestion if it is the list already
                        suggestions.add(string.toString());
            }
        }
        return suggestions;
    }
}
