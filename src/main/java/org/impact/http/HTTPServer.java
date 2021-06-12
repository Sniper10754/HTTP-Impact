package org.impact.http;

import org.configMaster.Configuration;
import org.impact.config.ServerConfiguration;
import org.impact.http.handler.HTTPHandler;
import org.impact.http.handler.HTTPServerDriver;
import org.impact.http.handler.HandlerThread;
import org.impact.route.Route;
import org.impact.route.ServerRoute;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class HTTPServer extends Thread implements HTTPServerDriver {

    private final ServerSocket ss;
    private HTTPHandler executor;
    private static final Configuration config = ServerConfiguration.getConfig();

    public HTTPServer(int port) throws IOException {
        this(port, null);
    }

    public HTTPServer(int port, HTTPHandler executor) throws IOException {
        if (executor == null) {
            this.executor = new HTTPHandler() {
                @Override
                public void afterServingClient(HTTPServer server, Route r, Socket s) { }

                @Override
                public void beforeServingClient(HTTPServer server, Route r, Socket s) { }
            };
        } else
            this.executor = executor;

        try {
            ss = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("An error occurred initializing HTTPServer:\n" + e);
            throw e;
        }
    }

    public HTTPServer(HTTPHandler executor) throws IOException {
        this(80, executor);
    }

    public HTTPServer() throws IOException {
        this(80, null);
    }

    @Override
    public void run() {

        while (this.isAlive()) {
            Socket s = null;

            while (s == null) {
                try {
                    Thread.sleep(200);
                    s = ss.accept();
                } catch (SocketException e) {
                    System.out.println("Client connection broke. " + e.getLocalizedMessage());
                } catch (IOException e) {
                    System.out.println("Something broke: " + e.getMessage());
                } catch (InterruptedException ignored) { }
            }
            new HandlerThread(this, s, ServerRoute.getRoutes(), this.executor).start();
        }
    }


    public HTTPHandler getExecutor() {
        return executor;
    }

    public void setExecutor(HTTPHandler executor) {
        this.executor = executor;
    }

    @Override
    public void stopServer() {
        this.interrupt();
    }

    @Override
    public int startServer() {
        try {
            this.start();
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

}
