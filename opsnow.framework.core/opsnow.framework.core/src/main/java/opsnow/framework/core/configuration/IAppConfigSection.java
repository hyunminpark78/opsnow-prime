package opsnow.framework.core.configuration;

import java.util.function.Consumer;

public interface IAppConfigSection extends IAppConfigRoot {
    String getValue();
}