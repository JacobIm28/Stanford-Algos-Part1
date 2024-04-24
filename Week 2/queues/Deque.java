/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    public boolean isEmpty() {
        return first == null || last == null;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.prev = null;

        if (last == null) {
            last = first;
        } else if (oldFirst != null) {
            oldFirst.prev = first;
        }

        size += 1;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;

        if (first == null) {
            first = last;
        } else if (oldLast != null) {
            oldLast.next = last;
        }

        size += 1;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = first.item;
        first = first.next;

        if (isEmpty()) {
            last = null;
        } else {
            first.prev = null;
        }

        size -= 1;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = last.item;
        last = last.prev;

        if (isEmpty()) {
            first = null;
        } else {
            last.next = null;
        }

        size -= 1;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> d = new Deque<Integer>();

        d.addFirst(10);
        d.addFirst(25);
        d.addLast(100);
        d.addLast(1);

        int sum = 0;
        for (Integer i: d) {
            sum += i;
        }
        System.out.println("sum: " + sum);

        for (int a : d) {
            for (int b : d)
                System.out.print(a + "-" + b + " ");
            System.out.println();
        }

        System.out.println("size: " + d.size());
        int item = d.removeFirst();
        System.out.println(item);
        int item2 = d.removeLast();
        System.out.println(item2);
        System.out.println(d.isEmpty());
        d.removeFirst();
        d.removeLast();
        System.out.println(d.isEmpty());

    }
}
