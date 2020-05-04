import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int size;
    private Node head = null;
    private Node tail = null;

    // construct an empty deque
    public Deque() {
        size = 0;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque <String> deque = new Deque<>();
        deque.addLast("TBVSKKZKCI");
        deque.removeFirst();
        deque.size();
        // main method
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Item must not be null");
        if (size == 0) {
            tail = new Node(item);
            head = tail;
        }
        else {
            Node temp = tail;
            tail = new Node(item);
            tail.previous = temp;
            temp.next = tail;
        }
        size++;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Empty deque");
        Item temp = tail.item;
        if (tail == head) {
            tail = null;
            head = null;
        }
        else {
            tail = tail.previous;
            tail.next = null;
        }
        size--;
        return temp;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Item must not be null");
        if (size == 0) {
            head = new Node(item);
            tail = head;
        }
        else {
            Node temp = head;
            head = new Node(item);
            head.next = temp;
            temp.previous = head;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Empty deque");
        Item temp = head.item;
        head = head.next;
        if (head != null)
            head.previous = null;
        size--;
        return temp;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class Node {
        Item item;
        Node previous;
        Node next;

        public Node(Item item) {
            this.item = item;
        }
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current = head;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No more elements");
            Item temp = current.item;
            current = current.next;
            return temp;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove is evil");
        }
    }
}