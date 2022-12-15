package zxf.escape;


import org.apache.commons.text.StringEscapeUtils;

public class EscapeTest {

    public static void main(String[] args) {
        System.out.println("escapeCsv: \"abc => " + StringEscapeUtils.escapeCsv("\"abc"));
        System.out.println("escapeJson: \"abc\" => " + StringEscapeUtils.escapeJson("\"abc\""));
    }
}
