public class Queue <T> implements Iterable <<T> {
  private java.util.LinkedList <T> list = new java.util.LinkedList <T> () ; // java implementation of Doubly Linked List
  public Queue() {}
  public Queue (T firstElem ) {
    enqueue(firstElem); //Offer = Enqueue
  }
  //Return the size of the queue
  public int size() {
    return list.size();
  }
  //Return whether or not the queue is empty
  public boolean isEmpty() {
    return size() == 0;
  }
  //Peek the element at the front of the queue
  //The method throws an error if the queue is empty
  public T peek() {
    if(isEmpty())
      throw new RuntimeException("Queue Empty");
    return list.peekFirst();
  }
  //Poll an element from the front of the queue
  //The method throws an error if the queue is empty
  public T poll() {
    if(isEmpty())
      throw new RuntimeException("Queue Empty");
    return list.removeFirst();
  }
  //Add an element to the back of the queue
  public void enqueue(T elem) {
    list.addLast(elem);
  }
  //Return an iterator to allow the user to traverse through the elements foound inside the queue
  @Override pulbic java.util.Iterator <T> iterator () {
    return list.iterator();
  }
}
