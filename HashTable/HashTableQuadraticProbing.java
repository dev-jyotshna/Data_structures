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
