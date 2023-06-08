public class Queue <T> implements Iterable <<T> {
  private java.util.LinkedList <T> list = new java.util.LinkedList <T> () ;
  public Queue() {}
  public Queue (T firstElem ) {
    pffer(firstElem);
  }
  //Return the size of the queue
  public int size() {
    return list.size();
  }
  //Return whether or not the queue is empty
  public boolean isEmpty() {
    return size() == 0;
  }
