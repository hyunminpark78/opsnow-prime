package opsnow.framework.core.security;

import java.util.Map;

public interface IAppRepository<TElement> {
    /**
     * Gets all top-level elements in the repository.
     *
     * @return A read-only collection of key-value pairs.
     */
    Map<String, TElement> getAllElements();

    /**
     * Gets the element.
     *
     * @param friendlyName A required name to be associated with the data element.
     * @return The element associated with the given name.
     */
    TElement getElement(String friendlyName);
}
