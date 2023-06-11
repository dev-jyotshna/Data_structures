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
      2:19:24
