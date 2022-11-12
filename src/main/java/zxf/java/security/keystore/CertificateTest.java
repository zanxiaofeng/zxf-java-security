package zxf.java.security.keystore;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

public class CertificateTest {
    /*
    Export certificate to pem
    keytool -export -keystore <keystore-file> -storetype <type> -storepass <pass> -alias <alias> -rfc -file <export.pem>
     */
    public static void main(String[] args) throws IOException, CertificateException {
        try (InputStream stream = new FileInputStream(Paths.get("./keystores/certificate.pem").toFile())) {
            Certificate certificate = CertificateFactory.getInstance("X.509").generateCertificate(stream);
            PublicKey publicKey = certificate.getPublicKey();
        }
    }
}
