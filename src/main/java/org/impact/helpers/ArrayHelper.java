package org.impact.helpers;

import java.util.ArrayList;

public class ArrayHelper {

    @Deprecated
    public static int measureContentLength(ArrayList<String> arr) {
        int counter = 0;
        for (String var : arr) {
            counter = counter + var.length() + 1;
        }
        return counter;
    }

    public static int measureByteContentLength(ArrayList<byte[]> arr) {
        int counter = 0;
        for (byte[] var : arr) {
            counter = counter + var.length + 1;
        }
        return counter;
    }
}
