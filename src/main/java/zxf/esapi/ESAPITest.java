package zxf.esapi;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.MySQLCodec;
import org.owasp.esapi.codecs.OracleCodec;

public class ESAPITest {
    public static void main(String[] args) {
        testEncoder();
        testValidator();
    }

    private static void testEncoder() {
        System.out.println("encodeForSQL: ' or 1=1->" + ESAPI.encoder().encodeForSQL(new MySQLCodec(MySQLCodec.Mode.STANDARD), "' or 1=1"));
        System.out.println("encodeForSQL: ' or 1=1->" + ESAPI.encoder().encodeForSQL(new OracleCodec(), "' or 1=1"));
        System.out.println("encodeForHTML: &<>->" + ESAPI.encoder().encodeForHTML("&<>"));
    }

    private static void testValidator(){
        Boolean result1 = ESAPI.validator().isValidInput("HTTPScheme", "sql", "HTTPScheme", 6, false);
        System.out.println(result1);
        Boolean result2 = ESAPI.validator().isValidInput("HTTPScheme", "http", "HTTPScheme", 6, false);
        System.out.println(result2);
    }
}
