package opsnow.framework.core.features;

import opsnow.framework.core.configuration.IAppConfigProvider;

public interface ISecurityKeyVaultFeature extends IAppConfigProvider {
    IKeyVaultRepository  getRepository();
}
