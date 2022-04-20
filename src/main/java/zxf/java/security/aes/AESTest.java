package zxf.java.security.aes;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import java.security.NoSuchAlgorithmException;

import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class AESTest {
    //CBC mode need an iv(16 byte)
    private static IvParameterSpec CBC_PARAMETER = new IvParameterSpec("1234567890123456".getBytes(StandardCharsets.UTF_8));
    //TLen value must be one of {128, 120, 112, 104, 96}
    private static GCMParameterSpec GCM_PARAMETER = new GCMParameterSpec(112, "1234567".getBytes(StandardCharsets.UTF_8));

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        use_case_load_and_use_for_cbc();
        use_case_load_and_use_for_gcm();
    }

    public static void use_case_gen_and_store() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        SecretKey key = AESKeyTool.generateKey();
        AESKeyTool.storeKey(key);
        AESKeyTool.loadKey();
    }

    public static void use_case_load_and_use_for_cbc() throws IOException {
        SecretKey key = AESKeyTool.loadKey();

        String encrypted = encryptForCBC("123456", key);
        System.out.println(encrypted);

        String decrypted = decryptForCBC(encrypted, key);
        System.out.println(decrypted);
    }

    public static void use_case_load_and_use_for_gcm() throws IOException {
        SecretKey key = AESKeyTool.loadKey();

        String encrypted = encryptForGCM("123456", key);
        System.out.println(encrypted);

        String decrypted = decryptForGCM(encrypted, key);
        System.out.println(decrypted);
    }

    public static String encryptForCBC(String msg, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, CBC_PARAMETER);
            byte[] encrypted = cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Exception when do an encrypt", e);
        }
    }

    public static String decryptForCBC(String msg, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, CBC_PARAMETER);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(msg));
            return new String(decrypted);
        } catch (Exception e) {
            throw new RuntimeException("Exception when do an decrypt", e);
        }
    }

    public static String encryptForGCM(String msg, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, GCM_PARAMETER);
            byte[] encrypted = cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Exception when do an encrypt", e);
        }
    }

    public static String decryptForGCM(String msg, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, GCM_PARAMETER);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(msg));
            return new String(decrypted);
        } catch (Exception e) {
            throw new RuntimeException("Exception when do an decrypt", e);
        }
    }
}
