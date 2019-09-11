import java.util.AbstractList;
import java.util.Iterator;

public class MyLinkedList<E> extends AbstractList<E> {

    private int size = 0;
    public static final int INITIAL_CAPACITY = 160;
    private E[] data = (E[]) new Object[INITIAL_CAPACITY];

    @Override
    public E get(int index) {
        checkIndexPosition(index);
        return data[index];
    }

    private void checkIndexPosition(int index) {
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException("index " + index + " out of bounds");
    }

    @Override
    public int size() {
        return data.length;
    }

    @Override
    public void add(int index, E e) {
        ensureCapacity();
        for(int i = size - 1; i >= index; i--) {
            data[i + 1] = data[i];
        }
        data[index] = e;
        size++;
    }

    @Override
    public boolean add(E e) {
        add(size, e);
        return true;
    }

    private void ensureCapacity() {
        if(size >= data.length) {
            E[] newData = (E[]) (new Object[size * 2 + 1]);
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
    }

    public Iterator<E> iterator() {
        return new MyLinkedListIterator();
    }

    private class MyLinkedListIterator implements Iterator<E> {
        private int current = 0; // current index

        @Override
        public boolean hasNext() {
            return (current < size);
        }

        @Override
        public E next() {
            return data[current++];
        }
    }

}
