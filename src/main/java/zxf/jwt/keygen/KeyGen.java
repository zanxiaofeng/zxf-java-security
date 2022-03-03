package zxf.jwt.keygen;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.util.Base64;

public class KeyGen {
    public static void main(String[] args) {
        generateKeyForHS256();
        generateKeyForRS256();
    }

    public static void generateKeyForHS256() {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String secretString = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("HS256: \n" + secretString);
    }

    public static void generateKeyForRS256() {
        KeyPair key = Keys.keyPairFor(SignatureAlgorithm.RS256);

        System.out.println("RS256: \n" + Base64.getEncoder().encodeToString(key.getPublic().getEncoded())
                + "\n" + Base64.getEncoder().encodeToString(key.getPrivate().getEncoded()));
    }
}
