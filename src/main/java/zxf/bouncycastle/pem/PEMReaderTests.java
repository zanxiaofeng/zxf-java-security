package zxf.bouncycastle.pem;

import org.apache.xerces.impl.dv.util.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.io.pem.PemHeader;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.FileReader;
import java.security.Security;
import java.util.stream.Collectors;

public class PEMReaderTests {
    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        try (FileReader fileReader = new FileReader("keys/encrypted/encrypted-rsa-key.pem"); PemReader pemReader = new PemReader(fileReader)) {
            PemObject pemObject = pemReader.readPemObject();
            System.out.println("Type: " + pemObject.getType());
            System.out.println("Headers: " + pemObject.getHeaders().stream().map(PEMReaderTests::formatHeader).collect(Collectors.toList()));
            System.out.println("Content: " + Base64.encode(pemObject.getContent()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String formatHeader(Object header) {
        return String.format("%s: %s", ((PemHeader) header).getName(), ((PemHeader) header).getValue());
    }
}
