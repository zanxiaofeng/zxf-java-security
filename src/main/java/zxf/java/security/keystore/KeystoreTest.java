package zxf.java.security.keystore;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Collections;

import static zxf.bouncycastle.pem.CertificationTests.writeCertificateWithPem;
import static zxf.bouncycastle.pem.PrivateKeyTests.writePrivateKeyWithPem;
import static zxf.bouncycastle.pem.PublicKeyTests.writePublicKeyWithPem;

public class KeystoreTest {
    public static void main(String[] args) throws UnrecoverableKeyException, CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        loadPKCS12Keystore("./keystores/p12/mykeystore.p12", "changeit");
    }

    private static void loadPKCS12Keystore(String file, String pw) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, UnrecoverableKeyException {
        /* PKCS12
        keytool -genseckey -alias mySecretKey -keypass changeit -keyalg AES -keysize 256 -keystore mykeystore.p12 -storepass changeit -storetype PKCS12 -v
        keytool -genkeypair -alias myPrivateKey -keypass changeit -keyalg RSA -keysize 2048 -validity 1 -dname "CN=John Smith, OU=Development, O=Standard Supplies Inc., L=Anytown, S=North Carolina, C=US" -keystore mykeystore.p12 -storepass changeit -storetype PKCS12 -v
        keytool -v -list -keystore mykeystore.p12 -storepass changeit
        #export cert from keystore
        keytool -export -keystore mykeystore.p12 -storetype PKCS12 -storepass <pass> -alias changeit -rfc -file certificate.pem
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
        writePrivateKeyWithPem((RSAPrivateKey) myPrivateKey);

        Certificate myCertificate = keyStore.getCertificate("myPrivateKey");
        writeCertificateWithPem((X509Certificate) myCertificate);

        PublicKey myPublicKey = myCertificate.getPublicKey();
        writePublicKeyWithPem((RSAPublicKey) myPublicKey);

        SecretKey mySecretKey = (SecretKey) keyStore.getKey("mySecretKey", password);

        Collections.list(keyStore.aliases()).stream().forEach((alias) -> {
            try {
                if (keyStore.isCertificateEntry(alias)) {
                    KeyStore.TrustedCertificateEntry trustedCertificateEntry = (KeyStore.TrustedCertificateEntry) keyStore.getEntry(alias, null);
                    System.out.println("##.TrustedCertificateEntry." + alias);
                    System.out.println("##.." + trustedCertificateEntry);
                    return;
                }

                KeyStore.Entry entry = keyStore.getEntry(alias, new KeyStore.PasswordProtection(password));
                if (entry instanceof KeyStore.PrivateKeyEntry) {
                    KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) entry;
                    System.out.println("##.PrivateKeyEntry." + alias);
                    System.out.println("##.." + privateKeyEntry);
                } else if (entry instanceof KeyStore.SecretKeyEntry) {
                    KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry) entry;
                    System.out.println("##.SecretKeyEntry." + alias);
                    System.out.println("##.." + secretKeyEntry);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
