package comprehensive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class to run a predictive AI model in the main method
 * Takes an input file path, a seed word, a k value
 * and either no fourth argument, or "one" or "all" as the fourth argument
 * and returns either a possible chain of k length or the k most likely next words
 *
 * @author Andy Martins and Sherry Pan
 * @version April 23, 2024
 */
public class TextGenerator {

    /**
     * Main method that cleans a file, uses the cleaned words to construct a Markov Graph
     * and prints either a chain of words or the k most probable words depending on the fourth argument.
     *
     * @param args A string array with the file path, seed word, k int, and an optional fourth argument of "one" or "all"
     */

    public static void main(String[] args) {

        List<String> cleanedWords;

        // Takes a file and cleans up the valid words into a List if the file exists.
        try {
            cleanedWords = FileToTextCleanup(args[0]);
        } catch (FileNotFoundException e){
            throw new IllegalArgumentException("File does not exist");
        }

        MarkovGraph graph = new MarkovGraph(cleanedWords);

        // Stores the seed and the k value
        String seed = args[1];
        int k =  Integer.parseInt(args[2]);

        // if there are only 3 arguments, calls the findKLargest method which returns the k most probable next words.
        if(args.length == 3){
            String text = buildTextFromList(graph.findKLargest(seed,k));
            System.out.println(text);

            /*
             if the fourth argument is "all", calls generateWRS method which takes the seed and k int and generates
             chain picking based on the weighted random of all words that come after each seed word.
             */
        } else if (args[3].equals("all")) {
            String text = buildTextFromList(graph.generateWRS(seed,k));
            System.out.println(text);

            /*
             if the fourth argument is "one", calls generateMostProbableChain and makes a chain
             by picking the most probable word for each seed
             */
        } else if (args[3].equals("one")) {
            String text = buildTextFromList(graph.generateMostProbableChain(seed,k));
            System.out.println(text);
        }
        else{
            throw new IllegalArgumentException("4th argument not a valid command");
        }


    }

    /**
     * Takes an input file and returns a list with the input cleaned up
     * so that all letters are lowercase, punctuation is removed,
     * and anything that appears after punctuation is skipped.
     *
     * @param filePath String path to input file
     * @return List of String containing cleaned up words
     */
    public static List<String> FileToTextCleanup(String filePath) throws FileNotFoundException {

        List<String> cleanedList = new ArrayList<>();
        Scanner fileReader = new Scanner(new File(filePath));
        String cleanedWord;

        /*
         while there are still items in the input file, takes the next item in the scanner,
         cleans it, and adds it to the output List as long as the word isn't null or an empty string.
         */
        while(fileReader.hasNext()){

            cleanedWord = cleanWord(fileReader.next());

            if( cleanedWord != null && !(cleanedWord.equals(""))) {
                cleanedList.add(cleanedWord);
            }
        }

        return cleanedList;
    }

    /**
     * Method that takes a word, makes it lowercase, and removes all punctuation and words
     * that come after the puncruation aside from underscores
     *
     * @param word String input word
     * @return String the cleaned up word in front of any punctuation
     */
    private static String cleanWord(String word){
        String input = word;
        input = input.toLowerCase(); // takes a string input and turns it to lowercase

        // splits the word on the given regex and puts it in an array
        String[] splitWord;
        splitWord = input.split("[\\p{P}\\p{S}&&[^_]]+"); // regex removes all punctuation aside from underscores

        // if there is no available strings in the array, returns null
        if(splitWord.length == 0){
            return null;
        }
        // returns the first element of the array as nothing after the split upon puncuation is included
        return splitWord[0];
    }

    /**
     * Takes a List of Strings and uses a stringBuilder to turn them into a chain of strings
     *
     * @param wordList List of Strings that contain the words to be combined into a String
     * @return String the chain of all words within the given list
     */
    private static String buildTextFromList(List<String> wordList){

        StringBuilder stringBuilder = new StringBuilder();

        // for each String in the List, appends it to the StringBuilder with a space at the end
        for(String word: wordList){
            stringBuilder.append(word + " ");
        }

        // removes the last whitespace after the last word has been added
        stringBuilder.deleteCharAt(stringBuilder.length()-1);

        return stringBuilder.toString();
    }

}
