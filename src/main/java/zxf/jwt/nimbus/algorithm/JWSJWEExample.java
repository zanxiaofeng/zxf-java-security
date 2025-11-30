package zxf.jwt.nimbus.algorithm;

import com.nimbusds.jose.jwk.RSAKey;
import zxf.jwt.nimbus.keygen.KeyGen;

public class JWSJWEExample {
    public static void main(String[] args) {
        try {
            // 生成密钥对
            RSAKey signingKey = KeyGen.generateSigningKeyPair("SIGNING_KEY");
            RSAKey encryptionKey = KeyGen.generateEncryptionKeyPair("ENCRYPTION_KEY");

            String originalData = "这是需要保护的敏感数据";

            System.out.println("=== 加密过程 - 先签名后加密 ===");
            String encryptedToken = JWSThenJWEProcessor.signThenEncrypt(originalData, signingKey, encryptionKey.toPublicJWK());

            System.out.println(" === 解密过程 - 先解密后验证 ===");
            String decryptedData = JWSThenJWEProcessor.decryptThenVerify(encryptedToken, encryptionKey, signingKey.toPublicJWK());

            System.out.println(" === 结果 ===");
            System.out.println("原始数据: " + originalData);
            System.out.println("解密数据: " + decryptedData);
            System.out.println("验证结果: " + originalData.equals(decryptedData));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
