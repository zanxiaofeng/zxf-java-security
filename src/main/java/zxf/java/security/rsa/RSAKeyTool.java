package zxf.java.security.rsa;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAKeyTool {
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

    public static void storeKeyPair(KeyPair keyPair) {
        try {
            Files.write(Paths.get("./keys/public-key"), keyPair.getPublic().getEncoded());
            Files.write(Paths.get("./keys/private-key"), keyPair.getPrivate().getEncoded());
        } catch (Exception e) {
            throw new RuntimeException("Exception when store a keypair", e);
        }
    }

    public static void storeKeyPairAsBase64(KeyPair keyPair) {
        try {
            Files.write(Paths.get("./keys/public-key-base64"), Base64.getEncoder().encode(keyPair.getPublic().getEncoded()));
            Files.write(Paths.get("./keys/private-key-base64"), Base64.getEncoder().encode(keyPair.getPrivate().getEncoded()));
        } catch (Exception e) {
            throw new RuntimeException("Exception when store a keypair with base64 encoded", e);
        }
    }

    public static KeyPair loadKeyPair() {
        try {
            PublicKey publicKey = loadPublicKey(Files.readAllBytes(Paths.get("./keys/public-key")));
            PrivateKey privateKey = loadPrivateKey(Files.readAllBytes(Paths.get("./keys/private-key")));
            return new KeyPair(publicKey, privateKey);
        } catch (Exception e) {
            throw new RuntimeException("Exception when load a keypair", e);
        }
    }

    public static KeyPair loadKeyPairFromBase64() {
        try {
            PublicKey publicKey = loadPublicKey(Base64.getDecoder().decode(Files.readAllBytes(Paths.get("./keys/public-key-base64"))));
            PrivateKey privateKey = loadPrivateKey(Base64.getDecoder().decode(Files.readAllBytes(Paths.get("./keys/private-key-base64"))));
            return new KeyPair(publicKey, privateKey);
        } catch (Exception e) {
            throw new RuntimeException("Exception when load a keypair with base64 encoded", e);
        }
    }


    private static PublicKey loadPublicKey(byte[] encodedKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(encodedKey));
    }

    private static PrivateKey loadPrivateKey(byte[] encodedKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
    }
}
