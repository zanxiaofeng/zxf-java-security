package zxf.esapi;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.MySQLCodec;

public class ESAPITest {
    public static void main(String[] args) {
        System.out.println("encodeForSQL: ' or 1=1->" + ESAPI.encoder().encodeForSQL(new MySQLCodec(MySQLCodec.Mode.STANDARD), "' or 1=1"));
        System.out.println("encodeForHTML: &<>->" + ESAPI.encoder().encodeForHTML("&<>"));
    }
}
