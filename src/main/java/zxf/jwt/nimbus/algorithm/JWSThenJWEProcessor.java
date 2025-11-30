package zxf.jwt.nimbus.algorithm;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.util.Date;

public class JWSThenJWEProcessor {

    /**
     * 先签名后加密
     */
    public static String signThenEncrypt(String payload, RSAKey signingKey, RSAKey encryptionKey) throws Exception {
        // ========== 第一步：JWS签名 ==========
        // 1. 创建JWT声明集
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject("user123")
                .issuer("https://example.com")
                .expirationTime(new Date(System.currentTimeMillis() + 3600 * 1000))
                .claim("data", payload)
                .build();

        // 2. 创建签名JWT
        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.RS256)
                        .keyID(signingKey.getKeyID())
                        .build(), claimsSet);

        // 3. 使用私钥签名
        JWSSigner signer = new RSASSASigner(signingKey);
        signedJWT.sign(signer);

        String signedJWTString = signedJWT.serialize();
        System.out.println("JWS签名后的token: " + signedJWTString);

        // ========== 第二步：JWE加密 ==========
        // 4. 创建JWE头（指定加密算法）
        JWEHeader jweHeader = new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM)
                // 重要：表明加密的内容是JWT
                .contentType("JWT")
                .keyID(encryptionKey.getKeyID())
                .build();

        // 5. 创建JWE对象，将签名的JWT作为载荷
        JWEObject jweObject = new JWEObject(jweHeader, new Payload(signedJWT));

        // 6. 使用公钥加密
        JWEEncrypter encrypter = new RSAEncrypter(encryptionKey);
        jweObject.encrypt(encrypter);

        String jweToken = jweObject.serialize();
        System.out.println("JWE加密后的token: " + jweToken);
        return jweToken;
    }

    /**
     * 先解密后验证签名
     */
    public static String decryptThenVerify(String jweToken, RSAKey decryptionKey, RSAKey verificationKey) throws Exception {
        // ========== 第一步：JWE解密 ==========
        // 1. 解析JWE token
        JWEObject jweObject = JWEObject.parse(jweToken);

        // 2. 使用私钥解密
        JWEDecrypter decrypter = new RSADecrypter(decryptionKey);
        jweObject.decrypt(decrypter);

        // 3. 获取解密后的载荷（应该是签名的JWT）
        Payload payload = jweObject.getPayload();
        SignedJWT signedJWT = payload.toSignedJWT();

        if (signedJWT == null) {
            throw new JOSEException("解密后的内容不是有效的JWT");
        }
        System.out.println("解密后的JWS: " + signedJWT.serialize());

        // ========== 第二步：JWS验签 ==========
        // 4. 验证签名
        JWSVerifier verifier = new RSASSAVerifier(verificationKey);
        if (!signedJWT.verify(verifier)) {
            throw new JOSEException("签名验证失败");
        }

        // 5. 验证过期时间等声明
        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
        if (claimsSet.getExpirationTime().before(new Date())) {
            throw new JOSEException("Token已过期");
        }

        // 6. 返回原始数据
        return claimsSet.getStringClaim("data");
    }
}
