package zxf.jwt.jjwt.keygen.algorithm;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class HS256 {
    public static void main(String[] args) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        String jws = Jwts.builder()
                .setHeaderParam("kid", "123456")
                .setSubject("Joe")
                .setIssuer("davis")
                .setIssuedAt(new Date())
                .setNotBefore(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 2000))
                .claim("user", "tester")
                .signWith(key)
                .compact();


        Jws<Claims> jwsR = Jwts.parser().setSigningKey(key).parseClaimsJws(jws);
        System.out.println(jwsR);
        System.out.println(jwsR.getBody().get("user"));
    }
}
