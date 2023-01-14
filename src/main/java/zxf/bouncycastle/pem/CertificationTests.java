package zxf.bouncycastle.pem;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.*;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.cert.*;
import java.security.spec.InvalidKeySpecException;

public class CertificationTests {
    private static File certificateFile = Paths.get("./keystores/certificate.pem").toFile();

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException, CertificateException {
        X509Certificate certificate1 = readCertificateFileWithPem();
        writeCertificateWithPem(certificate1);
    }

    private static X509Certificate readCertificateFileWithPem() throws IOException, CertificateException {
        try (InputStream stream = new FileInputStream(Paths.get("./keystores/certificate.pem").toFile())) {
            return (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(stream);
        }
    }

    public static void writeCertificateWithPem(X509Certificate certificate) throws IOException, CertificateEncodingException {
        try (StringWriter stringWriter = new StringWriter(); PemWriter privateKeyWriter = new PemWriter(stringWriter)) {
            privateKeyWriter.writeObject(new PemObject("CERTIFICATE", certificate.getEncoded()));
            privateKeyWriter.flush();
            System.out.println(stringWriter.toString());
        }
    }
}
