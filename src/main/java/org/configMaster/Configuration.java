package org.configMaster;

import java.util.HashMap;

public class Configuration {
    private final HashMap<String, String> keyValues;

    public Configuration(HashMap<String, String> keyValues) {

        this.keyValues = keyValues;
    }

    public HashMap<String, String> getKeyValues() {
        return keyValues;
    }
}
