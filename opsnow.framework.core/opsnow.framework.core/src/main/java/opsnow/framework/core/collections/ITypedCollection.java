package opsnow.framework.core.collections;

import java.util.Map;
import java.util.function.Function;

public interface ITypedCollection extends Iterable<Map.Entry<Class<?>, Object>> {

    /**
     * Registers the initialization.
     *
     * @param targetType  The class type of the target.
     * @param initializer The initializer function.
     */
    void registerInitialization(Class<?> targetType, Function<Object, Void> initializer);

    /**
     * Determines whether this instance contains the object of the specified type.
     *
     * @param targetType The class type of the target.
     * @return True if the collection contains the target type; otherwise, false.
     */
    boolean contains(Class<?> targetType);

    /**
     * Sets the given value in the collection.
     *
     * @param targetType The class type of the target.
     * @param instance   The instance to set.
     */
    void set(Class<?> targetType, Object instance);

    /**
     * Attempts to add the specified value to the collection if the key does not exist.
     *
     * @param targetType The class type of the target.
     * @param factory    The factory function to produce the value.
     * @return True if the value was added; otherwise, false.
     */
    boolean trySet(Class<?> targetType, Function<Void, Object> factory);

    /**
     * Retrieves the requested value from the collection.
     *
     * @param targetType The class type of the target.
     * @return The requested value, or null if it is not present.
     */
    Object get(Class<?> targetType);

    /**
     * Retrieves the requested value from the collection or adds it if it doesn't exist.
     *
     * @param targetType The class type of the target.
     * @param factory    The factory function to produce the value.
     * @return The requested value.
     */
    Object getOrAdd(Class<?> targetType, Function<Void, Object> factory);

    /**
     * Removes the requested value from the collection.
     *
     * @param targetType The class type of the target.
     * @return The removed value, or null if it was not present.
     */
    Object remove(Class<?> targetType);

    /**
     * Clears all data from the collection.
     */
    void clear();
}
