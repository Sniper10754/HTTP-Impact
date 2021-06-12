package org.impact.route;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class ServerRoute {
    private static final HashMap<String, Route> routeLists = new HashMap<>();
    private static boolean useStartScreen = true;

    public static void useStartScreen(boolean condition) {
        useStartScreen = condition;
    }

    public static boolean getUseStartScreen() {
        return useStartScreen;
    }

    public static void setRoute(Route route) {
        /*
          we setup some exceptions for the cases
         */

        InputMismatchException ex = new InputMismatchException("Path Doesn't start with \"/\"");
        InputMismatchException dex = new InputMismatchException("Duplicated Route inserted");

        if (route.getRequestedURL().startsWith("/404")) {
            /*
              we OverWrite the 404 Default page
             */
            routeLists.remove("/404");
            routeLists.put(route.getRequestedURL(), route);
        } else {
            routeLists.remove(route.getRequestedURL());
            for (Map.Entry<String, Route> entry : routeLists.entrySet()) {
                String k = entry.getKey();
                Route v = entry.getValue();

                if (route.getRequestedURL().equals(k) | (route.getRequestedURL().equals(k) || route.equals(v)))
                    throw dex;
            }
        }

        if (! route.getRequestedURL().startsWith("/"))
            throw ex;
        routeLists.put(route.getRequestedURL(), route);
    }

    public static Route getRoute(String path) {
        Route eq = null;

        for (Map.Entry<String, Route> entry : routeLists.entrySet()) {
            String k = entry.getKey();
            Route v = entry.getValue();

            if (k.equals(path)) {
                eq = v;
            }
        }

        return eq;
    }

    public static HashMap<String, Route> getRoutes() {
        return routeLists;
    }
}
