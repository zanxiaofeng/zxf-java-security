package zxf.java.security.iostream;

import org.springframework.util.StreamUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class IOStreamTest {
    public static void main(String[] args) throws IOException {
        case1_1();
        case2_1();
        case2_2();
        case3_1();
        case3_2();
    }

    public static void case1_1() throws IOException {
        //produce by self
        InputStream stream = new FileInputStream("./keys/hmac-secret-key-hmacsha256-base64");
        //consume by self
        System.out.println("case 1-1: " + StreamUtils.copyToString(stream, StandardCharsets.UTF_8));
        //close by self
        stream.close();
    }

    public static void case2_1() throws IOException {
        //produce by Files
        InputStream stream = Files.newInputStream(Paths.get("./keys/hmac-secret-key-hmacsha256-base64"));
        //consume by self
        System.out.println("case 1-1: " + StreamUtils.copyToString(stream, StandardCharsets.UTF_8));
        //close by self
        stream.close();
    }

    public static void case2_2() throws IOException {
        Consumer<InputStream> consumer = (stream) -> {
            //consume by consumer
            try {
                System.out.println("case 2-2 in consumer: " + StreamUtils.copyToString(stream, StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }

            //close by consumer
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        //produce by producer
        InputStream stream = new FileInputStream("./keys/hmac-secret-key-hmacsha256-base64");
        //consume by consumer
        consumer.accept(stream);
    }

    public static void case3_1() throws IOException {
        BiConsumer<InputStream, Integer> consumer = (stream, len) -> {
            //consume by consumer
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                StreamUtils.copyRange(stream, byteArrayOutputStream, 0, len);
                System.out.println("case 3-1 in consumer: " + new String(byteArrayOutputStream.toByteArray(), "UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            //close by consumer -- will not really close it by wrap the stream with StreamUtils.nonClosing
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        //produce by producer
        InputStream stream = new FileInputStream("./keys/hmac-secret-key-hmacsha256-base64");
        //consume first 10 chars in consumer
        consumer.accept(StreamUtils.nonClosing(stream), 10);
        //consume rest chars in producer
        System.out.println("case 3-1 in producer: " + StreamUtils.copyToString(stream, StandardCharsets.UTF_8));
        //close by producer
        stream.close();
    }

    public static void case3_2() throws IOException {
        BiConsumer<Supplier<InputStream>, Integer> consumer = (streamProvider, len) -> {
            //produce by producer
            InputStream stream = streamProvider.get();

            //consume by consumer
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                StreamUtils.copyRange(stream, byteArrayOutputStream, 0, len);
                System.out.println("case 3-2 in consumer: " + new String(byteArrayOutputStream.toByteArray(), "UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            //close by consumer -- will not really close it by wrap the stream with StreamUtils.nonClosing
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        //produce by producer
        InputStream stream = new FileInputStream("./keys/hmac-secret-key-hmacsha256-base64");
        //consume first 20 chars in consumer
        consumer.accept(() -> StreamUtils.nonClosing(stream), 20);
        //consume rest chars in producer
        System.out.println("case 3-2 in producer: " + StreamUtils.copyToString(stream, StandardCharsets.UTF_8));
        //close by producer
        stream.close();
    }
}
