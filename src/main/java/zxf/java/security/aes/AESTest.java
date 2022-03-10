package zxf.java.security.aes;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import java.security.NoSuchAlgorithmException;

import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class AESTest {
    //CBC mode need an iv(16 byte)
    private static IvParameterSpec IV_SPEC = new IvParameterSpec("1234567890123456".getBytes(StandardCharsets.UTF_8));

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        use_case_load_and_use();
    }

    public static void use_case_gen_and_store() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        SecretKey key = AESKeyTool.generateKey();
        AESKeyTool.storeKey(key);
        AESKeyTool.loadKey();
    }

    public static void use_case_load_and_use() throws IOException {
        SecretKey key = AESKeyTool.loadKey();

        String encrypted = encrypt("123456", key);
        System.out.println(encrypted);

        String decrypted = decrypt(encrypted, key);
        System.out.println(decrypted);
    }

    public static String encrypt(String msg, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, IV_SPEC);
            byte[] encrypted = cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Exception when do an encrypt", e);
        }
    }

    public static String decrypt(String msg, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, IV_SPEC);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(msg));
            return new String(decrypted);
        } catch (Exception e) {
            throw new RuntimeException("Exception when do an decrypt", e);
        }
    }
}
