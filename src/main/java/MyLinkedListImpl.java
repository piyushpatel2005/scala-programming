import java.util.AbstractList;
import java.util.Iterator;

public class MyLinkedListImpl<E> extends AbstractList<E> {

    private int size = 0;
    private Node<E> head, tail;

    private static class Node<E> {
        E elem;
        Node<E> next;

        public Node(E e) {
            this.elem = e;
        }
    }

    MyLinkedListImpl () {

    }

    MyLinkedListImpl(E[] obj) {
        for(int i = 0; i < obj.length; i++)
            add(obj[i]);
    }

    public int size() {
        return size;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            return null;
        } else if (index == 0) {
            return (size == 0) ? null : head.elem;
        } else if(index == size - 1) {
            return (size == 0) ? null : tail.elem;
        } else {
            Node<E> current = head.next;

            for(int i = 1; i < index; i++) {
                current = current.next;
            }
            return current.elem;
        }
    }

    @Override
    public boolean add(E e) {
        this.addLast(e);
        return true;
    }


    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e);
        newNode.next = head;
        head = newNode;
        size++;

        if(tail == null) tail = head;
    }

    public void addLast(E e) {
        Node<E> newNode = new Node<>(e);
        if(tail == null) head = tail = newNode;
        else {
            tail.next = newNode;
            tail = tail.next;
        }

    }

    public void add (int index, E e) {
        if (index == 0) addFirst(e);
        else if (index >= size) addLast(e);
        else {
            Node<E> currentNode = head;
            for(int i = 1; i < index; i++) {
                currentNode = currentNode.next;
            }
            Node<E> temp = currentNode.next;
            currentNode.next = new Node<>(e);
            (currentNode.next).next = temp;
            size++;
        }
    }

    public Iterator<E> iterator() {
        return new MyLinkedListIterator();
    }

    private class MyLinkedListIterator implements Iterator<E> {
        private Node<E> currentNode = head;

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public E next() {
            E e = currentNode.elem;
            currentNode = currentNode.next;
            return e;
        }
    }
}
