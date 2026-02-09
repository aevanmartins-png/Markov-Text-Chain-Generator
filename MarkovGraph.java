package comprehensive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Class that represents a MarkovGraph with input text words stored in a HashMap.
 * Includes methods for generating the most probable chain, weighted random chain, and k most probable words.
 *
 * @author Andy Martins and Sherry Pan
 * @version April 23, 2024
 */
public class MarkovGraph {

    private HashMap<String, VertexNode> vertices;
    private VertexNode prevWord;

    /**
     * Constructor for MarkovGraph.
     * Takes a List of words as input and builds a HashMap with the input List.
     *
     * @param input List containing Strings to be put in the MarkovGraph
     */
    public MarkovGraph(List<String> input){
        vertices = new HashMap<String,VertexNode>();
        buildMap(input);

    }

    /**
     * Goes through the list of strings and adds the word to the MarkovGraph
     *
     * @param input List of Strings to be put into the MarkovGraph
     */
    private void buildMap(List<String> input){
        for(String word : input){
            addWord(word);
        }
    }

    /**
     * Adds the current word to the MarkovGraph and puts it in the Map of words that follow the previous word
     *
     * @param nodeName String the word to add to the graph
     */
    private void addWord(String nodeName){

        // if the current word is the first word to be added, sets the prev word to null and adds the word to the graph
        if(prevWord == null){

            // creates a vertexNode with the current word and puts the node in the graph with its name and the node reference
            prevWord = new VertexNode(nodeName);
            vertices.put(nodeName, prevWord);
            return;
        }

        VertexNode node;

        // if the node already exists in the HashMap, adds an occurrence of it
        if(vertices.containsKey(nodeName)){
            node = vertices.get(nodeName);
            node.addOccurrence();
        }

        // creates a new node with the word and puts it in the MarkovGraph with the word and the node
        else{
            node = new VertexNode(nodeName);
            vertices.put(nodeName, node);
        }

        // if the word has a previous word, adds the word to the Map containing words that follow the previous word
        prevWord.addEdge(nodeName);
        prevWord = node; // sets the current node to the previous node to use the next time the method is called
    }

    /**
     * Returns a list of the k most probable next words if the seed word exists
     *
     * @param seed String, the word to search for the k most probable words that come after it
     * @param k int the number of most probable words to return that comes after the current word
     * @return List of most probable words that come after the seed word
     */
    public List<String> findKLargest(String seed, int k){

        if(!vertices.containsKey(seed)){
            throw new NoSuchElementException("Seed word is not in graph");
        }
        // returns a list of the k most probable next words
        return vertices.get(seed).kMostProbable(k);
    }

    /**
     * Generates a chain of length k that chooses each word by taking
     * the most probable next word from each previous word
     *
     * @param seed String the starting word
     * @param k int the length of the output chain
     * @return List the chain of most probable next words
     */
    public List<String> generateMostProbableChain(String seed, int k){

        if(!vertices.containsKey(seed)){
            throw new NoSuchElementException("Seed word is not in graph");
        }

        VertexNode currNode = vertices.get(seed); // gets the seed word from the MarkovGraph
        List<String> outputChain = new ArrayList<>();

        // adds the current word to the output and then sets the current word to the most probable next word k times
        for(int i = 0; i < k; i++ ){
            outputChain.add(currNode.getWord());
            currNode = vertices.get(currNode.mostProbableNextWord());

            // if there were no available words, sets the current node back to the seed
            if(currNode == null){
                currNode = vertices.get(seed);
            }
        }

        return outputChain;
    }

    /**
     * Generates a chain of length k that chooses each word by taking
     * a weighted random next word from each previous word
     *
     * @param seed String the starting word
     * @param k int the length of the output chain
     * @return List of weighted random next words that form a chain
     */
    public List<String> generateWRS(String seed, int k){

        if(!vertices.containsKey(seed)){
            throw new NoSuchElementException("Seed word is not in graph");
        }

        VertexNode currNode = vertices.get(seed); // gets seed word from the MarkovGraph
        List<String> outputChain = new ArrayList<>();

        // adds the current word to the output and then sets the current word to a random following word k times
        for(int i = 0; i < k; i++){
            outputChain.add(currNode.getWord());
            currNode = vertices.get(currNode.getWeightedRandomNextWord());

            // if there were no available words, sets the current node back to the seed
            if(currNode == null){
                currNode = vertices.get(seed);
            }

        }
        return outputChain;
    }

}
