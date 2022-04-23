package zxf.java.security.mac;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class HMACTool {
    private String algorithm;
    private SecretKey secretKey;

    public HMACTool(String algorithm, SecretKey secretKey) {
        this.algorithm = algorithm;
        this.secretKey = secretKey;

    }

    public String generateHMAC(String message) throws Exception {
        Mac mac = Mac.getInstance(algorithm);
        mac.init(secretKey);
        mac.update(message.getBytes(StandardCharsets.UTF_8));
        byte[] result = mac.doFinal();
        return Base64.getEncoder().encodeToString(result);
    }

    public void verifyHMAC(String message, String hmac) throws Exception {
        String calculatedHMAC = generateHMAC(message);
        if (!calculatedHMAC.equals(hmac)) {
            throw new Exception("invalid hmac");
        }
    }
}
