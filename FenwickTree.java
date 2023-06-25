//Fenwick Tree( Binary Indexed Tree)
public class FenwickTree {
  //This array conatains the Fenwick Tree ranges
  private long[] tree;

  //Create an empty Fenwick Tree 
  public FenwickTree( int sz) {
    tree = new long[sz + 1];
  }

  //Make sure the 'values' array is one-based meaning
  //values[0] does not get used, O(n) construction
  public FenwickTree(long[] values) {
    if( values == null) 
      throw new IllegalArgumentException( " Values array cannot be null! ");

    //Make a clone of the values array since we manipulate the array
    //in place destroying all its original content.
    this.tree = values.clone();
