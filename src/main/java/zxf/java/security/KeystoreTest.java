package zxf.java.security;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public class KeystoreTest {
    public static void main(String[] args) throws UnrecoverableKeyException, CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        loadPKCS12Keystore("./keystores/mykeystore.p12", "changeit");
    }

    private static void loadPKCS12Keystore(String file, String pw) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, UnrecoverableKeyException {
        /* PKCS12
        keytool -genseckey -alias mySecretKey -keypass changeit -keyalg AES -keysize 256 -keystore mykeystore.p12 -storepass changeit -storetype PKCS12 -v
        keytool -genkeypair -alias myPrivateKey -keypass changeit -keyalg RSA -keysize 2048 -validity 1 -dname "CN=John Smith, OU=Development, O=Standard Supplies Inc., L=Anytown, S=North Carolina, C=US" -keystore mykeystore.p12 -storepass changeit -storetype PKCS12 -v
        keytool -list -keystore mykeystore.p12 -storepass changeit
        CN - Common Name of the certificate owner
        OU - Organizational Unit of the certificate owner
        O - Organization to which the certificate owner belongs
        L - Locality name of the certificate owner
        S - State or province of the certificate owner
        C - Country of the certificate owner
        */

        char[] password = pw.toCharArray();
        KeyStore keyStore = KeyStore.getInstance(Paths.get(file).toFile(), password);
        PrivateKey myPrivateKey = (PrivateKey) keyStore.getKey("myPrivateKey", password);
        Certificate myCertificate = keyStore.getCertificate("myPrivateKey");
        PublicKey myPublicKey = myCertificate.getPublicKey();
        SecretKey mySecretKey = (SecretKey) keyStore.getKey("mySecretKey", password);
    }
}
