package org.impact.helpers

import java.util.ArrayList

object ArrayHelper {
    @Deprecated("")
    fun measureContentLength(arr: ArrayList<String>): Int {
        var counter = 0
        for (`var` in arr) {
            counter = counter + `var`.length + 1
        }
        return counter
    }

    @JvmStatic
    fun measureByteContentLength(arr: ArrayList<ByteArray>): Int {
        var counter = 0
        for (`var` in arr) {
            counter = counter + `var`.size + 1
        }
        return counter
    }
}