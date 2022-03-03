package zxf.java.security;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSATest {

    public static void main(String[] args) {
        use_case_generate_and_store();
        use_case_load_and_use();
        use_case_load_and_use_for_base64();
    }

    public static void use_case_generate_and_store() {
        KeyPair keyPair = generateKeyPair();

        storeKeyPair(keyPair);

        storeKeyPairAsBase64(keyPair);
    }

    public static void use_case_load_and_use() {
        KeyPair keyPair = loadKeyPair();

        String encrypted = encrypt("123456", keyPair.getPublic());
        System.out.println(encrypted);

        String decrypted = decrypt(encrypted, keyPair.getPrivate());
        System.out.println(decrypted);
    }

    public static void use_case_load_and_use_for_base64() {
        KeyPair keyPair = loadKeyPairFromBase64();

        String encrypted = encrypt("123456", keyPair.getPublic());
        System.out.println(encrypted);

        String decrypted = decrypt(encrypted, keyPair.getPrivate());
        System.out.println(decrypted);
    }

    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            //512, 1024, 2048
            keyPairGenerator.initialize(2048, new SecureRandom());
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new RuntimeException("Exception when generate a keypair", e);
        }
    }

    public static void storeKeyPair(KeyPair keyPair) {
        try {
            Files.write(Paths.get("./keys/public-key"), keyPair.getPublic().getEncoded());
            Files.write(Paths.get("./keys/private-key"), keyPair.getPrivate().getEncoded());
        } catch (Exception e) {
            throw new RuntimeException("Exception when store a keypair", e);
        }
    }

    public static KeyPair loadKeyPair() {
        try {
            PublicKey publicKey = loadPublicKey(Files.readAllBytes(Paths.get("./keys/public-key")));
            PrivateKey privateKey = loadPrivateKey(Files.readAllBytes(Paths.get("./keys/private-key")));
            return new KeyPair(publicKey, privateKey);
        } catch (Exception e) {
            throw new RuntimeException("Exception when load a keypair", e);
        }
    }

    public static void storeKeyPairAsBase64(KeyPair keyPair) {
        try {
            Files.write(Paths.get("./keys/public-key-base64"), Base64.getEncoder().encode(keyPair.getPublic().getEncoded()));
            Files.write(Paths.get("./keys/private-key-base64"), Base64.getEncoder().encode(keyPair.getPrivate().getEncoded()));
        } catch (Exception e) {
            throw new RuntimeException("Exception when store a keypair", e);
        }
    }

    public static KeyPair loadKeyPairFromBase64() {
        try {
            PublicKey publicKey = loadPublicKey(Base64.getDecoder().decode(Files.readAllBytes(Paths.get("./keys/public-key-base64"))));
            PrivateKey privateKey = loadPrivateKey(Base64.getDecoder().decode(Files.readAllBytes(Paths.get("./keys/private-key-base64"))));
            return new KeyPair(publicKey, privateKey);
        } catch (Exception e) {
            throw new RuntimeException("Exception when load a keypair", e);
        }
    }


    public static PublicKey loadPublicKey(byte[] encodedKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(encodedKey));
    }

    public static PrivateKey loadPrivateKey(byte[] encodedKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
    }

    public static String encrypt(String msg, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encrypted = cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Exception when do an encrypt", e);
        }
    }

    public static String decrypt(String msg, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(msg));
            return new String(decrypted);
        } catch (Exception e) {
            throw new RuntimeException("Exception when do an decrypt", e);
        }
    }
}
