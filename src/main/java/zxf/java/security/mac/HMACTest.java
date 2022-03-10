package zxf.java.security.mac;


import javax.crypto.Mac;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HMACTest {

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
        case_normal("HMACSHA1");
        case_normal("HMACSHA256");
        case_normal("HMACSHA384");
        case_normal("HMACSHA512");
    }

    private static void case_normal(String algorithm) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(algorithm);
        mac.init(HMACKeyTool.generateKey(algorithm));
        mac.update("12132235325324324".getBytes(StandardCharsets.UTF_8));
        byte[] result = mac.doFinal();
        System.out.println(algorithm + ": " + Base64.getEncoder().encodeToString(result));
    }
}
