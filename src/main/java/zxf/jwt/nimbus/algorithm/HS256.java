package zxf.jwt.nimbus.algorithm;


import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import org.springframework.util.Assert;

import java.security.SecureRandom;
import java.text.ParseException;

public class HS256 {
    public static void main(String[] args) throws JOSEException, ParseException {
        normal_case();
    }

    private static void normal_case() throws JOSEException, ParseException {
        // Generate random 256-bit (32-byte) shared secret
        SecureRandom random = new SecureRandom();
        byte[] sharedSecret = new byte[32];
        random.nextBytes(sharedSecret);

        // Generate
        JWSSigner signer = new MACSigner(sharedSecret);
        JWSObject jwsObjectGen = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload("Hello, world!"));
        jwsObjectGen.sign(signer);
        String s = jwsObjectGen.serialize();

        // Verify
        JWSObject jwsObjectVef = JWSObject.parse(s);
        JWSVerifier verifier = new MACVerifier(sharedSecret);

        System.out.println(jwsObjectVef.verify(verifier));
        Assert.isTrue(jwsObjectVef.verify(verifier), "verify failed");
        System.out.println(jwsObjectVef.getPayload().toString());
        Assert.isTrue(jwsObjectVef.getPayload().toString().equals("Hello, world!"), "payload not match");

        // Just decode
        JWSObject jwsObjectDec = JWSObject.parse(s);
        System.out.println(jwsObjectDec.getPayload().toString());
    }
}
