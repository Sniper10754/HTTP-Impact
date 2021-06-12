package org.impact.config;

import org.configMaster.ConfigParser;
import org.configMaster.Configuration;


public class ServerConfiguration {
    private static Configuration config = ConfigParser.getConfigFromString("debug=false");

    public static Configuration getConfig() {
        return config;
    }

    public static void setConfig(Configuration config) {
        ServerConfiguration.config = config;
    }
}
