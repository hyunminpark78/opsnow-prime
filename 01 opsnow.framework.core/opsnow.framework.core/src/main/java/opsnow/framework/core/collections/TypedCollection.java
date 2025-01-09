package opsnow.framework.core.collections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 */
public class TypedCollection implements ITypedCollection {
    private final ConcurrentMap<Class<?>, Object> collection;
    private final ConcurrentMap<Class<?>, Function<Object, Void>> initializers;

    public TypedCollection() {
        this.collection = new ConcurrentHashMap<>();
        this.initializers = new ConcurrentHashMap<>();
    }

    @Override
    public void registerInitialization(Class<?> targetType, Function<Object, Void> initializer) {
        initializers.put(targetType, initializer);
        Object instance = collection.get(targetType);
        if (instance != null) {
            initializer.apply(instance);
        }
    }

    @Override
    public boolean contains(Class<?> targetType) {
        return collection.containsKey(targetType);
    }

    @Override
    public void set(Class<?> targetType, Object instance) {
        collection.put(targetType, instance);
        Function<Object, Void> initializer = initializers.get(targetType);
        if (initializer != null) {
            initializer.apply(instance);
        }
    }

    @Override
    public boolean trySet(Class<?> targetType, Function<Void, Object> factory) {
        return collection.computeIfAbsent(targetType, key -> factory.apply(null)) == null;
    }

    @Override
    public Object get(Class<?> targetType) {
        return collection.get(targetType);
    }

    @Override
    public Object getOrAdd(Class<?> targetType, Function<Void, Object> factory) {
        return collection.computeIfAbsent(targetType, key -> {
            Object instance = factory.apply(null);
            if (instance != null) {
                Function<Object, Void> initializer = initializers.get(key);
                if (initializer != null) {
                    initializer.apply(instance);
                }
            }
            return instance;
        });
    }

    @Override
    public Object remove(Class<?> targetType) {
        return collection.remove(targetType);
    }

    @Override
    public void clear() {
        collection.clear();
        initializers.clear();
    }

    @Override
    public java.util.Iterator<Map.Entry<Class<?>, Object>> iterator() {
        return collection.entrySet().iterator();
    }
}