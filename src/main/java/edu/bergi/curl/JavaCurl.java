package edu.bergi.curl;



import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class JavaCurl {
    public static String inputStreamToString(InputStream inputStream) {
        final int bufferSize = 8 * 1024;
        byte[] buffer = new byte[bufferSize];
        final StringBuilder builder = new StringBuilder();
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, bufferSize)) {
            while (bufferedInputStream.read(buffer) != -1) {
                builder.append(new String(buffer));
            }
        } catch (IOException ex) {
            log.error("Error! {}",ex.getMessage(),ex);
        }
        return builder.toString();
    }

    public static void consumeInputStream(InputStream inputStream) {
        inputStreamToString(inputStream);
    }
}
