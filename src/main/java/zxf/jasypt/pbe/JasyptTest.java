package zxf.jasypt.pbe;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import java.security.Security;

public class JasyptTest {
    private static String ALGORITHM = "PBEWITHSHA256AND256BITAES-CBC-BC";
    private static String PASSWORD = "131234434242";

    public static void main(String[] args) {
        System.out.println("enc: " + encrypt("abc"));
        System.out.println("dec: " + decrypt("X1M5fMGxStZ+dmWBmO+gT1ybQ/w2TuLUAlkxcyvJqbw="));
    }

    public static String encrypt(String plainText) {
        Security.addProvider(new BouncyCastleProvider());
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        standardPBEStringEncryptor.setAlgorithm(ALGORITHM);
        standardPBEStringEncryptor.setPassword(PASSWORD);
        standardPBEStringEncryptor.setStringOutputType("base64");
        standardPBEStringEncryptor.setKeyObtentionIterations(10000);
        standardPBEStringEncryptor.setProvider(new BouncyCastleProvider());
        return standardPBEStringEncryptor.encrypt(plainText);
    }

    public static String decrypt(String encryptedText) {
        Security.addProvider(new BouncyCastleProvider());
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        standardPBEStringEncryptor.setAlgorithm(ALGORITHM);
        standardPBEStringEncryptor.setPassword(PASSWORD);
        standardPBEStringEncryptor.setStringOutputType("base64");
        standardPBEStringEncryptor.setKeyObtentionIterations(10000);
        standardPBEStringEncryptor.setProviderName("BC");
        return standardPBEStringEncryptor.decrypt(encryptedText);
    }
}
