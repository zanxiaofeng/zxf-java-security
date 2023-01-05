package zxf.java.security.info;

import java.util.*;
import javax.net.ssl.*;

public class ListSSLCipherSuites {
    public static void main(String[] args) {
        Arrays.stream(((SSLServerSocketFactory)SSLServerSocketFactory.getDefault()).getSupportedCipherSuites()).forEach(System.out::println);
    }
}