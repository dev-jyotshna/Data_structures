public class Stack <T> implements Iterable <T> {
  private java.util.LinkedList <T> list = new java.util.LinkedList <T> ();
  
  //Create an empty stack
  public Stack() {} 
  
  // Create a stack with an initial element
  public Stack(T firstElem) {
    push(firstElem);
  }
  
  //Return the number of elements in theh stack
  public int size() {
    return list.size();
  }
  
  //Check if the stack is empty
  public boolean isEmpty(){
    return size() == 0;
  }
  
  // Push an element on the stack
  public void push(T elem) {
    list.addLast(elem);
  }
  
  //Pop an elemet off the stack
  //Throw an error if the stack is empty
  public T pop() {
    if(isEmpty())
      throw new java.util.EmptyStackException();
    return list.removeLast();
  }
