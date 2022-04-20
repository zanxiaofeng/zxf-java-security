package zxf.java.security.aes;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AESKeyTool {
    public static SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        //AES support 128(default), 192, 256
        keyGenerator.init(226, new SecureRandom());
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey;
    }

    public static void storeKey(SecretKey key) throws IOException {
        Files.write(Paths.get("./keys/aes-secret-key"), key.getEncoded());
    }

    public static SecretKey loadKey() throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get("./keys/aes-secret-key"));
        return new SecretKeySpec(encoded, "AES");
    }
}
