package opsnow.framework.core.security;

import opsnow.framework.core.runtime.AppRuntime;
import opsnow.framework.core.ONIConst;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class SecurityKeyStoreFeature implements ISecurityKeyStoreFeature {
    public static final String MASTER_KEY = "MASTER_KEY";
    public static final String DEFAULT_NAME = "keystore";

    private final ISecurityKeyVaultFeature provider;
    private final String storeName;
//
//    public SecurityKeyStoreFeature() {
//        this(AppRuntime.getKeyVault(), DEFAULT_NAME);
//    }

    public SecurityKeyStoreFeature(ISecurityKeyVaultFeature provider) {
        this(provider, DEFAULT_NAME);
    }

    public SecurityKeyStoreFeature(ISecurityKeyVaultFeature provider, String storeName) {
        if (provider == null) throw new IllegalArgumentException("provider cannot be null");
        if (storeName == null) throw new IllegalArgumentException("storeName cannot be null");

        this.provider = provider;
        this.storeName = storeName;
    }

    // Gets the key value of the keyName
    @Override
    public String GetKey(String keyName) {
        if (keyName == null || keyName.isEmpty()) {
            throw new IllegalArgumentException("keyName cannot be null or empty");
        }

        String keyPath = String.join(":", storeName, keyName);
        return provider.getValue(keyPath, (x) -> {
            try {
                return unprotectKey(x.getValue());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    // Gets the master key
    protected byte[] getMasterKey() {
        String keyPath = String.join(":", storeName, MASTER_KEY);

        return provider.getValue(keyPath, (x) -> {
            if (x.getValue() == null || x.getValue().isEmpty()) {
                throw new IllegalArgumentException("keyPath cannot be null or empty");
            }
            return decodeHex(x.getValue());
        });
    }

    // Protects the key.
    protected String protectKey(Cipher crypto, String keyDataValue) throws Exception {
        if (keyDataValue == null || keyDataValue.isEmpty()) {
            throw new IllegalArgumentException("keyDataValue cannot be empty");
        }

        byte[] secretKey = getMasterKey();

        // Generate IV
        byte[] iv = new byte[crypto.getBlockSize()];
        crypto.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(secretKey, "AES"), new IvParameterSpec(iv));

        try (ByteArrayOutputStream ms = new ByteArrayOutputStream()) {
            try (CipherOutputStream cs = new CipherOutputStream(ms, crypto)) {
                byte[] data = keyDataValue.getBytes(StandardCharsets.UTF_8);
                cs.write(data, 0, data.length);
            }
            String encodedIv = encodeHex(iv);
            String encodedData = encodeHex(ms.toByteArray());
            return encodedIv + "|" + encodedData;
        }
    }

    // Unprotects the key.
    protected String unprotectKey(String keyDataValue) throws Exception {
        try {
            String[] arrKeyData = keyDataValue.split("\\|");
            if (arrKeyData.length != 2) {
                throw new IllegalArgumentException("SecurityKeyStore.UnprotectKey()");
            }

            byte[] secretKey = getMasterKey();
            byte[] secretIv = decodeHex(arrKeyData[0]);
            byte[] protectedData = decodeHex(arrKeyData[1]);

            Cipher crypto = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivSpec = new IvParameterSpec(secretIv);
            crypto.init(Cipher.DECRYPT_MODE, new SecretKeySpec(secretKey, "AES"), ivSpec);

            try (ByteArrayInputStream src = new ByteArrayInputStream(protectedData);
                 ByteArrayOutputStream ms = new ByteArrayOutputStream();
                 CipherInputStream cs = new CipherInputStream(src, crypto)) {
                byte[] buffer = new byte[1024];
                int read;
                while ((read = cs.read(buffer)) != -1) {
                    ms.write(buffer, 0, read);
                }

                return new String(ms.toByteArray(), StandardCharsets.UTF_8);
            }
        } catch (Exception ex) {
            throw new Exception("Failed to unprotect the key", ex);
        }
    }

    // Hex encoder
    private String encodeHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }

    // Hex decoder
    private byte[] decodeHex(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

}
