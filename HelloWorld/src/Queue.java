
public class Queue<T> {
	
	private static class Node<T> {
		T value;
		Node<T> next;
		
		Node(T value) {
			this.value = value;
		}
	}
	
	
	private Node<T> head;
	private Node<T> tail;
	private int length;
	
	
	public Queue() {
		this.head = null;
		this.tail = null;
		this.length = 0;
	}
	
	public int length() {
		return length;
	}
	
	public void enqueue(T item) {
		Node<T> node = new Node<>(item);
	}
}
