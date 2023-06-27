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

    for( int i = 1; i < tree.length; i++) {
      int j = i + lsb(i);
      if( j < tree.length) tree[j] += tree[i];
    }
  }

// Returns the value of the least significant bit (LSB) 
//lsb(108) = lsb(0b1101100) = 0b100 = 4
//lsb(104) = lsb(0b1101000) = 0b1000 = 8
//lsb(96) = lsb(0b1100000)  = 0b100000 = 32
//lsb(64) = lsb(0b1000000)  = 0b1000000 = 64
 private int lsb( int i ) {

  //Isolates the lowest one bit value
  return i & -i;
   //An alternative method is to use the Java's built-in method
   //return Integer.lowestOneBit(i);
 }
