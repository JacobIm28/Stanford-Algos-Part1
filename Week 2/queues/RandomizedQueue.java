/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INIT_CAPACITY = 8;
    private Item[] a;
    private int size;

    public RandomizedQueue() {
        a = (Item []) new Object[INIT_CAPACITY];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void resize(int capacity) {
        Item[] b = (Item[]) new Object[capacity];

        for (int i = 0; i < size; i++) {
            b[i] = a[i];
        }

        a = b;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (size > 0 && size == a.length) {
            resize(2 * a.length);
        }

        int idx = 0;

        if (size > 0) {
            if (StdRandom.bernoulli((double) 1 / (size + 1))) {
                idx = size;
            } else {
                idx = StdRandom.uniform(size);
            }
        }

        a[size] = a[idx];
        a[idx] = item;
        size += 1;
        // StdRandom.shuffle(a, 0, size);
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item ret = a[size - 1];
        a[size - 1] = null;
        size -= 1;

        if (size > 0 && size == a.length / 4) {
            resize(a.length / 2);
        }

        return ret;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int idx = StdRandom.uniform(size);

        return a[idx];
    }

    public Iterator<Item> iterator() {
        StdRandom.shuffle(a, 0, size);
        return new ReverseArrayIterator();
    }

    private class ReverseArrayIterator implements Iterator<Item> {
        private int i = size;

        public boolean hasNext() {
            return i > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (i == 0) {
                throw new NoSuchElementException();
            }

            return a[--i];
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

        for (int i = 0; i < 10; i++) {
            rq.enqueue(i + 1);
        }

        System.out.println("Iterator 1");
        for (Integer n: rq) {
            //System.out.println(n);
            for (Integer m: rq) {
                System.out.println(n + "-" + m);
            }
        }

        System.out.println("Iterator 2");


        System.out.println("Sampling");
        for (int j = 0; j < 10; j++) {
            System.out.println(rq.sample());
        }

        System.out.println("Empty?: " + rq.isEmpty());
        System.out.println("Size: " + rq.size());

        System.out.println("Dequeueing");
        for (int j = 0; j < 10; j++) {
            System.out.println(rq.dequeue());
        }

        System.out.println("Empty?: " + rq.isEmpty());
        System.out.println("Size: " + rq.size());
    }
}
