import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Object[] array;
    private int count;


    // construct an empty randomized queue
    public RandomizedQueue() {
        array = new Object[1];
        count = 0;
    }

    // unit testing (required)
    public static void main(String[] args) {
        int n = 10;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
        for (int i = 0; i < n; i++) {
            StdOut.println(queue.dequeue());
            StdOut.println(queue.array.length + " - " + queue.count);
        }
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Item must not be null");
        if (count == array.length) resize(2 * array.length);
        array[count++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Empty deque");
        int index = StdRandom.uniform(count);
        Item temp = (Item) array[index];
        for (int i = index; i < array.length; i++) {
            if (i + 1 < array.length)
                array[i] = array[i + 1];
        }
        if (count > 0 && count == array.length / 4) resize(array.length / 2);
        count--;
        return temp;
    }

    private void resize(int capacity) {
        Object[] copy = new Object[capacity];
        for (int i = 0; i < count; i++)
            copy[i] = array[i];
        array = copy;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return count;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Empty deque");
        return (Item) array[StdRandom.uniform(count)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        Object[] copy;
        int n;

        public RandomizedQueueIterator() {
            copy = new Object[count];
            int j = 0;
            int i = 0;
            while (j < count) {
                if (array[i] == null) {
                    i++;
                    continue;
                }
                copy[j++] = array[i++];
            }
            StdRandom.shuffle(copy);
            n = 0;
        }

        public boolean hasNext() {
            return n < count;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No more elements");
            Item item = (Item) copy[n];
            n++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove is evil");
        }
    }
}