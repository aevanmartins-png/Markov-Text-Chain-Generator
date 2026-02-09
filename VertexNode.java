package comprehensive;

import java.util.*;

/**
 * Class that creates a vertex node containing the word and a HashMap of the words that follow the current word.
 * The Hashmap contains the name of the node following this node, along with the number of times it appears after
 * this word.
 *
 * @author Andy Martins and Sherry Pan
 * @version April 23, 2024
 */
public class VertexNode {

    private String word;
    private int numOccurrences; // tracks how many times this word appears
    private int numAfterWords; // tracks how many words come after the word

    // hashmap of all nodes/words that follow this word and the number of times it comes after the word
    private HashMap<String,Integer> outWords = new HashMap<>();

    /**
     * Constructor that sets the word to the give word and the number of occurrences to 1
     *
     * @param word Input word
     */
    public VertexNode(String word){
        this.word = word;
        numOccurrences = 1;
    }

    /**
     * Adds next word to the Hashmap containing all the words that follow this word
     *
     * @param nextWord String the word that comes after this word
     */
    public void addEdge(String nextWord){

        // if the word has already been recorded, increases the corresponding integer
        if(outWords.containsKey(nextWord)){
            outWords.put(nextWord, outWords.get(nextWord) + 1);
        }
        // else adds the next word and sets the number of times it has appeared after the current word to 1
        else{
            outWords.put(nextWord, 1);
        }

        numAfterWords++;

    }

    /**
     * Getter method for the number of times this word occurs in the input file
     *
     * @return int the number of times this word occurs in the input file
     */
    public int getNumOccurrences(){
        return numOccurrences;
    }

    /**
     * Increases the number of times this word occurs in the input
     */
    public void addOccurrence(){
        numOccurrences++;
    }

    /**
     * Getter method for the word
     *
     * @return the word contained at the VertexNode
     */
    public String getWord(){
        return word;
    }

    /**
     * Goes through the keySet of words that follow this word and
     * finds the word with the greatest number of appearances
     *
     * @return String the word that appears most following this word
     */
    public String mostProbableNextWord(){

        String mostProbable = null;
        int greatestVal = 0;

        // Goes through the map that contains every following word
        for(String word : outWords.keySet()){

            int wordValue = outWords.get(word);

            // if it is the first word in the keySet, sets it to the most probable word
            if (mostProbable == null){
                mostProbable = word;
                greatestVal = outWords.get(word);
            }

            /*
            // if the amount of times the word appears after the word is greater than the word with the current
            // largest appearances, replaces the word as the mostProbable word
             */
            if (wordValue> greatestVal || (wordValue == greatestVal && word.compareTo(mostProbable) < 0)){
                mostProbable  = word;
                greatestVal = wordValue;
            }



        }
        return mostProbable;
    }

    /**
     * Takes the map of following words, creates a heap using the words and
     * extracts the most probable word k times. If there are fewer words available than
     * specified with k, returns all available words.
     *
     * @param k int number of items to extract
     * @return List containing k number of most probable words in descending order
     */
    public List<String> kMostProbable(int k) {

        if (k < 0) {
            throw new IllegalArgumentException("k is out of bounds");
        }

        BinaryMaxHeapComp<String> wordHeap = createHeap(); // creates a heap from the map of following words
        List<String> kOutput = new ArrayList<>();

        int count = Math.min(k, wordHeap.size()); // figures out if the number of following words or k is smaller

        // extracts the max word from the heap count times
        for (int i = 0; i < count; i++) {
            kOutput.add(wordHeap.extractMax());
        }
        return kOutput;
    }

    /**
     * Creates a heap using the map of words that follow the current word
     * Uses our heap implementation from assignment 10
     *
     * @return BinaryMaxHeap ordered with the word with the most occurrences after the current word at the top
     */
        private BinaryMaxHeapComp<String> createHeap(){

            List<String> heapList = new ArrayList<>();

            // goes through every word that follows the current word from the map and adds it to the heap
            for (String word : outWords.keySet()){
                heapList.add(word);
            }

            // returns the heap ordered with the largest element at the top
//            return new BinaryMaxHeapComp<>(heapList, (o1, o2) -> outWords.get(o1).compareTo(outWords.get(o2)));
            return new BinaryMaxHeapComp<>(heapList, new compareByFrequency());
        }

    /**
     * Creates a BinaryMaxHeap out of the Map of words that follow the current word
     * and creates a random which is used to decide which word to return.
     *
     * @return String the randomly chosen following word
     */
    public String getWeightedRandomNextWord(){

        // if no words follow the current word returns null
        if(outWords.isEmpty()){
            return null;
        }

        // creates a random int with the upper boundary being the number of times a word follows the current word
        Random rand = new Random();
        int randomNum = rand.nextInt(numAfterWords + 1);

        // creates a heap with all the words that follow the current word
        BinaryMaxHeapComp<String> heap = createHeap();
        
        String current;

        /*
        while the random number is greater than 0, extracts the most probable word and subtracts the times it
        appears after the current word from the random number. Stops when the word is 0 and returns the current
        following word it is at
         */
        do {
            current = heap.extractMax();
            randomNum -= outWords.get(current);
        }
        while(randomNum > 0);

        return current;
    }


    /**
     * This comparator compares by the number of times the string came after the word
     * Ties are broken using lexicographical ordering
      */
    private class compareByFrequency implements Comparator<String>{

        /**
         * Compares two strings for the frequency in which they appear after the current word
         * @param s1 the first object to be compared.
         * @param s2 the second object to be compared.
         * @return negative int if s1 is smaller and positive int if s2 is bigger
         */
        @Override
        public int compare(String s1, String s2) {

            int comparison = outWords.get(s1).compareTo(outWords.get(s2));

            if(comparison == 0){ //If Integer comparison is equal then compare by lexicographical ordering
                return s2.compareTo(s1);
            }
            return comparison;
        }
    }

}
