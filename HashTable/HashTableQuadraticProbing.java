import java.util.*;

@SuppressWarnings("unchecked")
public class HashTableQuadraticProbing <K,V> implements Iterable <K> {
  private double loadFactor;
  private int capacity, threshold, modificationCount = 0;
  
  // 'usedBuckets' counts the total number of used buckets inside the hashtable( includes cells marked as deleted). 
  //While 'keyCount' tracks the number of unique keys currently inside the hash-table.
  private int usedBuckets = 0, keyCount = 0;
  
  //These arrays store the key-value pairs.
  private K [] keyTable;
  private V [] valueTable;

  //Flag used to indicate whether an item was found in the hash table
  private boolean containsFlag = false;

  //Special marker token used to indicate the deletion of a key- value pair 
  private final K TOMBSTONE = (K) (new Object());
  
  private static final int DEFAULT_CAPACITY = 8;
  private static final double DEFAULT_LOAD_FACTOR = 0.45;

  public HashTableQuadraticProbing() {
     this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
  }

  public HashTableQuadraticProbing(int capacity) {
     this(capacity, DEFAULT_LOAD_FACTOR);
  }

  //Designated constructor
  public HashTableQuadraticProbing ( int capacity, double loadfactor) {
     if( capacity <= 0)
        throw new IllegalArgumentException(" Illegal capacity: " + capacity);

     if ( loadFactor <= 0 || Double.isNaN(loadFactor) || Double.isInfinite(loadFactor))
             throw new IllegalArgumentException(" Illegal loadFactor: " + loadFactor);

     this.loadFactor = loadFactor;
     this.capacity = Math.max(DEFAULT_CAPACITY, next2Power(capacity));
     threshold = (int) (this.capacity * loadFactor);

     keyTable = (K[]) new Object[this.capacity];
     valueTable = (V[]) new Object[this.capacity];
  }

  //Given a number this method finds the next power of two above this value.
  private static int next2Power( int n) {
     return Integer.highestOneBit(n) << 1;
  }

  //Quadratic probingn function (x^2 + x)/2
  private static int P( int x) {
        return (x*x +x) >> 1;
  }

  //Converts a hashvalue to an index.Essentially, this strips the negative sign and 
  //places the hash value in the domain [0, capacity]
  private int normalizeIndex(int keyHash) {
        return (keyHash & 0x7FFFFFFF) % capacity;
  }

  //Clears all the contents of the hash-table
  public void clear() {
     for( int i = 0; i < capacity; i++) {
        keyTable[i] = null;
        valueTable[i] = null;
     }
     keyCount = usedBuckets = 0;
     modificationCount++;
  }

 //Returns the number of keys currently inside the hashTable
 public int size() { return keyCount;}

 //Returns true/false depending on whether the hash table is emty
 public boolean isEmpty() { return keyCount == 0;}

5:36:16
