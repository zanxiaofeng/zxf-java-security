package zxf.java.security.info;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;

import java.security.Provider;
import java.security.Security;

public class ListProviders {

    static {
        Security.addProvider(new BouncyCastleProvider());
        Security.addProvider(new BouncyCastlePQCProvider());
    }

    public static void main(String[] args) {
        for (Provider provider : Security.getProviders()) {
            System.out.println(provider.getName() + ": " + provider.getClass().getName());

            provider.stringPropertyNames().stream().forEach(key -> {
                System.out.println("\tP: " + key + "\t: " + provider.getProperty(key));
            });

            provider.getServices().stream().forEach(s -> {
                System.out.println("\tS: " + s.getType() + "\t: " + s.getAlgorithm());
            });
        }
    }
}
