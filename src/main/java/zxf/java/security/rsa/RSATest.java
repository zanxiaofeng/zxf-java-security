package zxf.java.security.rsa;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class RSATest {

    public static void main(String[] args) {
        use_case_load_and_use();
        use_case_load_and_use_for_base64();
    }

    public static void use_case_gen_and_store() {
        KeyPair keyPair = RSAKeyTool.generateKeyPair();

        RSAKeyTool.storeKeyPair(keyPair);

        RSAKeyTool.storeKeyPairAsBase64(keyPair);
    }

    public static void use_case_load_and_use() {
        KeyPair keyPair = RSAKeyTool.loadKeyPair();

        String encrypted = encrypt("123456", keyPair.getPublic());
        System.out.println(encrypted);

        String decrypted = decrypt(encrypted, keyPair.getPrivate());
        System.out.println(decrypted);
    }

    public static void use_case_load_and_use_for_base64() {
        KeyPair keyPair = RSAKeyTool.loadKeyPairFromBase64();

        String encrypted = encrypt("123456", keyPair.getPublic());
        System.out.println(encrypted);

        String decrypted = decrypt(encrypted, keyPair.getPrivate());
        System.out.println(decrypted);
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
