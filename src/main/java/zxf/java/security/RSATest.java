package zxf.java.security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.util.Base64;

public class RSATest {

    public static void main(String[] args) {
        KeyPair keyPair = generateKeyPair();

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
            Files.write(Paths.get("./public-key"), keyPair.getPublic().getEncoded());
            Files.write(Paths.get("./private-key"), keyPair.getPrivate().getEncoded());
        } catch (Exception e) {
            throw new RuntimeException("Exception when store a keypair", e);
        }
    }

    public static KeyPair loadKeyPair() {
        try {
            //TODO:: load
            return new KeyPair(null, null);
        } catch (Exception e) {
            throw new RuntimeException("Exception when load a keypair", e);
        }
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
