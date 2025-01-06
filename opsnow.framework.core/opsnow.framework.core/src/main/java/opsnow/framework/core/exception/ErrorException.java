package opsnow.framework.core.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 * Represents an implementation of the ErrorException.
 */
public class ErrorException extends Exception {
    private Map<String, Object> data = new HashMap<>();

    /**
     * Initializes a new instance of the ErrorException class.
     */
    public ErrorException() {
    }

    /**
     * Initializes a new instance of the ErrorException class.
     * @param message
     */
    public ErrorException(String message) {
        super(message);
    }

    /**
     * Initializes a new instance of the ErrorException class.
     * @param ex
     */
    public ErrorException(Exception ex) {
        super(ex);
    }

    public void addDetail(String key, Object value) {
        data.put(key, value);
    }

    public Object getDetail(String key) {
        return data.get(key);
    }

    public Map<String, Object> getData() {
        return data;
    }
}
