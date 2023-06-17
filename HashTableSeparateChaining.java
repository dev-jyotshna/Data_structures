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
  
  //Returns the number of 
