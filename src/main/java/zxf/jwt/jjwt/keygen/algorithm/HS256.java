package zxf.jwt.jjwt.keygen.algorithm;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.Date;

public class HS256 {
    public static void main(String[] args) {
        // Generate a secure random key for HS256 (256 bits = 32 bytes)
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32];
        random.nextBytes(keyBytes);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        String jws = Jwts.builder()
                .header().keyId("123456").and()
                .subject("Joe")
                .issuer("davis")
                .issuedAt(new Date())
                .notBefore(new Date())
                .expiration(new Date(System.currentTimeMillis() + 2000))
                .claim("user", "tester")
                .signWith(key)
                .compact();

        Jws<Claims> jwsR = Jwts.parser().verifyWith(key).build().parseSignedClaims(jws);
        System.out.println(jwsR);
        System.out.println(jwsR.getPayload().get("user"));
    }
}
