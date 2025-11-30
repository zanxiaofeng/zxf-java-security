package zxf.jwt.auth0.algorithm;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class HS256 {
    private static Algorithm algorithmHS = Algorithm.HMAC256("3425345345435");

    public static void main(String[] args) {
        normal_case();
        time_validation_ok_case();
        time_validation_ok_not_before_case();
        time_validation_failed_expired_case();
        time_validation_failed_before_case();
    }

    public static void normal_case() {
        //generate
        String token = JWT.create()
                .withIssuer("davis")
                .sign(algorithmHS);
        System.out.println(token);

        //verify
        DecodedJWT verifiedJWT = JWT.require(algorithmHS)
                .withIssuer("davis")
                .build()
                .verify(token);

        //Just decode
        DecodedJWT decodedJWT = JWT.decode(token);
    }

    public static void time_validation_ok_case() {
        Long currentTime = System.currentTimeMillis();
        //generate
        String token = JWT.create()
                .withIssuer("davis")
                .withIssuedAt(new Date(currentTime))
                .withNotBefore(new Date(currentTime))
                .withExpiresAt(new Date(currentTime + 2000))
                .sign(algorithmHS);
        System.out.println(token);

        //verify
        DecodedJWT verifiedJWT = JWT.require(algorithmHS)
                .withIssuer("davis")
                .build()
                .verify(token);

        //Just decode
        DecodedJWT decodedJWT = JWT.decode(token);
    }

    public static void time_validation_ok_not_before_case()  {
        Long currentTime = System.currentTimeMillis();
        //generate
        String token = JWT.create()
                .withIssuer("davis")
                .withIssuedAt(new Date(currentTime))
                .withNotBefore(new Date(currentTime + 5000))
                .withExpiresAt(new Date(currentTime + 10000))
                .sign(algorithmHS);
        System.out.println(token);

        try {
            Thread.sleep(6000);
            //verify
            DecodedJWT verifiedJWT = JWT.require(algorithmHS)
                    .withIssuer("davis")
                    .build()
                    .verify(token);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //Just decode
        DecodedJWT decodedJWT = JWT.decode(token);
    }

    public static void time_validation_failed_expired_case() {
        Long currentTime = System.currentTimeMillis();
        //generate
        String token = JWT.create()
                .withIssuer("davis")
                .withIssuedAt(new Date(currentTime))
                .withNotBefore(new Date(currentTime))
                .withExpiresAt(new Date(currentTime + 2000))
                .sign(algorithmHS);
        System.out.println(token);

        try {
            Thread.sleep(3000);
            //verify
            DecodedJWT verifiedJWT = JWT.require(algorithmHS)
                    .withIssuer("davis")
                    .build()
                    .verify(token);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Just decode
        DecodedJWT decodedJWT = JWT.decode(token);
    }

    public static void time_validation_failed_before_case() {
        Long currentTime = System.currentTimeMillis();
        //generate
        String token = JWT.create()
                .withIssuer("davis")
                .withIssuedAt(new Date(currentTime))
                .withNotBefore(new Date(currentTime + 5000))
                .withExpiresAt(new Date(currentTime + 10000))
                .sign(algorithmHS);
        System.out.println(token);

        try {
            Thread.sleep(2000);
            //verify
            DecodedJWT verifiedJWT = JWT.require(algorithmHS)
                    .withIssuer("davis")
                    .build()
                    .verify(token);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Just decode
        DecodedJWT decodedJWT = JWT.decode(token);
    }
}
