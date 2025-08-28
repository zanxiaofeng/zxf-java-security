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

public class LoadEncryptedPem {
    public static void main(String[] args) {
        try {
            String pemFilePath = "keys/encrypted/encrypted-private-key.pem";
            String password = "123456";
            PrivateKey privateKey = loadPrivateKey(pemFilePath, password.toCharArray());
            System.out.println("Successfully loaded and decrypted the private key.");
        } catch (Exception e) {
            System.out.println("Error loading PEM file: " + e.getMessage());
        }
    }

    public static PrivateKey loadPrivateKey(String pemFilePath, char[] password) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        try (FileReader fileReader = new FileReader(pemFilePath); PEMParser pemParser = new PEMParser(fileReader)) {
            Object object = pemParser.readObject();
            System.out.println("file: " + pemFilePath + ", type=" + object.getClass().getName());
            if (object instanceof PKCS8EncryptedPrivateKeyInfo) {
                PKCS8EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = (PKCS8EncryptedPrivateKeyInfo) object;
                InputDecryptorProvider decryptorProvider = new JceOpenSSLPKCS8DecryptorProviderBuilder().build(password);
                PrivateKeyInfo privateKeyInfo = encryptedPrivateKeyInfo.decryptPrivateKeyInfo(decryptorProvider);
                JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
                return converter.getPrivateKey(privateKeyInfo);
            } else if (object instanceof PEMEncryptedKeyPair) {
                PEMEncryptedKeyPair encryptedKeyPair = (PEMEncryptedKeyPair) object;
                PEMDecryptorProvider decryptorProvider = new JcePEMDecryptorProviderBuilder().build(password);
                PEMKeyPair keyPair = encryptedKeyPair.decryptKeyPair(decryptorProvider);
                JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
                return converter.getPrivateKey(keyPair.getPrivateKeyInfo());
            }
            throw new IllegalArgumentException("Unsupported PEM object type: " + object.getClass().getName());
        }
    }
}
