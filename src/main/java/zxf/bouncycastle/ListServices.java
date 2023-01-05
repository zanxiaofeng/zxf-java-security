package zxf.bouncycastle;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;

import java.util.stream.Stream;

public class ListServices {
    public static void main(String[] args) {
        Stream.of(new BouncyCastleProvider(), new BouncyCastlePQCProvider()).forEach(provider -> {
            System.out.println(provider.getName());

            provider.getServices().stream().forEach(s -> {
                System.out.println("\tS: " + s.getType() + "\t: " + s.getAlgorithm());
            });
        });
    }
}
