package zxf.jwt.algorithm;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

public class RS256 {

    public static void main(String[] args) {
        normal_case();
        time_validation_ok_case();
        time_validation_failed_expired_case();
    }

    public static void normal_case(){
        KeyPair keyPair = generateKeyPair();

        Algorithm rs256ForSign = Algorithm.RSA256(null, (RSAPrivateKey) keyPair.getPrivate());
        Algorithm rs256ForVerify = Algorithm.RSA256((RSAPublicKey) keyPair.getPublic(), null);

        //generate
        String token = JWT.create()
                .withIssuer("davis")
                .sign(rs256ForSign);
        System.out.println(token);

        //verify
        DecodedJWT verifiedJWT = JWT.require(rs256ForVerify)
                .withIssuer("davis")
                .build()
                .verify(token);

        //Just decode
        DecodedJWT decodedJWT = JWT.decode(token);
    }

    public static void time_validation_ok_case() {
        Long currentTime = System.currentTimeMillis();
        KeyPair keyPair = generateKeyPair();

        Algorithm rs256ForSign = Algorithm.RSA256(null, (RSAPrivateKey) keyPair.getPrivate());
        Algorithm rs256ForVerify = Algorithm.RSA256((RSAPublicKey) keyPair.getPublic(), null);

        //generate
        String token = JWT.create()
                .withIssuer("davis")
                .withIssuedAt(new Date(currentTime))
                .withNotBefore(new Date(currentTime))
                .withExpiresAt(new Date(currentTime + 2000))
                .sign(rs256ForSign);
        System.out.println(token);

        //verify
        DecodedJWT verifiedJWT = JWT.require(rs256ForVerify)
                .withIssuer("davis")
                .build()
                .verify(token);

        //Just decode
        DecodedJWT decodedJWT = JWT.decode(token);
    }

    public static void time_validation_failed_expired_case() {
        Long currentTime = System.currentTimeMillis();
        KeyPair keyPair = generateKeyPair();

        Algorithm rs256ForSign = Algorithm.RSA256(null, (RSAPrivateKey) keyPair.getPrivate());
        Algorithm rs256ForVerify = Algorithm.RSA256((RSAPublicKey) keyPair.getPublic(), null);

        //generate
        String token = JWT.create()
                .withIssuer("davis")
                .withIssuedAt(new Date(currentTime))
                .withNotBefore(new Date(currentTime))
                .withExpiresAt(new Date(currentTime + 2000))
                .sign(rs256ForSign);
        System.out.println(token);

        try {
            Thread.sleep(3000);
            //verify
            DecodedJWT verifiedJWT = JWT.require(rs256ForVerify)
                    .withIssuer("davis")
                    .build()
                    .verify(token);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Just decode
        DecodedJWT decodedJWT = JWT.decode(token);
    }

    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            //512, 1024, 2048
            keyPairGenerator.initialize(2048, new SecureRandom());
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new RuntimeException("Exception when generate a keypair", e);
        }
    }
}
