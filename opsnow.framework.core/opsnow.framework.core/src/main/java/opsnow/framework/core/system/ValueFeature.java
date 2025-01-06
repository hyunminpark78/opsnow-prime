package opsnow.framework.core.system;

public class ValueFeature<TValue> implements IValueFeature {
    private final TValue value;

    /**
     * Gets the current value.
     *
     * @return The current value.
     */
    public TValue getValueTyped() {
        return value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    /**
     * Initializes a new instance of the ValueFeature class.
     *
     * @param value The value to store.
     */
    public ValueFeature(TValue value) {
        this.value = value;
    }

    /**
     * Releases resources if the value implements AutoCloseable.
     */
    @Override
    public void close() {
        if (value instanceof AutoCloseable) {
            try {
                ((AutoCloseable) value).close();
            } catch (Exception e) {
                // Handle or log the exception as necessary.
                throw new RuntimeException("Failed to close value", e);
            }
        }
    }
}