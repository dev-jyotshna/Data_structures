import java.util.*;

class Entry <K, V> { //Generic
  int hash;
  K key, V value;
  
  public Entry(K key, V value) {
    this.key = key;
    this.value = value;
    this.hash = key.hashCode();
  }
  
  //We're not overriding the Object equals method
  //No casting is required with this method.
  public boolean equals( Entry <K, V> other) {
    if( hash != other.hash) return false;
    return key.equals( other.key) ;
  }
  
  @Override public String toString() {
    return key + " => " + value;
  }
}

@SuppressWarnings("unchecked")
public class HashTableSeparateChaining <K, V> implements Iterable <K> {
  private static final int DEFAULT_CAPACITY = 3;
  private static final double DEFAULT_LOAD_FACTOR = 0.75;
  
  private double maxLoadFactor;
  private int capacity, threshold, size = 0;
  private LinkedList <Entry<K, V>> [] table;
  
  public HashTableSeparateChaining () {
    this(DEFAULT_CAPACITY, DEAFAULT_LOAD_FACTOR);
  }
  
  public HashTableSeparateChaining ( int capacity) {
    this( capacity, DEFAULT_LOAD_FACTOR);
  }
  
  //Designated constructor
  public HashTableSeparateChaining ( int capacity, double maxLoadFactor) {
    if( capacity < 0)
      throw new IllegalArgumentException( " Illegal capacity" );
    if( maxLoadFactor <= 0 || Double.isNaN(maxLoadFactor) || Double.isInfinite(maxLoadFactor))
      throw new IIlegalArgumentExcaption(" Illegal maxLoadfactor ");
    this.maxLoadFactor = maxLoadfactor;
    this.capacity = Math.max( DEFAULT_CAPACITY, capacity);
    threshold = (int) ( this.capacity * maxLoadFactor);
    table = new LinkedList[this.capacity];
  }
  
  //Returns the number of elements currently inside the hash-table
  public itn size() {return size;}
  
  //Returns true/false depending on whether the hash-table is empty
  public boolean isEmpty() { return size == 0; }
  
  //Converts a hash value to an index. Essentially, this strips the negative sign & places the hash value in the domain [0, capacity)
  private int normalizeIndex( int keyHash) {
    return ( keyHash & 0x7FFFFFFF) % capacity; //hash value to index, as hash value is integer so it can also be -ve
  }
  
  //Clears all the contents of the hash table
  public void clear() {
    Array.fill( table, null);
    size = 0;
  }
  
  public boolean containsKey( K key) { return hasKey(key);}
  
  //Returns true/false depending on whether a key is in the hash table
  public boolean hasKey( K key) {
    int bucketIndex = normalizeIndex(key.hashCode());
    return bucketSeekEntry( bucketIndex, key) != null; //which bucket to choose to enter
  }
  
  //Insert, put and add all place a value in the hash table
  public V put( K key, V value) { return insert( key, value); }
  public V add( K key, V value) { return insert( key, value);}
  
  public V insert( K key, V value) {
    if( key == null) throw new IllegalArgumentException(" Null Key ");
    Entry <K, V> newEntry = new Entry<> ( key, value);
    int bucketIndex = normalizeIndex( newEntry.hash);
    return bucketInsertEntry( bucketIndex, newEntry);
  }
  
  //Get key's values from the map ans returns the value.
  //NOTE: returns null if the value is null ans also returns null if the key does not exists, so watch out..
  public V get(K key) {
    if( key == null) return null;
    int bucketIndex = normalizeIndex(key.hashCode());
    Entry <K, V> entry = bucketSeekEntry( bucketIndex, key);
    if( entry != null) return entry.value;
    return null;
  }
  
  //Removes a key from the map ans returns the value.
  //Note: returns null if the value is null and also returns null if the key does not exits.
  public V remove ( K key) {
    if( key == null) return null;
    int bucketIndex = normalizeIndex(key.hashCode());
    return bucketRemoveEntry( bucketIndex, key);
  }
  
  //Removes an entry from a given bucket if it exists
  private V bucketRemoveEntry( int bucketIndex, K key) {
    Entry <K, V> entry = bucketSeekEntry( bucketIndex, key);
    if( entry != null) {
      LinkedList < Entry < K, V>> links = table[bucketIndex];
      links.remove(entry);
      --size;
      return entry.value;
    }
    else return null;
  }
  
  //Inserts an entry in a given bucket only if the entry does not already exists
  //in the given bucket , but if it does the update the entry value.
  private V bucketInsertEntry( int bucketIndex, Entry <K, V> entry) {
    
    LinkedList <Entry <K, V>> bucket = table[bucketIndex];
    if( bucket == null) table[bucketIndex] = bucket = new LinkedList<> ();
    
    Entry <K, V> existentEntry = bucketSeekEntry(bucketIndex, entry.key);
    if( existentEntry == null) {
      bucket.add(entry);
      if( ++size > threshold) resizeTabel();
      return null;// Use null to indicate that there was no previous entry
    }
    else{
      V oldVal = existentEntry.value;
      existentEntry.value = entry.value;
      return oldVal;
    }
  }
  
  //
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
