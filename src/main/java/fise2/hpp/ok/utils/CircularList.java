package fise2.hpp.ok.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * This class implements a circular doubly-linked list, i.e. the first and last element point to each other.
 *
 * @param <T>
 */
public class CircularList<T> implements Iterable<T> {

    /**
     * The size of the list.
     */
    private int size = 0;
    /**
     * The current node.
     */
    private Node<T> curr = null;

    public CircularList() {
    }

    public CircularList(CircularList<T> other) {
        this();
        switch (other.size) {
            case 0:
                break;
            case 1:
                curr = new Node<T>(null, other.curr(), null);
                curr.prev = curr.next = curr;
                ++size;
                break;
            default:
                for (T e : other) {
                    curr = other.curr;
                    ++size;
                }
                break;
        }
    }

    public CircularList(Collection<? extends T> c) {
        this();
        switch (c.size()) {
            case 0:
                break;
            case 1:
                curr = new Node<T>(null, c.stream().findFirst().get(), null);
                curr.prev = curr.next = curr;
                ++size;
                break;
            default:
                curr = new Node<T>(null, c.stream().findFirst().get(), null);
                Node<T> first = curr;
                c.stream().skip(1).forEach(o -> {
                    curr.next = new Node<T>(curr, o, null);
                    curr = curr.next;
                });
                curr.next = first;
                first.prev = curr;
                size = c.size();
                break;
        }
    }

    /**
     * Adds an item after the current one.
     *
     * @param t the item to add
     */
    public void addAfter(T t) {
        switch (size) {
            case 0:
                curr = new Node<T>(null, t, null);
                curr.prev = curr.next = curr;
                break;
            case 1:
                curr.prev = curr.next = new Node<T>(curr, t, curr);
                break;
            default:
                curr.next = new Node<T>(curr, t, curr.next);
                break;
        }
        ++size;
    }

    /**
     * Adds an item before the current one.
     *
     * @param t the item to add
     */
    public void addBefore(T t) {
        switch (size) {
            case 0:
                curr = new Node<T>(null, t, null);
                curr.prev = curr.next = curr;
                break;
            case 1:
                curr.prev = curr.next = new Node<T>(curr, t, curr);
                break;
            default:
                curr.prev = new Node<T>(curr.prev, t, curr);
                break;
        }
        ++size;
    }

    /**
     * Removes the current item and returns true, or false if the list is empty.
     *
     * @return true if an item has been removed
     */
    public boolean removeCurr() {
        switch (size) {
            case 0:
                return false;
            case 1:
                curr = null;
                --size;
                return true;
            default:
                curr.next.prev = curr.prev;
                curr.prev.next = curr.next;
                curr = curr.next;
                --size;
                return true;
        }
    }

    /**
     * Advances to the next item and returns it.
     *
     * @return the next item
     */
    public T advanceForward() {
        curr = curr.next;
        return curr.item;
    }

    /**
     * Goes back to the previous item and returns it.
     *
     * @return the previous item
     */
    public T advanceBackward() {
        curr = curr.prev;
        return curr.item;
    }

    /**
     * Returns the next item.
     *
     * @return the next item
     */
    public T next() {
        return curr.next.item;
    }

    /**
     * Returns the previous item.
     *
     * @return the previous item
     */
    public T prev() {
        return curr.prev.item;
    }

    /**
     * Returns the current item.
     *
     * @return the current item
     */
    public T curr() {
        return curr.item;
    }

    /**
     * Sets the value of the current item.
     *
     * @return the value of the current item
     */
    public void setCurrent(T t) {
        curr.item = t;
    }

    /**
     * Checks if empty.
     *
     * @return true if the list is empty
     */
    public boolean isEmpty() {
        return curr == null;
    }

    /**
     * Returns the size of the list
     *
     * @return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Returns an array containing all of the elements in this list.
     *
     * @return an array containing all of the elements in this list
     */
    public Object[] toArray() {
        Object[] output = new Object[size];
        for (int i = 0; i < size; ++i) {
            output[i] = curr();
            advanceForward();
        }
        return output;
    }

    @Override
    public Iterator<T> iterator() {
        Iterator<T> it = new Iterator<>() {
            private final Node<T> begin = curr;
            private Node<T> it_curr = curr;
            private boolean hasRemoved = false;
            private boolean hasAdvanced = false;

            @Override
            public boolean hasNext() {
                return !isEmpty() && !(hasAdvanced && it_curr == begin);
            }

            @Override
            public T next() throws NoSuchElementException {
                if (!hasNext()) {
                    throw new NoSuchElementException("This iteration has no more elements.");
                }
                hasRemoved = false;
                hasAdvanced = true;
                it_curr = it_curr.next;
                return it_curr.item;
            }

            @Override
            public void remove() throws IllegalStateException {
                if (hasRemoved) {
                    throw new IllegalStateException("The remove method has already been called after the last call to the next method.");
                }
                //TODO remove
                --size;
                hasRemoved = true;
            }

            @Override
            public void forEachRemaining(Consumer<? super T> action) {
                Objects.requireNonNull(action);
                do {
                    action.accept(it_curr.item);
                    it_curr = it_curr.next;
                } while (it_curr != begin);
            }
        };
        return it;
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        final Node<T> begin = curr;
        do {
            action.accept(curr.item);
            advanceForward();
        } while (curr != begin);
    }

    /**
     * Checks if {@code this} is equal to {@code o}.
     * The elements in both lists must be the same in the same order.
     * Note that this method is in {@code O(n*n(-1))} at worst, {@code O(n)} at best.
     *
     * @param o the Object to test for equality
     *
     * @return true if {@code this} is equal to {@code o}, false elsewhen
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CircularList<T> that;
        try {
            that = (CircularList<T>) o;
        } catch (Exception ignored) {
            return false;
        }
        if (size != that.size) {
            return false;
        }
        if (isEmpty() && that.isEmpty()) {
            return true;
        }

        final Node<T> thisbegin = curr; // checkpoint pour remettre this là où elle était au départ
        // se cale sur un objet identique
        int i = 0;
        for (; i < size; i++) {
            if (Objects.equals(curr.item, that.curr.item)) {
                break;
            } else {
                this.advanceForward();
            }
        }
        // si on a trouvé aucun item identique, pas égal
        if (i > 0 && curr == thisbegin) {
            return false;
        }

        final Node<T> thatbegin = that.curr;
        // teste si tous les éléments sont identiques un a un
        for (int j = 0; j < size; j++) {
            if (!Objects.equals(this.advanceForward(), that.advanceForward())) {
                curr = thisbegin;
                that.curr = thatbegin;
                return false;
            }
        }
        curr = thisbegin;
        that.curr = thatbegin;
        return true;
    }

    /**
     * This class implements a Node of a doubly-linked list.
     *
     * @param <T> The type of the item stored by this list.
     */
    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.item = element;
            this.next = next;
        }
    }
}
