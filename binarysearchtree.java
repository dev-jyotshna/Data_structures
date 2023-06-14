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
      root = add(root, elem); // Private add is used in public add method.. :)
      nodeCount++;
      return true;
    }
  }
  
  //Private method to recursively add a value in the binary tree.
  private Node add(Node node, T elem) {
    
    //Base case : found a leaf node
    if(node == null)
      node = new Node ( null, null, elem);
    else {
      //Place lower element values in the left subtree
      if( elem.compareTo)node.data) < 0) 
        node.left = add(node.left, elem);
      else
        node.right = add(node.right, elem);
    }
    return node;
  }
  
  //Remove a value from this binary tree, if it exists.
  public boolean remove(T elem) {
    //Make sure the node we want to remove actually exists before we remove it.
    if( contains(elem)) {
      root = remove( root, elem); //Private remove method is used in public remove method.. ;)
      nodeCount--;
      return true;
    }
    return false;
  }
  
  private Node remove( Node node, T elem) {
    if(node == null) return null;
    
    int cmp = elm.compareTo(node.data);
    //FIND PHASE
    //First check in the left subtree for the node we're looking for.
    if( cmp < 0) {
      node.left = remove( node.left, elem);
    }
    //Check the right subtree for the node we're looking for. 
    else if( cmp > 0) {
      node.right = remove( node.right, elem);
    }
    //Found the node we wish to remove
    else{
      
      //This is the case where node only has a right subtree.
      if(node.left ==null) {
        Node rightChild = node.right;
        node.data = null; // Destroy the data & the node itself
        node = null;
        
        return rightChild;
      }
      
      //This is the case where node only has a left subtree.
      else if (node.right ==null) {
        Node leftChild = node.left;
        node.data = null; // Destroy the data & the node itself
        node = null;
        
        return leftChild;
      }
      
      //Case where node has both lrft subtree & right subtree
      else {
        // Find the leftmost(smallest) node in the right subtree
        Node tmp = digLeft(node.right);
        node.data = tmp.data; //Swap the data
        //Go into the right subtree & remove the leftmost node, we found and swapped data with. This prevents us from having two nodes in our tree with the same value.
        node.right = remove(node.right, tmp.data);
        
        //If instead we wanted to find the largest node in the left subtree as opposed to smallest node in the right subtree :
        Node tmp = digRight( node.left);
        node.data = tmp.data;
        node.left = remove( node.left, tmp.data);
      }
    }
    return node;
  }
  
  //Helper method to find the leftmost node
  private Node digLeft( Node node) {
    Node cur = node;
    while( cur.left != null) 
      cur = cur.left;
    return cur;
  }
   //Helper method to find the rightmost node
  private Node digRight( Node node) {
    Node cur = node;
    while( cur.right != null) 
      cur = cur.right;
    return cur;
  }
  
  //Returns true if the element exists in the tree
  public boolean contains(T elem) {
    return contains(root, elem); //Called private contains method (recursive)
  }
  
  //Private recursive method to find an element in the tree
  private boolean contains(Node node, T elem) {
    //Base case: Reached bottom , value not found
    if(node == null) return false;
    
    int cmp = elem.compareTo(node.data);
    //Dig into left subtree because the value we're looking for is smaller than the current value
    if( cmp < 0) return contains(node.left, elem);
    
    //Dig into right subtree because the value we're looking for is greater than the current value
    else if ( cmp > 0) return contains(node.right, elem);
    
    //We found the value we were looking for
    else return true;
  }
  
  //Computes th eheight of the tree, O(n)
  public int height() {
    return height(root);
  }
  
  //Recursive helper method to compute the height of the tree
  private int height( Node node) {
    if( node == null) return 0;
    return Math.max( height( node.left), height( node.right)) + 1;
  }
  
    // This method returns an iterator for a given TreeTraversalOrder.
  // The ways in which you can traverse the tree are in four different ways:
  // preorder, inorder, postorder and levelorder.
  public java.util.Iterator<T> traverse(TreeTraversalOrder order) {
    switch (order) {
      case PRE_ORDER:
        return preOrderTraversal();
      case IN_ORDER:
        return inOrderTraversal();
      case POST_ORDER:
        return postOrderTraversal();
      case LEVEL_ORDER:
        return levelOrderTraversal();
      default:
        return null;
    }
  }

  // Returns as iterator to traverse the tree in pre order
  private java.util.Iterator<T> preOrderTraversal() {

    final int expectedNodeCount = nodeCount;
    final java.util.Stack<Node> stack = new java.util.Stack<>();
    stack.push(root);

    return new java.util.Iterator<T>() {
      @Override
      public boolean hasNext() {
        if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
        return root != null && !stack.isEmpty();
      }

      @Override
      public T next() {
        if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
        Node node = stack.pop();
        if (node.right != null) stack.push(node.right);
        if (node.left != null) stack.push(node.left);
        return node.data;
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  // Returns as iterator to traverse the tree in order
  private java.util.Iterator<T> inOrderTraversal() {

    final int expectedNodeCount = nodeCount;
    final java.util.Stack<Node> stack = new java.util.Stack<>();
    stack.push(root);

    return new java.util.Iterator<T>() {
      Node trav = root;

      @Override
      public boolean hasNext() {
        if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
        return root != null && !stack.isEmpty();
      }

      @Override
      public T next() {

        if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();

        // Dig left
        while (trav != null && trav.left != null) {
          stack.push(trav.left);
          trav = trav.left;
        }

        Node node = stack.pop();

        // Try moving down right once
        if (node.right != null) {
          stack.push(node.right);
          trav = node.right;
        }

        return node.data;
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  // Returns as iterator to traverse the tree in post order
  private java.util.Iterator<T> postOrderTraversal() {
    final int expectedNodeCount = nodeCount;
    final java.util.Stack<Node> stack1 = new java.util.Stack<>();
    final java.util.Stack<Node> stack2 = new java.util.Stack<>();
    stack1.push(root);
    while (!stack1.isEmpty()) {
      Node node = stack1.pop();
      if (node != null) {
        stack2.push(node);
        if (node.left != null) stack1.push(node.left);
        if (node.right != null) stack1.push(node.right);
      }
    }
    return new java.util.Iterator<T>() {
      @Override
      public boolean hasNext() {
        if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
        return root != null && !stack2.isEmpty();
      }

      @Override
      public T next() {
        if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
        return stack2.pop().data;
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  // Returns as iterator to traverse the tree in level order
  private java.util.Iterator<T> levelOrderTraversal() {

    final int expectedNodeCount = nodeCount;
    final java.util.Queue<Node> queue = new java.util.LinkedList<>();
    queue.offer(root);

    return new java.util.Iterator<T>() {
      @Override
      public boolean hasNext() {
        if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
        return root != null && !queue.isEmpty();
      }

      @Override
      public T next() {
        if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
        Node node = queue.poll();
        if (node.left != null) queue.offer(node.left);
        if (node.right != null) queue.offer(node.right);
        return node.data;
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }
}
