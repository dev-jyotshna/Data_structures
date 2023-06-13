//Array based UnionFind But pointer based unionFind can also be implemented
public class UnionFind {
  
  //Number of elements in this union find
  private int size;
  
  //Used to track the sizes of each of the components(group)
  private int[] sz;
  
  //id[i] points to the parent of i, if id[i] = i then i is a root node
  private int[] id;
  
  //Tracks the number  of components in the union find
  private int numComponents;
  
  public UnionFind(int size) {
    if(size <= 0)
      throw new IllegalArgumentException("Size <= 0 is not allowed");
    
    this.size = numComponents = size;
    sz = new int[size];
    id = new int[size];
    
    for(int i = 0; i < size; i++) {
      id[i] = i; //Link to itself (self root)
      sz[i] = 1; //Each component is originally of size one
    }
  }
  
  //Find which component/set 'p' belongs to, takes amortized constant time.
  public int find(int p) { // Does path compressions along the way
    
    //Find the root of the component/set
    int root = p;
    while(root != id[root] )
      root = id[root];  
    
    //Compress the path leading back to the root 
    //Doing this operation is called "path compression".
    //This aldo gives us amortised constant time complexity.
    while (p != root) {
      int next = id[p];
      id[p] = root;
      p = next;
    }
    return root;
  }
  
  //Returns whether or not the elements 'p' & 'q' are in same components(sets/group)
  public boolean connected(int p, int q) {
    return find(p) == find(q); // roots are equal or not
  }
  
  //Returns the size of the components/set/group 'p' belongs to.
  public int componentSize(int p) {
    return sz[find(p)];
  }
  
  //Returns the number of elements int this UnionFind/Disjoint set.
  public int size() {
    return size; // size variable is private
  }
  
  //Returns the number of remaining components/sets/groups
  public int components() {
    return numComponents;
  }
  
  //Unify the components/sets/groups containing elements 'p' & 'q'
  public void unify(int p, int q) {
    int root1 = find(p);
    int root2 = find(q);
    
    //These elements are already int the same group
    if(root1 = root2) return ;
    
    //Merge 2 groups together( smaller gorups into larger groups)
    if(sz[root1] < sz[root2]) {
      sz[root2] += sz[root1];
      id[root1] = root2;
    }
    else {
      sz[root1] += sz[root2];
      id[root2] = root1;
    }
    
    //Since the roots found are different we know that the number of components/sets/groups has decreased by one.
    numComponents--;
  }
}
