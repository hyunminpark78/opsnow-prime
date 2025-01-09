package opsnow.framework.core.configuration;

import java.util.function.Function;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 */
public interface IAppConfigRoot {

    /**
     * Gets the value associated with the given key.
     *
     * @param key The key.
     * @return The value as a String.
     */
    String getValue(String key);

    /**
     * Gets the value associated with the given key using a value creator.
     *
     * @param <T>           The type of the returned value.
     * @param key           The key.
     * @param valueCreator  A function to create the value from the configuration section.
     * @return The value as type T.
     */
    <T> T getValue(String key, Function<IAppConfigSection, T> valueCreator);

    /**
     * Gets a cached value using a value creator.
     *
     * @param <T>           The type of the returned value.
     * @param valueCreator  A function to create the value from the configuration section.
     * @return The cached value as type T.
     */
    <T> T getCachedValue(Function<IAppConfigSection, T> valueCreator);

}
