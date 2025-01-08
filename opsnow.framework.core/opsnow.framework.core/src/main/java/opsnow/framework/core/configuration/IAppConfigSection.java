package opsnow.framework.core.configuration;

import java.util.function.Consumer;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 */
public interface IAppConfigSection extends IAppConfigRoot {
    String getValue();
}