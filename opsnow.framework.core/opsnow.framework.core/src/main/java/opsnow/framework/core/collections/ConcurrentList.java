package opsnow.framework.core.collections;

import java.util.*;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 * @param <T>
 */
public class ConcurrentList<T> implements List<T> {
    private List<T> list = Collections.synchronizedList(new ArrayList<>());

    public ConcurrentList() {
        // 기본 생성자
    }

    @Override
    public boolean add(T e) {
        synchronized (list) {
            return list.add(e);
        }
    }

    @Override
    public void add(int index, T element) {
        synchronized (list) {
            list.add(index, element);
        }
    }

    @Override
    public T get(int index) {
        synchronized (list) {
            return list.get(index);
        }
    }

    @Override
    public T set(int index, T element) {
        return null;
    }

    @Override
    public T remove(int index) {
        synchronized (list) {
            return list.remove(index);
        }
    }

    @Override
    public boolean remove(Object o) {
        synchronized (list) {
            return list.remove(o);
        }
    }

    @Override
    public void clear() {
        synchronized (list) {
            list.clear();
        }
    }

    @Override
    public int size() {
        synchronized (list) {
            return list.size();
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (list) {
            return list.isEmpty();
        }
    }

    @Override
    public boolean contains(Object o) {
        synchronized (list) {
            return list.contains(o);
        }
    }

    @Override
    public Iterator<T> iterator() {
        synchronized (list) {
            return new ArrayList<>(list).iterator();
        }
    }

    @Override
    public Object[] toArray() {
        synchronized (list) {
            return list.toArray();
        }
    }

    @Override
    public <T> T[] toArray(T[] a) {
        synchronized (list) {
            return list.toArray(a);
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        synchronized (list) {
            return list.containsAll(c);
        }
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        synchronized (list) {
            return list.addAll(c);
        }
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        synchronized (list) {
            return list.addAll(index, c);
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        synchronized (list) {
            return list.removeAll(c);
        }
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        synchronized (list) {
            return list.retainAll(c);
        }
    }

    @Override
    public int indexOf(Object o) {
        synchronized (list) {
            return list.indexOf(o);
        }
    }

    @Override
    public int lastIndexOf(Object o) {
        synchronized (list) {
            return list.lastIndexOf(o);
        }
    }

    @Override
    public ListIterator<T> listIterator() {
        return list.listIterator(); // 주의: 이터레이터는 스레드-세이프하지 않을 수 있음
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return list.listIterator(index); // 주의: 이터레이터는 스레드-세이프하지 않을 수 있음
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        synchronized (list) {
            return new ArrayList<>(list.subList(fromIndex, toIndex));
        }
    }
}
