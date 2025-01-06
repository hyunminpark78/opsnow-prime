package opsnow.framework.core.collections;

import java.util.*;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 * Declaring ALKList object from original does not guarantee the deep copy until you try to modify it.
 * You can consider to use List<T> instead to ensure deep copy.
 * @param <T>
 */
public class ALKList<T> implements List<T> {
    private static final int MAX_ARRAY_SIZE = 256;
    private ArrayList<T> arrayList;
    private LinkedList<T> nodeList;
    private List<T> currentList;

    /**
     * Initializes a new instance of the <see cref="ALKList{T}"/> class.
     */
    public ALKList() {
        this.arrayList = new ArrayList<>();
        this.currentList = this.arrayList;
    }

    /**
     * Initializes a new instance of the <see cref="ALKList{T}"/> class.
     * @param capacity The capacity.
     */
    public ALKList(int capacity) {
        if (capacity <= MAX_ARRAY_SIZE) {
            this.arrayList = new ArrayList<>(Math.max(capacity, 4));
            this.currentList = this.arrayList;
        } else {
            this.nodeList = new LinkedList<>();
            this.currentList = this.nodeList;
        }
    }

    /**
     * Initializes a new instance of the <see cref="ALKList{T}"/> class.
     * @param collection The dense collection.
     */
    public ALKList(Collection<? extends T> collection) {
        if (collection.size() <= MAX_ARRAY_SIZE) {
            this.arrayList = new ArrayList<>(collection);
            this.currentList = this.arrayList;
        } else {
            this.nodeList = new LinkedList<>(collection);
            this.currentList = this.nodeList;
        }
    }

    private void ensureCapacity() {
        if (currentList instanceof ArrayList && currentList.size() >= MAX_ARRAY_SIZE) {
            nodeList = new LinkedList<>(arrayList);
            currentList = nodeList;
            arrayList = null;
        }
    }

    @Override
    public int size() {
        return currentList.size();
    }

    @Override
    public boolean isEmpty() {
        return currentList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return currentList.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return currentList.iterator();
    }

    @Override
    public Object[] toArray() {
        return currentList.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return currentList.toArray(a);
    }

    @Override
    public boolean add(T t) {
        ensureCapacity();
        return currentList.add(t);
    }

    @Override
    public boolean remove(Object o) {
        return currentList.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return currentList.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        ensureCapacity();
        return currentList.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        ensureCapacity();
        return currentList.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return currentList.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return currentList.retainAll(c);
    }

    @Override
    public void clear() {
        currentList.clear();
    }

    @Override
    public T get(int index) {
        return currentList.get(index);
    }

    @Override
    public T set(int index, T element) {
        return currentList.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        ensureCapacity();
        currentList.add(index, element);
    }

    @Override
    public T remove(int index) {
        return currentList.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return currentList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return currentList.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return currentList.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return currentList.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return currentList.subList(fromIndex, toIndex);
    }
}
