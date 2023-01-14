package zxf.bouncycastle.pem;

import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.*;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class PublicKeyTests {
    private static File publicKeyFile = Paths.get("./keystores/public-key.pem").toFile();

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        RSAPublicKey publicKey1 = readPublicKeyWithPem();
        RSAPublicKey publicKey2 = readPublicKeyWithPem2();
        writePublicKeyWithPem(publicKey1);
        writePublicKeyWithPem(publicKey2);
    }

    private static RSAPublicKey readPublicKeyWithPem() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        try (FileReader keyReader = new FileReader(publicKeyFile); PemReader pemReader = new PemReader(keyReader)) {
            PemObject pemObject = pemReader.readPemObject();
            byte[] content = pemObject.getContent();
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(content);
            return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(pubKeySpec);
        }
    }

    private static RSAPublicKey readPublicKeyWithPem2() throws IOException {
        try (FileReader keyReader = new FileReader(publicKeyFile)) {
            PEMParser pemParser = new PEMParser(keyReader);
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            SubjectPublicKeyInfo publicKeyInfo = SubjectPublicKeyInfo.getInstance(pemParser.readObject());
            return (RSAPublicKey) converter.getPublicKey(publicKeyInfo);
        }
    }

    public static void writePublicKeyWithPem(RSAPublicKey publicKey) throws IOException {
        try (StringWriter stringWriter = new StringWriter(); PemWriter publicKeyWriter = new PemWriter(stringWriter)) {
            publicKeyWriter.writeObject(new PemObject("PUBLIC KEY", publicKey.getEncoded()));
            publicKeyWriter.flush();
            System.out.println(stringWriter.toString());
        }
    }
}
