package zxf.bouncycastle.pem;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JceOpenSSLPKCS8DecryptorProviderBuilder;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;
import org.bouncycastle.operator.InputDecryptorProvider;
import org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfo;

import java.io.FileReader;
import java.security.PrivateKey;
import java.security.Security;

public class EncryptedPemTests {
    public static void main(String[] args) {
        loadPrivateKey("keys/encrypted/encrypted-rsa-key.pem", "123456".toCharArray());
        loadPrivateKey("keys/encrypted/encrypted-dsa-key.pem", "123456".toCharArray());
    }

    public static PrivateKey loadPrivateKey(String pemFilePath, char[] password) {
        Security.addProvider(new BouncyCastleProvider());
        try (FileReader fileReader = new FileReader(pemFilePath); PEMParser pemParser = new PEMParser(fileReader)) {
            Object object = pemParser.readObject();
            System.out.println("file: " + pemFilePath + ", type=" + object.getClass().getName());
            if (object instanceof PKCS8EncryptedPrivateKeyInfo) {
                PKCS8EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = (PKCS8EncryptedPrivateKeyInfo) object;
                InputDecryptorProvider decryptorProvider = new JceOpenSSLPKCS8DecryptorProviderBuilder().build(password);
                PrivateKeyInfo privateKeyInfo = encryptedPrivateKeyInfo.decryptPrivateKeyInfo(decryptorProvider);
                JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
                PrivateKey privateKey = converter.getPrivateKey(privateKeyInfo);
                System.out.println("Successfully loaded and decrypted the private key.");
                return privateKey;
            } else if (object instanceof PEMEncryptedKeyPair) {
                PEMEncryptedKeyPair encryptedKeyPair = (PEMEncryptedKeyPair) object;
                PEMDecryptorProvider decryptorProvider = new JcePEMDecryptorProviderBuilder().build(password);
                PEMKeyPair keyPair = encryptedKeyPair.decryptKeyPair(decryptorProvider);
                JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
                PrivateKey privateKey = converter.getPrivateKey(keyPair.getPrivateKeyInfo());
                System.out.println("Successfully loaded and decrypted the private key.");
                return privateKey;
            }
            throw new IllegalArgumentException("Unsupported PEM object type: " + object.getClass().getName());
        } catch (Exception e) {
            System.out.println("Error loading PEM file: " + e.getMessage());
        }
        return null;
    }
}
