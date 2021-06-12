package org.impact.helpers.http;

public class HTTPRequestParser {
    private HTTPRequestParser() { }

    public static String parseGETRequest(String str) {

        return str.replace("GET ", "")
                .replace("HTTP/", "")
                .replace("1.1", "")
                .replace("1.0", "")
                .replace(":", "");
    }

    public static String parseURL(String url) {
        String finalUrl;
        url = url.replace("https://", "");
        url = url.replace("http://", "");
        finalUrl = url.replace("/", " ");

        return finalUrl;
    }
}
