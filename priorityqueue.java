import java.util.*;

class PQueue <T extends Comparable <T>> {
  //The number of elements currently inside the heap
  private int heapSize = 0;
  
  //The internal Capacity of the heap or total size of heap
  private int heapCapacity = 0;
  
  // A dynamic list to track the elements inside the heap
  private List <T> heap = null;
  
  //This map keeps track of the possible indices a particular node is found in the heap.
  //Having this mapping lets us have O(log(n)) removals & O(1) elements containment check 
  //at the cost of some additional space and minor overhead.
  private Map <T, TreeSet<Integer>> map = new HashMap<>();
  
  //Construct and initialize empty priority queue
  public PQueue() {this(1);}
  //Construct a priority queue with an initial capacity
  public PQueue(int sz){
    heap = new ArrayList(sz);
  }
  
  //Construct a priority queue using heapify in O(n) time
  public PQueue(T[] elems) {
    heapSize = heapCapacity = elems.length;
    heap = new ArrayList<T> (heapCapacity);
    
    //Place all the elements in the heap
    for(int i = 0; i < heapsize; i++) {
      mapAdd(elems[i], i);
      heap.add(elems[i]);
    }
    //Heapify process, Here RT = O(n) , Generallt RT O(log(n))
    for(int i = Math.max(0, (heapSize/2)-1); i >= 0; i--)
      sink(i);
  }
  // Priority queue construction, O(nlog(n))
  public PQueue (Collection <T> elems) {
    this(elem.size());
    for(T elem : elems) add(elem);
  }
  
  //Returns true/false depending on if the priority queue is empty 
  public boolean isEmpty() {
    return heapSize == 0;
  }
  
  //Clears everything inside the heap , O(n)
  public void clear() {
    for(int i = 0; i < heapcapacity ; i++)
      heap.set(i.null);
    heapSize = 0;
    map.clear();
  }
  
  //Return the size of the heap
  public int size(){
    return heapSize;
  }
  
  //Returns the value of the element with the lowest priority in this priority queue. 
  //If the prority queue is empty it returns null.
  public T peek() {
    if (isEmpty()) return null;
    return heap.get(0);
  }
  
  //Remove the root of the heap, O(log(n))
  public T poll() {
    return removeAt(0);
  }
  
  //Test if an element is in heap, O(1)
  public boolean contains (T elem) {
    
    //Map lookup to check contaiment, O(1)
    if (elem == null) return false;
    return map.containsKey(elem);
    
    //Linear scan to check containment , O(n)
    //for(int i = 0; i < heapSize; i++)
    //  if(heap.get(i).equals(elem))
    //    return true;
    //return false;
    
    //Adds an element t the priority queue , the element must not be null, O(log(n))
    public void add(T elem) {
      if( elem == null) throw new IllegalArgumentException();
      
      if(heapSize < heapCapacity)
        heap.set(heapSize, elem);
      else {
        heap.add(elem);
        heapCapacity++;
      }
      mapAdd(elem, heapSize); //Add tp map to keep track of elem
      swim(heapSize); //elem added at the end of the heap, swim up the element
      heapSize++;
    }
    
    //Tests if the value of node i <= node j
    //This method assumes i & j are valid indices, O(1)
    private boolean less(int i, int j){
      T node1 = heap.get(i);
      T node2 = heap.get(j);
      return node1.compareTo(node2) <= 0;
    }
    
    //Bottom up node swim, O(log(n)
    private void swim(int k){
      //Grab the index of the parent node of k
      int parent = (k-1)/2; 
      
      //Keep swimming while we have not reached the root & while we're less than our parent.
      while (k > 0 && less(k, parent)) { //k < parent (MINHEAP)
        swap(parent, k);
        k = parent;
        parent = (k-1)/2;
      }
    }
    
    //Top down node sink, O(log(n))
    private void sink (int k) {
      while(true){
        int left = 2*k + 1;
        int right = 2*k + 2;
        int smallest = left;
        if(right < heapSize && less(right,left))
           smallest = right;
        if(left >= heapSize || less(k,smallest)) break; //termination condition
        //Move down the tree following the smallest node
        swap(smallest, k);
        k = smallest;
     }
    }
    
    //Swap two nodes. Assume i & j are valid, O(1)
    private void swap(int i , int j){
      T ielem = heap.get(i);
      T jelem = heap.get(j);
      
      heap.set(i, jelem);
      heap.set(j, ielem);
      
      mapSwap(ielem, jelem, i, j);
    }
    
    //Remove a particular element int the heap, O(log(n))
    public boolean remove(T element){
      if(element == null) return false;
      
      //Linear reomoval via serach, O(n)
      //for(int i = 0; i < heapSize;i++) {
      //  if( element.equals(heap.get(i))){
      //    removeAt(i);
      //    return true;
      //  }
      // }
      
     //Logarithmic removal map, O(log(n)
      Integer index = mapGet(element);
      if(index != null) removeAt(index);
      return index != null;
    }
    
    //Removes a node at a particular index, O(log(n))
    private T removeAt(int i){
      if(isEmpty()) return null;
      
      heapSize--;
      T removed_data = heap.get(i);
      swap(i, heapSize);
      
      //Obliterate the value
      heap.set(heapSize, null);
      mapRemove(removed_data, heapSize);
      
      //Removed last element 
      if(i == heapSize) return removed_data;
      
      T elem = heap.get(i);
      
      //Try sinking element
      sink(i);
      //If sinking didn't work try swimming
      if( heap.get(i).equals(elem))
        swim(i);
      return removed_data;
    }
    
    //Recursively checks if this heap is a min heap
    //This method is just for testing integrity of min heap
    //& it makes sure heap invariant is maintained , k = 0 is the root
    public boolean isMinHeap(int k) {
      //If we are outside the bounds of the heap return true
      if(k >= heapsize) return true;
      
      int left = 2 * k + 1;
      int right = 2 * k + 2;
      
      // Make sure the current node k is less than both of its children left & right , if the exist
      // otherwise return false to indicate an invalid heap
      if(left < heapSize && !less(k, left))   return false;
      if(right < heapSize && !less(k, right)) return false;
      
      //Recurse on both children to make sure they're also valid heap
      return isMinHeap(left) && isMinHeap(right);
    }
    
    //Add a node value and its index to the map
    private void mapAdd(T value , int index) {
      TreeSet <Integer> set = map.get(value); //TreeSet use becuz it is a binary banlanced tree
      
      //New value being inserted in the map
      if(set == null) {
        set = new TreeSet<>();
        set.add(index);
        map.put(value, set);
      }
      // value already exists in the map
      else set.add(index);
    }
    
    //Remove sthe index at a given value, O(log(n))
    private void mapRemove(T value, int index) {
      TreeSet <Integer> set = map.get(value);
      set.remove(index); // TreeSet takes O(log(n)) removal time
      if(set.size() == 0) map.remove(value);
    }
    
    //Extract index position of the given value 
    //Note: If a value exists multiple times in the heap the highest index is returned( That has been arbitrarily chosen)
    private Integer mapGet(T value) {
      TreeSet <Integer> set = map.get(value);
      if(set != null) return set.last();
      return null;
    }
    
    //Exchange the index of the two nodes internally within the map
    private void mapSwap(T val1, T val2 , int val1Index, int val2Index) {
      Set <Integer> set1 = map.get(val1);
      Set <Integer> set2 = map.get(val2);
      
      set1.remove(val1Index);
      set2.remove(val2Index);
      
      set1.add(val2Index);
      set2.add(val1Index);
    }
    
    @Override public String toString() {
      return heap.toString();
    }
  }
  
