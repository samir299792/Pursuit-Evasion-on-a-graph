/**
 * Samir Bikram Dhami
 * Filename: Heap.java
 * April 25th, 2024
 * This file represents a heap. 
 */

 /*
  * importing all the required libraries.
  */
import java.util.Comparator;
import java.util.ArrayList;

public class Heap<T> implements PriorityQueue<T>{
    private Comparator<T> comparator; // the comparator
    private ArrayList<T> heap; // the heap

    /**
     * Constructor for a Heap based on the given comparator and the given boolean
     * indicating max or min.
     * 
     * @param comparator the comparator
     * @param maxHeap boolean, true if max, otherwise false
     */
    @SuppressWarnings("unchecked")
    public Heap(Comparator<T> comparator, boolean maxHeap) {
        this.heap = new ArrayList<>();
        if (comparator != null) {
            this.comparator = comparator;
        } else {
            // Default comparator assuming T extends Comparable<T>
            this.comparator = new Comparator<T>() {
                @Override
                public int compare(T o1, T o2) {
                    return ((Comparable<T>) o1).compareTo(o2);
                }
            };
        }
        if (maxHeap) {
            // Reversing the comparator for max heap
            this.comparator = new Comparator<T>() {
                @Override
                public int compare(T o1, T o2) {
                    return -comparator.compare(o1, o2);
                }
            };
        }
    }
    // Creates a min heap with the comparator provided 
    public Heap(Comparator<T> comparator){
        this(comparator, false);
    }
    /**
     * swaps the data at idx1 and idx2.
     * 
     * @param idx1 first index.
     * @param idx2 second index.
     */
    private void swap(int idx1, int idx2){
        T temp;
        temp = heap.get(idx1);
        heap.set(idx1,heap.get(idx2));
        heap.set(idx2, temp);
    }

    /**
     * returns the index of the parent
     * 
     * @param idx given index
     * @return the index of the parent.
     */
    private int getParentIdx( int idx ){
        return (idx - 1) / 2;
    }  

    /**
     * returns the index of the left child
     * 
     * @param idx given index
     * @return the index of the left child.
     */
    private int getLeftChildIdx( int idx ){
        return idx * 2 + 1;
    }  

    /**
     * returns the index of the right child
     * 
     * @param idx given index
     * @return the index of the right child.
     */
    private int getRightChildIdx( int idx ){
        return idx * 2 + 2;
    }

    /**
     * Repeatedly swaps the item up the heap starting from the given index until
     * it is bigger than the item above it.
     * 
     * @param idx the index to bubble up from.
     */
    private void bubbleUp(int idx) {
        while (idx != 0 && comparator.compare(heap.get(getParentIdx(idx)), heap.get(idx)) > 0) {
            int parentIdx = getParentIdx(idx); // Get the current parent index
            swap(idx, parentIdx); // Perform the swap
            idx = parentIdx; // Update idx to move up to the parent's position
        }
    }

    /** 
     * The specified node gets sifted down until it
     * satisfies the Heap property.
     * 
     * @param idx the index to bubble down from.
     */
    private void bubbleDown(int idx) {
        int size = heap.size();
        while (true) {
            int leftIdx = getLeftChildIdx(idx);
            int rightIdx = getRightChildIdx(idx);
            int smallest = idx;
    
            if (leftIdx < size && comparator.compare(heap.get(leftIdx), heap.get(smallest)) < 0) {
                smallest = leftIdx;
            }
            if (rightIdx < size && comparator.compare(heap.get(rightIdx), heap.get(smallest)) < 0) {
                smallest = rightIdx;
            }
            if (smallest != idx) {
                swap(idx, smallest);
                idx = smallest; // Continue bubbling down from the new position
            } else {
                break; // If the current element is in the correct place, stop the loop
            }
        }
    }

    /**
     * method to return the size of the heap.
     */
    public int size(){
        return heap.size();
    }

    /**
     * method to return the data of highest priority.
     */
    public T peek() {
        if (heap.isEmpty()) {
            return null; // Return null if the heap is empty
        }
        return heap.get(0);
    }

    /**
     * Adds the specified item into the Heap. 
     * Puts item at the next empty spot in the heap, and calls bubbleUp.
     */
    public void offer(T item){
        heap.add(item); // Adds the item to the next empty spot at the end of the ArrayList
        bubbleUp(heap.size() - 1); // Calls bubbleUp from the last index to restore heap property
    }

    /**
     * returns and removes the item of highest priority.
     */
    public T poll() {
        if (heap.isEmpty()) {
            return null; // Return null if the heap is empty to avoid exceptions
        }
    
        T out = heap.get(0); // Get the root element, which is the item of highest priority
        T lastItem = heap.remove(heap.size() - 1); // Remove the last item and reduce the size of the heap
    
        if (!heap.isEmpty()) {
            heap.set(0, lastItem); // Replace the root with the last item only if the heap is not empty after removal
            bubbleDown(0); // Restore the heap property by bubbling down the new root
        }
    
        return out; // Return the original root element
    }

    /**
     * Updates the priority of the given item - that is, ensures that it is 'behind'
     * items with higher priority and 'ahead' of items with lower priority
     */
    public void updatePriority(T item) {
        // Find the index of the item
        int index = heap.indexOf(item);
        if (index == -1) {
            throw new IllegalArgumentException("Item does not exist in the heap");
        }
    
        // Determine if we need to bubble up or bubble down
        boolean bubbleUp = true;
    
        if (index != 0 && comparator.compare(heap.get(index), heap.get(getParentIdx(index))) < 0) {
            // If the item is not the root and less than its parent in a min-heap, bubble up
            bubbleUp = true;
        } else if ((getLeftChildIdx(index) < heap.size() && comparator.compare(heap.get(index), heap.get(getLeftChildIdx(index))) > 0) ||
                   (getRightChildIdx(index) < heap.size() && comparator.compare(heap.get(index), heap.get(getRightChildIdx(index))) > 0)) {
            // If the item has children and is greater than any of them in a min-heap, bubble down
            bubbleUp = false;
        }
    
        if (bubbleUp) {
            bubbleUp(index);
        } else {
            bubbleDown(index);
        }
    }
    
    // helper method for toString
    public String toString() {
        int depth = 0 ;
        return toString( 0 , depth );
    }
    
    // returns the string representation of the heap.
    private String toString( int idx , int depth ) {
        if (idx >= this.size() ) {
            return "";
        }
        String left = toString(getLeftChildIdx( idx ) , depth + 1 );
        String right = toString(getRightChildIdx( idx ) , depth + 1 );

        String myself = "\t".repeat(depth) + this.heap.get( idx ) + "\n";
        return right + myself + left;
    }
}
