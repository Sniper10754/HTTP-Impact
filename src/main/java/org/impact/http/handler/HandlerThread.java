package org.impact.http.handler;

import org.impact.http.HTTPServer;
import org.impact.route.Route;
import java.net.Socket;
import java.util.HashMap;

public class HandlerThread extends Thread {

    private final Socket s;
    private final HashMap<String, Route> contentList;
    private HTTPHandler executor;
    private final HTTPServer server;

    public HandlerThread(HTTPServer server, Socket s, HashMap<String, Route> contentList, HTTPHandler executor) {
        this.server = server;
        this.s = s;
        this.contentList = contentList;
        this.setExecutor(executor);
    }

    @Override
    public void run() {
        this.executor.handleClient(this.server, s, contentList);
    } 

    public HTTPHandler getExecutor() {
        return executor;
    }

    public void setExecutor(HTTPHandler executor) {
        this.executor = executor;
    }

}
