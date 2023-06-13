public class BinarySearchTree <T extends Comparable<T>> {
  
  //Tracks the number of nodes in this BST
  private int nodeCount = 0;
  
  //This BST is a rooted tree so we maintain a handle on the root node
  private Node root = null;
  
  //Internal node containing node references and the actual node data.
  private class Node {
    T data;
    Node left, right;
    public Node ( Node left, Node right, T elem) {
      this.data = elem;
      this.left = left;
      this.right = right;
    }
  }
  //Check if this binary tree is empty
  public boolean isEmpty() {
    return size() == 0;
  }
  
  //Get the number of nodes in this binary tree
  public int size() {
    return nodeCount;
  }
  
  //Add an element to this binary tree, Returns true if we successfully perform the insertion.
  public boolean add(T elem) {
    //Check if the value already exists in this binary tree, if it does ignore adding it.
    if(contains(elem)) {
      return false;
    }
    //Otherwise add this element to the binary tree.
    else{
      root = add(root, elem);
      nodeCount++;
      return true;
    }
  }
  3:48:20
