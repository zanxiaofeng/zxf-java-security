package zxf.java.security.mac;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Locale;

public class HMACKeyTool {
    public static SecretKey generateKey(String algorithm) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        keyGenerator.init(new SecureRandom());
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey;
    }

    public static void storeKey(String algorithm, SecretKey key) throws IOException {
        Files.write(Paths.get("./keys/hmac-secret-key-" + algorithm.toLowerCase(Locale.ROOT)), key.getEncoded());
    }

    public static void storeKeyAsBase64(String algorithm, SecretKey key) throws IOException {
        Files.write(Paths.get("./keys/hmac-secret-key-" + algorithm.toLowerCase(Locale.ROOT) + "-base64"),
                Base64.getEncoder().encode(key.getEncoded()));
    }

    public static SecretKey loadKey(String algorithm) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get("./keys/hmac-secret-key-" + algorithm.toLowerCase(Locale.ROOT)));
        return new SecretKeySpec(encoded, algorithm);
    }

    public static SecretKey loadKeyAsBase64(String algorithm) throws IOException {
        byte[] encoded = Base64.getDecoder().decode(Files.readAllBytes(Paths.get("./keys/hmac-secret-key-" +
                algorithm.toLowerCase(Locale.ROOT) + "-base64")));
        return new SecretKeySpec(encoded, algorithm);
    }
}
