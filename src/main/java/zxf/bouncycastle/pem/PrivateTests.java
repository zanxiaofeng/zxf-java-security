package zxf.bouncycastle.pem;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.io.StringWriter;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

public class PrivateTests {
    private static File privateKeyFile = Paths.get("./keystores/private-key.pem").toFile();

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        RSAPrivateKey privateKey1 = readPrivateKeyWithPem();
        RSAPrivateKey privateKey2 = readPrivateKeyWithPem2();
        writePrivateKeyWithPem(privateKey1);
        writePrivateKeyWithPem(privateKey2);
    }

    private static RSAPrivateKey readPrivateKeyWithPem() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        try (FileReader keyReader = new FileReader(privateKeyFile); PemReader pemReader = new PemReader(keyReader)) {
            PemObject pemObject = pemReader.readPemObject();
            byte[] content = pemObject.getContent();
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(content);
            return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(privateKeySpec);
        }
    }

    private static RSAPrivateKey readPrivateKeyWithPem2() throws IOException {
        try (FileReader keyReader = new FileReader(privateKeyFile)) {
            PEMParser pemParser = new PEMParser(keyReader);
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(pemParser.readObject());
            return (RSAPrivateKey) converter.getPrivateKey(privateKeyInfo);
        }
    }

    public static void writePrivateKeyWithPem(RSAPrivateKey privateKey) throws IOException {
        try (StringWriter stringWriter = new StringWriter(); PemWriter privateKeyWriter = new PemWriter(stringWriter)) {
            privateKeyWriter.writeObject(new PemObject("PRIVATE KEY", privateKey.getEncoded()));
            privateKeyWriter.flush();
            System.out.println(stringWriter.toString());
        }
    }
}
