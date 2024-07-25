package zxf.escape;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;

public class EscapeTest {

    public static void main(String[] args) {
        //On windows line.separator = \r\n = CR(0D)+LF(0A)
        //On linux line.separator = \n = LF(0A)
        System.out.println("line.separator=" + Hex.toHexString(System.getProperty("line.separator").getBytes(StandardCharsets.UTF_8)));
        System.out.println("line.separator=" + Hex.toHexString(System.lineSeparator().getBytes(StandardCharsets.UTF_8)));

        System.out.println("a\r\n\t=" + "a\r\n\tbc".replaceAll("(\\r|\\n|\\t)", ""));

        System.out.println("normalizeSpace: a\n\r\tbc => " + StringUtils.normalizeSpace("a\n\r\tbc"));

        System.out.println("escapeCsv::");
        System.out.println("escapeCsv: \"abc => " + StringEscapeUtils.escapeCsv("\"abc"));
        System.out.println("escapeCsv: a,bc => " + StringEscapeUtils.escapeCsv("a,bc"));
        System.out.println("escapeCsv: \"abc\" => " + StringEscapeUtils.escapeCsv("\"abc\""));
        System.out.println("escapeCsv: \"ab,c\" => " + StringEscapeUtils.escapeCsv("\"ab,c\""));
        System.out.println("escapeCsv: a\n\rbc => " + StringEscapeUtils.escapeCsv("a\n\rbc"));

        System.out.println("escapeJson::");
        System.out.println("escapeJson: \"abc\" => " + StringEscapeUtils.escapeJson("\"abc\""));
    }
}
