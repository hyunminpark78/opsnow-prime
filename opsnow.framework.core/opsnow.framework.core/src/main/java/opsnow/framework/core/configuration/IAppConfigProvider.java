package opsnow.framework.core.configuration;

import java.util.function.Consumer;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 */
public interface IAppConfigProvider extends IAppConfigRoot, AutoCloseable  {
    /**
     * Gets the configuration root.
     *
     * @return The configuration root.
     */
    //IConfigurationRoot getConfiguration();

    /**
     * Gets the cache for configuration sections.
     *
     * @return The cache as a concurrent collection.
     */
    //IConcurrentCollection<String, IAppConfigSection> getSectionCache();

    /**
     * Registers a callback which is called when the configuration is loaded.
     *
     * @param callback The callback to register.
     */
    void registerLoadedCallback(Consumer<IAppConfigProvider> callback);
}
