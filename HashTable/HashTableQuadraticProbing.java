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

  //Insert, put and add all place a value in the hash table
  public V put ( K key, V value) {return insert(key, value); }
  public V add( K key, V value) { return insert( key, value); }

  //Place a key - value pair into the hash-table. 
  //If the value already exists inside the hash-table then the value is updated. 
  public V insert( K key, V val) {
    
    if( key == null) throw new IllegalArgumentException(" Null Kay");
    if( usedBuckets >= threshold) resizeTable();

    final int hash = normalizeIndex(key.hashCode());
    int i = hash, j = -1, x = 1; // j -> the 1st TOMBSTONE encountered

    do{
      //The current slot was previously deleted
      if( keyTable[i] == TOMBSTONE) {
        if( j == -1 ) j = i;
      }
      //The current cell already contains a key
      else if( keyTable[i] != null) {
        //The key we're trying to insert already exists in the hash-table,
        // so , update its value with the most recent value
        if( keyTable[i].equals(key)) {

           V oldValue = valueTable[i];
           if( j == -1) {
             valueTable[i] = val;
           }
           else{
             keyTable[i] = TOMBSTONE;
             valueTable[i] = null;
             keyTable[j] = key;
             valueTable[j] = val;
           }
           modificationCount++;
           return oldValue;
        }
      }
      //Current ceel is null so an insertion/update can occur
      else{
        //No previously encountered deleted buckets
        if( j == -1) { //No tombstones found yet
          usedBuckets++; keyCount++;
          keyTable[i] = key;
          valueTable[i] = val;
        }
        //Previously see deleted bucket. Instead of inserting 
        //the new element at i where thenull element is insert it where the deleted token was found.
        else  {
          keyCount++;
          keyTable[j] = key;
          valueTable[j] = val;
        }
        modificationCount++;
        return null;
      }
      i = normalizeIndex(hash + P(x++));
    }while(true);
  }
  // Returns true/false on whether a given key exists in the hash table 
  public boolean containskey(K key) {
    return hashKey(key);
  }

  // Returns true/false on whether a given key exists in the hash table used in containskey
  public boolean hashKey( K key) {
    get(key);
    return containsFlag;
  }
  
  //Get teh value associated with the input key.
  //NOTE: returns null if the value is null AND also returns null if the key does not exists.
  public V get(K key) {
    if( key == null) throw new IllegalArgumentException(" Null Key" );
    final int hash = normalizeIndex(key.hashCode());
    int i = hash, j = -1, x = 1;
    //Starting at the original hash index quadratically probe until we find a spot where our key is 
    //or we hit a null element in which case our element does not exists.

    do { 
      //Ignore deleted cells, but record where the first index of the deleted cell is found
      //to per
