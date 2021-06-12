package org.configMaster;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

public class ConfigParser {

    public static Configuration getConfigFromFile(File f) {
        return new Configuration(parseFile(f));
    }

    public static Configuration getConfigFromString(String str) {
        return new Configuration(parseString(str));
    }

    public static HashMap<String, String> parseString(String str) {
        HashMap<String, String> keyValues = new HashMap<>();
        String[] x = str.split(" ");

        for (String z : x) {
            String[] kv = z.split("=");

            for (int i = 0; i < Arrays.stream(kv).count(); i++) {
                String k = kv[0];
                if (k.equals(""))
                    k = null;
                String v;
                try {
                    v = kv[1];
                } catch (ArrayIndexOutOfBoundsException e) {
                    v = null;
                }
                keyValues.put(k, v);
            }
        }
        return keyValues;
    }

    public static HashMap<String, String> parseFile(File file) {

        HashMap<String, String> keyValues = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                HashMap<String, String> hashMap = parseString(line);
                hashMap.forEach((k, v) -> keyValues.put(k, v));
            }
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
        return keyValues;
    }
}
