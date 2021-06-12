import org.configMaster.ConfigParser;
import org.impact.config.ServerConfiguration;
import org.impact.helpers.ArrayListHelper;
import org.impact.http.HTTPImage;
import org.impact.http.HTTPPage;
import org.impact.http.HTTPServer;
import org.impact.http.handler.HTTPHandler;
import org.impact.route.Route;
import org.impact.route.ServerRoute;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class Main {

    static HTTPServer server;

    public static void main(String[] args) {
        try {
            server = new HTTPServer(80);
            server.setExecutor(new HTTPHandler() {
                @Override
                public void afterServingClient(HTTPServer server, Route r, Socket s) { }

                @Override
                protected void beforeServingClient(HTTPServer server, Route r, Socket s) { }
            });
        } catch (IOException e) {
            assert server == null;
        }

        String path = "./.impact";
        ServerConfiguration.setConfig(ConfigParser.getConfigFromFile(new File(path)));

        System.out.println(ServerConfiguration.getConfig().getKeyValues());

        ServerRoute.setRoute(new Route("/", new HTTPPage(ArrayListHelper.readFromFile(".\\src\\main\\resources\\ImpactWorkSpace\\index.html"), "html")));

        ServerRoute.setRoute(new Route("/script.js", new HTTPPage(ArrayListHelper.readFromFile(".\\src\\main\\resources\\ImpactWorkSpace\\script.js"), "js")));

        ServerRoute.setRoute(new Route("/image.png", new HTTPImage(ArrayListHelper.readFromImage(".\\src\\main\\resources\\ImpactWorkSpace\\image.png"), "image")));

        server.start();
    }
}
