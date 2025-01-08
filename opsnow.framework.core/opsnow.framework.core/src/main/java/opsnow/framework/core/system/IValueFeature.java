package opsnow.framework.core.system;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 */
public interface IValueFeature extends AutoCloseable {
    /**
     * Gets the value.
     *
     * @return The value object.
     */
    Object getValue();

    /**
     * Disposes the value feature.
     *
     * Implementations should release resources if necessary.
     */
    @Override
    default void close() {
        // Default implementation: no-op
        // Override in implementation classes if disposal logic is needed
    }
}