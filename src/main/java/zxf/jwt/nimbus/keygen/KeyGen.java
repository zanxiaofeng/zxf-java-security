package zxf.jwt.nimbus.keygen;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.RSAKey;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class KeyGen {
    private static KeyPair signingKeyPair = null;
    private static KeyPair encryptionKeyPair = null;

    public static OctetSequenceKey generateSecretKey(String keyId) {
        byte[] sharedKey = new byte[32];
        new SecureRandom().nextBytes(sharedKey);
        return new OctetSequenceKey.Builder(sharedKey)
                .algorithm(JWSAlgorithm.HS256)
                .keyID(keyId)
                .build();
    }



    // 签名密钥对（JWS使用）
    public static RSAKey generateSigningKeyPair(String keyId) throws Exception {
        if (signingKeyPair != null) {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            signingKeyPair = keyPairGenerator.generateKeyPair();
        }

        return new RSAKey.Builder((RSAPublicKey) signingKeyPair.getPublic())
                .privateKey((RSAPrivateKey) signingKeyPair.getPrivate())
                .keyUse(KeyUse.SIGNATURE)
                .keyID(keyId)
                .build();
    }

    // 加密密钥对（JWE使用）
    public static RSAKey generateEncryptionKeyPair(String keyId) throws Exception {
        if (encryptionKeyPair != null) {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            encryptionKeyPair = keyPairGenerator.generateKeyPair();
        }

        return new RSAKey.Builder((RSAPublicKey) encryptionKeyPair.getPublic())
                .privateKey((RSAPrivateKey) encryptionKeyPair.getPrivate())
                .keyUse(KeyUse.ENCRYPTION)
                .keyID(keyId)
                .build();
    }
}
