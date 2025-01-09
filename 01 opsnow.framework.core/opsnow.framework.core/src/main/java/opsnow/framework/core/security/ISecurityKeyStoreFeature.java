package opsnow.framework.core.security;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 */
public interface ISecurityKeyStoreFeature
{
    /// <summary>
    /// Gets the value of the keyName.
    /// </summary>
    /// <param name="keyName">Name of the specific key such as "AES256".</param>
    String GetKey(String keyName);
}