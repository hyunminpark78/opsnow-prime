package opsnow.framework.core.pooling;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;
import java.util.function.Consumer;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 * Generic implementation of object pooling pattern with predefined pool size limit. The main
 * purpose is that limited number of frequently used objects can be kept in the pool for
 * further recycling.
 * @param <T>
 */
public class ObjectPoolImpl<T> {
    private static final int DEFAULT_MAX_COUNT = 100;
    private final ConcurrentLinkedQueue<T> pool;
    private final Function<Integer, T> objectGenerator;
    private final Consumer<T> objectResetor;
    private final Function<T, Integer> retrieveCapacity;
    private final int maxSize;
    private int maxCount;

    public ObjectPoolImpl(int maxCount) {
        this(Integer.MAX_VALUE, maxCount, null, null, null);
    }

    public ObjectPoolImpl(int maxSize, Function<Integer, T> objectGenerator, Consumer<T> objectResetor) {
        this(maxSize, DEFAULT_MAX_COUNT, objectGenerator, objectResetor, null);
    }

    public ObjectPoolImpl(int maxSize, int maxCount, Function<Integer, T> objectGenerator, Consumer<T> objectResetor) {
        this(maxSize, maxCount, objectGenerator, objectResetor, null);
    }

    public ObjectPoolImpl(int maxSize, Function<Integer, T> objectGenerator, Consumer<T> objectResetor, Function<T, Integer> retrieveCapacity) {
        this(maxSize, DEFAULT_MAX_COUNT, objectGenerator, objectResetor, retrieveCapacity);
    }

    public ObjectPoolImpl(int maxSize, int maxCount, Function<Integer, T> objectGenerator, Consumer<T> objectResetor, Function<T, Integer> retrieveCapacity) {
        this.maxSize = maxSize;
        this.maxCount = maxCount;
        this.objectGenerator = objectGenerator;
        this.objectResetor = objectResetor;
        this.retrieveCapacity = retrieveCapacity;
        this.pool = new ConcurrentLinkedQueue<>();
    }

    public T take() {
        T item = pool.poll();
        if (item == null) {
            return objectGenerator.apply(maxSize);
        }
        return item;
    }

    public T take(Function<T, T> factory) {
        T item = pool.poll();
        if (item == null) {
            return factory.apply(null);
        }
        return item;
    }

    public void release(T item) {
        if (item != null && (retrieveCapacity == null || retrieveCapacity.apply(item) <= maxSize)) {
            if (pool.size() < maxCount) {
                objectResetor.accept(item);
                pool.offer(item);
            }
        }
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }
}
