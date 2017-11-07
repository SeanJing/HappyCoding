import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int size = 0;

    public RandomizedQueue() {
        s = (Item[]) new Object[1];
    }

    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException("cannot add a null item");
        if (size == s.length) resize(2 * s.length);
        s[size++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("cannot deque from an empty queue");
        int random = StdRandom.uniform(size);
        Item item = s[random];
        s[random] = s[--size];
        s[size] = null;
        if (size > 0 && size == s.length / 4) resize(s.length / 2);
        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("cannot deque from an empty queue");
        int random = StdRandom.uniform(size);
        return s[random];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void resize(int length) {
        Item[] copy = (Item[]) new Object[length];
        for (int i = 0; i < size; i++) {
            copy[i] = s[i];
        }
        s = copy;
    }


    public Iterator<Item> iterator() {
        return new RandomArrayIterator();
    }

    private class RandomArrayIterator implements Iterator<Item> {

        public RandomArrayIterator() {
            shuffle = (Item[]) new Object[size];
            for (int j = 0; j < size; j++) {
                shuffle[j] = s[j];
            }
            StdRandom.shuffle(shuffle);
        }

        private int i = size;
        private Item[] shuffle;

        public boolean hasNext() {
            return i > 0;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("cannot iterate in empty deque");
            return shuffle[--i];
        }

        public void remove() {
            throw new UnsupportedOperationException("remove is not supported");
        }

    }
}
