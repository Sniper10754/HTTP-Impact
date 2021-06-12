package org.impact.http.handler;

import org.impact.helpers.SocketHelper;
import org.impact.helpers.http.HTTPHelper;
import org.impact.helpers.http.HTTPRequestParser;
import org.impact.http.HTTPImage;
import org.impact.http.HTTPPage;
import org.impact.http.HTTPServer;
import org.impact.route.Route;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class HTTPHandler {

    public abstract void afterServingClient(HTTPServer server, Route r, Socket s);

    protected abstract void beforeServingClient(HTTPServer server, Route r, Socket s);

    public void handleClient(HTTPServer server, Socket s, HashMap<String, Route> contentHashMap) {
        InputStream inputStream;
        OutputStream outputStream;

        try {
            inputStream = s.getInputStream();
            outputStream = s.getOutputStream();


            Integer responseCode = null;
            String request = "";
            String infoLine;
            String requestedUrl;
            String rawRequestedUrl = "";
            ArrayList<byte[]> contentOfNotFoundPage = new ArrayList<>();
            contentOfNotFoundPage.add("404".getBytes(StandardCharsets.UTF_8));

            /*
              If you ask why theres a class that contains variables, these variables
              are referenced during the Indexing part with @param contentHashMap
              and a forEach() requires final variables, by moving the variables into a container
              you resolve the problem.
             */

            class varContainer {
                HTTPPage NotFoundPage;
                HTTPPage correspondentPage;
                Route currentRoute;
            }

            varContainer container = new varContainer();

            container.NotFoundPage = new HTTPPage(contentOfNotFoundPage, "text");

            /*
              Here's the indexing part
              see JavaDoc down below for more info.
             */

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                while ((infoLine = reader.readLine()) != null) {
                    /*
                      when the browsers finish to send data, they send empty
                      bytes to indicate this.
                     */
                    if (infoLine.trim().isEmpty())
                        break;
                    /*
                      if the sent line starts with GET, we take it and parse to get requested URL.
                     */
                    if (infoLine.startsWith("GET"))
                        rawRequestedUrl = infoLine;
                    else
                        request = infoLine;
                }

                /*
                  now we parse the request.
                 */

                requestedUrl = HTTPRequestParser.parseGETRequest(rawRequestedUrl);
                String finalRequestedUrl = requestedUrl.trim();

                contentHashMap.forEach((k, v) -> {
                    if (k.contains("404"))
                        container.NotFoundPage = new HTTPPage(v.getCorrespondentArr(), "html/text");

                    if (k.equals(finalRequestedUrl)) {
                        container.currentRoute = contentHashMap.get(finalRequestedUrl);
                    }
                });

                /*
                  now we are indexing the contentHashMap for searching the 404 Page
                  and the correspondent page (That will be sent if the url is correspondent to Route.correspondentURL)
                 */
                int status = -1;
                if (! rawRequestedUrl.equals("") || (request.equals(""))) {
                    try {
                        container.correspondentPage = container.currentRoute.getPage();
                        container.correspondentPage.setCorrespondentContent(container.currentRoute.getCorrespondentArr());
                        if (! container.correspondentPage.getCorrespondentContent().isEmpty()) {
                            responseCode = 200;
                        }
                    } catch (NullPointerException e) {
                        container.correspondentPage = container.NotFoundPage;
                        responseCode = 404;
                    }

                    if (responseCode != null) {
                        switch (responseCode) {
                            case 200:
                                try {
                                    this.beforeServingClient(server, container.currentRoute, s);
                                } catch (Throwable e) {
                                    System.err.println("Handler failed, after serving client an exception was thrown:\n" + e);
                                }
                                System.out.println("\"" + rawRequestedUrl + "\"" + " Has been requested by: " + SocketHelper.getRemoteIP(s).replace("/", "").replace("0:0:0:0:0:0:0:1", "127.0.0.1") + ":" + s.getLocalPort());

                                if (container.correspondentPage instanceof HTTPImage)
                                    status = HTTPHelper.sendGETResponse(responseCode, container.correspondentPage, s, Charset.defaultCharset());
                                else
                                    status = HTTPHelper.sendGETResponse(responseCode, container.correspondentPage, s, StandardCharsets.UTF_8);
                                break;
                            case 404:
                                System.err.println("\"" + rawRequestedUrl + "\"" + " Has been requested by: " + SocketHelper.getRemoteIP(s).replace("/", "").replace("0:0:0:0:0:0:0:1", "127.0.0.1") + ":" + s.getLocalPort());
                                try {
                                    this.beforeServingClient(server, container.currentRoute, s);
                                } catch (Throwable e) {
                                    System.err.println("Handler failed, after serving client an exception was thrown:\n" + e);
                                }

                                if (container.correspondentPage instanceof HTTPImage)
                                    status = HTTPHelper.sendGETResponse(responseCode, container.correspondentPage, s, Charset.forName(System.getProperty("file.encoding")));
                                else
                                    status = HTTPHelper.sendGETResponse(responseCode, container.correspondentPage, s, StandardCharsets.UTF_8);
                                break;
                            default:
                                break;
                        }
                    } else //noinspection StatementWithEmptyBody
                        if (request.startsWith("POST")) {
                            // TODO Implement POST Handler in class HTTPHelper.
                        }
                }


                /*
                  now we flush the OutputStream so it makes less system calls
                 */

                outputStream.flush();

                /*
                  If the response is 404, it will be passed null as Route, so avoid
                  making errors and use an if to check if the Route is null

                     if (route != null) {...}
                 */

                try {
                    this.afterServingClient(server, container.currentRoute, s);
                } catch (Throwable e) {
                    System.err.println("Handler failed, after serving client an exception was thrown:\n" + e);
                }

                s.close();
            } catch (IOException e) {
                System.err.println("An error occurred: " + e.getMessage() + ",\nServer is still operating");
            }
        } catch (IOException e) {
            System.err.println("Error has occurred during the handling: \n" + e.getMessage());
        }
    }
}
