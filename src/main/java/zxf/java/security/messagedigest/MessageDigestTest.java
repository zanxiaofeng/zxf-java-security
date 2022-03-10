package zxf.java.security.messagedigest;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class MessageDigestTest {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        case_normal("MD5");
        case_normal("SHA-1");
        case_normal("SHA-256");
        case_normal("SHA-384");
        case_normal("SHA-512");
    }

    private static void case_normal(String algorithm) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance(algorithm);
        String messageDigest = Base64.getEncoder().encodeToString(md5.digest("12345678080808080".getBytes(StandardCharsets.UTF_8)));
        System.out.println(algorithm + ": " + messageDigest);
    }
}
