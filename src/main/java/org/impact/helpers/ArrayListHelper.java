package org.impact.helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ArrayListHelper {

    public static ArrayList<byte[]> readFromImage(String filepath) {
        return readFromFile(filepath, StandardCharsets.US_ASCII);
    }


    public static ArrayList<byte[]> readFromFile(String filepath, Charset charset) {
        try (FileInputStream _reader = new FileInputStream(filepath)) {
            InputStreamReader reader = new InputStreamReader(_reader, charset);
            ArrayList<byte[]> bytes = new ArrayList<>();
            ArrayList<Byte> readedBytes = new ArrayList<>();

            byte currentByte;
            while ((currentByte = (byte) reader.read()) != -1)
                readedBytes.add(currentByte);


            String[] strings;
            Byte[] originalReadedBytes = readedBytes.toArray(new Byte[0]);
            int j=0;
            byte[] finalBytes = new byte[originalReadedBytes.length];

            for(Byte b: originalReadedBytes)
                finalBytes[j++] = b;

            strings = new String(finalBytes).split("\n");

            for (String str : strings) {
                bytes.add(str.getBytes(StandardCharsets.UTF_8));
            }

            return bytes;
        } catch (IOException e) {
            return null;
        }
    }
    public static ArrayList<byte[]> readFromFile(String filepath) {
        try (FileInputStream _reader = new FileInputStream(filepath)) {
            InputStreamReader reader = new InputStreamReader(_reader);
            ArrayList<byte[]> bytes = new ArrayList<>();
            ArrayList<Byte> readedBytes = new ArrayList<>();

            byte currentByte;
            while ((currentByte = (byte) reader.read()) != -1)
                readedBytes.add(currentByte);


            String[] strings;
            Byte[] originalReadedBytes = readedBytes.toArray(new Byte[0]);
            int j=0;
            byte[] finalBytes = new byte[originalReadedBytes.length];

            for(Byte b: originalReadedBytes)
                finalBytes[j++] = b;

            strings = new String(finalBytes).split("\n");

            for (String str : strings) {
                bytes.add(str.getBytes(StandardCharsets.UTF_8));
            }

            return bytes;
        } catch (IOException e) {
            return null;
        }
    }
}
