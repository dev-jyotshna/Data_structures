public class DoublyLinkedList <T> implements Iterable <T> {
  private int size = 0;
  private Node <T> head = null;
  private Node <T> tail = null;
  
  //Internal node class to represent data
  private class Node <T> {
    T data;
    NOde <T> prev, next;
    public Node (T data , Node <T> prev , Node <T> next){
      this.data = data;
      this.prev = prev;
      this.next = next;
    }
    @Override public String toSTring() {
      return data.toSTring();
    }
  }
  
  //Empty this linked list, O(n)
  public void clear() {
    
