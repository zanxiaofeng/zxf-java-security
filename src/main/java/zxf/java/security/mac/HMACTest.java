package zxf.java.security.mac;

public class HMACTest {

    public static void main(String[] args) throws Exception {
        case_normal("HMACSHA1");
        case_normal("HMACSHA256");
        case_normal("HMACSHA384");
        case_normal("HMACSHA512");
        case_error("HMACSHA512");
    }

    private static void case_normal(String algorithm) throws Exception {
        System.out.println("case_normal");
        HMACTool hmacTool = new HMACTool(algorithm, HMACKeyTool.loadKey(algorithm));
        String message = "12132235325324324";
        String hmac = hmacTool.generateHMAC(message);
        System.out.println(algorithm + ": " + message + "," + hmac);
        hmacTool.verifyHMAC(message, hmac);
    }

    private static void case_error(String algorithm) throws Exception {
        System.out.println("case_error");
        HMACTool hmacTool = new HMACTool(algorithm, HMACKeyTool.loadKey(algorithm));
        String message1 = "12132235325324324";
        String hmac1 = hmacTool.generateHMAC(message1);
        System.out.println(algorithm + ": " + message1 + "," + hmac1);

        String message2 = "12132235325324325";
        String hmac2 = hmacTool.generateHMAC(message2);
        System.out.println(algorithm + ": " + message2 + "," + hmac2);

        hmacTool.verifyHMAC(message1, hmac2);
    }
}
