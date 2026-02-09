package comprehensive;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class implements a PriorityQueue using a BinaryMaxHeap
 * Only the maximum element can be accessed or removed
 * Type passed must be of type comparable or a comparator must be passed
 *
 * @param <E> Elements being stored in the priority Queue
 * @author Sherry Pan and Andy Martins
 * @version 4/11/2024
 */
public class BinaryMaxHeapComp<E> implements PriorityQueue<E> {

    private E[] heapArray;
    private Comparator<? super E> comparator;
    private int size;

    /**
     * Default constructor of BinaryMaxHeap
     * Creates an empty heap of type E
     * Type passed must be comparable
     */
    @SuppressWarnings("unchecked")
    public BinaryMaxHeapComp(){

        this.heapArray = (E[])new Object[10];
        this.comparator = null;
        this.size = 0;

    }

    /**
     * Creates a BinaryMaxHeap which makes comparisons using the comparator passed
     * @param comparator - Comparator used to determine the maximum element
     */
    @SuppressWarnings("unchecked")
    public BinaryMaxHeapComp(Comparator<? super E> comparator){
        this.heapArray = (E[])new Object[10];
        this.comparator = comparator;
        this.size = 0;


    }

    /**
     * Creates a BinaryMaxHeap containing the elements in the list passed
     * Type must be comparable
     * @param list - A list of elements to be added to the heap
     */
    @SuppressWarnings("unchecked")
    public BinaryMaxHeapComp(List<? extends E> list){
        this.heapArray = (E[]) list.toArray();
        size = list.size();
        buildHeap();
    }

    /**'
     * Creates a BinaryMaxHeap containing the elements and makes comparisons using the comparator passed
     * @param list - A list of elements to be added to the heap
     * @param comparator Comparator used to determine the maximum element
     */
    @SuppressWarnings("unchecked")
    public BinaryMaxHeapComp(List<? extends E> list, Comparator<? super E> comparator){
        this.heapArray = (E[]) list.toArray();
        this.comparator = comparator;
        size = list.size();
        buildHeap();
    }

    /**
     * Adds the given item to this priority queue.
     * O(1) in the average case, O(log N) in the worst case
     *
     * @param item Item to be added
     */
    @Override
    public void add(E item) {

        if(size == heapArray.length){
            growArray();
        }

        // adds item to the end of the array and percolates it up to the right position
        heapArray[size] = item;
        percolateUp(size);
        size++;

    }

    /**
     * Returns, but does not remove, the maximum item this priority queue.
     * O(1)
     *
     * @return the maximum item
     * @throws NoSuchElementException if this priority queue is empty
     */
    @Override
    public E peek() throws NoSuchElementException {
        if (isEmpty()){
            throw new NoSuchElementException("Heap is empty");
        }

        return heapArray[0];
    }

    /**
     * Returns and removes the maximum item this priority queue.
     * O(log N)
     *
     * @return the maximum item
     * @throws NoSuchElementException if this priority queue is empty
     */
    @Override
    public E extractMax() throws NoSuchElementException {
        if (isEmpty()){
            throw new NoSuchElementException("Heap is empty");
        }

        E max = heapArray[0];

        // sets the root to the last element (overwriting the original root) and percolates it down
        heapArray[0] = heapArray[size - 1];
        percolateDown(0);
        size--;

        return max;
    }

    /**
     * Returns the number of items in this priority queue.
     * O(1)
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns true if this priority queue is empty, false otherwise.
     * O(1)
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Empties this priority queue of items.
     * O(1)
     */
    @Override
    @SuppressWarnings("unchecked")
    public void clear() {
        heapArray = (E[]) new Object[10];
        size = 0;
    }

    /**
     * Creates and returns an array of the items in this priority queue,
     * in the same order they appear in the backing array.
     * O(N)
     * <p>
     * (NOTE: This method is needed for grading purposes. The root item
     * must be stored at index 0 in the returned array, regardless of
     * whether it is in stored there in the backing array.)
     */
    @Override
    public Object[] toArray() {

        Object[] output = new Object[size];  // makes a new array with the capacity of the size

        for (int i = 0; i < size; i++){
            output[i] = heapArray[i];
        }

        return output;
    }


    /**
     * Puts the backing array into a valid heap ordering where children are stored at 2 * i + 1 and 2 * i + 2
     */
    private void buildHeap(){

        // percolates down for every element in the first half of the array starting from the middle
        for (int i = size/2; i >= 0; i--){
            percolateDown(i);
        }
    }

    /**
     * Puts the element at a given index in correct heap ordering by comparing it with its
     * parent, and if it's larger, moves it up the heap until it is in the right location
     *
     * @param index - position of element to be put in order
     */
    private void percolateUp(int index){
        int currIndex = index;

        // While the current element isn't the root, and greater than it's parent
        while(currIndex != 0 && innerCompare(heapArray[currIndex], heapArray[(currIndex - 1)/2]) > 0){

            // swap element with its parent
            E temp = heapArray[currIndex];
            heapArray[currIndex] = heapArray[(currIndex-1)/2];
            heapArray[(currIndex - 1)/2] = temp;

            // update position of the element
            currIndex = (currIndex - 1)/2;

        }
    }

    /**
     * Puts the element at a given index in correct heap ordering by moving it down the heap
     *
     * @param index - position of element to be put in order
     */
    private void percolateDown(int index){
        int currIndex = index;
        int leftChild = 2 * currIndex + 1;
        int rightChild = 2 * currIndex + 2;
        int largest;
        boolean inPlace = false;

        while (!inPlace){

            // if the node is a leaf node, returns as it is already at the bottom level
            if (leftChild >= size && rightChild >= size){
                inPlace = true;
                return;
            }

            // if there is no right child, sets the larger of the children to the left child
            else if (rightChild >= size){
                largest = leftChild;
            }

            // if the node has both children, compares the left and right child to find which is larger
            else {
                if (innerCompare(heapArray[leftChild], heapArray[rightChild]) > 0) {
                    largest = leftChild;
                }
                else{
                    largest = rightChild;
                }
            }

            // if the current node is smaller than the larger child node, swaps the current/parent and larger child node
            if (innerCompare(heapArray[currIndex], heapArray[largest]) < 0){

                E temp = heapArray[currIndex];
                heapArray[currIndex] = heapArray[largest];
                heapArray[largest] = temp;

                // updates the index of the current node to where it was swapped to, and updates the left and right children
                currIndex = largest;
                leftChild = 2 * currIndex + 1;
                rightChild = 2 * currIndex + 2;
            }
            else {
                inPlace = true;
            }

        }

    }

    /**
     * If a comparator doesn't exist, uses comparable's compareTo
     * else if a comparator exists, uses the comparator's compare.
     *
     * @param o1 E object to be compared
     * @param o2 E object to be compared to
     *
     * @return int the result of the comparison
     */
    @SuppressWarnings("unchecked")
    private int innerCompare(E o1, E o2){

        if(comparator == null){
            return ((Comparable<? super E>)o1).compareTo(o2);
        }

        return comparator.compare(o1,o2);
    }

    /**
     * Doubles the array capacity with items in the same index as previously.
     */
    @SuppressWarnings("unchecked")
    private void growArray(){
        E[] tempArray = (E[])new Object[heapArray.length * 2];

        // copies the original array into the expanded array
        for(int i = 0; i < heapArray.length; i++){
            tempArray[i] = heapArray[i];
        }

        heapArray = tempArray;
    }

}
