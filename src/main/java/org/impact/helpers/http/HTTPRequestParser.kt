package org.impact.helpers.http

object HTTPRequestParser {
    @JvmStatic
    fun parseGETRequest(str: String): String {
        return str.replace("GET ", "")
            .replace("HTTP/", "")
            .replace("1.1", "")
            .replace("1.0", "")
            .replace(":", "")
    }

    @JvmStatic
    fun parseURL(url: String): String {
        var url = url
        val finalUrl: String
        url = url.replace("https://", "")
        url = url.replace("http://", "")
        finalUrl = url.replace("/", " ")
        return finalUrl
    }
}