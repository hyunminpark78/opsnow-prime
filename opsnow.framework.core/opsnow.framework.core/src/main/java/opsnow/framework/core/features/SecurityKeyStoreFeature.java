package opsnow.framework.core.features;

import opsnow.framework.core.ONIConst;
import opsnow.framework.core.runtime.AppRuntime;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class SecurityKeyStoreFeature implements ISecurityKeyStoreFeature {
//    public static final String MASTER_KEY = "MASTER_KEY";
//    public static final String DEFAULT_NAME = "keystore";
//
//    private final ISecurityKeyVaultFeature provider;
//    private final String storeName;
//
//    public SecurityKeyStoreFeature() {
//        this(AppRuntime.getKeyVault(), DEFAULT_NAME);
//    }
//
//    public SecurityKeyStoreFeature(ISecurityKeyVaultFeature provider) {
//        this(provider, DEFAULT_NAME);
//    }
//
//    public SecurityKeyStoreFeature(ISecurityKeyVaultFeature provider, String storeName) {
//        if (provider == null) throw new IllegalArgumentException("provider cannot be null");
//        if (storeName == null) throw new IllegalArgumentException("storeName cannot be null");
//
//        this.provider = provider;
//        this.storeName = storeName;
//    }

    // Gets the key value of the keyName
    @Override
    public String GetKey(String keyName) {
        return "";
//        if (keyName == null || keyName.isEmpty()) {
//            throw new IllegalArgumentException("keyName cannot be null or empty");
//        }
//
//        String keyPath = String.join(":", storeName, keyName);
//        return provider.getValue(keyPath, this::unprotectKey);
    }

    // Gets the master key
    protected byte[] getMasterKey() {
        return ONIConst.EmptyByteArray;
//        String keyPath = String.join(":", storeName, MASTER_KEY);
//
//        return provider.getValue(keyPath, value -> {
//            if (value == null || value.isEmpty()) {
//                throw new KeyNotFoundException("The masterKey entry does not exist in the vault.");
//            }
//            return decodeHex(value);
//        });
    }
//
//    // Protects the key
//    protected String protectKey(Cipher cipher, String keyDataValue) throws Exception {
//        if (keyDataValue == null || keyDataValue.isEmpty()) {
//            throw new IllegalArgumentException("keyDataValue cannot be null or empty");
//        }
//
//        byte[] keyBytes = keyDataValue.getBytes(StandardCharsets.UTF_8);
//        cipher.init(Cipher.ENCRYPT_MODE, cipher.getParameters().getKey());
//        byte[] iv = cipher.getIV();
//        byte[] encryptedData = cipher.doFinal(keyBytes);
//
//        return encodeHex(iv) + "|" + encodeHex(encryptedData);
//    }
//
//    // Unprotects the key
//    protected String unprotectKey(String keyDataValue) {
//        try {
//            String[] arrKeyData = keyDataValue.split("\\|");
//            if (arrKeyData.length != 2) {
//                throw new IllegalArgumentException("Invalid key data format");
//            }
//
//            byte[] secretKey = getMasterKey();
//            byte[] secretIv = decodeHex(arrKeyData[0]);
//            byte[] protectedData = decodeHex(arrKeyData[1]);
//
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//            SecretKey aesKey = new SecretKeySpec(secretKey, "AES");
//            cipher.init(Cipher.DECRYPT_MODE, aesKey, new IvParameterSpec(secretIv));
//
//            byte[] decryptedData = cipher.doFinal(protectedData);
//            return new String(decryptedData, StandardCharsets.UTF_8);
//        } catch (Exception ex) {
//            throw new RuntimeException("Failed to unprotect key", ex);
//        }
//    }
//
//    // Hex encoder
//    private String encodeHex(byte[] bytes) {
//        StringBuilder hex = new StringBuilder();
//        for (byte b : bytes) {
//            hex.append(String.format("%02x", b));
//        }
//        return hex.toString();
//    }
//
//    // Hex decoder
//    private byte[] decodeHex(String hex) {
//        int len = hex.length();
//        byte[] data = new byte[len / 2];
//        for (int i = 0; i < len; i += 2) {
//            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
//                    + Character.digit(hex.charAt(i + 1), 16));
//        }
//        return data;
//    }

}
