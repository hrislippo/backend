package lippo.hris.system.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class EncryptionUtil {

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int IV_LENGTH = 12;
    private static final int TAG_LENGTH = 128;

    private final SecretKeySpec secretKey;

    public EncryptionUtil(
            @Value("${encryption.key}") String encryptionKey
    ) {

        if (encryptionKey == null || encryptionKey.isBlank()) {
            throw new IllegalStateException(
                    "DB_ENCRYPTION_KEY is not configured"
            );
        }

        try {
            byte[] key = Base64.getDecoder().decode(encryptionKey);

            if (key.length != 32) {
                throw new IllegalArgumentException(
                        "Encryption key must be 32 bytes"
                );
            }

            this.secretKey = new SecretKeySpec(key, "AES");

        } catch (IllegalArgumentException e) {
            throw new IllegalStateException(
                    "Invalid DB_ENCRYPTION_KEY",
                    e
            );
        }
    }

    public String encrypt(String value) throws Exception {

        if (value == null) {
            return null;
        }

        byte[] iv = new byte[IV_LENGTH];

        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);

        Cipher cipher = Cipher.getInstance(ALGORITHM);

        GCMParameterSpec spec =
                new GCMParameterSpec(TAG_LENGTH, iv);

        cipher.init(
                Cipher.ENCRYPT_MODE,
                secretKey,
                spec
        );

        byte[] encrypted =
                cipher.doFinal(
                        value.getBytes(StandardCharsets.UTF_8)
                );

        ByteBuffer buffer =
                ByteBuffer.allocate(iv.length + encrypted.length);

        buffer.put(iv);
        buffer.put(encrypted);

        return Base64.getEncoder()
                .encodeToString(buffer.array());
    }

    public String decrypt(String encryptedValue) throws Exception {

        if (encryptedValue == null) {
            return null;
        }

        byte[] decoded =
                Base64.getDecoder().decode(encryptedValue);

        if (decoded.length <= IV_LENGTH) {
            throw new IllegalArgumentException(
                    "Invalid encrypted value"
            );
        }

        ByteBuffer buffer =
                ByteBuffer.wrap(decoded);

        byte[] iv = new byte[IV_LENGTH];
        buffer.get(iv);

        byte[] encrypted =
                new byte[buffer.remaining()];

        buffer.get(encrypted);

        Cipher cipher = Cipher.getInstance(ALGORITHM);

        GCMParameterSpec spec =
                new GCMParameterSpec(TAG_LENGTH, iv);

        cipher.init(
                Cipher.DECRYPT_MODE,
                secretKey,
                spec
        );

        byte[] decrypted =
                cipher.doFinal(encrypted);

        return new String(
                decrypted,
                StandardCharsets.UTF_8
        );
    }
}