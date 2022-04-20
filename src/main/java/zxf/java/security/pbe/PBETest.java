package zxf.java.security.pbe;

import javax.crypto.Cipher;
import javax.crypto.spec.PBEParameterSpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class PBETest {
    //Salt must be 8 bytes long
    private static byte[] salt = "12345678".getBytes(StandardCharsets.UTF_8);

    public static void main(String[] args) {
        String result = encrypt("hello world!", "passwd");
        System.out.println("encrypt: " + result);
        String original = decrypt(result, "passwd");
        System.out.println("decrypt: " + original);
    }

    public static String encrypt(String msg, String password) {
        try {
            Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
            PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 20);
            cipher.init(Cipher.ENCRYPT_MODE, PBEKeyTool.getKey(password), pbeParameterSpec);
            byte[] encrypted = cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Exception when do an encrypt", e);
        }
    }

    public static String decrypt(String msg, String password) {
        try {
            Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
            PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 20);
            cipher.init(Cipher.DECRYPT_MODE, PBEKeyTool.getKey(password), pbeParameterSpec);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(msg));
            return new String(decrypted);
        } catch (Exception e) {
            throw new RuntimeException("Exception when do an decrypt", e);
        }
    }
}
