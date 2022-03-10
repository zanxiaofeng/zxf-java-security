package zxf.java.security.mac;


import javax.crypto.Mac;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

public class HMACTest {

    public static void main(String[] args) {

    }

    private static void case_normal(String algorithm) throws NoSuchAlgorithmException {
        Mac mac = Mac.getInstance(algorithm);
        mac.init();
        mac.update("12132235325324324".getBytes(StandardCharsets.UTF_8));
        byte[] result = mac.doFinal();
    }
}
