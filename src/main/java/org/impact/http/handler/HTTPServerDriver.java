package org.impact.http.handler;

public interface HTTPServerDriver {
    void stopServer();

    int startServer();

    default int restart() {
        stopServer();
        return startServer();
    }
}
