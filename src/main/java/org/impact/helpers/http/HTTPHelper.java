package org.impact.helpers.http;

import org.impact.Properties;
import org.impact.helpers.ArrayHelper;
import org.impact.http.HTTPPage;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Date;

public class HTTPHelper {
    public static int sendGETResponse(Integer responseCode, HTTPPage page, Socket client, Charset encoding) {
        try (OutputStream outputStream = client.getOutputStream()) {

            outputStream.write(("HTTP/1.1 " + responseCode + " OK\n").getBytes(encoding));
            outputStream.write(("Connection: Keep-Alive\n").getBytes(encoding));
            outputStream.write(("Keep-Alive: timeout=5, max=1000\n").getBytes(encoding));
            if (page.getContentType().contains("image"))
                outputStream.write(("Content-Type: " + page.getContentType() + "\n").getBytes(encoding));
            else
                outputStream.write(("Content-Type: " + page.getContentType() + " charset=" + encoding.displayName() + "\n").getBytes(encoding));
            outputStream.write(("Vary: User-Agent\n").getBytes(encoding));
            outputStream.write(("Server: Impact-HTTP/" + Properties.version + " Java/" + System.getProperty("java.version") + "\n").getBytes(encoding));
            outputStream.write(("Date: " + new Date() + "\n").getBytes(encoding));
            outputStream.write(("Content-Length: " + ArrayHelper.measureByteContentLength(page.getCorrespondentContent()) + "\n\n").getBytes(encoding));

            for (byte[] forLine : page.getCorrespondentContent()) {
                /*
                  now in a for loop we write all the content
                 */
                outputStream.write((new String(forLine) + "\n").getBytes(encoding));
            }

            return 0;
        } catch (IOException e) {
            return 1;
        }
    }
}
