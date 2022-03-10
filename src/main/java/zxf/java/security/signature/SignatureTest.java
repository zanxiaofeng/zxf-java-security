package zxf.java.security.signature;


import zxf.java.security.rsa.RSAKeyTool;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class SignatureTest {
    public static void main(String[] args) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        case_normal("SHA1withRSA");
        case_normal("SHA256withRSA");
        case_normal("SHA384withRSA");
        case_normal("SHA512withRSA");
    }


    private static void case_normal(String algorithm) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        KeyPair keyPair = RSAKeyTool.loadKeyPairFromBase64();
        byte[] content = "1234567890".getBytes(StandardCharsets.UTF_8);

        Signature signer = Signature.getInstance(algorithm);
        signer.initSign(keyPair.getPrivate());
        signer.update(content);
        byte[] signature = signer.sign();
        System.out.println(algorithm + ": " + Base64.getEncoder().encodeToString(signature));

        Signature verify = Signature.getInstance(algorithm);
        verify.initVerify(keyPair.getPublic());
        verify.update(content);
        verify.verify(signature);
    }
}
