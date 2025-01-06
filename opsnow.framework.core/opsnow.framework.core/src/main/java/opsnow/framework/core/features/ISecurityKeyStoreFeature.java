package opsnow.framework.core.features;

public interface ISecurityKeyStoreFeature
{
    /// <summary>
    /// Gets the value of the keyName.
    /// </summary>
    /// <param name="keyName">Name of the specific key such as "AES256".</param>
    String GetKey(String keyName);
}